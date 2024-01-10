package com.emr.springredistransaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TransactionalMethodController {

    private final TransactionalMethodService service;

    @GetMapping("/save-numbers")
    public ResponseEntity<Void> saveNumbers() {
        service.saveNumbersUsingTransactionWithAnnotation(List.of(0,4,2,1,3));
        return ResponseEntity.ok(null);
    }
}
