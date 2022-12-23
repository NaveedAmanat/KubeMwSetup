package com.idev4.setup.repository;

import com.idev4.setup.domain.MwAnswr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwAnswr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAnswrRepository extends JpaRepository<MwAnswr, Long> {

    public List<MwAnswr> findAllByDelFlgAndCrntRecFlg(boolean delflag, boolean recFlag);

    public MwAnswr findOneByAnswrSeqAndCrntRecFlg(long answrSeq, boolean recFlag);

    public List<MwAnswr> findByQstSeqInAndCrntRecFlg(List<Long> qstsSeq, boolean recFlag);

    public List<MwAnswr> findAllByQstSeqAndCrntRecFlg(long qstSeq, boolean recFlag);

    public List<MwAnswr> findAllByLastUpdDtAfterAndCrntRecFlg(Instant date, boolean flag);

    public List<MwAnswr> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                       boolean delFlg);
}
