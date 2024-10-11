package com.padaks.todaktodak.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalInfoDto {
    private String name; //병원이름
    private String dong; //병원 동
}
