package com.idev4.setup.repository;

import com.idev4.setup.domain.MwForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwFormRepository extends JpaRepository<MwForm, Long> {

    public List<MwForm> findAllByCrntRecFlg(boolean flag);

    public MwForm findOneByFormSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwForm> findAllByFormSeq(long seq);

    public List<MwForm> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                      boolean delFlg);
}
