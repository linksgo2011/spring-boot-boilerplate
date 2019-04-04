package cn.printf.springbootboilerplate.config;

import cn.printf.springbootboilerplate.SpringBootBoilerplateApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackageClasses = {SpringBootBoilerplateApplication.class, Jsr310JpaConverters.class})
@EnableTransactionManagement
@EnableJpaAuditing
@ComponentScan(basePackages = {"cn.printf.springbootboilerplate.persistence"})
@Configuration
public class JpaConfiguration {

}
