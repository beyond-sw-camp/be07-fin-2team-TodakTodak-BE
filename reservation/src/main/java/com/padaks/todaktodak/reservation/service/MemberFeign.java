package com.padaks.todaktodak.reservation.service;

import com.padaks.todaktodak.common.config.FeignConfig;
import com.padaks.todaktodak.common.dto.CommonResDto;
import com.padaks.todaktodak.reservation.dto.MemberResDto;
import com.padaks.todaktodak.reservation.dto.NotificationReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-service", configuration = FeignConfig.class)
public interface MemberFeign {

    @PostMapping(value = "/notification/create")
    CommonResDto sendReservationNotification(@RequestBody NotificationReqDto dto);

    @GetMapping("/member/get/{email}")
    MemberResDto getMemberByEmail(@PathVariable String email);
}
