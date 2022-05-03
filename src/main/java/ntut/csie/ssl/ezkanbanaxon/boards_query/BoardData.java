package ntut.csie.ssl.ezkanbanaxon.boards_query;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(appliesTo = "board")
public class BoardData {

    @Id
    @Column(name = "board_id")
    private String boardId;

    @Column(name = "team_id", nullable = false)
    private String teamId;

    @Column(name = "board_name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "board_workflows", joinColumns = @JoinColumn(name = "board_id"))
    @JoinColumn(name = "workflowList")
    private Set<String> workflowList;
}
