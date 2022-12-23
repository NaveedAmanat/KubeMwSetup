package com.idev4.setup.repository;

import com.idev4.setup.domain.MwAddrRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MwAddrRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAddrRelRepository extends JpaRepository<MwAddrRel, Long> {

    public MwAddrRel findOneByEntySeq(long entySeq);

    public MwAddrRel findOneByEntySeqAndEntyType(long entySeq, String entyTyp);

    public MwAddrRel findOneByEntySeqAndEntyTypeAndCrntRecFlg(long entySeq, String entyTyp, boolean flag);
}
