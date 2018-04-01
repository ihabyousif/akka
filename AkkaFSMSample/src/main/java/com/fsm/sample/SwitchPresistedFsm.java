package com.fsm.sample;

import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.persistence.fsm.AbstractPersistentFSM;

public class SwitchPresistedFsm extends AbstractPersistentFSM<SwitchState, SwitchData, IEvent>{

public static class InprogressCommand {
		
	}
	public static class CloseCommand {
		
	}
	
	public static class InprogressEvent implements IEvent{
		
	}
	public static class ClosedEvent implements IEvent{
		
	}
	
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	private String persistenceId;
	
	public SwitchPresistedFsm(String persistenceId , SwitchData switchData) {
		this.persistenceId = persistenceId;
		  startWith(SwitchState.CREATED, switchData);

          when(SwitchState.CREATED,
              matchEvent(InprogressCommand.class,
                  (event, data) ->
                      goTo(SwitchState.IN_PROGRESS).applying(new InprogressEvent()).replying(SwitchState.IN_PROGRESS) 
              )
          );
          
          when(SwitchState.IN_PROGRESS,
                  matchEvent(InprogressCommand.class,
                      (event, data) ->stay().applying(new InprogressEvent()).replying(SwitchState.IN_PROGRESS) 
                  ).event(CloseCommand.class,
                          (event, data) ->
                              goTo(SwitchState.CLOSED).applying(new ClosedEvent()).replying(SwitchState.CLOSED)
                              )
                                  
              );
          
          when(SwitchState.CLOSED,
                  matchEvent(ClosedEvent.class,
                      (event, data) ->
                          goTo(SwitchState.CLOSED).andThen(exec(_data -> {
                        	       
                          			getContext().stop(self());
                                  })).replying(SwitchState.CLOSED) 
                  )
              );	


	}

	
	@Override
	public SwitchData applyEvent(IEvent domainEvent, SwitchData data) {
		 if (domainEvent instanceof InprogressEvent) {
             return data;
         } else if (domainEvent instanceof ClosedEvent) {
             return data;
         } 
         throw new RuntimeException("Unhandled");
	}

	@Override
	public String persistenceId() {
		return persistenceId;
	}

	@Override
	public Class<IEvent> domainEventClass() {
		return IEvent.class;
	}
	
	public static Props props(String persistenceId , SwitchData switchData) {
		return Props.create(SwitchPresistedFsm.class, () -> new SwitchPresistedFsm(persistenceId, switchData));
	}
	
}
