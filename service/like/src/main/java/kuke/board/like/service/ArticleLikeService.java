package kuke.board.like.service;


import kuke.board.common.snowflake.Snowflake;
import kuke.board.like.entity.ArticleLike;
import kuke.board.like.entity.ArticleLikeCount;
import kuke.board.like.repository.ArticleLikeCountRepository;
import kuke.board.like.repository.ArticleLikeRepository;
import kuke.board.like.service.response.ArticleLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {
    private final Snowflake snowflake = new Snowflake();
    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleLikeCountRepository articleLikeCountRepository;

    public ArticleLikeResponse read(Long articleId, Long userId) {
        return
            articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
                .map(ArticleLikeResponse::from)
                .orElseThrow();
    }

    @Transactional
    public void like(Long articleId, Long userId) {
        ArticleLike articleLike = ArticleLike.create(snowflake.nextId(), articleId, userId);
        articleLikeRepository.save(articleLike);
    }

    @Transactional
    public void unlike(Long articleId, Long userId) {
        articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
            .ifPresent(articleLikeRepository::delete);
    }

    @Transactional
    public void likePessimisticLock1(Long articleId, Long userId) {
        ArticleLike articleLike = ArticleLike.create(snowflake.nextId(), articleId, userId);
        articleLikeRepository.save(articleLike);

        int count = articleLikeCountRepository.increase(articleId);

        if (count == 0) {
            articleLikeCountRepository.save(ArticleLikeCount.init(articleId, 1L));
        }

    }

    @Transactional
    public void unlikePessimisticLock1(Long articleId, Long userId) {
        articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
            .ifPresent(like -> {
                articleLikeRepository.delete(like);
                articleLikeCountRepository.decrease(articleId);
            });
    }

    @Transactional
    public void likePessimisticLock2(Long articleId, Long userId) {
        ArticleLike articleLike = ArticleLike.create(snowflake.nextId(), articleId, userId);
        articleLikeRepository.save(articleLike);

        ArticleLikeCount articleLikeCount = articleLikeCountRepository.findByArticleId(articleId)
            .orElseGet(() -> articleLikeCountRepository.save(ArticleLikeCount.init(articleId, 0L)));

        articleLikeCount.increase();

    }

    @Transactional
    public void unlikePessimisticLock2(Long articleId, Long userId) {
        articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
            .ifPresent(like -> {
                articleLikeRepository.delete(like);
                ArticleLikeCount articleLikeCount = articleLikeCountRepository.findByArticleId(articleId)
                    .orElseThrow();
                articleLikeCount.decrease();
            });
    }

    @Transactional
    public void likeOptimisticLock(Long articleId, Long userId) {
        ArticleLike articleLike = ArticleLike.create(snowflake.nextId(), articleId, userId);
        articleLikeRepository.save(articleLike);

        articleLikeCountRepository.findById(articleId)
            .orElseGet(() -> articleLikeCountRepository.save(ArticleLikeCount.init(articleId, 0L)))
            .increase();
    }

    @Transactional
    public void unlikeOptimisticLock(Long articleId, Long userId) {
        articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
            .ifPresent(
                      like -> {
                          articleLikeRepository.delete(like);
                          articleLikeCountRepository.findById(articleId)
                              .orElseThrow()
                              .decrease();
                      }
                      );
    }

}
