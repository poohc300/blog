package com.example.blog.comment.service;

import com.example.blog.comment.mapper.CommentMapper;
import com.example.blog.comment.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final PasswordEncoder passwordEncoder;

    public List<CommentView> getByPostId(Long postId) {
        return commentMapper.findViewsByPostId(postId);
    }

    public void create(Long postId, CommentCreateRequest req, Long adminUserId) {
        // depth 계산
        int depth = 0;
        if (req.getParentCommentId() != null) {
            CommentForDelete parent = commentMapper.findParentForDepth(req.getParentCommentId());
            if (parent != null) depth = parent.getDepth() + 1;
        }

        Long commenterId;

        if (adminUserId != null) {
            // 관리자: 기존 commenter_id 사용
            commenterId = commentMapper.findCommenterIdByUserId(adminUserId);
        } else {
            // 게스트: 유효성 검사
            if (req.getGuestName() == null || req.getGuestName().isBlank())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이름을 입력해주세요.");
            if (req.getGuestPassword() == null || req.getGuestPassword().isBlank())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호를 입력해주세요.");

            // commenter 생성
            Commenter commenter = new Commenter();
            commenter.setType("guest");
            commentMapper.insertCommenter(commenter);
            commenterId = commenter.getId();

            // guest 생성
            String hash = passwordEncoder.encode(req.getGuestPassword());
            commentMapper.insertGuest(commenterId, req.getGuestName(), hash);
        }

        commentMapper.insertComment(postId, commenterId, req, depth);
    }

    public void delete(Long commentId, String guestPassword, boolean isAdmin) {
        CommentForDelete comment = commentMapper.findForDelete(commentId);
        if (comment == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");
        if (comment.isDeleted())
            throw new ResponseStatusException(HttpStatus.GONE, "이미 삭제된 댓글입니다.");

        if (isAdmin) {
            commentMapper.softDelete(commentId);
            return;
        }

        // 게스트 본인 확인
        if (!"guest".equals(comment.getCommenterType()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        if (guestPassword == null || !passwordEncoder.matches(guestPassword, comment.getGuestPasswordHash()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.");

        commentMapper.softDelete(commentId);
    }
}
