package ntut.csie.ssl.ezkanbanaxon.workflows_query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkflowDataRepository {
    private final List<WorkflowData> workflowDataList;

    public WorkflowDataRepository() {
        this.workflowDataList = new ArrayList<>();
    }

    public void save(WorkflowData workflowData) {
        workflowDataList.add(workflowData);
    }

    public Optional<WorkflowData> getById(String id) {
        return this.workflowDataList.stream().filter(x -> x.getWorkflowId().equals(id)).findAny();
    }
}
