package br.com.java.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;

import br.com.java.todolist.user.UserModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	private UUID idUser;

	@Column(length = 50)
	private String title;
	private String description;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private String priority;

	@CreationTimestamp
	private LocalDateTime createdAt;
}
