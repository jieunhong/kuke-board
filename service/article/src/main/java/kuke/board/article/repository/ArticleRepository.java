package kuke.board.article.repository;

import java.util.List;
import kuke.board.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(nativeQuery = true,
        value = """
                select article.* from (
                    select article_id
                    from article 
                    where board_id = :boardId
                    order by article_id desc
                    limit :limit
                    offset :offset
                ) t left join article on t.article_id = article.article_id 
                """)
    List<Article> findAll(
        @Param("boardId") Long boardId,
        @Param("offset") Long offset,
        @Param("limit") Long limit);

    @Query(nativeQuery = true,
        value = """
                select count(*) from (
                    select article_id
                    from article 
                    where board_id = :boardId
                    limit :limit
                ) t
                """)
    Long count(
        @Param("boardId") Long boardId,
        @Param("limit") Long limit);


    @Query(nativeQuery = true,
        value = """
                select * from article 
                         where
                             board_id = :boardId
                         order by article_id desc limit :limit
                """)
    List<Article> findAllInfiniteScroll(
        @Param("boardId") Long boardId,
        @Param("limit") Long limit);


    @Query(nativeQuery = true,
        value = """
                select * from article 
                         where
                             board_id = :boardId
                             and article_id < :lastArticleId
                         order by article_id desc limit :limit
                """)
    List<Article> findAllInfiniteScroll(
        @Param("boardId") Long boardId,
        @Param("limit") Long limit,
        @Param("lastArticleId") Long lastArticleId);
}
