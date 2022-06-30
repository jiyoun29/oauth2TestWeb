package oauth2Web.Entity;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;

@Entity @Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk주기
    private int mno; //멤버의 번호를 자동으로 받음
    private String mid; //멤버 아이디를 받음
    private String mpw; //멤버 패스워드를 받음
    private Role role; //enum 클래스 받아옴????

}
