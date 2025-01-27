package learnbyteaching.shoplist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learnbyteaching.shoplist.repositories.vo.ShopItem;
import learnbyteaching.shoplist.service.ShoplistService;

@RestController
@RequestMapping("/api/shoplist")
public class ShoplistApiController {
	@Autowired
	private ShoplistService shoplistService;
	
	//	GET: /api/shoplist -> 목록 불러오기
	@GetMapping
	public ResponseEntity<List<ShopItem>> getAllItems() {
		List<ShopItem> items = shoplistService.selectAllItems();
		return ResponseEntity.ok(items);
	}
	
	//	POST: /api/shoplist -> 새 쇼핑 항목 추가
	@PostMapping
	public ResponseEntity<ShopItem> createItem(@RequestBody ShopItem item) {
		ShopItem savedItem = shoplistService.insertItem(item);
		return ResponseEntity.ok(savedItem);	//	ResponseEntity.created로 하는 것이 의미상 더 나을 수도 있다.
	}
	
	//	PUT: /api/shoplist/{id} -> 쇼핑 항목 수정
	@PutMapping("/{id}")
	public ResponseEntity<ShopItem> updateItem(@RequestBody ShopItem item, @PathVariable("id") Integer id) {
		item.setId(id);
		ShopItem updatedItem = shoplistService.updateItem(item);
		return ResponseEntity.ok(updatedItem);
	}
	
	//	DELETE: /api/shoplist/{id} -> 쇼핑 항목 삭제
	@DeleteMapping("/{id}")
	//	Body에 실어 보낼 내용이 없음 -> Void
	public ResponseEntity<Void> deleteItem(@PathVariable("id") Integer id) {
		shoplistService.deleteItem(id);
		return ResponseEntity.ok().<Void>build();
	}
}
