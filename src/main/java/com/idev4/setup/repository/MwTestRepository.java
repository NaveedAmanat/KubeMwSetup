package com.idev4.setup.repository;

import com.idev4.setup.domain.MwTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MwTest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwTestRepository extends JpaRepository<MwTest, Long> {

    public MwTest findOneByRefCdGrpSeqAndCrntRecFlg(long seq, boolean flag);
}
