package com.padaks.todaktodak.chat.cs.repository;

import com.padaks.todaktodak.chat.cs.domain.Cs;
import com.padaks.todaktodak.chat.cs.domain.CsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Repository
public interface CsRepository extends JpaRepository<Cs, Long> {
    // memberId로 CS 목록 조회
    @Query("SELECT cs FROM Cs cs JOIN cs.chatRoom cr WHERE cr.member.id = :memberId")
    List<Cs> findByMemberId(@Param("memberId") Long memberId);

    // memberId로 CS 목록 조회 (deletedAt이 null인 경우만)
    @Query("SELECT cs FROM Cs cs JOIN cs.chatRoom cr WHERE cr.member.id = :memberId AND cs.deletedAt IS NULL")
    Page<Cs> findByMemberIdAndDeletedAtIsNull(@Param("memberId") Long memberId, Pageable pageable);

    // 채팅방 ID로 CS 목록 조회 (deletedAt이 null인 경우만)
    @Query("SELECT cs FROM Cs cs WHERE cs.chatRoom.id = :chatRoomId AND cs.deletedAt IS NULL")
    List<Cs> findByChatRoomIdAndDeletedAtIsNull(@Param("chatRoomId") Long chatRoomId);

    // id 기준으로 삭제되지 않은 CS 조회
    Optional<Cs> findByIdAndDeletedAtIsNull(Long id);

    default Cs findByIdOrThrow(Long id){
        return findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("CS가 존재하지 않습니다."));
    }
    Page<Cs> findByChatRoom_Member_NameContainingOrChatRoom_Member_MemberEmailContainingAndCsStatus(String name, String email, CsStatus csStatus, Pageable pageable);

    Page<Cs> findByChatRoom_Member_NameContainingOrChatRoom_Member_MemberEmailContaining(String memberName, String memberEmail, Pageable pageable);

    Page<Cs> findByCsStatus(CsStatus csStatus, Pageable pageable);
}
