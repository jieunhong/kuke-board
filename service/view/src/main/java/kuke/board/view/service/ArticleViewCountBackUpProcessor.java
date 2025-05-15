package kuke.board.view.service;

import kuke.board.view.entity.ArticleViewCount;
import kuke.board.view.repository.ArticleViewCountBackUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleViewCountBackUpProcessor {

    private final ArticleViewCountBackUpRepository articleViewCountBackUpRepository;

    @Transactional
    public void process(Long articleId, Long viewCount) {
        int result = articleViewCountBackUpRepository.updateArticleCount(articleId, viewCount);
        if (result == 0) {
            articleViewCountBackUpRepository.findById(articleId)
                .ifPresentOrElse(
                    ignore -> {},
                    () -> articleViewCountBackUpRepository.save(ArticleViewCount.init(articleId, viewCount)));
        }
    }


}
