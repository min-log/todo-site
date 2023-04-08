package com.example.todosite.service;

import com.example.todosite.model.TodoModel;
import com.example.todosite.model.TodoRequest;
import com.example.todosite.service.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;


//   1. todo 리스트 목록에 아이템 추가
    public TodoModel add(TodoRequest request){
        TodoModel todoModel = new TodoModel();
        todoModel.setTitle(request.getTitle());
        todoModel.setOrder(request.getOrder());
        todoModel.setCompleted(request.getCompleted());

        TodoModel entity = this.todoRepository.save(todoModel);
        return entity;
    }

//    todo 리스트 목록 중 특정 아이템 조회
    public TodoModel searchById(Long id){
        TodoModel entity = this.todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return entity;
    }
//    todo리스트 전체 목록 조회
    public List<TodoModel> searchAll(){
        List<TodoModel> entities = this.todoRepository.findAll();
        return entities;
    }
//    todo 리스트 목록 중 특정 아이템 수정
    public TodoModel updateById(Long id , TodoRequest request){
        TodoModel todoModel = this.searchById(id);
        if (request.getTitle() != null){
            todoModel.setTitle(request.getTitle());
        }

        if (request.getOrder() != null){
            todoModel.setOrder(request.getOrder());
        }
        if (request.getCompleted() != null){
            todoModel.setCompleted(request.getCompleted());
        }

        return this.todoRepository.save(todoModel);
    }
//    todo 리스트 목록 중 특정 아이템 삭제
    public void deleteById(Long id){
        this.todoRepository.deleteById(id);
    }
//    todo 리스트 전체 목록 삭제
    public void deleteAll(){
        this.todoRepository.deleteAll();
    }
}
