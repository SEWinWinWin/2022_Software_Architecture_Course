package ntut.csie.ssl.ezkanbanaxon;

import ntut.csie.ssl.ezkanbanaxon.command.CreateWorkflowCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;

public class CreateWorkflowUseCase {
    private final CommandGateway commandGateway;

    public CreateWorkflowUseCase(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {
        CreateWorkflowCommand command = new CreateWorkflowCommand(input.getWorkflowId(), input.getWorkflowName(), input.getBoardId());
        String result = commandGateway.sendAndWait(command);

        output.setWorkflowId(result);
    }
}
