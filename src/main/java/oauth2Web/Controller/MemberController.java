package oauth2Web.Controller;

import oauth2Web.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/member/info")
    public String memberinfo(){
        return memberService.인증된회원아이디호출();
    }
}
