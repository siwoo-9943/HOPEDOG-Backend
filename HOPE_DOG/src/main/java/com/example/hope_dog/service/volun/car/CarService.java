package com.example.hope_dog.service.volun.car;

import com.example.hope_dog.dto.centerMember.CenterMemberDTO;
import com.example.hope_dog.dto.member.MemberDTO;
import com.example.hope_dog.dto.page.Criteria;
import com.example.hope_dog.dto.volun.car.CarCommentDTO;
import com.example.hope_dog.dto.volun.car.CarDTO;
import com.example.hope_dog.dto.volun.car.CarDetailDTO;
import com.example.hope_dog.mapper.volun.car.CarMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final CarMapper carMapper;

    //목록조회 메서드
    public List<CarDTO> getCarList(HttpSession session) {
        //carmapper로 정보 조회
        List<CarDTO> carList = carMapper.selectAllCars();

        //세션에서 로그인한 회원 정보 가져옴
        Long memberNo = (Long) session.getAttribute("memberNo");
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");

        //각 게시글 작성자 확인
        for (CarDTO car : carList) {

            if (car.getCarWriter() % 2 == 0) {//작성자id가 짝수일 때
                // 센터회원인 경우 작성자 센터회원 정보 조회
                CenterMemberDTO centerMember = carMapper.selectCenterMemberByNo(car.getCarWriter());
                if (centerMember != null) { //센터회원 정보가 존재할 때
                    //센터회원 이름 보여주기
                    car.setCenterMemberName(centerMember.getCenterMemberName());
                }

            } else {//작성자 id가 홀수일 때
                // 일반회원으로
                MemberDTO member = carMapper.selectMemberByNo(car.getCarWriter());
                if (member != null) { //일반회원 정보가 존재할 때
                    car.setMemberNickname(member.getMemberNickname());
                }
            }
        }


        return carList;
    }


    // 카테고리에 따른 게시글 조회
    public List<CarDTO> getCarListByCate(String cate){
        List<CarDTO> carList = carMapper.findCarByCate(cate);

        // 각 게시글 작성자 확인
        for (CarDTO car : carList) {
            if (car.getCarWriter() % 2 == 0) {
                // 센터회원인 경우 작성자 센터회원 정보 조회
                CenterMemberDTO centerMember = carMapper.selectCenterMemberByNo(car.getCarWriter());
                if (centerMember != null) { // 센터회원 정보가 존재할 때
                    // 센터회원 이름 보여주기
                    car.setCenterMemberName(centerMember.getCenterMemberName());
                }
            } else {
                // 일반회원인 경우
                MemberDTO member = carMapper.selectMemberByNo(car.getCarWriter());
                if (member != null) {
                    car.setMemberNickname(member.getMemberNickname());
                }
            }
        }

        return carList;
    }



    //게시글 상세 조회

    public List<CarDetailDTO> selectCarDetail(Long carNo){
        return carMapper.selectCarDetail(carNo);
    }

    //게시글 댓글 불러오기
    public List<CarCommentDTO> carComment(Long carNo){
        return carMapper.carComment(carNo);
    }

    //게시글 작성
    public void carWriter(CarDTO carDTO){
        //작성자 null인지
        if(carDTO.getCarWriter() == null){
            throw new IllegalArgumentException("작성자 id가 필요합니다");
        }

        //작성자 일반회원인지 센터회원인지 확인하여 정보 세팅하기
        Long carWriter = carDTO.getCarWriter();

        if(carWriter % 2 == 0){ // 짝수 = 센터회원
            CenterMemberDTO centerMember = carMapper.selectCenterMemberByNo(carWriter);
            if (centerMember != null) {
                carDTO.setCenterMemberName(centerMember.getCenterMemberName());
            }
        }else{ //홀수 = 일반회원
            MemberDTO member = carMapper.selectMemberByNo(carWriter);
            if (member != null) {
                carDTO.setMemberNickname(member.getMemberNickname());
            }
        }

        // 작성 날짜 및 수정 날짜 설정
        Date now = new Date();
        carDTO.setCarRegiDate(now);
        carDTO.setCarUpdateDate(now);

        //db에 게시글 저장
        carMapper.carWriter(carDTO);

    }

    //페이지네이션
//    // 전체 게시글 조회
//    public List<CarDTO> findCarMain() {
//        return carMapper.selectCarMain();
//    }
//
//    // 게시글 총 개수 조회 (Criteria를 통해 카테고리 정보를 확인)
//    public int findCarTotal(Criteria criteria) {
//        String cate = criteria.getCate(); // Criteria에서 카테고리 정보 추출
//        if (cate != null && !cate.isEmpty()) {
//            return carMapper.countCarsByCategory(cate); // 카테고리별 총 개수 조회
//        }
//        return carMapper.carTotal(); // 카테고리 없는 경우 전체 개수 조회
//    }
//
//    // 페이지별 게시글 조회
//    public List<CarDTO> findCarPage(Criteria criteria) {
//        return carMapper.selectCarPage(criteria);
//    }


    // 검색기능
//    public List<CarDTO> searchCars(String searchType, String keyword) {
//        Map<String, Object> params = new HashMap<>();
//        System.out.println("서비스 Map 들어옴");
//
//        // 검색 조건 설정
//        if ("title".equals(searchType)) {
//            params.put("carTitle", keyword);
//        } else if ("nickname".equals(searchType)) {
//            try {
//                Long writerId = Long.parseLong(keyword);
//                params.put("carWriter", writerId);
//            } catch (NumberFormatException e) {
//                return List.of(); // 변환 실패 시 빈 리스트 반환
//            }
//        }
//
//        // Mapper 호출
//        List<CarDTO> carList = carMapper.searchCars(params);
//        System.out.println("메소드 리스트 들어오세요");
//
//        // 작성자 정보 설정
//        for (CarDTO car : carList) {
//            if (car.getCarWriter() % 2 == 0) {
//                // 센터회원인 경우 작성자 센터회원 정보 조회
//                CenterMemberDTO centerMember = carMapper.selectCenterMemberByNo(car.getCarWriter());
//                if (centerMember != null) {
//                    car.setCenterMemberName(centerMember.getCenterMemberName());
//                }
//            } else {
//                // 일반회원인 경우
//                MemberDTO member = carMapper.selectMemberByNo(car.getCarWriter());
//                if (member != null) {
//                    car.setMemberNickname(member.getMemberNickname());
//                }
//            }
//        }
//
//        return carList;
//    }






    }

