package learnbyteaching.todos.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import learnbyteaching.todos.repositories.dao.TodoRepository;
import learnbyteaching.todos.repositories.vo.TodoItem;

//	CORS 설정: 원칙적으로는 허용할 도메인을  정확하게 명시해야 한다.
@CrossOrigin(origins="*",	//	모든 호스트로부터의 오리진 허가 (프로젝트할 때 이 방법 x)
	methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
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
	
	//	keyword 파라미터 전달 받아서 title을 대상으로 검색 -> 목록
	@GetMapping("/search")
	public ResponseEntity<List<TodoItem>> searchTodos(@RequestParam("keyword") String keyword) {
		List<TodoItem> foundTodos = todoRepository.findAll().stream()
					.filter(todo -> todo.getTitle().toLowerCase()
					.contains(keyword.toLowerCase()))
					.collect(Collectors.toList());
		
		return ResponseEntity.ok(foundTodos);
	}
	
	//	새로운 TodoItem 생성
	@PostMapping
	public  ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem todoItem) {
		TodoItem savedTodo = todoRepository.save(todoItem);
		/*
		return ResponseEntity.status(HttpStatus.CREATED)
					.header("Location", "/api/todos/" + savedTodo.getId()).body(savedTodo);
		*/
		
		URI location = URI.create("/api/todos/" + savedTodo.getId());
		return ResponseEntity.created(location).body(savedTodo);
	}
	
	//	기존 TodoItem 수정
	@PutMapping("/{id}")	//	/api/todos/{id}
	public ResponseEntity<TodoItem> updateTodo(@PathVariable Long id, @RequestBody TodoItem updatedTodo) {
		return todoRepository.findById(id)
			.map(todo -> {
				todo.setTitle(updatedTodo.getTitle());
				todo.setCompleted(updatedTodo.isCompleted());	//	lombok: boolean의 경우 get -> is
				TodoItem savedTodo = todoRepository.save(todo);	
				return ResponseEntity.ok(savedTodo);
			})
			.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	//	기존 TodoItem 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
		Optional<TodoItem> existingTodo = todoRepository.findById(id);
		
		if (!existingTodo.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		todoRepository.deleteById(id);
		return ResponseEntity.ok().<Void>build();
	}
	
}
