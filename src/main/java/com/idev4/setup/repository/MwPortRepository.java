package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwPort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPortRepository extends JpaRepository<MwPort, Long> {

    public List<MwPort> findAllByBrnchSeqAndCrntRecFlg(long branchSeq, boolean flag);

    public List<MwPort> findAllByBrnchSeqAndCrntRecFlgOrderByPortNm(long branchSeq, boolean flag);

    public List<MwPort> findAllByCrntRecFlg(boolean flag);

    public MwPort findTopByOrderByPortSeqDesc();

    public MwPort findOneByPortSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwPort> findAllByPortSeq(long seq);

    public MwPort findOneByPortCdAndCrntRecFlg(String cd, boolean flag);
}
