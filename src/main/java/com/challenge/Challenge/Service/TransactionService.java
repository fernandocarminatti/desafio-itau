package com.challenge.Challenge.Service;

import com.challenge.Challenge.Dto.TransactionDto;
import com.challenge.Challenge.Model.StatisticsSummary;
import com.challenge.Challenge.Model.Transaction;
import com.challenge.Challenge.Repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Optional<TransactionDto> createTransaction(TransactionDto transactionDto) {
        Transaction transaction = buildTransaction(transactionDto);
        transactionRepository.save(transaction);
        LOGGER.info("Transaction Generated. ID: {}", transaction.getId());
        return Optional.of(transactionDto);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteAllTransactions() {
        LOGGER.info("Request to delete all transactions.");
        transactionRepository.deleteAll();
    }

    public StatisticsSummary getSummaryOfTransactions(OffsetDateTime timestamp) {
        return transactionRepository.getSummaryOfTransactions(timestamp);
    }

    public Transaction buildTransaction(TransactionDto transactionDto) {
        return new Transaction(transactionDto.valor(), transactionDto.dataHora());
    }
}