package com.example.hope_dog.dto.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MypageAdoptListDTO {
    private String adoptTitle;
    private String adoptStatus;
    private Date adoptPeriodEnd;
    private Long memberNo;
}
