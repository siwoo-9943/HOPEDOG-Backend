package com.example.hope_dog.mapper.donation;

import com.example.hope_dog.dto.donation.DonationListDTO;
import com.example.hope_dog.dto.donation.DonationMainDTO;
import com.example.hope_dog.dto.donation.DonationViewDTO;
import com.example.hope_dog.dto.donation.DonationWriteDTO;
import com.example.hope_dog.dto.page.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DonationMapper {

    // 기부 글 목록
    List<DonationListDTO> donationList();

    //페이지네이션
    List<DonationMainDTO> selectAll();

//    List<DonationMainDTO> donationMainList();

    int selectTotal();

    List<DonationMainDTO> selectAllPage(Criteria criteria);


    // 기부 글 상세
    List<DonationViewDTO> donationView(Long donaNo);

    // 기부 글 작성
    void donationWrite(DonationWriteDTO donationWriteDTO);

}
