package cn.printf.springbootboilerplate.rest;

import cn.printf.springbootboilerplate.rest.resources.HelloResource;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @RequestMapping("/hello")
    public Resource<Object> hello() {
        return new Resource(new HelloResource("您好！"));
    }
}
