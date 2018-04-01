package com.fsm.sample;


import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public class AkkaApp {

	public static void main(String [] args) {
		ActorSystem actorSystem = ActorSystem.create("SwitchApp");
		SwitchData switchData = new SwitchData("sample string to test presistance");
		String persistenceId = "switch_1111"; 
		
		ActorRef ref = actorSystem.actorOf(SwitchPresistedFsm.props(persistenceId, switchData),persistenceId);
		
		System.out.println("switch actor created ");
		
		// now shut down and test if the actor presisted
		
		//ActorSelection actorSelection = actorSystem.actorSelection("switch_1111");
		//actorSelection.tell( new SwitchPresistedFsm.InprogressCommand(), null);
		
		
	}
}
