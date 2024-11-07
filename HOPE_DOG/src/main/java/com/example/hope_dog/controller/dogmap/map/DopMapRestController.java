package com.example.hope_dog.controller.dogmap.map;

import com.example.hope_dog.dto.dogmap.dogmap.DogMapDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Controller
public class DopMapRestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/dogmap")
    public String getDogMap(Model model) {
        return "dogmap/dogmap";
    }

    @PostMapping("/dogmap")
    public String getDogMapList(Model model) {
        String baseURL = "https://openapi.gg.go.kr/OrganicAnimalProtectionFacilit";
        String keyCode = "cb4a8539e39647ce8a9bc10f33030f55";

        URI uri = URI.create(baseURL + "?Key=" + keyCode + "&Type=json");

        try {
            DogMapDTO response = restTemplate.getForObject(uri, DogMapDTO.class);
            if (response != null) {
                System.out.println("Response: " + response); // 응답 로그

                // head 정보를 확인하여 결과 코드 및 메시지 체크
                if (response.getHead() != null && !response.getHead().isEmpty()) {
                    DogMapDTO.Head head = response.getHead().get(0);
                    if (head.getResult() != null && "INFO-000".equals(head.getResult().getResultCode())) {
                        model.addAttribute("dogMapDTO", response);
                    } else {
                        model.addAttribute("error", "API 호출 결과 오류: " + head.getResult().getResultMessage());
                    }
                } else {
                    model.addAttribute("error", "헤드 정보가 없습니다.");
                }
            } else {
                model.addAttribute("error", "응답이 null입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();  // 오류 로그 출력
            model.addAttribute("error", "오류가 발생했습니다: " + e.getMessage());
        }

        return "dogmap/dogmap"; // Thymeleaf 템플릿 파일 이름
    }
}

