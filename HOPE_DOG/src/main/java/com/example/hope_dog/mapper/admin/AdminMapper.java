package com.example.hope_dog.mapper.admin;

import com.example.hope_dog.dto.admin.AdminDTO;
import com.example.hope_dog.dto.admin.AdminSessionDTO;
import com.example.hope_dog.dto.admin.MemberDTO;
import com.example.hope_dog.dto.admin.ReportDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMapper {
    Optional<Long> selectId(@Param("adminId") String adminId, @Param("adminPw") String adminPw);

    Optional<AdminSessionDTO> selectLoginInfo(@Param("adminId") String adminId, @Param("adminPw") String adminPw);

    List<MemberDTO> selectMemberList();

    List<ReportDTO> selectReportList();
}
