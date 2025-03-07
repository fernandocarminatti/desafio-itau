package com.challenge.Challenge.Service;

import com.challenge.Challenge.Dto.StatisticsResponseDto;
import com.challenge.Challenge.Dto.TransactionDto;
import com.challenge.Challenge.Model.Statistic;
import com.challenge.Challenge.Model.StatisticsSummary;
import com.challenge.Challenge.Model.Transaction;
import com.challenge.Challenge.Repository.StatisticRepository;
import com.challenge.Challenge.Repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private TransactionRepository transactionRepository;
    private StatisticRepository statisticsRepository;
    private final int DEFAULT_MINUTES_OFFSET = 60;

    public TransactionService(TransactionRepository transactionRepository, StatisticRepository statisticsRepository) {
        this.transactionRepository = transactionRepository;
        this.statisticsRepository = statisticsRepository;
    }

    public Optional<TransactionDto> createTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction(transactionDto.valor(), transactionDto.dataHora());
        transactionRepository.save(transaction);
        LOGGER.info("Transaction Generated. ID: " + transaction.getId());
        return Optional.of(transactionDto);
    }

    public StatisticsResponseDto getStatistics() {
        OffsetDateTime timestamp = OffsetDateTime.now().minusMinutes(DEFAULT_MINUTES_OFFSET);
        StatisticsSummary statisticsData = transactionRepository.getStatistics(timestamp);
        if (statisticsData.getCount() == 0) {
            LOGGER.info("No transactions found for timestamp: " + timestamp);
            statisticsRepository.save(new Statistic(0, 0, 0, 0, 0));
            return new StatisticsResponseDto(0, 0, 0, 0, 0);
        }
        Statistic statistics = new Statistic(statisticsData.getCount(), statisticsData.getSum(), statisticsData.getAvg(), statisticsData.getMin(), statisticsData.getMax());
        statisticsRepository.save(statistics);
        LOGGER.info("Statistic Generated. ID: " + statistics.getId() + " | Generated for timestamp: " + timestamp);
        return new StatisticsResponseDto(statisticsData.getCount(), statisticsData.getSum(), statisticsData.getAvg(), statisticsData.getMin(), statisticsData.getMax());

    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}