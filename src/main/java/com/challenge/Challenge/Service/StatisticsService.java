package com.challenge.Challenge.Service;

import com.challenge.Challenge.Dto.StatisticsResponseDto;
import com.challenge.Challenge.Model.Statistic;
import com.challenge.Challenge.Model.StatisticsSummary;
import com.challenge.Challenge.Repository.StatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class StatisticsService {
    private final StatisticRepository statisticRepository;
    private final TransactionService transactionService;
    private final Logger LOGGER = LoggerFactory.getLogger(StatisticsService.class);
    private static final int MINUTES_OFFSET = 60;

    public StatisticsService(StatisticRepository statisticRepository, TransactionService transactionService) {
        this.statisticRepository = statisticRepository;
        this.transactionService = transactionService;
    }

    public StatisticsResponseDto getStatistics() {
        OffsetDateTime timestamp = OffsetDateTime.now().minusMinutes(MINUTES_OFFSET);
        StatisticsSummary summary = transactionService.getSummaryOfTransactions(timestamp);

        if (summary.getCount() == 0) {
            LOGGER.info("No transactions found for timestamp: {}", timestamp);
            statisticRepository.save(Statistic.zeroedStatistics());
            return StatisticsResponseDto.zeroedStatisticResponse();
        }

        Statistic statistic = buildStatisticFromSummary(summary);
        statisticRepository.save(statistic);
        LOGGER.info("Statistic Generated. ID: {} | Generated for timestamp: {}", statistic.getId(), timestamp);
        return createResponseFromSummary(summary);
    }

    private Statistic buildStatisticFromSummary(StatisticsSummary summary) {
        return new Statistic(
                summary.getCount(),
                summary.getSum(),
                summary.getAvg(),
                summary.getMin(),
                summary.getMax()
        );
    }

    private StatisticsResponseDto createResponseFromSummary(StatisticsSummary summary) {
        return new StatisticsResponseDto(
                summary.getCount(),
                summary.getSum(),
                summary.getAvg(),
                summary.getMin(),
                summary.getMax()
        );
    }
}