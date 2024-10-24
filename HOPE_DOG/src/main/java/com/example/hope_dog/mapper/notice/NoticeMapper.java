package com.example.hope_dog.mapper.notice;

import com.example.hope_dog.dto.notice.NoticeListDTO;
import com.example.hope_dog.dto.notice.NoticeViewDTO;
import com.example.hope_dog.dto.notice.page.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NoticeMapper {
//    void insertBoard(BoardWriteDTO boardWriteDTO);

//    void updateBoard(BoardUpdateDTO boardUpdateDTO);

    Optional<NoticeViewDTO> selectById(Long noticeNo);

    List<NoticeListDTO> selectAll();

    int selectTotal();

    List<NoticeListDTO> selectAllPage(Criteria criteria);

//    void deleteBoard(Long boardId);

}
