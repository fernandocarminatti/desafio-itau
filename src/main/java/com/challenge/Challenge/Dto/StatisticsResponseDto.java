package com.challenge.Challenge.Dto;

import com.challenge.Challenge.Model.StatisticsSummary;

public record StatisticsResponseDto(long count, double sum, double avg, double min, double max) {

    public StatisticsResponseDto fromQuery(StatisticsSummary statisticsSummary) {
        return new StatisticsResponseDto(statisticsSummary.getCount(), statisticsSummary.getSum(), statisticsSummary.getAvg(), statisticsSummary.getMin(), statisticsSummary.getMax());
    }
}