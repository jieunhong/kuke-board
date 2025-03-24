package kuke.board.article.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PageLimitCalculatorTest {

    @Test
    void calculatePageLimitTest() {
        calculatePageLimit(1L, 30L, 10L, 301L);
        calculatePageLimit(7L, 30L, 10L, 301L);
        calculatePageLimit(10L, 30L, 10L, 301L);
        calculatePageLimit(11L, 30L, 10L, 601L);
        calculatePageLimit(11L, 30L, 10L, 601L);
    }

    void calculatePageLimit(Long page, Long pageSize, Long count, Long expected) {
        Long result = PageLimitCalculator.calculatePageLimit(page, pageSize, count);
        assertEquals(expected, result);
    }

}