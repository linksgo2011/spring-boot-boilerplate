package springbootboilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication()
@EntityScan("cn/printf/springbootboilerplate/domain")
public class SpringBootBoilerplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBoilerplateApplication.class, args);
    }
}
