package com.challenge.Challenge.Service;
import com.challenge.Challenge.Dto.StatisticsResponseDto;
import com.challenge.Challenge.Model.StatisticsSummary;
import com.challenge.Challenge.Repository.StatisticRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StatisticsServiceTest {
    private static final long TEST_COUNT = 4L;
    private static final double TEST_SUM = 1000.0;
    private static final double TEST_AVG = 110.0;
    private static final double TEST_MIN = 2.0;
    private static final double TEST_MAX = 20.0;

    private StatisticsSummary createMockSummary(long count, double sum, double avg, double min, double max) {
        StatisticsSummary mockSummary = mock(StatisticsSummary.class);
        when(mockSummary.getCount()).thenReturn(count);
        when(mockSummary.getSum()).thenReturn(sum);
        when(mockSummary.getAvg()).thenReturn(avg);
        when(mockSummary.getMin()).thenReturn(min);
        when(mockSummary.getMax()).thenReturn(max);
        return mockSummary;
    }

    private void assertStatisticsResponse(StatisticsResponseDto result, long expectedCount,
                                          double expectedSum, double expectedAvg,
                                          double expectedMin, double expectedMax) {
        assertNotNull(result);
        assertEquals(expectedCount, result.count());
        assertEquals(expectedSum, result.sum());
        assertEquals(expectedAvg, result.avg());
        assertEquals(expectedMin, result.min());
        assertEquals(expectedMax, result.max());
    }

    @Test
    void getZeroedStatistics() {
        // Arrange
        StatisticsSummary mockSummary = createMockSummary(0L, 0.0, 0.0, 0.0, 0.0);
        TransactionService mockTransactionService = mock(TransactionService.class);
        StatisticRepository mockRepository = mock(StatisticRepository.class);
        StatisticsService statisticsService = new StatisticsService(mockRepository, mockTransactionService);
        when(mockTransactionService.getSummaryOfTransactions(any())).thenReturn(mockSummary);

        // Act
        StatisticsResponseDto result = statisticsService.getStatistics();

        // Assert
        assertStatisticsResponse(result, 0, 0, 0, 0, 0);
        verify(mockTransactionService).getSummaryOfTransactions(any());
        verify(mockRepository).save(any());
    }

    @Test
    void getValidStatistics() {
        // Arrange
        StatisticsSummary mockSummary = createMockSummary(
                TEST_COUNT, TEST_SUM, TEST_AVG, TEST_MIN, TEST_MAX
        );
        TransactionService mockTransactionService = mock(TransactionService.class);
        StatisticRepository mockRepository = mock(StatisticRepository.class);
        StatisticsService statisticsService = new StatisticsService(mockRepository, mockTransactionService);
        when(mockTransactionService.getSummaryOfTransactions(any())).thenReturn(mockSummary);

        // Act
        StatisticsResponseDto result = statisticsService.getStatistics();

        // Assert
        assertStatisticsResponse(result, TEST_COUNT, TEST_SUM, TEST_AVG, TEST_MIN, TEST_MAX);
        verify(mockTransactionService).getSummaryOfTransactions(any());
        verify(mockRepository).save(any());
    }
}