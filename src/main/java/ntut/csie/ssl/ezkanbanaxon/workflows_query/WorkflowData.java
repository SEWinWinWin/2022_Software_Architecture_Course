package ntut.csie.ssl.ezkanbanaxon.workflows_query;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "workflow")
public class WorkflowData {
    @Id
    @Column(name = "workflow_id")
    private String workflowId;

    @Column(name = "workflow_name")
    private String name;

    @Column(name = "board_id")
    private String boardId;
}
