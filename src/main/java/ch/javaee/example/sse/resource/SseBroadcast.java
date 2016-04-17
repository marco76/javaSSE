package ch.javaee.example.sse.resource;

import java.util.Calendar;

import javax.ejb.Singleton;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

@Path("hello_sse_broadcast")
@Singleton // singleton, one producer and multiple listener
public class SseBroadcast {	
	
	SseBroadcaster broadcaster = new SseBroadcaster();
	
	/**
	 * When a client connect to this resource it opens a communication channel.
	 * @return
	 */
	@Path("listen")
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput listenData() {
		 final EventOutput eventOutput = new EventOutput();
	        this.broadcaster.add(eventOutput);
	       
	        return eventOutput;
	}

	/**
	 * For each call to this method some data is broadcasted to the listeners
	 * @return
	 */
	
	@POST
	@Path("send")
	public String sendData(@FormParam(value = "text") String text) {
		OutboundEvent.Builder builder = new OutboundEvent.Builder();
		builder.comment("optional comment: " + text);
		builder.data(Calendar.getInstance().getTime().toString());
		builder.mediaType(MediaType.APPLICATION_JSON_TYPE);
		
		broadcaster.broadcast(builder.build());
		
		return "date sent";

	}
}
