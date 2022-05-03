package ntut.csie.ssl.ezkanbanaxon.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardCreatedEvent {
    private final String boardId;
    private final String teamId;
    private final String name;
}
