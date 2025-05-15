package kuke.board.view.service;

import kuke.board.view.repository.ArticleViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleViewService {

    private final ArticleViewCountBackUpProcessor articleViewCountBackUpProcessor;
    private final ArticleViewCountRepository articleViewCountRepository;

    private static final int BACKUP_COUNT = 100;


    public Long increase(Long articleId, Long userId) {
        Long viewCount = articleViewCountRepository.increase(articleId);

        if (viewCount % BACKUP_COUNT == 0) {
            articleViewCountBackUpProcessor.process(articleId, viewCount);
        }

        return viewCount;
    }

    public Long count(Long articleId) {
        return articleViewCountRepository.read(articleId);
    }


}
