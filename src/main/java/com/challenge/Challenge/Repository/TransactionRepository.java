package com.challenge.Challenge.Repository;

import com.challenge.Challenge.Model.StatisticsSummary;
import com.challenge.Challenge.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT COUNT(t) as count, SUM(t.amount) as sum, " +
            "AVG(t.amount) as avg, MIN(t.amount) as min, MAX(t.amount) as max " +
            "FROM Transaction t " +
            "WHERE t.timestamp < :timestamp")
    StatisticsSummary getStatistics(@Param("timestamp") OffsetDateTime timestamp);

}