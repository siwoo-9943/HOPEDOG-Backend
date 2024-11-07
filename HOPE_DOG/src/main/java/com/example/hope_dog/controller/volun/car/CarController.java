package com.example.hope_dog.controller.volun.car;

import com.example.hope_dog.dto.page.Criteria;
import com.example.hope_dog.dto.page.Page;
import com.example.hope_dog.dto.volun.car.CarCommentDTO;
import com.example.hope_dog.dto.volun.car.CarDTO;
import com.example.hope_dog.dto.volun.car.CarDetailDTO;
import com.example.hope_dog.service.volun.car.CarService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/car")// 시작 도메인 localhost:8060/car
//생성자 주입
public class CarController {
    private final CarService carService;

    //글 목록
    @GetMapping("/main")
    public String carList(HttpSession session, Model model){
        // 세션에서 memberNo와 centerMemberNo를 가져옴
        Long memberNo = (Long) session.getAttribute("memberNo");
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");

        //서비스에서 게시글 목록 가져옴
        List<CarDTO> carList = carService.getCarList(session);

        //list 비어있는지 확인
        if(carList.isEmpty()){
            System.out.println("게시글 목록이 비어있습니다.");
        }else {
            //각 게시글의 작성자 정보 출력하여 확인
            for (CarDTO car: carList) {
                System.out.println("게시글 작성자 ID:" + car.getCarWriter() +
                        ",일반회원 :" + car.getMemberNickname() +
                        ",센터 : " + car.getCenterMemberName());
            }
        }

        model.addAttribute("carList", carList);

        return "volun/car/volun-car-main";
    }


    // 카테고리 분류에 따른 게시글 조회
    @GetMapping("/filter")
    public String filterCarList(@RequestParam("cate") String cate, Model model) {

        List<CarDTO> carList = carService.getCarListByCate(cate);
        model.addAttribute("carList", carList);
        model.addAttribute("cate", cate);

        System.out.println("cate : " + cate);
        System.out.println("carList : " + carList
        );
        return "volun/car/volun-car-main";
    }

    // 검색
//    @GetMapping("/main/search")
//    public String searchCars(@RequestParam("searchType") String searchType,
//                             @RequestParam("keyword") String keyword,
//                             Model model) {
//        System.out.println("컨트롤러 검색 타입 : " + searchType);
//        System.out.println("컨트롤러 키워드 : " + keyword);
//
//        // 검색 조건에 따른 차량 리스트 조회
//        List<CarDTO> carList = carService.searchCars(searchType, keyword);
//        System.out.println("컨트롤러 List : " + carList);
//
//        model.addAttribute("carList", carList); // 차량 리스트 추가
//        model.addAttribute("searchType", searchType); // 검색 타입 추가
//        model.addAttribute("keyword", keyword); // 검색어 추가
//
//        return "volun/car/volun-car-main"; // 결과 페이지로 이동
//    }

    //게시글 상세
    @GetMapping("/post/{carNo}")
    public String selectCarDetail(@PathVariable("carNo") Long carNo, Model model,HttpSession session) {

        //세션에서 일반회원, 센터회원 가져오기
        Long memberNo = (Long) session.getAttribute("memberNo");
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");
        List<CarDetailDTO> carDetailList = carService.selectCarDetail (carNo);
        System.out.println("컨트롤러 carDetailList : " + carDetailList);

        //댓글
        List<CarCommentDTO> carCommentList = carService.carComment(carNo);
        System.out.println("컨트롤러 carCommentList : " + carCommentList);

        //사용자 정보 추가
        model.addAttribute("memberNo", memberNo);
        model.addAttribute("centerMemberNo", centerMemberNo);
        model.addAttribute("carDetailList", carDetailList);
        model.addAttribute("carCommentList", carCommentList);

        return "volun/car/volun-car-post";
    }

    //글 작성
    @GetMapping("/carWrite")
    public String carWriter(HttpSession session, Model model) {
        //일반회원 , 센터회원 가져오기
        Long memberNo = (Long) session.getAttribute("memberNo");
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");

        //모델에 센터회원, 일반회원 번호 추가
        model.addAttribute("memberNo", memberNo);
        model.addAttribute("centerMemberNo", centerMemberNo);
        return "volun/car/volun-car-post-write";
    }

    //글 등록
    @PostMapping("/carrequestRegi")
    public String postCarWrite(@SessionAttribute(name = "memberNo", required = false) Long memberNo,
                               @SessionAttribute(name = "centerMemberNo", required = false) Long centerMemberNo,
                               //세션에 저장된 memberNo를 조회
                                CarDTO carDTO) {
        Long 
    }


}