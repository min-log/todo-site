package com.example.todosite.web;

import com.example.todosite.model.TodoModel;
import com.example.todosite.model.TodoRequest;
import com.example.todosite.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TodoControllerTest.class)
class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    private TodoModel expected;

    @MockBean
    private TodoService todoService;

    @BeforeEach
    void setup(){
        this.expected = new TodoModel();
        this.expected.setId(123L);
        this.expected.setTitle("테스트1");
        this.expected.setOrder(3L);
        this.expected.setCompleted(false);

    }
    @Test
    void create () throws Exception{
        when(this.todoService.add(any(TodoRequest.class)))
                .then((i)->{
                    TodoRequest request = i.getArgument(0, TodoRequest.class);
                    return new TodoModel(this.expected.getId(),
                            this.expected.getTitle(),
                            this.expected.getOrder(),
                            this.expected.getCompleted());
                });
        TodoRequest request = new TodoRequest();
        request.setTitle("Any title");
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);
        this.mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Any title"));


    }


    @Test
    void readOne() {
    }
}