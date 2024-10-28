package com.example.hope_dog.service.volun.car;

import com.example.hope_dog.dto.centerMember.CenterMemberDTO;
import com.example.hope_dog.dto.member.MemberDTO;
import com.example.hope_dog.dto.volun.car.CarDTO;
import com.example.hope_dog.mapper.volun.car.CarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {


    private final CarMapper carMapper;
    //목록조회 메서드
    public List<CarDTO> getCarList() {
        //carmapper로 정보 조회
        List<CarDTO> carList = carMapper.selectAllCars();

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
    public List<CarDTO> getCarListByCate(String cate) {
        List<CarDTO> carList = carMapper.selectCate(cate);

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
    //게시글 상세페이지 작성자
//    public CarDTO getCarPost(Long no) {
//        CarDTO car = carMapper.selectCarById(no);
//
//        if (car.getCarWriter() % 2 == 0) {
//            // 센터회원인 경우 작성자 센터회원 정보 조회
//            CenterMemberDTO centerMember = carMapper.selectCenterMemberByNo(car.getCarWriter());
//            if (centerMember != null) {
//                car.setCenterMemberName(centerMember.getCenterMemberName());
//            }
//        } else {
//            // 일반회원인 경우
//            MemberDTO member = carMapper.selectMemberByNo(car.getCarWriter());
//            if (member != null) {
//                car.setMemberNickname(member.getMemberNickname());
//            }
//        }
//
//        return car;
//    }
//
//    @Autowired
//    public CarService(CarMapper carMapper) {
//        this.carMapper = carMapper;
//    }

    // 게시글과 댓글 조회
    public CarDTO getCarWithComments(Long carNo) {
        return carMapper.selectCarWithComment(carNo);
    }
}
