package springbootboilerplate.modules.frontend.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootboilerplate.modules.admin.application.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/frontend/home")
public class HomeController {

    private UserService userService;

    @GetMapping
    public String home() {
        return "this is frontend home";
    }
}
