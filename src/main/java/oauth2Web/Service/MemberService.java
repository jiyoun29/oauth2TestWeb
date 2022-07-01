package oauth2Web.Service;

import oauth2Web.Dto.LoginDto;
import oauth2Web.Dto.MemberDto;
import oauth2Web.Dto.Oauth2Dto;
import oauth2Web.Entity.MemberEntity;
import oauth2Web.Entity.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.transaction.Transactional;
import java.security.Security;
import java.util.*;

public class MemberService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    MemberRepository memberRepository;


    public String 인증된회원아이디호출 () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Authentication을 SecurityContextHolder에 담고 읽어온다.
        Object principal = authentication.getPrincipal(); //객체 정보를 가져온다.

        String mid = null; //mid의 초기값을 null로 받는다.
        if(principal != null){ //컨트롤러에 자동으로 값을 전달해주는 principal이 null이 아닐때,
            if(principal instanceof UserDetails){ //만일 principal이 참조하고 있는 변수가 UserDetails일때
                mid = ((UserDetails) principal).getUsername(); //UserDetails에 있는 변수를 자동으로 username을 가져와 mid를 형성한다.
            } else if(principal instanceof OAuth2User){ //혹은 principal이 참조하고 있는 변수가 OAuth2User일때

                Map<String, Object> attributes = ((OAuth2User) principal).getAttributes();
                //map에 attributes를 참조한다.

                if(attributes.get("response") != null ){ //가져온 attributes가 null이 아닐때
                    Map<String, Object> map = (Map<String, Object>) attributes.get("response");
                    //map 안에 response를 대입한다.
                    mid = map.get("email").toString(); //mid는 email을 가져온 값이 된다??
                } else if(attributes.get("kakao_account") != null){ ///가져온 kakao_account가 null이 아닐때
                    Map<String, Object> map = (Map<String, Object>) attributes.get("kakao_account");
                    //map 안에 kakao_account를 대입한다.
                    mid = map.get("email").toString();  //mid는 email을 가져온 값이 된다??????
                }

            } //else if end
        } else { return null; } //그 외에는 null 값을 return 한다.
        return null;
    }

//    Map<String, Object> attributes = null; //로그인 성공 시 sns 정보
//    String nameAttributeKey = null; //로그인 성공 시  sns 정보


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }



    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        //DefaultOAuth2UserService는 OAuth2UserService구현체이다. 새로운 구현체를 선언한다.

        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        // oAuth2UserService에 user를 불러오고 대입한다?
        String registrationid = userRequest.getClientRegistration().getRegistrationId();
        // 아이디에 요청에 따라 ClientRegistration와 RegistrationId를 대입한다.
        Map<String,Object> attributes = oAuth2User.getAttributes();
        //map 안에도 대입한다....
        String nameAttributeKey = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        Oauth2Dto oauth2Dto = Oauth2Dto.sns확인(registrationid, attributes,nameAttributeKey);
        Optional<MemberEntity> optional = memberRepository.findBymid(oauth2Dto.getMid());
        //내 아이디를 찾아 대입한다.

        if(!optional.isPresent()){
            memberRepository.save(oauth2Dto.toentity()); //리포지토리에 저장한다.
        } else { //로그인 했을 경우 db업데이트 처리
        }
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("SNS사용자")),
                attributes, nameAttributeKey);
        //세가지 정보를 담은 객체를 return한다. (중복처리가 아닌 새로운 객체)
    }





}