package kuke.board.view.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleViewCountRepository {

    private final StringRedisTemplate redisTemplate;
    private static final String KEY_FORMAT = "view::article::%s::view_count";

    public Long read(Long articleId) {
        String viewCount = redisTemplate.opsForValue()
            .get(generateKey(articleId));

        if (viewCount == null) {
            return 0L;
        }
        return Long.parseLong(viewCount);
    }

    public Long increase(Long articleId) {
        return redisTemplate.opsForValue()
            .increment(generateKey(articleId));
    }

    public String generateKey(Long articleId) {
        return KEY_FORMAT.formatted(articleId);
    }
}
