package com.example.hope_dog.service.donation;

import com.example.hope_dog.dto.donation.DonationListDTO;
import com.example.hope_dog.dto.donation.DonationMainDTO;
import com.example.hope_dog.dto.donation.DonationViewDTO;
import com.example.hope_dog.dto.donation.DonationWriteDTO;
import com.example.hope_dog.dto.page.Criteria;
import com.example.hope_dog.mapper.donation.DonationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service // 비즈니스 로직 처리
@Transactional // 트랜잭션을 지원
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class DonationService {

    private final DonationMapper donationMapper;

    // List
    public List<DonationListDTO> getDonationList() {
        return donationMapper.donationList();
    }


    //페이지네이션 관련 service
    public List<DonationMainDTO> findAll() {
        return donationMapper.selectAll();
    }


    public int findTotal(){
        return donationMapper.selectTotal();
    }

    public List<DonationMainDTO> findAllPage(Criteria criteria){
        return donationMapper.selectAllPage(criteria);
    }



    // View
    public List<DonationViewDTO> getDonationViewList(Long donaNo) {
        return donationMapper.donationView(donaNo);
    }

    // Write
    public void donationWrite (DonationWriteDTO donationWriteDTO) {
        donationMapper.donationWrite(donationWriteDTO);
    }




}
