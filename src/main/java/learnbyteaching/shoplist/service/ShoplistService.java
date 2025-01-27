package learnbyteaching.shoplist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learnbyteaching.shoplist.mappers.ShoplistMapper;
import learnbyteaching.shoplist.repositories.vo.ShopItem;

@Service
public class ShoplistService {
	@Autowired
	private ShoplistMapper shoplistMapper;
	
	//	목록 불러오기
	public List<ShopItem> selectAllItems() {
		List<ShopItem> items = shoplistMapper.selectAllItems();
		return items;
	}
	
	// 새 쇼핑 항목 추가
	public ShopItem insertItem(ShopItem item) {
		shoplistMapper.insertItem(item);
		//	새로 생성된 item의 PK
		Integer id = item.getId();
		return shoplistMapper.selectById(id);
	}
	
	//	쇼핑 항목 수정
	public ShopItem updateItem(ShopItem item) {
		shoplistMapper.updateItem(item);
		return item;
	}
	
	//	쇼핑 항목 삭제
	public int deleteItem(Integer id) {
		return shoplistMapper.deleteItem(id);
	}
}
