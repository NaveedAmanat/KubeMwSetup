package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPortEmpRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwPortEmpRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPortEmpRelRepository extends JpaRepository<MwPortEmpRel, Long> {

    public List<MwPortEmpRel> findAllByPortSeqAndCrntRecFlg(long portSeq, boolean flag);

    public MwPortEmpRel findOneByPortEmpSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwPortEmpRel> findAllByLastUpdDtAfterAndPortSeqInOrderByCrntRecFlg(Instant date, List<Long> ports);

    public MwPortEmpRel findOneByPortSeqAndCrntRecFlg(long portSeq, boolean flag);

    public MwPortEmpRel findOneByEmpSeqAndCrntRecFlg(long empSeq, boolean flag);
}
