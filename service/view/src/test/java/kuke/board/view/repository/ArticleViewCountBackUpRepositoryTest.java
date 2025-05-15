package kuke.board.view.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kuke.board.view.entity.ArticleViewCount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ArticleViewCountBackUpRepositoryTest {

    @Autowired
    ArticleViewCountBackUpRepository articleViewCountBackUpRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    void updateArticleCount() {
        // given
        articleViewCountBackUpRepository.save(ArticleViewCount.init(1L, 0L));

        entityManager.flush();
        entityManager.clear();

        // when
        int result1 = articleViewCountBackUpRepository.updateArticleCount(1L, 100L);
        int result2 = articleViewCountBackUpRepository.updateArticleCount(1L, 200L);
        int result3 = articleViewCountBackUpRepository.updateArticleCount(1L, 300L);

        // then
        assertThat(result1).isEqualTo(1);
        assertThat(result2).isEqualTo(1);
        assertThat(result3).isEqualTo(0);

        ArticleViewCount articleViewCount = articleViewCountBackUpRepository.findById(1L).get();
        assertThat(articleViewCount.getViewCount()).isEqualTo(300L);
    }

}