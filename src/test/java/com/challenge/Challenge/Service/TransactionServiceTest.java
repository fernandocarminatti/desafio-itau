package com.challenge.Challenge.Service;

import com.challenge.Challenge.Dto.TransactionDto;
import com.challenge.Challenge.Model.StatisticsSummary;
import com.challenge.Challenge.Model.Transaction;
import com.challenge.Challenge.Repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    @ExtendWith(MockitoExtension.class)

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void createTransaction_shouldSaveTransactionAndReturnDto() {
        // Arrange
        BigDecimal valor = BigDecimal.valueOf(100.0);
        OffsetDateTime dataHora = OffsetDateTime.now();
        TransactionDto inputDto = new TransactionDto(valor, dataHora);

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);

        // Act
        Optional<TransactionDto> result = transactionService.createTransaction(inputDto);

        // Assert
        verify(transactionRepository).save(transactionCaptor.capture());

        Transaction savedTransaction = transactionCaptor.getValue();
        assertAll(
                () -> assertNotNull(result),
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(inputDto, result.get()),
                () -> assertEquals(valor, savedTransaction.getAmount()),
                () -> assertEquals(dataHora, savedTransaction.getTimestamp())
        );

    }

    @Test
    void createTransaction_shouldLogTransactionId() {
        // Arrange
        TransactionDto inputDto = new TransactionDto(BigDecimal.valueOf(100.0), OffsetDateTime.now());

        // Usa um mock de Logger para capturar logs
        Logger mockLogger = mock(Logger.class);
        ReflectionTestUtils.setField(transactionService, "LOGGER", mockLogger);

        // Act
        transactionService.createTransaction(inputDto);

        // Assert
        verify(mockLogger).info(argThat(message ->
                message.startsWith("Transaction Generated. ID: ")));
    }

    @Test
    void getAllTransactions() {
        // Arrange
        Transaction transaction1 = new Transaction(BigDecimal.valueOf(200.0), OffsetDateTime.now());
        Transaction transaction2 = new Transaction(BigDecimal.valueOf(500.0), OffsetDateTime.now().minusHours(1));
        List<Transaction> transactions = List.of(transaction1, transaction2);

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        List<Transaction> result = transactionService.getAllTransactions();

        // Assert
        verify(transactionRepository).findAll();
        assertEquals(2, result.size());
        assertTrue(result.containsAll(transactions));
    }

    @Test
    void deleteAllTransactions() {
        // Arrange
        Logger mockLogger = mock(Logger.class);
        ReflectionTestUtils.setField(transactionService, "LOGGER", mockLogger);

        // Act
        transactionService.deleteAllTransactions();

        // Assert
        verify(transactionRepository).deleteAll();
        verify(mockLogger).info(argThat(message ->
                message.startsWith("DELETEING ALL TRANSACTIONS...")));

    }

    @Test
    void getSummaryOfTransactions() {
        // Arrange
        OffsetDateTime timestamp = OffsetDateTime.now().minusMinutes(60);
        StatisticsSummary mockSummary = Mockito.mock(StatisticsSummary.class);
        when(mockSummary.getCount()).thenReturn(5L);
        when(mockSummary.getSum()).thenReturn(500.0);
        when(mockSummary.getAvg()).thenReturn(100.0);
        when(mockSummary.getMin()).thenReturn(50.0);
        when(mockSummary.getMax()).thenReturn(150.0);

        when(transactionRepository.getSummaryOfTransactions(timestamp)).thenReturn(mockSummary);

        // Act
        StatisticsSummary result = transactionService.getSummaryOfTransactions(timestamp);

        // Assert
        verify(transactionRepository).getSummaryOfTransactions(timestamp);
        assertAll(
                () -> assertEquals(5, result.getCount()),
                () -> assertEquals(500.0, result.getSum()),
                () -> assertEquals(100.0, result.getAvg()),
                () -> assertEquals(50.0, result.getMin()),
                () -> assertEquals(150.0, result.getMax())
        );
    }
}