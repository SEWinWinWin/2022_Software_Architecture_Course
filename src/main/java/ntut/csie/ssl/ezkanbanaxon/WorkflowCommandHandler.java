package ntut.csie.ssl.ezkanbanaxon;

import ntut.csie.ssl.ezkanbanaxon.model.Workflow;
import org.axonframework.modelling.command.AggregateAnnotationCommandHandler;
import org.axonframework.modelling.command.Repository;

public class WorkflowCommandHandler extends AggregateAnnotationCommandHandler<Workflow> {

    private Repository<Workflow> repository;

    public WorkflowCommandHandler(Builder<Workflow> builder) {
        super(builder);
    }

    public WorkflowCommandHandler(Repository<Workflow> repository) {
        super(AggregateAnnotationCommandHandler.<Workflow>builder().aggregateType(Workflow.class).repository(repository));
        this.repository = repository;
    }
}
