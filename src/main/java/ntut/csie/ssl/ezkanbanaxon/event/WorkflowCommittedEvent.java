package ntut.csie.ssl.ezkanbanaxon.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkflowCommittedEvent {
    private final String workflowId;
    private final String boardId;
    private final int order;
}
