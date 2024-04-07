package com.gfg.minor1.repository;

import com.gfg.minor1.model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxnRepository extends JpaRepository<Txn, Integer> {

    Txn findByTxnId(String txnId);
}
