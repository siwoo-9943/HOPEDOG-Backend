package com.example.hope_dog.controller.note;

import com.example.hope_dog.service.note.NoteInfoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NoteInfoController {
    private final HttpSession session;
    private final NoteInfoService noteInfoService;

    @GetMapping("/count")
    public String countPage(Model model) {
        log.info("읽지 않은 쪽지 개수 페이지 요청");
        return "note/count"; // 반환할 뷰 이름
    }

    @PostMapping("/markAllAsRead")
    public String markAllAsRead() {
        Long memberNo = (Long) session.getAttribute("memberNo");
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");

        Long userNo = (memberNo != null) ? memberNo : centerMemberNo;

        if (userNo != null) {
            noteInfoService.markAllAsRead(userNo);
        }

        log.info("모든 쪽지를 읽음 처리했습니다.");
        return "redirect:/count"; // 처리 후 다시 특정 페이지로 리다이렉트
    }
}

