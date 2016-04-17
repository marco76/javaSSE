package ch.javaee.example.sse.resource;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import ch.javaee.example.sse.helper.ParticipantListHelper;
import ch.javaee.example.sse.model.Runner;

@Path("hello_sse")
public class SseEventOutput {

	static Logger logger = Logger.getLogger("HelloSse.class");

	// EventOutput is created when the client open this resource
	// the method return the object EventOutput to the client (http)
	// until when the EventOutput is not closed it can send data to the client using write()
	
	EventOutput eventOutput = new EventOutput();
	
	@Path("competition")
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput getServerSentEvents() {

		final EventOutput eventOutput = new EventOutput();

		// the timer determines when a new runner arrives at the end of the
		// competition
		Random individualTimer = new Random();

		// List of the participants and order
		List<Runner> runnerList = ParticipantListHelper.getParticipantsList();

		/**
		 * This thread simulates a running competition.
		 * Each time a participant finish the race a notification is sent to the client using http. 
		 */
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				// time from the beginning of the course
				int timer = 0;
				// final standing of the next runner
				int position = 1;

				try {
					
					for (Runner runner : runnerList) {
						
						// each runner arrives with a delay of 1 to 3 seconds from the previous runner
						int runnerDelay = 1000 + individualTimer.nextInt(2000);
						timer = timer + runnerDelay;
						
						try {
							// simulation of the time passing between two runners
							Thread.sleep(runnerDelay);
						
						} catch (InterruptedException e) {
							logger.log(Level.SEVERE, "error during the thread sleep");
						}
						
						// the total time is recorded in the runner class
						runner.setResultTime(String.valueOf(timer));
						

						// prepare the OutboundEvent to send to the client browser
						OutboundEvent event = buildOutboundEvent(runner);
						
						logger.log(Level.INFO, "writing the result for the participant in position: " + position);

						// the event is sent to the client
						// the EventOutput is already returned to the client but until when is not closed it can send messages to the client
						eventOutput.write(event);
						
						// waiting for the next runner
						position++;
					}
				} catch (IOException e) {
					logger.log(Level.SEVERE, "Error writing the event output.", e);

				} finally {
					try {

						logger.log(Level.INFO, "closing the event output, the client is disconnected");
						// this closes the communication with the client
						eventOutput.close();
						
					} catch (IOException ioe) {
						logger.log(Level.SEVERE, "Error closing the event output.", ioe);
					}
				}
			}
		}).start();
		
		logger.log(Level.INFO, "return the eventOutput object to the client");
		// the eventOutput is returned before the thread finish to execute.
		// A channel between the client and the server is created and closed only when eventOutput is closed.
		return eventOutput;
	}
	
	private OutboundEvent buildOutboundEvent(final Runner runner){
		
		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();

		eventBuilder.name("runner at the finish ... ");
		
		// the runner object will be converted to JSON
		eventBuilder.data(Runner.class, runner);
		eventBuilder.mediaType(MediaType.APPLICATION_JSON_TYPE);
	    
		return eventBuilder.build();

	}

}
