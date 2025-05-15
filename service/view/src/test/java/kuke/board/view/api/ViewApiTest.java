package kuke.board.view.api;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ViewApiTest {

    RestClient restClient = RestClient.create("http://localhost:9003");


    @Test
    void increase() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch = new CountDownLatch(10000);

        for (int i = 0; i < 10000; i++) {
            executorService.submit(() -> {
                restClient.post().uri("/v1/article-views/articles/1/users/1").retrieve();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        Long count =
            restClient.get().uri("/v1/article-views/articles/1/count").retrieve().body(Long.class);
        System.out.println(count);
    }

}
