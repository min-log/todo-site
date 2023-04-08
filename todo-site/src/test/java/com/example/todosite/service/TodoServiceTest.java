package com.example.todosite.service;


import com.example.todosite.model.TodoModel;
import com.example.todosite.model.TodoRequest;
import com.example.todosite.service.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TodoServiceTest {


    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void add() {
        when(this.todoRepository.save(any(TodoModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        TodoRequest expected = new TodoRequest();
        expected.setTitle("test title");
        TodoModel actual = this.todoService.add(expected);

        assertEquals(expected.getTitle(), actual.getTitle());

    }

    @Test
    void searchById() {
        TodoModel entity = new TodoModel();
        entity.setId(123L);
        entity.setTitle("TITLE");
        entity.setOrder(0L);
        entity.setCompleted(false);
        Optional<TodoModel> optional = Optional.of(entity);

        given(this.todoRepository.findById(anyLong()))
                .willReturn(optional);
        TodoModel actual = this.todoService.searchById(123L);
        TodoModel expected = optional.get();
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getTitle(),expected.getTitle());
        assertEquals(expected.getOrder(),expected.getOrder());
        assertEquals(expected.getCompleted(),expected.getCompleted());


    }

    @Test
    void searchByIdFailed() {
        given(this.todoRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,()->{
           this.todoService.searchById(123L);
        });

    }

}