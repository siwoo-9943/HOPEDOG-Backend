package com.example.hope_dog.mapper.volun.car;

import com.example.hope_dog.dto.volun.car.CarDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CarMapper {
    void insertcar(CarDTO carDTO)
}
