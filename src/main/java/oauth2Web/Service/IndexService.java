package oauth2Web.Service;

import lombok.Builder;
import oauth2Web.Dto.LoginDto;
import oauth2Web.Dto.MemberDto;
import oauth2Web.Entity.MemberEntity;
import oauth2Web.Entity.MemberRepository;
import oauth2Web.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IndexService implements UserDetailsService { //config에 사용하기 위한 구현
                    //1.UserDetailsService : 커스텀 로그인
    @Autowired
    private MemberRepository memberRepository; //멤버repository를 호출한다.

    //1. 회원가입
    public void signup(MemberDto memberDto){
//        System.out.println(memberDto.toString());
        memberRepository.save(memberDto.toentity());
    }

    //2. 로그인
    //패스워드 검증 x //구현(alt)
    @Override
    public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {
        //함수 loadUserByUsername에서 mid로 db를 조회하고, 찾지 못하였을 시 UsernameNotFoundException을 throws 한다.

        MemberEntity memberEntity = memberRepository.findBymid(mid).get(); //리포지토리를 통한 검증

        List<GrantedAuthority> authorities = new ArrayList<>(); //GrantedAuthority 사용권한
            //권한 부여를 리스트에 담아 확인?한다.

        authorities.add(new SimpleGrantedAuthority(memberEntity.getRole().getKey())); //권한 저장이 가능 ()안에 입력

        LoginDto loginDto = LoginDto.builder() //빌더를 통해 로그인 dto의 내용물을 빼내어 온다.
                .mid(memberEntity.getMid())
                .mpw(memberEntity.getMpw())
                .authorities(Collections.unmodifiableSet(new LinkedHashSet<>(authorities)))
                .build();

        return loginDto; // 로그인dto로 리턴한다.
    }
}
