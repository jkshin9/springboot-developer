package me.shinsunyoung.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Jwts;
import me.shinsunyoung.springbootdeveloper.domain.User;
import me.shinsunyoung.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    //@Autowired
    //private JwtProperties jwtProperties;


    @DisplayName("generate token111() : 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void getSecretKey() {
        //given

        //when

    }


    @DisplayName("generate token() : 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        //given
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        //when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        //then
        Long userId = Jwts.parser()
                .setSigningKey(tokenProvider.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
    }

    @DisplayName("valid token() : 유효한 토큰인지 확인할 수 있다.")
    @Test
    void validToken(){
        //given

    }
}
