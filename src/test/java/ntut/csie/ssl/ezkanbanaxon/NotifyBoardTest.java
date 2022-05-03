package ntut.csie.ssl.ezkanbanaxon;

import ntut.csie.ssl.ezkanbanaxon.boards_query.BoardDataRepository;
import ntut.csie.ssl.ezkanbanaxon.boards_query.BoardEventHandler;
import ntut.csie.ssl.ezkanbanaxon.model.Board;
import ntut.csie.ssl.ezkanbanaxon.model.Workflow;
import ntut.csie.ssl.ezkanbanaxon.workflows_query.WorkflowDataRepository;
import ntut.csie.ssl.ezkanbanaxon.workflows_query.WorkflowEventHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.config.AggregateConfigurer;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.modelling.command.AggregateAnnotationCommandHandler;
import org.axonframework.modelling.command.Repository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.UUID;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class NotifyBoardTest {
    private Configuration configuration;

    private SimpleEventBus eventBus;
    private EmbeddedEventStore eventStore;

    private CommandBus commandBus;
    private CommandGateway commandGateway;

    private Repository<Workflow> workflowRepository;
    private Repository<Board> boardRepository;
    private WorkflowCommandHandler workflowCommandHandler;
    private BoardCommandHandler boardCommandHandler;
    private BoardDataRepository boardDataRepository;
    private BoardEventHandler boardEventHandler;
    private WorkflowDataRepository workflowDataRepository;
    private WorkflowEventHandler workflowEventHandler;
    private NotifyBoard notifyBoard;

    private String boardId;
    private String teamId;
    private String workflowId;

    @Before
    public void setUp() {
        eventBus = SimpleEventBus.builder().build();
        eventStore = EmbeddedEventStore.builder().storageEngine(new InMemoryEventStorageEngine()).build();
        commandBus = SimpleCommandBus.builder().build();
        commandGateway = DefaultCommandGateway.builder().commandBus(commandBus).build();

        workflowRepository = EventSourcingRepository.builder(Workflow.class)
                .eventStore(eventStore)
                .build();
        workflowCommandHandler = new WorkflowCommandHandler(AggregateAnnotationCommandHandler.<Workflow>builder()
                        .aggregateType(Workflow.class)
                        .repository(workflowRepository));
        workflowCommandHandler.subscribe(commandBus);

        boardRepository = EventSourcingRepository.builder(Board.class)
                .eventStore(eventStore)
                .build();
        boardCommandHandler = new BoardCommandHandler(boardRepository);
        boardCommandHandler.subscribe(commandBus);

        boardDataRepository = new BoardDataRepository();
        boardEventHandler = new BoardEventHandler(boardDataRepository);
        workflowDataRepository = new WorkflowDataRepository();
        workflowEventHandler = new WorkflowEventHandler(workflowDataRepository);

        notifyBoard = new NotifyBoard(commandGateway);
        Configuration();

        boardId = UUID.randomUUID().toString();
        teamId = UUID.randomUUID().toString();
        workflowId = UUID.randomUUID().toString();
    }

    @Test
    public void create_a_workflow_commits_itself_to_its_board() throws InterruptedException {
        create_a_board();
        create_a_workflow();

        sleep(3000);
        assertTrue(boardDataRepository.getById(boardId).isPresent());
        assertTrue(workflowDataRepository.getById(workflowId).isPresent());
        assertEquals(1, boardDataRepository.getById(boardId).get().getWorkflowList().size());
    }

    private void create_a_board() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(commandGateway);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();
        input.setBoardId(boardId);
        input.setTeamId(teamId);
        input.setBoardName("BoardName");

        createBoardUseCase.execute(input, output);
    }

    private void create_a_workflow() {
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(commandGateway);
        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();
        input.setWorkflowId(workflowId);
        input.setWorkflowName("workflowName");
        input.setUserId("userId");
        input.setBoardId(boardId);

        createWorkflowUseCase.execute(input, output);
    }

    private void Configuration() {
        configuration = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(AggregateConfigurer.defaultConfiguration(Board.class))
                .configureAggregate(AggregateConfigurer.defaultConfiguration(Workflow.class))
                .configureCommandBus(c -> commandBus)
                .configureEventStore(c -> eventStore)
                .registerEventHandler(c -> notifyBoard)
                .registerEventHandler(c -> boardEventHandler)
                .registerEventHandler(c -> workflowEventHandler)
                .registerCommandHandler(c -> boardCommandHandler)
                .registerCommandHandler(c -> workflowCommandHandler)
                .buildConfiguration();

        configuration.start();
    }
}
