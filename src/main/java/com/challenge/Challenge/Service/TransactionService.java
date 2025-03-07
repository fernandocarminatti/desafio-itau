package com.challenge.Challenge.Service;

import com.challenge.Challenge.Dto.TransactionDto;
import com.challenge.Challenge.Model.Transaction;
import com.challenge.Challenge.Repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Optional<TransactionDto> createTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction(transactionDto.valor(), transactionDto.dataHora());
        transactionRepository.save(transaction);
        LOGGER.info("Transaction created successfully with id: " + transaction.getId());
        return Optional.of(transactionDto);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}