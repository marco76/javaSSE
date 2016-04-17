package ch.javaee.example.sse.helper;

import java.util.ArrayList;
import java.util.List;

import ch.javaee.example.sse.model.Runner;

public class ParticipantListHelper {

	/**
	 * Return an unique instance of participants, the list will be modified in
	 * the simulation
	 * 
	 * @return
	 */
	public static List<Runner> getParticipantsList() {
		// List of the participants and order
		List<Runner> runnerList = new ArrayList<Runner>();
		runnerList.add(new Runner("Marco", "1"));
		runnerList.add(new Runner("Mario", "2"));
		runnerList.add(new Runner("Luigi", "3"));
		
		return runnerList;
	}
}
