package kuke.board.like.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleLike {

    @Id
    private Long articleLikeId;
    private Long articleId;
    private Long userId;
    private LocalDateTime createdAt;


    public static ArticleLike create(Long id, Long articleId, Long userId) {
        ArticleLike articleLike = new ArticleLike();
        articleLike.articleLikeId = id;
        articleLike.articleId = articleId;
        articleLike.userId = userId;
        articleLike.createdAt = LocalDateTime.now();
        return articleLike;
    }
}
