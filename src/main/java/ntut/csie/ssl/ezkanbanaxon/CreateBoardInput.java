package ntut.csie.ssl.ezkanbanaxon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBoardInput {
    private String boardId;
    private String teamId;
    private String boardName;
}
