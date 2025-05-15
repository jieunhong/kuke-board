package kuke.board.article.api;

import java.time.LocalDateTime;
import java.util.List;
import kuke.board.article.service.response.ArticlePageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    public void createTest() {
        ArticleResponse response = create(new ArticleCreateRequest("title", "content", 1L, 1L));
        System.out.println(response);
    }

    ArticleResponse create(ArticleCreateRequest articleCreateRequest) {
        return restClient.post()
            .uri("/v1/articles")
            .body(articleCreateRequest)
            .retrieve()
            .body(ArticleResponse.class);
    }

    @Test
    public void readTest() {
        ArticleResponse article = create(new ArticleCreateRequest("title", "content", 1L, 1L));

        ArticleResponse response = read(article.getArticleId());
        System.out.println(response);
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
            .uri("/v1/articles/" + articleId)
            .retrieve()
            .body(ArticleResponse.class);
    }

    @Test
    public void updateTest() {
        ArticleResponse article = create(new ArticleCreateRequest("title", "content", 1L, 1L));

        ArticleResponse response = update(article.getArticleId(), new ArticleUpdateRequest("title2", "content2"));
        System.out.println(response);
    }

    ArticleResponse update(Long articleId, ArticleUpdateRequest articleUpdateRequest) {
        return restClient.put()
            .uri("/v1/articles/" + articleId)
            .body(articleUpdateRequest)
            .retrieve()
            .body(ArticleResponse.class);
    }

    @Test
    public void deleteTest() {
        create(new ArticleCreateRequest("title", "content", 1L, 1L));

        delete(1L);
    }

    void delete(Long articleId) {
        restClient.delete()
            .uri("/v1/articles/" + articleId)
            .retrieve();
    }

    @Test
    public void readAllTest() {
        create(new ArticleCreateRequest("title", "content", 1L, 1L));

        ArticlePageResponse response = readAll();
        System.out.println(response.getArticleCount());
        response.getArticles().forEach(System.out::println);
    }

    ArticlePageResponse readAll() {
        return restClient.get()
            .uri("/v1/articles?boardId=1&pageSize=30&page=50000")
            .retrieve()
            .body(ArticlePageResponse.class);
    }

    @Test
    public void readAllInfiniteTest() {
        create(new ArticleCreateRequest("title", "content", 1L, 1L));

        List<ArticleResponse> response = readAllInfinite();
        response.forEach(System.out::println);

        System.out.println("lastArticleId: " + response.getLast().getArticleId());

        List<ArticleResponse> response2 = readAllInfiniteLast(response.getLast().getArticleId());
        response2.forEach(System.out::println);
    }

    List<ArticleResponse> readAllInfinite() {
        return restClient.get()
            .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5")
            .retrieve()
            .body(new ParameterizedTypeReference<List<ArticleResponse>>() {});
    }

    List<ArticleResponse> readAllInfiniteLast(Long lastArticleId) {
        return restClient.get()
            .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5&lastArticleId=%s".formatted(lastArticleId))
            .retrieve()
            .body(new ParameterizedTypeReference<List<ArticleResponse>>() {});
    }

    @Test
    public void countTest() {
        ArticleResponse article = create(new ArticleCreateRequest("title", "content", 1L, 1L));

        Long response = restClient.get()
            .uri("/v1/articles/boards/{boardId}/count",article.getBoardId())
            .retrieve()
            .body(Long.class);
        System.out.println("count: " + response);

        restClient.delete()
            .uri("/v1/articles/" + article.getArticleId())
            .retrieve()
            .body(Long.class);

        Long count2 = restClient.get()
            .uri("/v1/articles/boards/{boardId}/count",1L)
            .retrieve()
            .body(Long.class);

        System.out.println("count: " + count2);
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long boardId;
        private Long writerId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleResponse {
        private Long articleId;
        private String title;
        private String content;
        private Long boardId;
        private Long writerId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
