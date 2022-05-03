package ntut.csie.ssl.ezkanbanaxon.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class CreateWorkflowCommand {
    @TargetAggregateIdentifier
    private final String workflowId;
    private final String name;
    private final String boardId;
}
