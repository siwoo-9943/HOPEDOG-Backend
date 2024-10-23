package com.example.hope_dog.controller.member;

import com.example.hope_dog.dto.member.MemberDTO;
import com.example.hope_dog.dto.member.MemberSessionDTO;
import com.example.hope_dog.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Mock
    private HttpSession session;

//   테스트가 실행되기전에 mockMvc를 초기화하는 역활을 함
//    WebApplicationContext를 사용해서 mockMvc를 설정함
    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

//    회원가입 페이지에 get요청을 테스트함
//
    @Test
    void testJoinPage() throws Exception {
        mockMvc.perform(get("/member/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/join"));
    }

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/member/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/login"));
    }

    @Test
    void testRegisterMember() throws Exception {
        // given
        doNothing().when(memberService).join(any(MemberDTO.class));

        // when & then
        mockMvc.perform(post("/member/join")
                        .param("memberId", "testUser")
                        .param("memberPw", "test123!")
                        .param("memberEmail", "test@example.com")
                        .param("memberName", "테스트")
                        .param("memberNickname", "테스트닉네임")
                        .param("memberPhoneNumber", "010-1234-5678")
                        .param("memberGender", "M")
                        .param("memberZipcode", "12345")
                        .param("memberAddress", "서울시 테스트구")
                        .param("memberDetailAddress", "테스트동 101호")
                        .param("memberTwoFactorEnabled", "N")
                        .param("memberStatus", "1")
                        .param("memberLoginStatus", "HOPEDOG"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/login"));
    }

    @Test
    void testLoginSuccess() throws Exception {
        // given
        MemberSessionDTO sessionDTO = new MemberSessionDTO();
        sessionDTO.setMemberNo(1L);
        sessionDTO.setMemberId("testUser");
        sessionDTO.setMemberName("테스트");
        sessionDTO.setMemberNickname("테스트닉네임");
        sessionDTO.setMemberEmail("test@example.com");
        sessionDTO.setMemberLoginStatus("HOPEDOG");

        when(memberService.login(anyString(), anyString())).thenReturn(sessionDTO);

        // when & then
        mockMvc.perform(post("/member/login")
                        .param("memberId", "testUser")
                        .param("memberPw", "test123!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/list"));
    }

    @Test
    void testLoginFailure() throws Exception {
        // given
        when(memberService.login(anyString(), anyString()))
                .thenThrow(new IllegalStateException("존재하지 않는 회원정보"));

        // when & then
        mockMvc.perform(post("/member/login")
                        .param("memberId", "wrongId")
                        .param("memberPw", "wrongPw"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testLogout() throws Exception {
        mockMvc.perform(get("/member/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/login"));
    }
}