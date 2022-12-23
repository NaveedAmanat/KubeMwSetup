package com.idev4.setup.repository;

import com.idev4.setup.domain.MwCommWfMsgRcpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwCommWfMsgRcpt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwCommWfMsgRcptRepository extends JpaRepository<MwCommWfMsgRcpt, Long> {

    public List<MwCommWfMsgRcpt> findAllByCrntRecFlg(boolean flag);

    public List<MwCommWfMsgRcpt> findAllByMsgKey(long msgKey);

    public List<MwCommWfMsgRcpt> findAllByMsgKeyAndCrntRecFlg(long msgKey, boolean flag);

    public MwCommWfMsgRcpt findOneByMsgKey(long msgKey);

    public MwCommWfMsgRcpt findOneByMsgKeyAndCrntRecFlg(long msgKey, boolean flag);

    public MwCommWfMsgRcpt findOneByCommWfMsgRcptSeqAndCrntRecFlg(long seq, boolean flag);
}
