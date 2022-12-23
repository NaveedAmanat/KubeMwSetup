package com.idev4.setup.repository;

import com.idev4.setup.domain.MwBrnchBdgt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface MwBrnchBdgtRepository extends JpaRepository<MwBrnchBdgt, Long> {

    MwBrnchBdgt findOneByBrnchSeqAndPredAndBdgtYr(long brnchSeq, long pred, long bdgtYr);

    MwBrnchBdgt findOneByBrnchSeqAndBdgtYrAndPredAndDelFlg(long brnchSeq, long bdgtYr, long pred, boolean flag);

    MwBrnchBdgt findOneByBrnchSeqAndPredAndBdgtCtgryKeyAndDelFlg(long brnchSeq, long pred, long bdgtYr, boolean flag);
}
