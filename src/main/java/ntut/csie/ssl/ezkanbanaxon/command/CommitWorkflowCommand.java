package ntut.csie.ssl.ezkanbanaxon.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class CommitWorkflowCommand {
    @TargetAggregateIdentifier
    private final String boardId;
    private final String workflowId;
    private final int order;
}
