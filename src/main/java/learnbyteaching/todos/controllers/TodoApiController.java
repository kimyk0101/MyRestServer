package learnbyteaching.todos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learnbyteaching.todos.repositories.dao.TodoRepository;
import learnbyteaching.todos.repositories.vo.TodoItem;

@RequestMapping("/api/todos")
@RestController	//	@Controller + ResponseBody
public class TodoApiController  {
	//	Repository 연결
	@Autowired
	private TodoRepository todoRepository;
	
	//	객체를 단순히 리턴만 하면 status 200번만 전송
	//	보다 세밀한 응답 정보를 함께 보내고자 한다면 ResponseEntity를 사용
	@GetMapping
//	public List<TodoItem> getAllTodos() {
	public ResponseEntity<List<TodoItem>> getAllTodos() {
		List<TodoItem> todos = todoRepository.findAll();
		return ResponseEntity.ok(todos);
		//	status 200에다가 body에 todos를 실어서 보냄
	}
	
	//	id로 TODO 항목 조회 -> /api/todos/{id}
	@GetMapping("/{id}")
	public ResponseEntity<TodoItem> getTodoById(@PathVariable("id") Long id) {
		Optional<TodoItem> todo = todoRepository.findById(id);
		return todo.map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
