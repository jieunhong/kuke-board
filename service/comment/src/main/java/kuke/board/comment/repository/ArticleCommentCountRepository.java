package kuke.board.comment.repository;

import java.util.List;
import kuke.board.comment.entity.ArticleCommentCount;
import kuke.board.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentCountRepository extends JpaRepository<ArticleCommentCount, Long> {
    @Query(value = "update article_comment_count a set a.comment_count = a.comment_count + 1 where a"
        + ".article_id = "
        + ":articleId", nativeQuery = true)
    @Modifying
    int increase(@Param("articleId") Long articleId);


    @Query(value = "update article_comment_count a set a.comment_count = a.comment_count - 1 where a"
        + ".article_id = "
        + ":articleId", nativeQuery = true)
    @Modifying
    int decrease(@Param("articleId") Long articleId);
}
