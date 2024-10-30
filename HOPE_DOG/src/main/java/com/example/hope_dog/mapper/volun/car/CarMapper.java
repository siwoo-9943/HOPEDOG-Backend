package com.example.hope_dog.mapper.volun.car;

import com.example.hope_dog.controller.centermember.CenterMemberController;
import com.example.hope_dog.dto.centerMember.CenterMemberDTO;
import com.example.hope_dog.dto.member.MemberDTO;
import com.example.hope_dog.dto.volun.car.CarCommentDTO;
import com.example.hope_dog.dto.volun.car.CarDTO;
import com.example.hope_dog.dto.volun.car.CarDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface CarMapper {
    //카풀 게시판 목록
   List<CarDTO> selectAllCars();

    //카테고리 분류별 게시글 조회
    List<CarDTO> selectCate(String cate);


   // 게시글 상세 조회 (게시글과 댓글 정보 포함)
   CarDetailDTO selectCarDetail(@Param("carNo") Long carNo);

    // 댓글 조회 (특정 게시글의 댓글 리스트)
    List<CarCommentDTO> selectCommentsByCarNo(@Param("carNo") Long carNo);

   //검색
    List<CarDTO> retrieve(Map<String,Object>params);

    //일반회원 조회
    MemberDTO selectMemberByNo(Long memberNo);
    //센터회원 조회
    CenterMemberDTO selectCenterMemberByNo(Long centerMemberNo);



}
