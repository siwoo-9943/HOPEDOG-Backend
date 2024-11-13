package com.example.hope_dog.service.dogmap;

import com.example.hope_dog.dto.dogmap.dogmap.AnimalShelterResponse;
import com.example.hope_dog.dto.dogmap.dogmap.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DogMapService {

    @Value("${api.service.key1}")
    private String serviceKey1;
    private String serviceKey2;

    public List<Item> getShelterInfo() {
<<<<<<< HEAD
        String baseurl = "http://apis.data.go.kr/1543061/animalShelterSrvc/shelterInfo";
        String subType = "_type=json";
        String numOfRows = "numOfRows=200";
        String pageNo = "pageNo=1";
        String KeyStr = "serviceKey=";


        // URL 인코딩
//        String encodedServiceKey;
//        try {
//            encodedServiceKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8.toString());
//        } catch (Exception e) {
//            log.error("서비스 키 인코딩 중 오류 발생: " + e.getMessage());
//            return new ArrayList<>(); // 인코딩 오류 발생 시 빈 리스트 반환
//        }
=======
        String baseurl = "https://apis.data.go.kr/1543061/animalShelterSrvc/shelterInfo";
        String subType = "json";
        String numOfRows = "250";
        String pageNo = "1";
>>>>>>> main

        // URL 생성
        String url = baseurl + "?serviceKey=" + serviceKey1 + "&numOfRows=" + numOfRows + "&pageNo=" + pageNo + "&_type=" + subType;
        log.info("URL 확인 : " + url);

        RestTemplate restTemplate = new RestTemplate();
        AnimalShelterResponse response = null;

        try {
            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            log.info("요청헤더 확인==============" + headers.toString());
            HttpEntity<String> entity = new HttpEntity<>(headers);
            log.info("요청헤더 Entity확인==============" + entity.toString());
            ResponseEntity<AnimalShelterResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, AnimalShelterResponse.class);

            // 응답 상태 코드 확인
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                response = responseEntity.getBody();
                log.info("API Response: " + response); // 응답 내용 출력
            } else {
                log.error("API 호출 실패: " + responseEntity.getStatusCode());
                return new ArrayList<>(); // 실패 시 빈 리스트 반환
            }
        } catch (RestClientException e) {
            log.error("API 호출 중 오류 발생: " + e.getMessage());
            return new ArrayList<>(); // 오류 발생 시 빈 리스트 반환
        }

        List<Item> items = new ArrayList<>();

        // JSON 응답에서 items 항목을 가져옴
        if (response != null && response.getResponse() != null && response.getResponse().getBody() != null) {
            // items 리스트 가져오기
            items = response.getResponse().getBody().getItems().getItem();
        } else {
            log.error("응답이 유효하지 않거나 JSON 형식이 아닙니다: " + response);
            return new ArrayList<>(); // 유효하지 않은 응답 시 빈 리스트 반환
        }

        return items;
    }

    // 주소 필터링하는 메소드
    public List<Item> filterByAddress(List<Item> dogmapDTOList, String addressPrefix) {
        List<Item> filteredItems = dogmapDTOList.stream()
                .filter(dto -> dto.getCareAddr().startsWith(addressPrefix))
                .collect(Collectors.toList());

        // 인덱스를 추가하면서 리스트에 저장
        for (int i = 0; i < filteredItems.size(); i++) {
            Item item = filteredItems.get(i);
            item.setIndex(i + 1); // 인덱스를 설정 (1부터 시작)
        }

        return filteredItems;
    }

}

