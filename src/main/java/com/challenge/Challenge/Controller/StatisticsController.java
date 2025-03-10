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

    private StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService){
        this.statisticsService = statisticsService;
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<?> getTransactionStatistics(){
        StatisticsResponseDto responseDto = statisticsService.getStatistics();
        return ResponseEntity.ok(responseDto);
    }
}