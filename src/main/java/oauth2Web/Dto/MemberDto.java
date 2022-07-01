package oauth2Web.Dto;

import lombok.*;
import oauth2Web.Entity.MemberEntity;
import oauth2Web.Entity.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;

@Getter  @Setter @ToString @Builder
@AllArgsConstructor  @NoArgsConstructor
public class MemberDto {

    private int mno; //멤버의 번호
    private String mid; //멤버의 아이디
    private String mpw; //멤버의 패스워드

    //dto로 변환되는 toentity
    public MemberEntity toentity(){ //엔티티 호출을 위한 toentity 메소드 정의
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //패스워드 1회 암호화(직접 패스워드 검증 불가능)
        return MemberEntity.builder() //생성자 생성 대신 빌더 패턴으로 일괄 생성
                .mid(this.mid)
                .mpw(encoder.encode(this.mpw))
                .role(Role.MEMBER) //멤버 dto이므로 멤버 호출
                .build();
    }
}
