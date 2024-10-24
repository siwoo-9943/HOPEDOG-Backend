package com.example.hope_dog.controller.notice;

import com.example.hope_dog.dto.notice.NoticeListDTO;
import com.example.hope_dog.dto.notice.page.Criteria;
import com.example.hope_dog.dto.notice.page.Page;
import com.example.hope_dog.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/list")
    public String noticeList(Criteria criteria, Model model) {
        List<NoticeListDTO> noticeList = noticeService.findAllPage(criteria);
        int total = noticeService.findTotal();
        Page page = new Page(criteria, total);

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("page", page);

        return "notice/list";
    }

//    @GetMapping("/write")
//    public String boardWrite(@SessionAttribute(value = "userId", required = false) Long userId) {
//        return userId == null ? "redirect:/user/login" : "board/write";
//    }
//
//    @PostMapping("/write")
//    public String boardWrite(BoardWriteDTO boardWriteDTO, @SessionAttribute("userId") Long userId
//            , RedirectAttributes redirectAttributes, @RequestParam("boardFile") List<MultipartFile> files) {
//        boardWriteDTO.setUserId(userId);
//        log.info("boardWriteDTO = ", boardWriteDTO);
//
//        try {
//            boardService.registerBoardWithFiles(boardWriteDTO, files);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        
        // addFlashAttribute(키, 값) 은 리다이렉트 되는 url과 매핑된 컨트롤러 메소드의 model객체에 데이터를 저장
        // /board/list와 매핑된 메소드의 model 객체에 데이터를 저장한다
        
        // redirectAttributes 객체로 데이터를 저장하는 방식
        // 1. 쿼리스트링
        // 컨트롤러 메소드의 매개변수로 데이터를 전달할 때 사용
        // 컨트롤러에서 직접적으로 사용되는 데이터를 전달할 때 사용
        
        // 2. 플래시 영역
        // 컨트롤러 메소드의 model 객체의 데이터를 저장할 때 사용
        // 최종적으로 띄워지는 화면에서 데이터를 사용할 목적으로 플래시에 저장한다
//        redirectAttributes.addFlashAttribute("boardId", boardWriteDTO.getBoardId());
//        return "redirect:/board/list";
//    }


}
