package com.example.todosite.service.repository;

import com.example.todosite.model.TodoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoModel,Long> {

}
