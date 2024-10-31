package com.example.hope_dog.controller.donation;

import com.example.hope_dog.dto.donation.DonationListDTO;
import com.example.hope_dog.dto.donation.DonationMainDTO;
import com.example.hope_dog.dto.donation.DonationViewDTO;
import com.example.hope_dog.dto.donation.DonationWriteDTO;
import com.example.hope_dog.dto.page.Criteria;
import com.example.hope_dog.dto.page.Page;
import com.example.hope_dog.service.donation.DonationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/dona")
public class DonationController {

    private final DonationService donationService;

//    @GetMapping("/list")
//    public String List(Model model) {
//        List<DonationListDTO> donationList = donationService.getDonationList();
//        model.addAttribute("donationList", donationList);
//
//        return "donation/donation-main-center";
//    }

    @GetMapping("/list")
    public String List(Criteria criteria, Model model, HttpSession session){
//        List<DonationListDTO> donationList = donationService.getDonationList();
        List<DonationMainDTO> donationMainList = donationService.findAllPage(criteria);
        int total = donationService.findTotal();
        Page page = new Page(criteria, total);
//        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo"); //이것도 무시 세션값 가져와서 저장

        model.addAttribute("DonationMainList", donationMainList);
        model.addAttribute("page", page);
//        model.addAttribute("centerMemberNo", centerMemberNo); //이건 나만 쓰는거 무시 세션값 html에서 쓸수있게 model추가

        return "donation/donation-main-center";
    }

    @GetMapping("/view")
    public String View(@RequestParam("donaNo") Long donaNo,Model model) {
        List<DonationViewDTO> donationViewList = donationService.getDonationViewList(donaNo);
        model.addAttribute("donationViewList", donationViewList);

        return "donation/donation-detail";
    }



    @GetMapping("/write")
    public String donationWrite(HttpSession session, Model model) {
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");

        model.addAttribute("centerMemberNo", centerMemberNo);
        return "donation/donation-write";
    }

//    //입양글작성페이지이동
//    @GetMapping("/adopt/adoptwrite")
//    public String adoptWrite(HttpSession session, Model model) {
//        // 세션에서 memberNo 가져오기
//        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");
//
//        // 모델에 memberNo 추가
//        model.addAttribute("centerMemberNo", centerMemberNo);
//
//        return "adopt/adopt/adopt-adoptwrite"; // 템플릿 이름
//    }

    @PostMapping("/writeRegi")
    public String postDonationWrite(DonationWriteDTO donationWriteDTO) {
//        donationService
        return "redirect:/dona/list";
    }

//    // 입양 글 등록 처리
//    @PostMapping("/adopt/adoptWriteRegi")
//    public String postAdoptWrite(
//            @DateTimeFormat(pattern = "yyyy-MM-dd") AdoptWriteDTO adoptWriteDTO) {
//        // 서비스 호출하여 데이터베이스에 저장
//        adoptService.registerAdoption(adoptWriteDTO);
//        return "redirect:/adopt/adopt";
//    }



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
