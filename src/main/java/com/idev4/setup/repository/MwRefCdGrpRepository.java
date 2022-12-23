package com.idev4.setup.repository;

import com.idev4.setup.domain.MwRefCdGrp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwRefCdGrp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwRefCdGrpRepository extends JpaRepository<MwRefCdGrp, Long> {

    public MwRefCdGrp findOneByRefCdGrpSeqAndCrntRecFlg(long seq, boolean flag);

    public MwRefCdGrp findOneByRefCdGrpSeqAndCrntRecFlgOrderByRefCdGrpSeqDesc(long seq, boolean flag);

    public MwRefCdGrp findOneByRefCdGrpNameAndCrntRecFlg(String name, boolean flag);

    public MwRefCdGrp findOneByRefCdGrpAndCrntRecFlg(String refCdGrp, boolean flag);

    public List<MwRefCdGrp> findAllByCrntRecFlgOrderByRefCdGrpSeq(boolean flag);

    public List<MwRefCdGrp> findAllByCrntRecFlgOrderByRefCdGrpSeqDesc(boolean flag);

    public List<MwRefCdGrp> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg,
                                                                                          Instant ldate, boolean delFlg);

}
