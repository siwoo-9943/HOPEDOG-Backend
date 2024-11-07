package com.example.hope_dog.mapper.admin;

import com.example.hope_dog.dto.admin.AdminFileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminFileMapper {
    void insertFile(@Param("file") AdminFileDTO file);

    List<AdminFileDTO> selectFileListByNoticeNo(@Param("noticeNo") Long noticeNo);

    AdminFileDTO selectFileByNo(@Param("fileNo") Long fileNo);

    void deleteFile(@Param("fileNo") Long fileNo);
}