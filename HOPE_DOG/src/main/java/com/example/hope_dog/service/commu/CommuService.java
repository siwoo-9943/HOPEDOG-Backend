package com.example.hope_dog.service.commu;

import com.example.hope_dog.dto.centerMember.CenterMemberDTO;
import com.example.hope_dog.dto.commu.CommuCommentDTO;
import com.example.hope_dog.dto.commu.CommuDTO;
import com.example.hope_dog.dto.commu.CommuDetailDTO;
import com.example.hope_dog.dto.commu.CommuReportDTO;
import com.example.hope_dog.dto.member.MemberDTO;
import com.example.hope_dog.dto.page.Criteria;
import com.example.hope_dog.mapper.commu.CommuMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CommuService {

    private final CommuMapper commuMapper;

    //목록 조회 메서드
    public List<CommuDTO> getCommuList(HttpSession session) {
        List<CommuDTO> commuList = commuMapper.commuCatalog();

        // 세션에서 로그인한 회원 정보를 가져옴
        Long memberNo = (Long) session.getAttribute("memberNo");
        Long centerMemberNo = (Long) session.getAttribute("centerMemberNo");

        // 각 게시글 작성자 확인
        for (CommuDTO commu : commuList) {
            Long writerNo = commu.getCommuWriter(); // 작성자 ID

            if (writerNo % 2 == 0) { // 작성자 ID가 짝수일 때
                // 센터회원인 경우 작성자 센터회원 정보 조회
                CenterMemberDTO centerMember = commuMapper.commuCenterMemberByNo(writerNo);
                if (centerMember != null) { // 센터회원 정보가 존재할 때
                    // 센터회원 이름 보여주기
                    commu.setCenterMemberName(centerMember.getCenterMemberName());
                    commu.setCenterMemberNo(writerNo); // 센터회원 번호 설정
                }
            } else { // 작성자 ID가 홀수일 때
                // 일반회원으로
                MemberDTO member = commuMapper.commuMemberByNo(writerNo);
                if (member != null) { // 일반회원 정보가 존재할 때
                    commu.setMemberNickname(member.getMemberNickname());
                    commu.setMemberNo(writerNo); // 일반회원 번호 설정
                }
            }
        }

        return commuList;
    }

    //게시글 클릭시마다 조회수 +1
    public void commuGood(CommuDTO commuDTO) {
        commuMapper.commuGood(commuDTO);
    }

    //인기 게시글 따로 보여주는
    public List<CommuDTO> cateCommuGood() {
        return commuMapper.findCateByGood(); // 인기 게시글을 조회하는 메서드로 수정
    }

    // 카테고리별 게시글 조회
    public List<CommuDTO> getCommuListByCate(String cate) {
        List<CommuDTO> commuList = commuMapper.findCate(cate); // 카테고리별 게시글 조회

        // 각 게시글 작성자 확인
        for (CommuDTO commu : commuList) {
            Long writerNo = commu.getCommuWriter(); // 작성자 ID

            if (writerNo % 2 == 0) { // 작성자 ID가 짝수일 때
                // 센터회원인 경우 작성자 센터회원 정보 조회
                CenterMemberDTO centerMember = commuMapper.commuCenterMemberByNo(writerNo);
                if (centerMember != null) { // 센터회원 정보가 존재할 때
                    // 센터회원 이름과 번호 세팅
                    commu.setCenterMemberName(centerMember.getCenterMemberName());
                    commu.setCenterMemberNo(writerNo); // 센터회원 번호 설정
                }
            } else { // 작성자 ID가 홀수일 때
                // 일반회원으로
                MemberDTO member = commuMapper.commuMemberByNo(writerNo);
                if (member != null) { // 일반회원 정보가 존재할 때
                    commu.setMemberNickname(member.getMemberNickname());
                    commu.setMemberNo(writerNo); // 일반회원 번호 설정
                }
            }
        }

        return commuList; // 작성자 정보가 설정된 결과 리스트 반환
    }
    //게시글 상세

    // 게시글 상세 정보 및 댓글 조회 메소드
    public CommuDetailDTO getCommuDetail(Long commuNo) {

        // 게시글 정보 조회
        CommuDetailDTO commuDetail = commuMapper.selectCommuByNo(commuNo); // 기존 메소드와 동일
        // 작성자 정보 세팅
        setWriterInfo(commuDetail.getCommuWriter(), commuDetail); // writerNo를 전달
        System.out.println("서비스 게시글 작성자 :" + commuDetail.getCommuWriter());

        // 댓글 정보 조회
        List<CommuCommentDTO> comments = commuMapper.selectCommentsByCommuNo(commuNo); // 댓글 리스트 조회
        System.out.println("댓글 : " + comments.size());
        commuDetail.setComments(comments); // 댓글 정보를 DTO에 세팅

        return commuDetail;
    }

    // 작성자 정보 세팅 메소드
    private void setWriterInfo(Long writerNo, CommuDetailDTO commuDetail) {
        if (writerNo % 2 == 0) { // 짝수면 센터회원
            CenterMemberDTO centerMember = commuMapper.commuCenterMemberByNo(writerNo);
            commuDetail.setCenterMemberName(centerMember != null ? centerMember.getCenterMemberName() : null);
        } else { // 홀수면 일반회원
            MemberDTO member = commuMapper.commuMemberByNo(writerNo);
            commuDetail.setMemberNickname(member != null ? member.getMemberNickname() : null);
        }
    }



    // 게시글 작성
    public void writePost(CommuDTO commuDTO) {
        // 작성자 ID가 null인지 확인
        if (commuDTO.getCommuWriter() == null) {
            throw new IllegalArgumentException("작성자 ID가 필요합니다.");
        }

        // 작성자가 일반회원인지 센터회원인지 확인하여 정보 세팅
        Long writerNo = commuDTO.getCommuWriter(); // writerNo로 수정

        if (writerNo % 2 == 0) { // 짝수일 때 센터회원
            CenterMemberDTO centerMember = commuMapper.commuCenterMemberByNo(writerNo);
            if (centerMember != null) {
                commuDTO.setCenterMemberName(centerMember.getCenterMemberName());
            }
        } else { // 홀수일 때 일반회원
            MemberDTO member = commuMapper.commuMemberByNo(writerNo);
            if (member != null) {
                commuDTO.setMemberNickname(member.getMemberNickname());
            }
        }

        // 작성 날짜 및 수정 날짜 설정
        Date now = new Date();
        commuDTO.setCommuRegiDate(now);
        commuDTO.setCommuUpdateDate(now);

        // DB에 게시글 저장
        commuMapper.insertWrite(commuDTO);
    }

    //게시글 삭제
    public void commuDelete(Long commuNo, Long writerNo) {
       CommuDetailDTO commuDetailDTO = commuMapper.selectCommuByNo(commuNo);
        System.out.println("서비스 작성자 No : " + commuDetailDTO.getCommuWriter()+",writerNo : " + writerNo);
       if(commuDetailDTO.getCommuWriter()== writerNo){
           commuMapper.commuDelete(commuDetailDTO);
       }
    }



    //게시글 신고
    public void commuReport(CommuReportDTO commuReportDTO) {
        commuMapper.commuReport(commuReportDTO);
    }








}
