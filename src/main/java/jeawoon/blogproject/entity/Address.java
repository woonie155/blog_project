package jeawoon.blogproject.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String address1;  //시, 구, 동
    private String zipcode;   //번지
    private String address2;  //건물이름, 몇동, 몇호

    public Address(String address1, String zipcode, String address2) {
        this.address1 = address1;
        this.zipcode = zipcode;
        this.address2 = address2;
    }
}