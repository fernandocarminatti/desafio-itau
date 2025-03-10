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

    private StatisticRepository statisticsRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(StatisticsService.class);
    private final int DEFAULT_MINUTES_OFFSET = 60;
    private TransactionService transactionService;

    public StatisticsService(StatisticRepository statisticsRepository, TransactionService transactionService){
        this.transactionService = transactionService;
        this.statisticsRepository = statisticsRepository;
    }

    public StatisticsResponseDto getStatistics() {
        OffsetDateTime timestamp = OffsetDateTime.now().minusMinutes(DEFAULT_MINUTES_OFFSET);
        StatisticsSummary statisticsData = transactionService.getSummaryOfTransactions(timestamp);
        if (statisticsData.getCount() == 0) {
            LOGGER.info("No transactions found for timestamp: " + timestamp);
            Statistic zeroedStatistics = Statistic.zeroedStatistics();
            statisticsRepository.save(zeroedStatistics);
            return StatisticsResponseDto.zeroedStatisticResponse();
        }
        Statistic statistics = new Statistic(
                statisticsData.getCount(),
                statisticsData.getSum(),
                statisticsData.getAvg(),
                statisticsData.getMin(),
                statisticsData.getMax());

        statisticsRepository.save(statistics);
        LOGGER.info("Statistic Generated. ID: " + statistics.getId() + " | Generated for timestamp: " + timestamp);
        return new StatisticsResponseDto(statisticsData.getCount(),
                statisticsData.getSum(),
                statisticsData.getAvg(),
                statisticsData.getMin(),
                statisticsData.getMax());
    }
}