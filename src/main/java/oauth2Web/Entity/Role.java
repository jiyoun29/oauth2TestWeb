package oauth2Web.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor // = @RequiredArgsConstructor 둘 중 뭘해도 갠찬
@Getter //호출해서 사용하기 위한 Getter
public enum Role {
    //enum : 열거형. 서로 연관된 상수들의 집합

    MEMBER("ROLE_MEMBER","회원"), //상수 member 정의
    ADMIN("ROLE_ADMIN","관리자"); //상수 admin 정의

    private final String key; //키 값을 고정
    private final String keyword; //키워드 값을 고정

}
