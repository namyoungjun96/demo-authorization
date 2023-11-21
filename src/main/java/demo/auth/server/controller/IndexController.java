package demo.auth.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class IndexController {
    @GetMapping
    public String index() {
        return "good!";
    }

    @GetMapping("/test")
    public String test() {
        return "success!";
    }
}
