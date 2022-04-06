package jeawoon.blogproject.controller;

import jeawoon.blogproject.entity.MemberDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetAndPostController{

    @GetMapping("/test/hello")
    public String hello(){
        return "<h1>안녕</h1>";
    }

    @GetMapping("/get")
    public String getTest(@RequestParam int id, @RequestParam String username){
        return "get 요청"+id + username;
    }
    @GetMapping("/get2")
    public String getTest2(MemberDto memberDto){
        return "get 요청2: "+memberDto.getUsername();
    }
    @PostMapping("/post") //x-www-form ...
    public String postTest(MemberDto memberDto){
        return "post 실행"+memberDto.toString();
    }
    @PostMapping("/post2") //raw -string
    public String postTest2(@RequestBody String text){
        return "post 실행"+text;
    }
    @PostMapping("/post3") //raw - json
    public String postTest2(@RequestBody MemberDto memberDto){
        return "post 실행"+memberDto.toString();
    }



}
