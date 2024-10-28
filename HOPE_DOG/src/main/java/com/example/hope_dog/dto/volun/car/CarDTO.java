package com.example.hope_dog.dto.volun.car;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
public class CarDTO {
    private Long carNo;         // 게시글 번호
    private String carCate;     // 게시글 분류
    private String carTitle;    // 게시글 제목
    private String carContent;  // 게시글 내용
    private Date carRegiDate;   // 게시글 작성일
    private Date carUpdateDate; // 수정일
    private Long carWriter;      // 게시글 작성자

    private Long memberNo;      //일반회원넘버
    private String memberNickname;  //일반회원 닉네임
    private Long centerMemberNo;    //센터회원 넘버
    private String centerMemberName;    //센터회원 이름

    private List<CarCommentDTO> comments; // 댓글 리스트
//    하나의 게시글에 여러 댓글이 달리 수 있어서 게시글 ｄｔｏ내에 댓글 ｄｔｏ리스트를 포함시켜서
//    한번의  호출로 게시글과 해당 댓글을 같이 가져올 수 있음
}
