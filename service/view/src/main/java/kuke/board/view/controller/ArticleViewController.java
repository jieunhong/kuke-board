package kuke.board.view.controller;

import kuke.board.view.service.ArticleViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/article-views/articles/{articleId}")
@RequiredArgsConstructor
public class ArticleViewController {

    private final ArticleViewService articleViewService;



    @PostMapping("users/{userId}")
    public Long create(
        @PathVariable Long articleId,
        @PathVariable Long userId) {
        return articleViewService.increase(articleId, userId);
    }

    @GetMapping("count")
    public Long count(@PathVariable Long articleId) {
        return articleViewService.count(articleId);
    }
}
