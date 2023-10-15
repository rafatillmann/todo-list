package br.com.java.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.java.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private ITaskRepository taskRepository;

	@PostMapping("/")
	public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
		taskModel.setIdUser((UUID) request.getAttribute("idUser"));
		var task = this.taskRepository.save(taskModel);

		var currentDate = LocalDateTime.now();
		if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date / end date must be after the current date");
		}
		if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End date must be after the start date");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(task);
	}

	@GetMapping("/")
	public ResponseEntity list(HttpServletRequest request){
		var idUser = (UUID) request.getAttribute("idUser");
		var tasks = this.taskRepository.findByIdUser(idUser);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(tasks);
	}

	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request){
		var previousTask = this.taskRepository.findById(id).orElseThrow();
		Utils.copyNonNullProperties(taskModel, previousTask);

		var task = this.taskRepository.save(previousTask);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(task);
	}
}

