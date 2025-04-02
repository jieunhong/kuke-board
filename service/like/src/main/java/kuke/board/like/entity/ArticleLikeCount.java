package kuke.board.like.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleLikeCount {

    @Id
    private Long articleId;
    private Long likeCount;
    @Version
    private Long version;


    public static ArticleLikeCount init(Long id, Long likeCount) {
        ArticleLikeCount articleLikeCount = new ArticleLikeCount();
        articleLikeCount.articleId = id;
        articleLikeCount.likeCount = likeCount;
        return articleLikeCount;
    }


    public void increase() {
        this.likeCount++;
    }

    public void decrease() {
        this.likeCount--;
    }
}
