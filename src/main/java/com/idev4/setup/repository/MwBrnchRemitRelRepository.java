package com.idev4.setup.repository;

import com.idev4.setup.domain.MwBrnchRemitRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface MwBrnchRemitRelRepository extends JpaRepository<MwBrnchRemitRel, Long> {

    public MwBrnchRemitRel findOneByBrnchRemitSeqAndCrntRecFlg(Long brnchRemitSeq, boolean flag);

    public List<MwBrnchRemitRel> findAllByBrnchSeqAndCrntRecFlg(Long brnchSeq, boolean flag);

    // Added by Areeba - 27-05-2022
    public MwBrnchRemitRel findOneByBrnchSeqAndCrntRecFlg(Long brnchSeq, boolean flag);

}


