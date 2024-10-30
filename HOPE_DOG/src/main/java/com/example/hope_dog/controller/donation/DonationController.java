package com.example.hope_dog.controller.donation;

import com.example.hope_dog.dto.donation.DonationListDTO;
import com.example.hope_dog.dto.donation.DonationViewDTO;
import com.example.hope_dog.dto.notice.NoticeListDTO;
import com.example.hope_dog.dto.notice.NoticeViewDTO;
import com.example.hope_dog.service.donation.DonationService;
import com.example.hope_dog.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/dona");;;
public class DonationController {

    private final DonationService donationService;

    @GetMapping("/list")
    public String List(Model model) {
        List<DonationListDTO> donationList = donationService.getDonationList();
        model.addAttribute("donationList", donationList);

        return "donation/donation-main-center";
    }

    @GetMapping("/view")
    public String View(@RequestParam("donaNo") Long donaNo,Model model) { // 메소드 매개변수
        List<DonationViewDTO> donationViewList = donationService.getDonationViewList(donaNo);
        model.addAttribute("donationViewList", donationViewList);

        return "donation/donation-detail";
    }


    @GetMapping("/write")
    public String donationWrite(@SessionAttribute(value = "centerMemberNo", required = false) Long centerMemberNo) {
        return centerMemberNo == null ? "redirect:/user/login" : "donation/donation-write";
    }

//    @PostMapping("/write")
//    public String boardWrite(NoticeWriteDTO noticeWriteDTO, @SessionAttribute("userId") Long userId
//            , RedirectAttributes redirectAttributes, @RequestParam("noticeFile") List<MultipartFile> files) {
//        noticeWriteDTO.setUserId(userId);
//        log.info("noticeWriteDTO = ", noticeWriteDTO);
//
//        try {
//            noticeService.registerBoardWithFiles(noticeWriteDTO, files);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        



}
