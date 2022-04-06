package jeawoon.blogproject.entity;

import lombok.Data;

@Data
public class MemberDto {
    private int id;
    private String username;

    ///생성자
    public MemberDto(){};
    public MemberDto(int id, String username) {
        this.id = id;
        this.username = username;
    }
}
