package ntut.csie.ssl.ezkanbanaxon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWorkflowInput {
    private String workflowId;
    private String workflowName;
    private String boardId;
    private String userId;
}
