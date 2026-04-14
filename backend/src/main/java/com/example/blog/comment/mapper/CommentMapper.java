package com.example.blog.comment.mapper;

import com.example.blog.comment.model.CommentCreateRequest;
import com.example.blog.comment.model.CommentForDelete;
import com.example.blog.comment.model.CommentView;
import com.example.blog.comment.model.Commenter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<CommentView> findViewsByPostId(Long postId);
    CommentForDelete findForDelete(Long commentId);

    void insertCommenter(Commenter commenter);
    void insertGuest(@Param("commenterId") Long commenterId,
                     @Param("name") String name,
                     @Param("passwordHash") String passwordHash);
    void insertComment(@Param("postId") Long postId,
                       @Param("commenterId") Long commenterId,
                       @Param("req") CommentCreateRequest req,
                       @Param("depth") int depth);

    Long findCommenterIdByUserId(Long userId);
    CommentForDelete findParentForDepth(Long parentCommentId);

    void softDelete(Long commentId);
}
