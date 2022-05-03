package ntut.csie.ssl.ezkanbanaxon;

import ntut.csie.ssl.ezkanbanaxon.command.CommitWorkflowCommand;
import ntut.csie.ssl.ezkanbanaxon.event.WorkflowCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("tep")
public class NotifyBoard {
    private final CommandGateway commandGateway;

    public NotifyBoard(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @EventHandler(payloadType = WorkflowCreatedEvent.class)
    public void whenWorkflowCreated(WorkflowCreatedEvent event) {
        System.out.println(" ============= Notify Board When WorkflowCreated ============= ");
        commandGateway.sendAndWait(new CommitWorkflowCommand(event.getBoardId(), event.getWorkflowId(), 0));
    }
}
