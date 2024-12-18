package com.padaks.todaktodak.child.controller;

import com.padaks.todaktodak.child.dto.*;
import com.padaks.todaktodak.child.service.ChildService;
import com.padaks.todaktodak.common.dto.CommonErrorDto;
import com.padaks.todaktodak.common.dto.CommonResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/child")
public class ChildController {
    private final ChildService childService;
    @PostMapping("/create")
    public ResponseEntity<CommonResDto> registerChild(ChildRegisterReqDto dto, @RequestPart(required = false) MultipartFile image){
        ChildRegisterResDto childRegisterResDto = childService.createChild(dto.getName(), dto.getSsn(),image);
        if (childRegisterResDto.getParents() != null) {
            return new ResponseEntity<>(new CommonResDto(HttpStatus.BAD_REQUEST,"이미 등록된 자녀입니다.",childRegisterResDto),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CommonResDto(HttpStatus.CREATED,"자녀 등록 성공",null),HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CommonResDto> myChild(){
        List<ChildResDto> childList = childService.myChild();
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK,"자녀 조회 성공",childList),HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<CommonResDto> updateChild(ChildUpdateReqDto dto, @RequestPart(required = false) MultipartFile image){
        childService.updateChild(dto, image);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK,"자녀 수정 성공",null),HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<CommonResDto> updateChild(@PathVariable Long id){
        childService.deleteChild(id);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK,"자녀 삭제 성공",null),HttpStatus.OK);
    }

    @PostMapping("/share")
    public ResponseEntity<?> shareChild(@RequestBody ChildShareReqDto dto){
        try {
            childService.shareChild(dto);
            return new ResponseEntity<>(new CommonResDto(HttpStatus.OK,"자녀 공유 성공",null),HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(commonErrorDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/detail/{id}")
    public ChildResDto getMyChild(@PathVariable Long id){
        return childService.getMyChild(id);
    }
}
