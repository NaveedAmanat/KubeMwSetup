package com.idev4.setup.repository;

import com.idev4.setup.domain.MwDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwDocRepository extends JpaRepository<MwDoc, Long> {

    public MwDoc findOneByDocSeqAndCrntRecFlg(Long docSeq, boolean flag);

    public List<MwDoc> findAllByDocSeq(Long docSeq);

    public List<MwDoc> findAllByCrntRecFlg(boolean flag);

    public List<MwDoc> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                     boolean delFlg);

}
