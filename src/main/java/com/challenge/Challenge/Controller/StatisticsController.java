package com.challenge.Challenge.Controller;

import com.challenge.Challenge.Dto.StatisticsResponseDto;
import com.challenge.Challenge.Service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<StatisticsResponseDto> getStatistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
}