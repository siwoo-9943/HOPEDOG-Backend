package com.example.hope_dog.service.volun.volun;

import com.example.hope_dog.dto.adopt.adopt.AdoptCommentDTO;
import com.example.hope_dog.dto.adopt.adopt.AdoptDetailDTO;
import com.example.hope_dog.dto.page.Criteria;
import com.example.hope_dog.dto.volun.volun.VolunCommentDTO;
import com.example.hope_dog.dto.volun.volun.VolunDetailDTO;
import com.example.hope_dog.dto.volun.volun.VolunMainDTO;
import com.example.hope_dog.mapper.volun.volun.VolunMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VolunService {
    private final VolunMapper volunMapper;

    //메인 게시글
    public List<VolunMainDTO> getVolunMainList() {
        return volunMapper.main();
    }

    //봉사 전체게시글 조회 + 페이지네이션 포함
    public List<VolunMainDTO> findAll() {
        return volunMapper.selectAll();
    }

    public int findTotal(){
        return volunMapper.selectTotal();
    }

    public List<VolunMainDTO> findAllPage(Criteria criteria){
        return volunMapper.selectAllPage(criteria);
    }

    //봉사 모집중게시글 조회 + 페이지네이션 포함
    public List<VolunMainDTO> findAllKeep() {
        return volunMapper.selectAllKeep();
    }

    public int findTotalKeep(){
        return volunMapper.selectTotalKeep();
    }

    public List<VolunMainDTO> findAllPageKeep(Criteria criteria){
        return volunMapper.selectAllPageKeep(criteria);
    }

    //게시글 조회
    public List<VolunDetailDTO> getVolunDetail(Long volunNo) {
        return volunMapper.volunDetail(volunNo); // adoptMapper의 메서드 호출
    }

    //댓글불러오기
    public List<VolunCommentDTO> getVolunComment(Long volunNo) {
        return volunMapper.volunComment(volunNo); // adoptMapper의 메서드 호출
    }
}
