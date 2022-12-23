package com.idev4.setup.repository;

import com.idev4.setup.domain.MwCommWfMsgDueDt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwCommWfMsgDueDt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwCommWfMsgDueDtRepository extends JpaRepository<MwCommWfMsgDueDt, Long> {

    public List<MwCommWfMsgDueDt> findAllByCrntRecFlg(boolean flg);

    public MwCommWfMsgDueDt findOneByMsgKey(long msgKey);

    public MwCommWfMsgDueDt findOneByMsgKeyAndCrntRecFlg(long msgKey, boolean flag);

    public MwCommWfMsgDueDt findOneBycommWfMsgDueDtSeqAndCrntRecFlg(long seq, boolean flag);

}
