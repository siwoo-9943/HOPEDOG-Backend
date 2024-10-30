package com.example.hope_dog.controller.volun.car;

import com.example.hope_dog.dto.volun.car.CarDTO;
import com.example.hope_dog.dto.volun.car.CarDetailDTO;
import com.example.hope_dog.service.volun.car.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    //카풀 게시판 목록
    @GetMapping("/main")
    public String carList(Model model) {
        //carService로 조회하고 carList 변수에 저장
        List<CarDTO> carList = carService.getCarList(); // 데이터 조회
        model.addAttribute("carList", carList);
        //조회한 carlist를 모델에 carlist라는 이름으로 추가해서 뷰에서 사용할 수 있도록 전달
        //model : 컨트롤러와 뷰 간에 데이터를 전달하는 역할
        return "volun/car/volun-car-main";
    }


    //카테고리 분류에 따른 게시글 조회
    @GetMapping("/filter")
    public String filterCarList(@RequestParam("cate") String cate, Model model) {
        List<CarDTO> carList = carService.getCarListByCate(cate);
        model.addAttribute("carList", carList);
        return "volun/car/volun-car-main";
    }

    //검색기능
    @GetMapping("/search")
    public String searchCars(@RequestParam("search-type") String searchType,
    @RequestParam("keyword")String keyword,Model model) {
        List<CarDTO> carList = carService.searchCars(searchType, keyword);
        model.addAttribute("carList", carList);
        return "volun/car/volun-car-main";
    }



    //게시글 상세
    @GetMapping("/post/{carNo}")
    public String carDetail(@PathVariable("carNo") Long carNo, Model model) {
        CarDetailDTO carDetail = carService.getCarDetail(carNo);
        model.addAttribute("carDetail", carDetail);
        return "volun/car/volun-car-post";
    }


}