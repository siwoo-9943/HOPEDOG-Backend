package com.example.hope_dog.mapper.volun.car;

import com.example.hope_dog.dto.volun.car.CarDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarMapper {
    public List<CarDTO> CarMain();

   List<CarDTO> selectAllCars();

}
