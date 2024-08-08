package me.shinsunyoung.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String issuer;
    private String secretKey;

}
