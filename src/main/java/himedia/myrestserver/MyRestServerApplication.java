package himedia.myrestserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//	방법 2. MapperScan으로 패키지 안에 Mapper 찾도록(여러개일 때는 이 방법이 더 편리함)
@MapperScan(basePackages={"learnbyteaching.shoplist.mappers"})
@ComponentScan(basePackages= {"himedia.myrestserver",
								"learnbyteaching.todos", 
								"learnbyteaching.shoplist"} )
public class MyRestServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRestServerApplication.class, args);
	}

}
