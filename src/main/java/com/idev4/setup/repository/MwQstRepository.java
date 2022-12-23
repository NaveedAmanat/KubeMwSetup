package com.idev4.setup.repository;

import com.idev4.setup.domain.MwQst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwQstRepository extends JpaRepository<MwQst, Long> {

    public MwQst findOneByQstSeqAndCrntRecFlg(Long seq, boolean flag);

    public List<MwQst> findAllByQstSeqAndCrntRecFlg(Long seq, boolean flag);

    public List<MwQst> findAllByQstnrSeqAndCrntRecFlg(Long seq, boolean flag);

    public List<MwQst> findAllByQstSeq(Long seq);

    public List<MwQst> findAllByDelFlgAndCrntRecFlg(boolean recflag, boolean delflag);

    public List<MwQst> findAllByDelFlgAndCrntRecFlgAndQstTypKey(boolean recflag, boolean delflag, long type);

    public List<MwQst> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                     boolean delFlg);

}
