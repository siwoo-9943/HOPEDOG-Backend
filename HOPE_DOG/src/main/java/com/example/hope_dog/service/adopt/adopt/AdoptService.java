package com.example.hope_dog.service.adopt.adopt;

import com.example.hope_dog.dto.adopt.adopt.AdoptDetailDTO;
import com.example.hope_dog.dto.adopt.adopt.AdoptMainDTO;
import com.example.hope_dog.dto.adopt.adopt.MainDTO;
import com.example.hope_dog.dto.page.Criteria;
import com.example.hope_dog.mapper.adopt.adopt.AdoptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdoptService {

    private final AdoptMapper adoptMapper;

    public List<MainDTO> getMainList() {
        return adoptMapper.main();
    }

    public List<AdoptDetailDTO> getAdoptDetail(Long adoptNo) {
        return adoptMapper.adoptDetail(adoptNo); // adoptMapper의 메서드 호출
    }

    public List<AdoptMainDTO> findAll() {
        return adoptMapper.selectAll();
    }

    public int findTotal(){
        return adoptMapper.selectTotal();
    }

    public List<AdoptMainDTO> findAllPage(Criteria criteria){
        return adoptMapper.selectAllPage(criteria);
    }


}

