package com.example.hope_dog.mapper.admin;

import com.example.hope_dog.dto.admin.AdminMemberDTO;
import com.example.hope_dog.dto.admin.AdminPostDTO;
import com.example.hope_dog.dto.admin.AdminSessionDTO;
import com.example.hope_dog.dto.admin.AdminReportDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMapper {
    Optional<Long> selectId(@Param("adminId") String adminId, @Param("adminPw") String adminPw);

    Optional<AdminSessionDTO> selectLoginInfo(@Param("adminId") String adminId, @Param("adminPw") String adminPw);

    List<AdminMemberDTO> selectMemberList();

    List<AdminReportDTO> selectReportList();

    List<AdminPostDTO> selectPostList();
}