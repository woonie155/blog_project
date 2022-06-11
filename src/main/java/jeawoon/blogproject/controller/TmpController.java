package jeawoon.blogproject.controller;

import jeawoon.blogproject.entity.Tmp;
import jeawoon.blogproject.service.TmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class TmpController {
    private final TmpService tmpService;

    @GetMapping("/abcd")
    public String abc(){
        Tmp tmp = new Tmp();
        tmp.setContent("aksdkfj");
        tmp.setLday(LocalDateTime.now());
        tmp.setTdat(new Timestamp(System.currentTimeMillis()));
        tmpService.tmp1(tmp);
        return "/tmp";
    }


}
