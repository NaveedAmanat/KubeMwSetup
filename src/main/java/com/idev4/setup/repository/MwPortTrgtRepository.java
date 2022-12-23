package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPortTrgt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MwPort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPortTrgtRepository extends JpaRepository<MwPortTrgt, Long> {

    // public List< MwPort > findAllByBrnchSeqAndCrntRecFlg( long branchSeq, boolean flag );
    //
    // public List< MwPort > findAllByCrntRecFlg( boolean flag );
    //
    // public MwPort findTopByOrderByPortSeqDesc();

    public MwPortTrgt findOneByBrnchTrgtsSeqAndPortSeqAndCrntRecFlg(long brncgTrgtseq, long portSeq, boolean flag);

    // public List< MwPort > findAllByPortSeq( long seq );
}
