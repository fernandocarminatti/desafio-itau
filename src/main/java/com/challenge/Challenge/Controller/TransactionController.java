package com.challenge.Challenge.Controller;

import com.challenge.Challenge.Dto.TransactionDto;
import com.challenge.Challenge.Service.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transacao")
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionDto transactionDto){
        LOGGER.info("New Request ->  Amount: " + transactionDto.valor() + " | Timestamp: " + transactionDto.dataHora());
        Optional<TransactionDto> transactionResponse = transactionService.createTransaction(transactionDto);
        if(transactionResponse.isPresent()){
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping()
    public ResponseEntity<?> getAllTransactions(){
        LOGGER.info("Request for all transactions");
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<?> deleteAllTransactions(){
        LOGGER.info("Request to delete all transactions");
        transactionService.deleteAllTransactions();
        return ResponseEntity.ok().build();
    }

}