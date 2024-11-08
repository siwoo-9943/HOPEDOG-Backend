package com.example.hope_dog.controller.volun.volun;

import com.example.hope_dog.dto.adopt.adopt.AdoptCommentDTO;
import com.example.hope_dog.dto.adopt.adopt.AdoptDetailDTO;
import com.example.hope_dog.dto.page.Criteria;
import com.example.hope_dog.dto.page.Page;
import com.example.hope_dog.dto.volun.volun.VolunCommentDTO;
import com.example.hope_dog.dto.volun.volun.VolunDetailDTO;
import com.example.hope_dog.dto.volun.volun.VolunMainDTO;
import com.example.hope_dog.service.volun.volun.VolunService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/volun")
public class VolunController {
    private final VolunService volunService;

    //봉사열기
    @GetMapping("/main")        //열릴페도메인 localhost:8060/adopt/main
    public String VolunMain(Model model) {
        List<VolunMainDTO> VolunMainList = volunService.getVolunMainList();

        System.out.println(VolunMainList + "확인");

        model.addAttribute("VolunMainList", VolunMainList);
        return "volun/volun/volun-main";  //localhost:8060/adopt/main로 접속했을시 열릴 html
    }

    //  봉사 전체게시글
    @GetMapping("/volun")
    public String volunList(Criteria criteria, Model model, HttpSession session){
        List<VolunMainDTO> volunMainList = volunService.findAllPage(criteria);
        int total = volunService.findTotal();
        Page page = new Page(criteria, total);
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo"); //이것도 무시 세션값 자겨와서 저장

        model.addAttribute("VolunMainList", volunMainList);
        model.addAttribute("page", page);
        model.addAttribute("centerMemberNo", centerMemberNo); //이건 나만 쓰는거 무시 세션값 html에서 쓸수있게 model추가
        model.addAttribute("All", true);
        return "volun/volun/volun-volun-main";
    }

    //봉사 모집중인 게시글
    @GetMapping("/volunKeep")
    public String volunListKeep(Criteria criteria, Model model, HttpSession session){
        List<VolunMainDTO> volunMainList = volunService.findAllPageKeep(criteria);
        int total = volunService.findTotalKeep();
        Page page = new Page(criteria, total);
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo"); //이것도 무시 세션값 자겨와서 저장

        model.addAttribute("VolunMainList", volunMainList);
        model.addAttribute("page", page);
        model.addAttribute("centerMemberNo", centerMemberNo); //이건 나만 쓰는거 무시 세션값 html에서 쓸수있게 model추가
        model.addAttribute("Keep", true);

        return "volun/volun/volun-volun-main";
    }

    //봉사상세글
    @GetMapping("/volun/volundetail")
    public String volunDetail(@RequestParam("volunNo") Long volunNo, Model model, HttpSession session) {
        List<VolunDetailDTO> volunDetailList = volunService.getVolunDetail(volunNo);
        List<VolunCommentDTO> volunCommentlList = volunService.getVolunComment(volunNo);
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");
        Long memberNo = (Long) session.getAttribute("memberNo");

        model.addAttribute("volunDetailList", volunDetailList);
        model.addAttribute("volunCommentList", volunCommentlList);
        model.addAttribute("centerMemberNo", centerMemberNo);
        model.addAttribute("memberNo", memberNo);

        return "volun/volun/volun-volundetail";
    }

}
