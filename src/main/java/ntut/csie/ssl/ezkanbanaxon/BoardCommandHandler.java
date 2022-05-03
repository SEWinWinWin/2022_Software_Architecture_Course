package ntut.csie.ssl.ezkanbanaxon;

import ntut.csie.ssl.ezkanbanaxon.command.CommitWorkflowCommand;
import ntut.csie.ssl.ezkanbanaxon.model.Board;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateAnnotationCommandHandler;
import org.axonframework.modelling.command.Repository;

public class BoardCommandHandler extends AggregateAnnotationCommandHandler<Board> {

    private Repository<Board> repository;

    public BoardCommandHandler(Builder<Board> builder) {
        super(builder);
    }

    public BoardCommandHandler(Repository<Board> repository) {
        super(AggregateAnnotationCommandHandler.<Board>builder().aggregateType(Board.class).repository(repository));
        this.repository = repository;
    }

    @CommandHandler
    public void whenWorkflowCommitted(CommitWorkflowCommand command) {
        System.out.println("====== commandHandler receiving command ======");
        repository.load(command.getBoardId())
                .execute(board -> board.whenCommitWorkflowCommand(command));
    }
}
