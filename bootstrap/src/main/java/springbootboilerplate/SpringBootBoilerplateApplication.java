package springbootboilerplate;

import cn.printf.springbootboilerplate.DomainBaseClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackageClasses = {
        SpringBootBoilerplateApplication.class,
        DomainBaseClass.class
})
@EntityScan("cn/printf/springbootboilerplate/usercontext/domain")
@EnableJpaRepositories(basePackages = "cn/printf/springbootboilerplate/usercontext/domain")
@EnableAsync
@EnableTransactionManagement
public class SpringBootBoilerplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBoilerplateApplication.class, args);
    }
}
