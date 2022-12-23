package com.idev4.setup.repository;

import com.idev4.setup.domain.MwQstnr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwQstnrRepository extends JpaRepository<MwQstnr, Long> {

    public MwQstnr findOneByQstnrSeqAndCrntRecFlg(Long seq, boolean flag);

    public List<MwQstnr> findAllByQstnrSeq(Long seq);

    public List<MwQstnr> findAllByCrntRecFlg(boolean recflag);

    public List<MwQstnr> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                       boolean delFlg);
}
