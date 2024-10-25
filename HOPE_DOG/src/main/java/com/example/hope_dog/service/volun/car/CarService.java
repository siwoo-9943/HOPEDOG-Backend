package com.example.hope_dog.service.volun.car;

import com.example.hope_dog.dto.volun.car.CarDTO;
import com.example.hope_dog.mapper.volun.car.CarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final CarMapper carMapper;

    public List<CarDTO> getCarList(){
        return carMapper.selectAllCars();
    }
}
