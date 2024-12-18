package com.padaks.todaktodak.comment.domain;

import com.padaks.todaktodak.comment.dto.CommentDetailDto;
import com.padaks.todaktodak.comment.dto.CommentUpdateReqDto;
import com.padaks.todaktodak.common.domain.BaseTimeEntity;

import com.padaks.todaktodak.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column(nullable = false)
    private String doctorEmail; //post 작성자 / 의사
    @Column(nullable = false, length = 3000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    //댓글 대댓글, 대대댓글... 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    private String name;
    private String profileImg;

    public CommentDetailDto listFromEntity(){
        return CommentDetailDto.builder()
                .id(this.id)
                .doctorEmail(this.doctorEmail)
                .content(this.content)
                .createdTimeAt(this.getCreatedTimeAt())
                .updatedTimeAt(this.getUpdatedTimeAt())
                .build();
    }

    public Comment update(CommentUpdateReqDto dto){
        this.content = dto.getContent();
        return this;
    }

}
