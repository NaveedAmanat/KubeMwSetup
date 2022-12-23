package com.idev4.setup.repository;

import com.idev4.setup.domain.MwRul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwRulRepository extends JpaRepository<MwRul, Long> {

    public MwRul findOneByRulSeqAndCrntRecFlg(Long rulSeq, boolean flag);

    public List<MwRul> findAllByRulSeq(Long rulSeq);

    public List<MwRul> findAllByCrntRecFlg(boolean flag);

    public List<MwRul> findAllByRulCtgryKeyAndCrntRecFlg(long rulCtgryKey, boolean flag);

    public List<MwRul> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                     boolean delFlg);
}
