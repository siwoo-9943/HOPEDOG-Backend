package com.example.hope_dog.controller.volun.car;

import com.example.hope_dog.dto.volun.car.CarDTO;
import com.example.hope_dog.service.volun.car.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/car")   // 시작 도메인 localhost:8060/car
public class CarController {
    private final CarService carService;

    @GetMapping("/main")
    public String Main(Model model) {
        List<CarDTO> carList = carService.getCarList(); // 데이터 조회
        model.addAttribute("carList", carList); // 모델에 추가
        return "volun/car/volun-car-main-member"; // 반환할 HTML 파일 경로
    }
}