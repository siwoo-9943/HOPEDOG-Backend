package com.example.hope_dog.service.admin;

import com.example.hope_dog.dto.admin.AdminSessionDTO;
import com.example.hope_dog.dto.admin.MemberDTO;
import com.example.hope_dog.dto.admin.ReportDTO;
import com.example.hope_dog.mapper.admin.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper adminMapper;

    public Long selectId(String adminId, String adminPw) {
        return adminMapper.selectId(adminId, adminPw).orElseThrow(() -> new IllegalStateException("존재하지 않는 관리자 정보"));
    }

    public AdminSessionDTO findLoginInfo(String adminId, String adminPw) {
        return adminMapper.selectLoginInfo(adminId, adminPw).orElseThrow(() -> new IllegalStateException("존재하지 않는 관리자 정보"));
    }

    public List<MemberDTO> selectMemberList(){
        return adminMapper.selectMemberList();
    }

    public List<ReportDTO> selectReportList(){
        return adminMapper.selectReportList();
    }
}
