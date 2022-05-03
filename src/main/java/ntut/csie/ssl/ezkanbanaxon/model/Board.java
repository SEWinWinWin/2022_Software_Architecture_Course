package ntut.csie.ssl.ezkanbanaxon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ntut.csie.ssl.ezkanbanaxon.command.CommitWorkflowCommand;
import ntut.csie.ssl.ezkanbanaxon.command.CreateBoardCommand;
import ntut.csie.ssl.ezkanbanaxon.event.BoardCreatedEvent;
import ntut.csie.ssl.ezkanbanaxon.event.WorkflowCommittedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Getter
@Aggregate
@NoArgsConstructor
public class Board {
    @AggregateIdentifier
    private String boardId;
    private String name;

    private List<String> workflowList;

    @CommandHandler
    public Board(CreateBoardCommand command) {
        apply(new BoardCreatedEvent(command.getBoardId(), command.getTeamId(), command.getName()));
    }

    @EventSourcingHandler
    public void onCreate(BoardCreatedEvent event) {
        this.boardId = event.getBoardId();
        this.name = event.getName();
        this.workflowList = new ArrayList<>();
    }

    @EventSourcingHandler
    public void onWorkflowCommitted(WorkflowCommittedEvent event) {
        this.workflowList.add(event.getOrder(), event.getWorkflowId());
    }

    @CommandHandler
    public void whenCommitWorkflowCommand(CommitWorkflowCommand command) {
        apply(new WorkflowCommittedEvent(command.getWorkflowId(), command.getBoardId(), command.getOrder()));
    }
}
