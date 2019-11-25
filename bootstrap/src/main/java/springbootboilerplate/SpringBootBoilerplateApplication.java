package springbootboilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
@EntityScan("cn/printf/springbootboilerplate/domain")
@EnableAsync
@EnableTransactionManagement
public class SpringBootBoilerplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBoilerplateApplication.class, args);
    }
}
