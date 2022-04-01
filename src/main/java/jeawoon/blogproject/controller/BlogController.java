package jeawoon.blogproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

    @GetMapping("/test/hello")
    public String hello(){
        return "<h1>안녕</h1>";
    }

}
