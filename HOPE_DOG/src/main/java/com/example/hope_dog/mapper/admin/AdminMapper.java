package com.example.hope_dog.mapper.admin;

import com.example.hope_dog.dto.admin.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMapper {
    Optional<Long> selectId(@Param("adminId") String adminId, @Param("adminPw") String adminPw);

    Optional<AdminSessionDTO> selectLoginInfo(@Param("adminId") String adminId, @Param("adminPw") String adminPw);

    List<AdminMemberDTO> selectMemberList();

    List<AdminMemberDTO> searchMemberByKeyword(@Param("keyword") String keyword);

    AdminMemberDTO selectMemberByNo(@Param("memberNo") Long memberNo);

    void deleteMembers(@Param("memberNoList") List<Long> memberNoList);

    void deleteCenterMembers(@Param("centerMemberNoList") List<Long> centerMemeberNoList);

    List<AdminCenterMemberDTO> selectCenterMemberList();

    AdminCenterMemberDTO selectNotPassedCenterMemberByNo(@Param("centerMemberNo") Long centerMemberNo);

    List<AdminCenterMemberDTO> selectPassedCenterMemberList();

    List<AdminCenterMemberDTO> searchPassedCenterMemberByKeyword(@Param("keyword") String keyword);

    AdminCenterMemberDTO selectPassedCenterMemberByNo(@Param("centerMemberNo") Long centerMemberNo);

    List<AdminReportDTO> selectReportList();

    List<AdminPostDTO> selectPostList();

    List<AdminPostDTO> searchPostByKeyword(@Param("keyword") String keyword);

    AdminPostDTO selectPostDetail(@Param("postType") String postType,@Param("postNo") Long postNo);

    List<AdminCommentDTO> selectCommentListByPostNo(@Param("postType") String postType, @Param("postNo") Long postNo);

    List<AdminCommentDTO> selectCommentList();

    List<AdminCommentDTO> searchCommentByKeyword(@Param("keyword") String keyword);

    List<AdminNoticeDTO> selectNoticeList();

    AdminNoticeDTO selectNoticeDetail(@Param("noticeNo") Long noticeNo);

    List<AdminNoticeDTO> searchNoticeByKeyword(@Param("keyword") String keyword);

    List<AdminAdoptRequestDTO> selectAdoptRequestList();

    List<AdminProtectRequestDTO> selectProtectRequestList();

    List<AdminVolunRequestDTO> selectVolunRequestList();

    List<AdminNoteSendDTO> selectNoteSendList();

    List<AdminNoteReceiveDTO> selectNoteReceiveList();
}