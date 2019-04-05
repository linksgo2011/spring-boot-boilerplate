package cn.printf.springbootboilerplate.rest;

import cn.printf.springbootboilerplate.rest.responses.HelloResponse;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public HelloResponse hello() {
        HelloResponse helloResponse = new HelloResponse("Hello!");
        helloResponse.add(
                ControllerLinkBuilder.linkTo(
                        methodOn(HelloController.class).hello()
                ).withRel("_self")
        );
        helloResponse.embedResource("additionalInfo","first endpoint with hateoas");
        return helloResponse;
    }
}
