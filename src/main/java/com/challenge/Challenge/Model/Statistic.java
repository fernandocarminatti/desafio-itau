package com.challenge.Challenge.Model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_statistic")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private long count;
    private double sum;
    private double avg;
    private double min;
    private double max;
    private OffsetDateTime timestamp;

    private static final long ZERO_COUNT = 0L;
    private static final double ZERO_DOUBLE = 0.0;

    public Statistic() {
    }

    public Statistic(long count, double sum, double avg, double min, double max) {
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.min = min;
        this.max = max;
        this.timestamp = OffsetDateTime.now();
    }

    public static Statistic zeroedStatistics() {
        return new Statistic(ZERO_COUNT, ZERO_DOUBLE, ZERO_DOUBLE, ZERO_DOUBLE, ZERO_DOUBLE);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", count=" + count +
                ", sum=" + sum +
                ", avg=" + avg +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}