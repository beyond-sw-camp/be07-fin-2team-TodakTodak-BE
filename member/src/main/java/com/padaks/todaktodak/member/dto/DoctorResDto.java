package com.padaks.todaktodak.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DoctorResDto {
    private Long id;
    private String name;
    private String memberEmail;
    private String profileImgUrl;
    private Long hospitalId;
}
