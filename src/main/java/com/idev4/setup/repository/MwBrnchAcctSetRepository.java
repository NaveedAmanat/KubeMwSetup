package com.idev4.setup.repository;

import com.idev4.setup.domain.MwBrnchAcctSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MwBrnchAcctSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBrnchAcctSetRepository extends JpaRepository<MwBrnchAcctSet, Long> {

    public MwBrnchAcctSet findOneByBrnchSeqAndCrntRecFlg(long seq, boolean flag);
}
