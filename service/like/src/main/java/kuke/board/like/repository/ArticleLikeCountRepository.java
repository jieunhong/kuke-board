package kuke.board.like.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import kuke.board.like.entity.ArticleLike;
import kuke.board.like.entity.ArticleLikeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLikeCountRepository extends JpaRepository<ArticleLikeCount, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<ArticleLikeCount> findByArticleId(Long articleId);

    @Query(value = "update article_like_count a set a.like_count = a.like_count + 1 where a"
        + ".articleId = "
        + ":articleId", nativeQuery = true)
    @Modifying
    int increase(@Param("articleId") Long articleId);


    @Query(value = "update article_like_count a set a.like_count = a.like_count - 1 where a"
        + ".articleId = "
        + ":articleId", nativeQuery = true)
    @Modifying
    int decrease(@Param("articleId") Long articleId);

}
