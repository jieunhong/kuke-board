package kuke.board.article.controller;

import java.util.List;
import kuke.board.article.service.ArticleService;
import kuke.board.article.service.request.ArticleCreateRequest;
import kuke.board.article.service.request.ArticleUpdateRequest;
import kuke.board.article.service.response.ArticlePageResponse;
import kuke.board.article.service.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ArticleResponse create(@RequestBody ArticleCreateRequest articleCreateRequest) {
        return articleService.create(articleCreateRequest);
    }

    @PutMapping("/{articleId}")
    public ArticleResponse update(@PathVariable Long articleId, @RequestBody ArticleUpdateRequest articleUpdateRequest) {
        return articleService.update(articleId, articleUpdateRequest);
    }

    @DeleteMapping("/{articleId}")
    public void delete(@PathVariable Long articleId) {
        articleService.delete(articleId);
    }

    @GetMapping("/{articleId}")
    public ArticleResponse read(@PathVariable Long articleId) {
        return articleService.read(articleId);
    }

    @GetMapping
    public ArticlePageResponse readAll(@RequestParam(name = "boardId") Long boardId,
                                       @RequestParam(name = "pageSize") Long pageSize,
                                       @RequestParam(name = "page") Long page) {
        return articleService.readAll(boardId, pageSize, page);
    }

    @GetMapping("/infinite-scroll")
    public List<ArticleResponse> search(@RequestParam(name = "boardId") Long boardId,
                                        @RequestParam(name = "pageSize") Long pageSize,
                                        @RequestParam(name = "lastArticleId", required = false) Long lastArticleId) {
        return articleService.readAllInfinite(boardId, pageSize, lastArticleId);
    }
}
