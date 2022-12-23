package com.idev4.setup.repository;

import com.idev4.setup.domain.MwCommWfMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwCommWfMsg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwCommWfMsgRepository extends JpaRepository<MwCommWfMsg, Long> {

    public List<MwCommWfMsg> findAllByCrntRecFlg(boolean flag);

    public MwCommWfMsg findOneByCommWfSeq(long commWfSeq);

    public MwCommWfMsg findOneByCommWfSeqAndCrntRecFlg(long commWfSeq, boolean flag);

    public MwCommWfMsg findOneByCommWfMsgSeqAndCrntRecFlg(long seq, boolean flag);
}
