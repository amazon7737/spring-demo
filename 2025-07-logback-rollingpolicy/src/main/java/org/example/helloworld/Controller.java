package org.example.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hello")
public class Controller {

    @GetMapping("/load")
    public String load(@RequestParam(value = "name") String name) {
        log.info("hello[{}]", name);
        return "hello world?";
    }

    @GetMapping("/exception")
    public String exception() {
        throw new IllegalArgumentException("");
    }
}
