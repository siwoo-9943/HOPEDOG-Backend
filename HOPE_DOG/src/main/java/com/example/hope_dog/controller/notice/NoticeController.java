package com.example.hope_dog.controller.notice;

import com.example.hope_dog.dto.notice.NoticeListDTO;
import com.example.hope_dog.dto.notice.NoticeViewDTO;
import com.example.hope_dog.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public String List(Model model) {
        List<NoticeListDTO> noticeList = noticeService.getNoticeList();
        model.addAttribute("noticeList", noticeList);

        return "notice/notice-list";
    }


//    @GetMapping("/list")
//    public String list(
//            Model model,
//            @PageableDefault(size = 10, sort = "noticeRegidate", direction = Sort.Direction.DESC) Pageable pageable) {
//
//        int page = pageable.getPageNumber() + 1;
//        int size = pageable.getPageSize();
//
//        List<NoticeListDTO> noticeList = noticeService.getNoticeList(page, size);
//        int totalNotices = noticeService.getNoticeCount();
//        int totalPages = (int) Math.ceil((double) totalNotices / size);
//
//        model.addAttribute("noticeList", noticeList);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", totalPages);
//
//        return "notice/notice-list";
//    }


    @GetMapping("/view")
    public String View(@RequestParam("noticeNo") Long noticeNo,Model model) {
        List<NoticeViewDTO> noticeViewList = noticeService.getNoticeViewList(noticeNo);
        model.addAttribute("noticeViewList", noticeViewList);

        return "notice/notice-detail";
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
        



}
