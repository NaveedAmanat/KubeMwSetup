package com.idev4.setup.repository;

import com.idev4.setup.domain.MwBrnchPrdRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data repository for the MwBrnchPrdRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBrnchPrdRelRepository extends JpaRepository<MwBrnchPrdRel, Long> {

    List<MwBrnchPrdRel> findAllByBrnchSeqAndCrntRecFlg(long brnchSeq, boolean flag);

    MwBrnchPrdRel findOneByBrnchSeqAndPrdSeqAndCrntRecFlg(long brnchSeq, long seq, boolean flag);

    List<MwBrnchPrdRel> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                      boolean delFlg);
}
