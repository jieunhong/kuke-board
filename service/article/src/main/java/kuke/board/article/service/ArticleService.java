package kuke.board.article.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import kuke.board.article.entity.Article;
import kuke.board.article.repository.ArticleRepository;
import kuke.board.article.service.request.ArticleCreateRequest;
import kuke.board.article.service.request.ArticleUpdateRequest;
import kuke.board.article.service.response.ArticleResponse;
import kuke.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse create(ArticleCreateRequest articleCreateRequest) {
        Article article = Article.create(
            snowflake.nextId(),
            articleCreateRequest.getTitle(),
            articleCreateRequest.getContent(),
            articleCreateRequest.getBoardId(),
            articleCreateRequest.getWriterId());
        articleRepository.save(article);
        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest articleUpdateRequest) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(articleUpdateRequest.getTitle(), articleUpdateRequest.getContent());
        articleRepository.save(article);
        return ArticleResponse.from(article);
    }

    @Transactional
    public void delete(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        articleRepository.delete(article);
    }

    @Transactional
    public ArticleResponse read(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        return ArticleResponse.from(article);
    }
}
