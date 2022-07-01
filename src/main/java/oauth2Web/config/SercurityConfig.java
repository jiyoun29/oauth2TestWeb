package oauth2Web.config;

import oauth2Web.Service.IndexService;
import oauth2Web.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //주입
public class SercurityConfig extends WebSecurityConfigurerAdapter { //상속


    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); //슈퍼를 쓰면 패스워드가 뜨고, 안 쓰면 안 뜬다.

        //어디를 열어주고, 어디를 막을지 결정해야한다.
        //열어주기(커스텀)         //이미 보안이 걸려있는 상태에서 원하는 대로 커스텀
        http.authorizeHttpRequests()
                .antMatchers("/")
                .permitAll() //모든 페이지의 모든 권한이 삽입
                .and()
                .formLogin() //로그인을 내가 원하는 곳에서 하겠다. //인증 접근 시작
                .loginPage("/member/login") //로그인 페이지 생성
                .loginProcessingUrl("/member/loginController") //로그인 컨트롤러로 이어짐
                .usernameParameter("mid")   //아이디 받기.
                .passwordParameter("mpw") //비밀번호 받기. 로그인 페이지 열어주기 끝
                .failureUrl("/member/login/error") //실패했을 경우 error 페이지로 이동
                .and()
                    .logout() //로그아웃
                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) //페이지 생성 없이 로그아웃
                    .logoutSuccessUrl("/") //로그아웃 성공시 메인페이지
                    .invalidateHttpSession(true) //로그인했던 정보를 모두 삭제
                .and()
                .csrf() //위조 요청 방지(막아버림)
                .ignoringAntMatchers("/member/loginController") //로그인 페이지를 열어준다.
                .ignoringAntMatchers("/member/signupController") //회원가입 페이지를 열어준다.
                .and()
                    .oauth2Login()
                    .userInfoEndpoint() //oauth2Login 처리 이후 설정
                    .userService(memberService); //멤버 서비스를 유저서비스에 담음?
    }

    @Autowired
    private IndexService indexService; //서비스 불러오기

    //인덱스 서비스를 oauth서비스로 사용하겠다는 선언
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //인코더
        auth.userDetailsService(indexService).passwordEncoder(new BCryptPasswordEncoder());
        //회원의 정보를 가져오면서 패스워드를 변경한다.
    }
}
