package com.cqre.cqre.security.OAuth2;

import com.cqre.cqre.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String name;
    private String email;
    private String nameAttributeKey;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String name, String email, String nameAttributeKey) {
        this.attributes = attributes;
        this.name = name;
        this.email = email;
        this.nameAttributeKey = nameAttributeKey;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        User buildUser = User.builder()
                            .name(name)
                            .email(email)
                            .studentId("OAuth2")
                            .password("OAuth2")
                            .loginId("OAuth2")
                            .build();

        buildUser.setEmailVerified("true");

        return buildUser;
    }
}
