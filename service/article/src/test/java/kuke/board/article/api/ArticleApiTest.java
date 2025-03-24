package kuke.board.article.api;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    public void create() {
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
    public void read() {
        ArticleResponse response = read(162135537400926208L);
        System.out.println(response);
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
            .uri("/v1/articles/" + articleId)
            .retrieve()
            .body(ArticleResponse.class);
    }

    @Test
    public void update() {
        ArticleResponse response = update(162135537400926208L, new ArticleUpdateRequest("title2", "content2"));
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
    public void delete() {
        delete(162135537400926208L);
    }

    void delete(Long articleId) {
        restClient.delete()
            .uri("/v1/articles/" + articleId)
            .retrieve();
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
