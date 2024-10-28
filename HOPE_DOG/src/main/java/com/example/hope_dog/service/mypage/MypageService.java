package com.example.hope_dog.service.mypage;

import com.example.hope_dog.dto.adopt.adopt.MainDTO;
import com.example.hope_dog.dto.member.MemberSessionDTO;
import com.example.hope_dog.dto.mypage.MypageAdoptListDTO;
import com.example.hope_dog.mapper.member.MemberMapper;
import com.example.hope_dog.mapper.mypage.MypageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageService {

    private final MemberMapper memberMapper;
    private final MypageMapper mypageMapper;

    @Autowired
    public MypageService(MemberMapper memberMapper, MypageMapper mypageMapper) {
        this.memberMapper = memberMapper;
        this.mypageMapper = mypageMapper;
    }

    public MemberSessionDTO getMemberInfo(String memberId) {
//        return memberMapper.findMemberById(memberId);
//        return mypageMapper.findMemberByName(memberId);
        return mypageMapper.findMemberById(memberId);

//        return memberMapper.selectMemberNo(getMemberInfo());
    }


        // 회원의 입양 목록을 가져오는 메서드
        public List<MypageAdoptListDTO> mypageAdoptList(Long memberNo) {
            // 매퍼의 메서드를 호출하여 입양 목록을 조회
            return mypageMapper.mypageAdoptList(memberNo);
        }
}


