package com.gfg.minor1.controller;

import com.gfg.minor1.exception.TxnException;
import com.gfg.minor1.model.Student;
import com.gfg.minor1.request.TxnCreateRequest;
import com.gfg.minor1.request.TxnReturnRequest;
import com.gfg.minor1.response.GenericResponse;
import com.gfg.minor1.service.TxnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/txn")
public class TxnController {

    @Autowired
    private TxnService txnService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponse<String>> createTxn(@RequestBody @Valid TxnCreateRequest txnCreateRequest) throws TxnException {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        Student student = (Student) authentication.getPrincipal();


        String txnId = txnService.create(txnCreateRequest, student);

        GenericResponse<String> response  = new GenericResponse<>(txnId, "", "success", "200");

        ResponseEntity entity = new ResponseEntity<>(response, HttpStatus.OK);
        return entity;

    }

    @PutMapping("/return")
    public int returnTxn(@RequestBody TxnReturnRequest txnReturnRequest) throws TxnException {
        return txnService.returnBook(txnReturnRequest);

    }
}
