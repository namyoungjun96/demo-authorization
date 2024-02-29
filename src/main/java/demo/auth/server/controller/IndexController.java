package demo.auth.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IndexController {
    @GetMapping("/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 사용자의 이름을 출력
        log.debug("현재 사용자 이름: {}", authentication.getName());
        return "good!";
    }

    @GetMapping("/login/oauth2/code/naver")
    public String loginSuccessCallback(@RequestParam("code") String code,
                                       @RequestParam("state") String state) {
        return "success!";
    }

    @GetMapping("/test")
    public String test() {
        return "login success!!!!";
    }
}
