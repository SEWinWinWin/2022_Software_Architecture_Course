package com.example.mashu.usecase.createBoard;

import com.example.mashu.event.AxonBoardCreatedEvent;
import com.example.mashu.usecase.repository.AxonBoardRepository;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.SimpleEventBus;
import org.junit.Test;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class AxonCreateBoardUseCaseWithoutEventBusTest {

  class FakeEventListener implements Consumer<List<? extends EventMessage<?>>> {
    public UUID createdBoardId;
    public int counter = 0;

    public void accept(List<? extends EventMessage<?>> e) {
      this.createdBoardId = ((AxonBoardCreatedEvent)(e.get(0).getPayload())).getBoardId();
      this.counter++;
    }
  }

    @Test
    public void createBoard() {
        AxonBoardRepository repo = new AxonBoardRepository();
        EventBus eventBus = SimpleEventBus.builder().build();

        FakeEventListener listener = new FakeEventListener();
        eventBus.subscribe(listener);

        String teamId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        AxonCreateBoardUseCaseInput input = new AxonCreateBoardUseCaseInput(
                teamId,
                "board name",
                userId
        );
        AxonCreateBoardUseCaseOutput output = new AxonCreateBoardUseCaseOutput();

        AxonCreateBoardUseCase useCase = new AxonCreateBoardUseCase(repo, eventBus);
        useCase.execute(input, output);

        assertNotNull(output.getBoardId());
        assertTrue(repo.getBoardById(output.getBoardId()).isPresent());
        assertNotNull(listener.createdBoardId);
        assertEquals(1, listener.counter);
    }
}