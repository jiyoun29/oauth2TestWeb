package oauth2Web.Controller;

import oauth2Web.Dto.MemberDto;
import oauth2Web.Service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    //메인페이지 열어주기
    @GetMapping("/")
    public String main(){return "main"; }

    @Autowired //인덱스서비스 호출
    private IndexService indexService;
    
    //로그인페이지 열어주기
    @GetMapping("/member/login")
    public String login(){
        return "login";
    }

    //회원가입 열어주기
    @GetMapping("/member/signup")
    public String signup(){
        return "signup";
    }

    //회원가입 처리
    @PostMapping("/member/signupController")
    public String signupcontroller(MemberDto memberDto){
        indexService.signup(memberDto); //인덱스 서비스에 회원가입한 멤버dto를 추가
        return "main";
    }


}
