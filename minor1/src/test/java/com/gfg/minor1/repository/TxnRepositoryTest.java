package com.gfg.minor1.repository;

import com.gfg.minor1.model.Txn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TxnRepositoryTest {
    @Autowired
    private TxnRepository txnRepository;

    private Txn txn;

    @BeforeEach
    public void setUp(){
       txn =  Txn.builder().txnId("2").build();
       txnRepository.save(txn);

    }

    @Test
    public void testfindByTxnId(){
        Txn t = txnRepository.findByTxnId("2");
        Assertions.assertEquals(txn.getTxnId(),t.getTxnId());

    }

}
