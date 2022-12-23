package com.idev4.setup.repository;

import com.idev4.setup.domain.MwRegAcctSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MwRegAcctSetRepository extends JpaRepository<MwRegAcctSet, Long> {

    MwRegAcctSet findByRegSeqAndCrntRecFlg(long regSeq, boolean flag);
}
