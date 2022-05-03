package ntut.csie.ssl.ezkanbanaxon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ntut.csie.ssl.ezkanbanaxon.command.CreateWorkflowCommand;
import ntut.csie.ssl.ezkanbanaxon.event.WorkflowCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Getter
@Aggregate
@NoArgsConstructor
public class Workflow {
    @AggregateIdentifier
    private String id;

    private String name;
    private String boardId;

    @CommandHandler
    public Workflow(CreateWorkflowCommand command) {
        apply(new WorkflowCreatedEvent(command.getWorkflowId(), command.getName(), command.getBoardId()));
    }

    @EventSourcingHandler
    public void on(WorkflowCreatedEvent event) {
        this.id = event.getWorkflowId();
        this.name = event.getName();
        this.boardId = event.getBoardId();
    }
}
