package kuke.board.article.service.response;

import java.time.LocalDateTime;
import java.util.List;
import kuke.board.article.entity.Article;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticlePageResponse {
    List<ArticleResponse> articles;
    Long articleCount;

    public static ArticlePageResponse of(List<ArticleResponse>  articles, Long articleCount) {
        ArticlePageResponse articlePageResponse = new ArticlePageResponse();
        articlePageResponse.articles = articles;
        articlePageResponse.articleCount = articleCount;
        return articlePageResponse;
    }
}
