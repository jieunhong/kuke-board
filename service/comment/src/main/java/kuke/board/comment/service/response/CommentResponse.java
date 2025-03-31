package kuke.board.comment.service.response;

import java.time.LocalDateTime;
import kuke.board.comment.entity.Comment;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentResponse {
    private Long commentId;
    private String content;
    private Long parentCommentId;
    private Long articleId;
    private Long writerId;
    private Boolean deleted;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.commentId = comment.getCommentId();
        response.content = comment.getContent();
        response.parentCommentId = comment.getParentCommentId();
        response.articleId = comment.getArticleId();
        response.writerId = comment.getWriterId();
        response.deleted = comment.isDeleted();
        response.createdAt = comment.getCreatedAt();
        return response;
    }
}
