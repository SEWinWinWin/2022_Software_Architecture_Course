package ntut.csie.ssl.ezkanbanaxon;

import ntut.csie.ssl.ezkanbanaxon.command.CreateBoardCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;

public class CreateBoardUseCase {
    private final CommandGateway commandGateway;

    public CreateBoardUseCase(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        CreateBoardCommand command = new CreateBoardCommand(input.getBoardId(), input.getTeamId(), input.getBoardName());
        String result = commandGateway.sendAndWait(command);

        output.setBoardId(result);
    }
}
