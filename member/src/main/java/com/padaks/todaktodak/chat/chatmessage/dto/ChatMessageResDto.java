package com.padaks.todaktodak.chat.chatmessage.dto;

import com.padaks.todaktodak.chat.chatmessage.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageResDto {
    private Long messageId;
    private String senderName;
    private String contents;
    private String createdAt;

    public static ChatMessageResDto fromEntity(ChatMessage message) {
        return ChatMessageResDto.builder()
                .messageId(message.getId())
                .senderName(message.getSender().getName())
                .contents(message.getContents())
                .createdAt(message.getCreatedAt().toString())
                .build();
    }

}