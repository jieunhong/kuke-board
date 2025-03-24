package kuke.board.article.service.response;

import java.time.LocalDateTime;
import kuke.board.article.entity.Article;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleResponse {
    private Long articleId;
    private String title;
    private String content;
    private Long boardId; // shard key
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ArticleResponse from(Article article) {
        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.articleId = article.getArticleId();
        articleResponse.title = article.getTitle();
        articleResponse.content = article.getContent();
        articleResponse.boardId = article.getBoardId();
        articleResponse.writerId = article.getWriterId();
        articleResponse.createdAt = article.getCreatedAt();
        articleResponse.modifiedAt = article.getModifiedAt();
        return articleResponse;
    }
}
