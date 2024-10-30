package com.example.hope_dog.service.member;

import com.example.hope_dog.dto.member.MemberDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        try {
            log.info("OAuth2User attributes: {}", oauth2User.getAttributes());  // 로깅 추가

            Map<String, Object> attributes = oauth2User.getAttributes();
            Long id = (Long) attributes.get("id");
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

            String email = (String) kakaoAccount.get("email");
            String nickname = (String) properties.get("nickname");

            // 최초 로그인인지 확인
            MemberDTO existingMember = memberService.findByProviderAndProviderId("kakao", String.valueOf(id));
            log.info("Existing member check: {}", existingMember);  // 로깅 추가

            if (existingMember == null) {
                // 최초 로그인 시 임시 회원 정보 세션에 저장
                log.info("New member, setting temporary session attributes");  // 로깅 추가
                session.setAttribute("tempEmail", email);
                session.setAttribute("tempNickname", nickname);
                session.setAttribute("tempProviderId", String.valueOf(id));
                session.setAttribute("needAdditionalInfo", true);
            } else {
                // 이미 가입된 회원이면 기존 정보로 세션 설정
                log.info("Existing member, setting session attributes");  // 로깅 추가
                setSessionAttributes(existingMember);
            }

            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    oauth2User.getAttributes(),
                    userRequest.getClientRegistration()
                            .getProviderDetails()
                            .getUserInfoEndpoint()
                            .getUserNameAttributeName()
            );
        } catch (Exception e) {
            log.error("Error in loadUser: ", e);  // 에러 로깅 추가
            throw e;
        }
    }

    private void setSessionAttributes(MemberDTO member) {
        session.setAttribute("memberNo", member.getMemberNo());
        session.setAttribute("memberId", member.getMemberId());
        session.setAttribute("memberName", member.getMemberName());
        session.setAttribute("memberNickname", member.getMemberNickname());
        session.setAttribute("memberEmail", member.getMemberEmail());
        session.setAttribute("memberLoginStatus", "KAKAO");
        session.setAttribute("memberTwoFactorEnabled", member.getMemberTwoFactorEnabled());
    }
}