package com.idev4.setup.repository;

import com.idev4.setup.domain.MwBrnchLocationRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwBrnchLocationRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBrnchLocationRelRepository extends JpaRepository<MwBrnchLocationRel, Long> {

    public List<MwBrnchLocationRel> findAllByBrnchSeqAndCrntRecFlg(long branchSeq, boolean recFlag);

    public MwBrnchLocationRel findOneByBrnchSeqAndCitySeqAndCrntRecFlg(long branchSeq, long seq, boolean recFlag);

    public List<MwBrnchLocationRel> findAllByBrnchSeqAndCitySeqAndCrntRecFlg(long branchSeq, long seq, boolean recFlag);

    public List<MwBrnchLocationRel> findAllByCitySeqAndCrntRecFlg(long seq, boolean recFlag);

    public List<MwBrnchLocationRel> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg,
                                                                                                  Instant ldate, boolean delFlg);

    public List<MwBrnchLocationRel> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlgAndBrnchSeq(Instant date,
                                                                                                             boolean crntRecFlg, Instant ldate, boolean delFlg, long branchSeq);
}
