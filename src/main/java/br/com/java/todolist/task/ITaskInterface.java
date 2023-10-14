package br.com.java.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskInterface extends JpaRepository<TaskModel, UUID> {
}
