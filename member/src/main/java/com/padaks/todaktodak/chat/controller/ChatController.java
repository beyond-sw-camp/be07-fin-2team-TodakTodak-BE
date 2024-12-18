package com.padaks.todaktodak.chat.controller;

import com.padaks.todaktodak.chat.chatmessage.dto.ChatMessageResDto;
import com.padaks.todaktodak.chat.chatroom.domain.ChatRoom;
import com.padaks.todaktodak.chat.chatroom.dto.ChatRoomListResDto;
import com.padaks.todaktodak.chat.chatroom.dto.ChatRoomMemberInfoResDto;
import com.padaks.todaktodak.chat.chatroom.dto.CsMemberResDto;
import com.padaks.todaktodak.chat.service.ChatService;
import com.padaks.todaktodak.common.dto.CommonResDto;
import com.padaks.todaktodak.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatController {
    private final ChatService chatService;

    // 채팅방 생성 (회원이 새로운 상담을 시작할 때)
    @PostMapping("/chatroom/create")
    public ResponseEntity<?> createChatRoom() {
        ChatRoom chatRoom = chatService.createChatRoom();
        return new ResponseEntity<>(new CommonResDto(HttpStatus.CREATED, "채팅방 생성 성공", chatRoom.getId()),HttpStatus.CREATED);
    }

    // 채팅방의 모든 메시지 조회
    @GetMapping("/chatroom/{chatRoomId}/messages")
    public ResponseEntity<?> getMessages(@PathVariable Long chatRoomId) {
        List<ChatMessageResDto> messages = chatService.getMessages(chatRoomId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "채팅방 메시지 조회 성공", messages), HttpStatus.OK);
    }

    // 채팅방 삭제
    @DeleteMapping("/chatroom/delete/{chatroomId}")
    public ResponseEntity<?> deleteChatRoom(@PathVariable Long chatRoomId){
        chatService.deleteChatRoom(chatRoomId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "채팅방 삭제 성공", null), HttpStatus.OK);
    }

    // 해당 회원이 속한 채팅방 리스트 (회원입장 채팅방 리스트)
    @GetMapping("/chatroom/list/member")
    private ResponseEntity<?> getMemberChatRoomList(Pageable pageable) {
        Page<ChatRoomListResDto> chatRoomList = chatService.getMemberChatRoomList(pageable);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "회원 채팅방 리스트 조회 성공", chatRoomList), HttpStatus.OK);
    }

    // admin 채팅방 리스트 (admin입장 채팅방 리스트)
    @GetMapping("/chatroom/list/admin")
    private ResponseEntity<?> getAdminChatRoomList(@PageableDefault(size = 7)Pageable pageable) {
        Page<ChatRoomListResDto> chatRoomList = chatService.getAdminChatRoomList(pageable);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "admin 채팅방 리스트 조회 성공", chatRoomList), HttpStatus.OK);
    }

    //    채팅방 id 로 멤버 정보 검색 (todakAdmin)
    @GetMapping("/member/info/{id}")
    public ResponseEntity<?> memberInfo(@PathVariable Long id){
        CsMemberResDto dto = chatService.getMemberInfo(id);
        return ResponseEntity.ok(dto);
    }

    // 채팅방 참여자 회원 정보조회 (토닥 admin만 가능)
    @GetMapping("/member/info/chatroom/{chatRoomId}")
    private ResponseEntity<?> getChatRoomMemberInfo(@PathVariable Long chatRoomId){
        try{
            ChatRoomMemberInfoResDto chatRoomMemberInfoResDto
                    = chatService.getChatRoomMemberInfo(chatRoomId);
            return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "채팅참여자 회원정보 조회 성공", chatRoomMemberInfoResDto), HttpStatus.OK);
        }catch (BaseException e) {
            return new ResponseEntity<>(new CommonResDto(HttpStatus.BAD_REQUEST, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}