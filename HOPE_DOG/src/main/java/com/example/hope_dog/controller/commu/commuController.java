package com.example.hope_dog.controller.commu;


import com.example.hope_dog.dto.commu.CommuCommentDTO;
import com.example.hope_dog.dto.commu.CommuDTO;
import com.example.hope_dog.dto.commu.CommuDetailDTO;
import com.example.hope_dog.dto.commu.CommuReportDTO;
import com.example.hope_dog.service.commu.CommuService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpLogging;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/commu") // localhost:8060/commu
public class commuController {
    private final CommuService commuService;

    @GetMapping("/main") // /commu/list 경로로 GET 요청을 받을 때
    public String getCommuList(HttpSession session, Model model) {
        // 세션에서 memberNo와 centerMemberNo를 가져옴
        Long memberNo = (Long) session.getAttribute("memberNo");
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");

        // 서비스에서 게시글 목록을 가져옴
        List<CommuDTO> commuList = commuService.getCommuList(session);

        // commuList가 비어있는지 확인
        if (commuList.isEmpty()) {
            System.out.println("게시글 목록이 비어있습니다.");
        } else {
            // 각 게시글의 작성자 정보를 출력하여 확인
            for (CommuDTO commu : commuList) {
                System.out.println("게시글 작성자 ID: " + commu.getCommuWriter() +
                        ", 일반회원 닉네임: " + commu.getMemberNickname() +
                        ", 센터회원 이름: " + commu.getCenterMemberName());
            }
        }

        model.addAttribute("commuList", commuList); // 모델에 데이터 추가

        System.out.println("컨트롤러 글목록회원" + memberNo + ", 센터회원" + centerMemberNo);
        return "commu/commu-main"; // 뷰 이름을 반환
    }

    //카테고리 분류
    @GetMapping("/filter")
    public String filterCommu(@RequestParam("cate") String cate, Model model) {
        List<CommuDTO> commuList = commuService.getCommuListByCate(cate);
        model.addAttribute("commuList", commuList);
        model.addAttribute("selectedCate", cate);

        // 콘솔에 출력
        System.out.println("Selected Category: " + cate);
        System.out.println("Posts Count: " + commuList.size());

        return "commu/commu-main"; // 뷰 이름 반환
    }

    //인기게시글
    @GetMapping("/good")
    public String getGoodCommuList(Model model) {
        List<CommuDTO> commuList = commuService.cateCommuGood();
        model.addAttribute("commuList", commuList);


        return "commu/commu-main";
    }



    //거지같은 검색 더럽게 안됨
//    @GetMapping("commu/main/commuSearch")
//    public String searchCommu(@RequestParam("keyword") String keyword, Model model) {
//        System.out.println("컨트롤러 검색 키워드: " + keyword);
//
//        List<CommuDTO> results = commuService.searchCommu(keyword);
//        System.out.println("컨트롤러 리절트" + results);
//
//        model.addAttribute("commuList", results); // 검색 결과를 commuList로 추가
//        System.out.println("컨트롤러commuList"+ results);
//        return "commu/commu-main";
//    }

    //게시글 상세
    // 게시글 상세 정보 조회
    @GetMapping("/post/{commuNo}")
    public String getCommuDetail(@PathVariable("commuNo") Long commuNo, Model model, CommuDTO commuDTO) {
        //조회수증가
        commuService.commuGood(commuDTO);

        // 게시글 상세 정보 조회
        CommuDetailDTO commuDetailDTO = commuService.getCommuDetail(commuNo);
        System.out.println("컨트롤러 상세정보 commuNo : " + commuNo);
        model.addAttribute("commuDetail", commuDetailDTO);

        return "commu/commu-post"; // 게시글 상세 페이지로 이동
    }

    //글작성
    @GetMapping("/commuWrite")
    public String commuWrite(HttpSession session, Model model) {
        // 세션에서 centerMemberNo와 memberNo 가져오기
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");
        Long memberNo = (Long) session.getAttribute("memberNo"); // 일반회원 ID 가져오기

        // 모델에 센터회원 번호와 일반회원 번호 추가
        model.addAttribute("centerMemberNo", centerMemberNo);
        model.addAttribute("memberNo", memberNo); // 일반회원 번호 추가
        System.out.println("컨트롤러 센터멤버"+centerMemberNo);
        System.out.println("컨트롤러멤버"+memberNo);

        return "commu/commu-post-write"; // 게시글 작성 페이지 템플릿 반환
    }

    // 커뮤니티 글 등록 처리
    @PostMapping("/commurequestRegi")
    public String postCommuWrite(@SessionAttribute(name = "memberNo", required = false) Long memberNo,
                                 @SessionAttribute(name = "centerMemberNo", required = false) Long centerMemberNo,
                                 CommuDTO commuDTO) {
        Long writerNo = memberNo != null ? memberNo : centerMemberNo;
        if (writerNo == null) {
            throw new IllegalArgumentException("로그인 상태가 필요합니다.");
        }

        commuDTO.setCommuWriter(writerNo);  // commuWriter에 세션에서 가져온 ID 설정
        System.out.println("컨트롤러 writerNo :" + writerNo);
        commuService.writePost(commuDTO);
        return "redirect:/commu/main";
    }

//    //커뮤니티 글 수정
//    @GetMapping("/rewrite/{commNO}")
//    public String rewriteCommu(@PathVariable("commNO") Long commNO, Model model) {
//        CommuDTO commuDTO = commuService.getCommuDetail(commNO);
//
//        model.addAttribute("commuDTO", commuDTO);
//
//        return "commu/commu-rewrite";
//    }

    // 글 삭제
    @DeleteMapping("/commuDelete/{commuNo}")
    public String commuDelete(@PathVariable("commuNo") Long commuNo, HttpSession session) {
        Long memberNo = (Long) session.getAttribute("memberNo");
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");
        Long writerNo = memberNo != null ? memberNo : centerMemberNo;
        commuService.commuDelete(commuNo,writerNo);

        return "redirect:/commu/main";

    }

    //글 신고
    // 커뮤니티 글 신고 처리
    @GetMapping("/commuReport")
    public String postCommuReport(@RequestParam("commuNo") Long commuNo,
                                  @RequestParam("reportContent") String reportContent,
                                  CommuReportDTO commuReportDTO,
                                  HttpSession session) {
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");
        Long memberNo = (Long) session.getAttribute("memberNo");

        // 신고 내용 설정
        commuReportDTO.setReportContent(reportContent);
        commuReportDTO.setReportContentNo(commuNo);
        commuReportDTO.setReportWriter(centerMemberNo != null ? centerMemberNo : memberNo);
        commuReportDTO.setReportCate("커뮤니티"); // 신고 카테고리 설정

        // 신고 처리 메소드 호출
        commuService.commuReport(commuReportDTO);

        // 신고 후 페이지 리다이렉트
        return "redirect:/commu/main"; // 커뮤니티 페이지로 리다이렉트
    }
}



