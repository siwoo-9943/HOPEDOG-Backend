package com.example.hope_dog.mapper.volun.volun;

import com.example.hope_dog.dto.adopt.adopt.AdoptCommentDTO;
import com.example.hope_dog.dto.adopt.adopt.AdoptDetailDTO;
import com.example.hope_dog.dto.adopt.adopt.AdoptWriteDTO;
import com.example.hope_dog.dto.page.Criteria;
import com.example.hope_dog.dto.volun.volun.VolunCommentDTO;
import com.example.hope_dog.dto.volun.volun.VolunDetailDTO;
import com.example.hope_dog.dto.volun.volun.VolunMainDTO;
import com.example.hope_dog.dto.volun.volun.VolunWriteDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VolunMapper {

    //봉사메인
    List<VolunMainDTO> main();

    //봉사 게시글(페이지네이션 포함)
    List<VolunMainDTO> selectAll();

    int selectTotal();

    List<VolunMainDTO> selectAllPage(Criteria criteria);

    //봉사 모집중인 게시글(페이지네이션 포함)
    List<VolunMainDTO> selectAllKeep();

    int selectTotalKeep();

    List<VolunMainDTO> selectAllPageKeep(Criteria criteria);

    //봉사 상세글
    List<VolunDetailDTO> volunDetail(Long volunNo);

    //댓글불러오기
    List<VolunCommentDTO> volunComment(Long volunNo);

    //입양글작성
    void volunWrite(VolunWriteDTO volunWriteDTO);

    //입양글삭제처리
    void volunDelete(VolunDetailDTO volunDetailDTO);

    //입양글수정
    void volunModify(VolunWriteDTO volunWriteDTO);
}
