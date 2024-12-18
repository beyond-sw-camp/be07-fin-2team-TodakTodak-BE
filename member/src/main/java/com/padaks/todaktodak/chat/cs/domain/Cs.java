package com.padaks.todaktodak.chat.cs.domain;

import com.padaks.todaktodak.chat.chatroom.domain.ChatRoom;
import com.padaks.todaktodak.chat.cs.dto.CsListResDto;
import com.padaks.todaktodak.chat.cs.dto.CsResDto;
import com.padaks.todaktodak.chat.cs.dto.CsUpdateReqDto;
import com.padaks.todaktodak.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Cs extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cs_id")
    private Long id;

    @Column(length = 3000)
    private String csContents; // 상담내역

    @Enumerated(EnumType.STRING)
    private CsStatus csStatus; // 상담처리상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 채팅방

    public void updateCs(CsUpdateReqDto dto,
                         ChatRoom chatRoom){
        this.csContents = dto.getCsContents();
        this.csStatus = dto.getCsStatus();
        this.chatRoom = chatRoom;
    }

    public CsListResDto listFromEntity(){
        return CsListResDto.builder()
                .id(this.id)
                .csContents(this.csContents)
                .csStatus(this.csStatus.getValue())
                .chatRoomId(this.chatRoom.getId())
                .memberEmail(this.chatRoom.getMember().getMemberEmail())
                .memberName(this.chatRoom.getMember().getName())
                .build();
    }
}
