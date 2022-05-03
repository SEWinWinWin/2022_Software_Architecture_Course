package ntut.csie.ssl.ezkanbanaxon.boards_query;

import ntut.csie.ssl.ezkanbanaxon.event.BoardCreatedEvent;
import ntut.csie.ssl.ezkanbanaxon.event.WorkflowCommittedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@ProcessingGroup("tep")
public class BoardEventHandler {

    private final BoardDataRepository boardDataRepository;

    public BoardEventHandler(BoardDataRepository boardDataRepository) {
        this.boardDataRepository = boardDataRepository;
    }

    @EventHandler
    public void on(BoardCreatedEvent event) {
        System.out.println("========== catching boardCreatedEvent and creating boardData ========");
        BoardData boardData = new BoardData();

        boardData.setBoardId(event.getBoardId());
        boardData.setTeamId(event.getTeamId());
        boardData.setName(event.getName());
        boardData.setWorkflowList(new HashSet<>());

        this.boardDataRepository.save(boardData);
    }

    @EventHandler
    public void on(WorkflowCommittedEvent event) {
        System.out.println("========== catching workflowCommittedEvent ========");
        Optional<BoardData> boardDataOptional = this.boardDataRepository.getById(event.getBoardId());
        if (boardDataOptional.isPresent()) {
            BoardData boardData = boardDataOptional.get();
            Set<String> workflowSet = boardData.getWorkflowList();
            workflowSet.add(event.getWorkflowId());
            this.boardDataRepository.save(boardData);
        }
    }
}
