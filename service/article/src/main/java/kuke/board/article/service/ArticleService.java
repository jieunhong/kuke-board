package kuke.board.article.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import kuke.board.article.entity.Article;
import kuke.board.article.repository.ArticleRepository;
import kuke.board.article.service.request.ArticleCreateRequest;
import kuke.board.article.service.request.ArticleUpdateRequest;
import kuke.board.article.service.response.ArticlePageResponse;
import kuke.board.article.service.response.ArticleResponse;
import kuke.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    public ArticlePageResponse readAll(Long boardId, Long page, Long pageSize) {
        List<ArticleResponse> articles = articleRepository.findAll(boardId, (page - 1) * pageSize,
                                                                   pageSize)
            .stream()
            .map(ArticleResponse::from)
            .toList();

        Long count = articleRepository.count(boardId, PageLimitCalculator.calculatePageLimit(page, pageSize, 10L));
        return ArticlePageResponse.of(articles, count);
    }

    public List<ArticleResponse> readAllInfinite(Long boardId, Long pageSize, Long lastArticleId) {
        List<Article> articles =
            lastArticleId == null ?
            articleRepository.findAllInfiniteScroll(boardId, pageSize)
            : articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId);

        return articles.stream().map(ArticleResponse::from).toList();
    }

}
