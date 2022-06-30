package oauth2Web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //스프링부트에 관련된 설정값을 모두 셋팅해준다
public class AppStart { //앱을 초기에 시작시켜준다.
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);
    }
}
