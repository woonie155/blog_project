package jeawoon.blogproject.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

    private String address1;  //시, 구, 동
    private String zipcode;   //번지
    private String address2;  //건물이름, 몇동, 몇호

}