package ntut.csie.ssl.ezkanbanaxon.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkflowCreatedEvent {
    private final String workflowId;
    private final String name;
    private final String boardId;
}
