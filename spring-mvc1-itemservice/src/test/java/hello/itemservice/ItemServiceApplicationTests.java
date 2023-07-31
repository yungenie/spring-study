package hello.itemservice;

import hello.itemservice.domain.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemServiceApplicationTests {
	
	Item item;
	
	@Test
	void contextLoads() {
	}

	@Test
	void itemDomainTest() {
		item = new Item("블로그 원고료", 1000, 1);
		System.out.println("item = " + item);

		item = new Item("블로그 애드포스트", null, null);
		System.out.println("item = " + item);
		
	}
}
