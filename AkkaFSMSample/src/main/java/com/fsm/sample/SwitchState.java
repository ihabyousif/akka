package com.fsm.sample;

import akka.persistence.fsm.PersistentFSM;

public enum  SwitchState implements PersistentFSM.FSMState{

	   
	    CREATED("CREATED"),
	    IN_PROGRESS("IN_PROGRESS"),
	    CLOSED("CLOSED");

	    private final String stateIdentifier;

	    SwitchState (String stateIdentifier) {
	        this.stateIdentifier = stateIdentifier;
	    }

	    @Override
	    public String identifier() {
	        return stateIdentifier;
	    }

}
