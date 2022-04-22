package jeawoon.blogproject.controller;

import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


@RestController
@RequiredArgsConstructor
public class RestTempController {

    private final UserRepository userRepository;

    @PostMapping("/test/join")
    public String join(User user){ //폼태그 방식과 동일
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "저장컨트롤러 실행완료";
    }

    @GetMapping("/test/user/{id}")
    public User detail(@PathVariable long id){
        return userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 사용자는 없습니다.");
        });
    }
    @GetMapping("test/users")
    public List<User> list(){
        return userRepository.findAll();
    }
    @GetMapping("test/user/page")
    public Page<User> page(@PageableDefault(size=2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }
    @GetMapping("test/user/page2")
    public List<User> page2(@PageableDefault(size=2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> pagingUsers = userRepository.findAll(pageable);
        List<User> users = pagingUsers.getContent();
        return users;
    }

    @Transactional
    @PutMapping("test/user/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User requestUser){ //제이슨 방식과 동일
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정 실패했습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        //userRepository.save(user);
        return user;
    }

    @DeleteMapping("test/delete/{id}")
    public String delete(@PathVariable long id){
        try{
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "삭제 실패: 해당 id DB에 없음";
        }
        return "삭제완료";
    }

}
