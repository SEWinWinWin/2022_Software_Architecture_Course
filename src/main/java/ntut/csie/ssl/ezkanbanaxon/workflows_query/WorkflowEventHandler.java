package ntut.csie.ssl.ezkanbanaxon.workflows_query;

import ntut.csie.ssl.ezkanbanaxon.event.WorkflowCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("tep")
public class WorkflowEventHandler {

    private final WorkflowDataRepository workflowDataRepository;

    public WorkflowEventHandler(WorkflowDataRepository workflowDataRepository) {
        this.workflowDataRepository = workflowDataRepository;
    }

    @EventHandler
    public void on(WorkflowCreatedEvent event) {
        System.out.println("========== catching workflowCreatedEvent and creating workflowData ========");
        WorkflowData workflowData = new WorkflowData();

        workflowData.setWorkflowId(event.getWorkflowId());
        workflowData.setBoardId(event.getBoardId());
        workflowData.setName(event.getName());

        this.workflowDataRepository.save(workflowData);
    }
}
