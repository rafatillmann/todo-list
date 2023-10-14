package br.com.java.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.java.todolist.user.UserModel;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private ITaskInterface taskInterface;

	@PostMapping("/")
	public ResponseEntity create(@RequestBody TaskModel taskModel){
		var task = this.taskInterface.save(taskModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(task);
	}
}


