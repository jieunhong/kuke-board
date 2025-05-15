package kuke.board.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    private Long commentId;
    private String content;
    private Long articleId;
    private Long parentCommentId;
    private Long writerId;
    private LocalDateTime createdAt;
    private boolean deleted;


    public static Comment create(Long id, String content, Long articleId, Long parentCommentId, Long writerId) {
        Comment comment = new Comment();
        comment.commentId = id;
        comment.content = content;
        comment.articleId = articleId;
        comment.parentCommentId = parentCommentId == null ? id : parentCommentId;
        comment.writerId = writerId;
        comment.createdAt = LocalDateTime.now();
        comment.deleted = false;
        return comment;
    }

    public boolean isRoot() {
        return parentCommentId.longValue() == commentId.longValue();
    }

    public void delete() {
        this.deleted = true;
    }
}
