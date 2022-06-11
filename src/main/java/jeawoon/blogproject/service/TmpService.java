package jeawoon.blogproject.service;

import jeawoon.blogproject.dto.JoinRequestDto;
import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.Tmp;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.repository.TmpRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TmpService {

    private final TmpRepository tmpRepository;

    @Transactional
    public void tmp1(Tmp tmp){
        tmpRepository.save(tmp);
    }
}
