package oauth2Web.Dto;

import lombok.*;
import oauth2Web.Entity.MemberEntity;
import oauth2Web.Entity.Role;

import java.util.Map;

@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class Oauth2Dto {

    private String mid; //멤버 아이디
    private String registrationid; //등록 아이디
    private Map<String,Object> attributes; //스트링과 오브젝트를 담은 멥
    private String nameAttributeKey; //

    public static Oauth2Dto sns확인(String registrationid, Map<String,Object> attributes, String nameAttributeKey){
        //sns로 로그인한 아이디를 확인하는 메소드. 인수로 registrationid, attributes, nameAttributeKey를 가진다.
        if(registrationid.equals("naver")){ //네이버와 비교해서 일치하면
            return 네이버변환(registrationid,attributes,nameAttributeKey); //받은 인수를 네이버로 저장한다.
        } else if(registrationid.equals("kakao")){ //카카오와 비교해서 일치하면
            return 카카오변환(registrationid,attributes, nameAttributeKey); //받은 인수를 카카오로 저장한다.
        } else { //그 외에는 null 값을 리턴한다.
            return null;
        }
    }

    public static Oauth2Dto 네이버변환(String registrationid, Map<String,Object> attributes, String nameAttributeKey){
        //네이버로 변환할 oauth2dto에 인수로 registrationid, attributes, nameAttributeKey를 받는다.
        Map<String,Object> response = (Map<String, Object>) attributes.get(nameAttributeKey); //받은 정보 객체를 네이버에 담고  response한다.

        return Oauth2Dto.builder() //email을 response하고 String으로 형변환을 해 mid에 담아 build한다.
                .mid((String)response.get("email"))
                .build();
    }

    public static Oauth2Dto 카카오변환(String registrationid, Map<String,Object> attributes, String nameAttributeKey){
        //카카오로 변환할 oauth2dto에 인수로 registrationid, attributes, nameAttributeKey를 받는다.
        Map<String,Object> kakao_account = (Map<String, Object>) attributes.get(nameAttributeKey);
        return Oauth2Dto.builder() //email을 kakao_account에 담고 String으로 형변환을 해 mid에 담아 build한다.
                .mid((String) kakao_account.get("email"))
                .build();
    }


    public MemberEntity toentity(){ //타 클래스에서 호출하기 위한 entity객체를 생성한다.
        //sns사용자(sns로그인)가 로그인하면 그것을 아이디로 사용한다?
        return MemberEntity.builder().mid(this.mid).role(Role.valueOf("SNS사용자")).build();}


}
