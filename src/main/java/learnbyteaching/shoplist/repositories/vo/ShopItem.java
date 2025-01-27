package learnbyteaching.shoplist.repositories.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ShopItem {
	Integer id;
	String name;
	//	필드명과 JSON 프로퍼티 명칭이 다를 경우 (isBought로 작성했으나 Getter, Setter는 필드명을 Bought로 인식)
	// @JsonProperty로 JSON 필드명을 고정
	@JsonProperty("isBought")
	boolean isBought;
}
