package ntut.csie.ssl.ezkanbanaxon.boards_query;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BoardDataRepository {
    private final Map<String, BoardData> boardDataList;

    public BoardDataRepository() {
        this.boardDataList = new HashMap<>();
    }

    public void save(BoardData boardData) {
        boardDataList.put(boardData.getBoardId(), boardData);
        System.out.println(boardDataList.get(boardData.getBoardId()).getWorkflowList());
    }

    public Optional<BoardData> getById(String id) {
        return Optional.ofNullable(this.boardDataList.get(id));
    }
}
