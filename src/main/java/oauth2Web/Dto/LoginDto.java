package oauth2Web.Dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder @Getter
public class LoginDto implements UserDetails { //꼭 상속해서 오버라이드

    private String mid;
    private String mpw;
    private Set<GrantedAuthority> authorities;

    //생성자 하나 생성
    public LoginDto( String mid, String mpw, Collection<? extends GrantedAuthority> authorities){
        this.mid = mid;
        this.mpw = mpw;
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(authorities)); //중복값 제거를 위한 set
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities; //변경
    }

    @Override
    public String getPassword() {
        return this.mpw;
    }

    @Override
    public String getUsername() {
        return this.mid;
    }
    //////



    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
