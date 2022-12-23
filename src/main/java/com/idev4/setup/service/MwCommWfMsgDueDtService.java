package com.idev4.setup.service;

import com.idev4.setup.domain.MwCommWfMsgDueDt;
import com.idev4.setup.dto.CommWorkflowDto;
import com.idev4.setup.repository.MwCommWfMsgDueDtRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing MwCommWfMsgDueDt.
 */
@Service
@Transactional
public class MwCommWfMsgDueDtService {

    private final Logger log = LoggerFactory.getLogger(MwCommWfMsgDueDtService.class);

    private final MwCommWfMsgDueDtRepository mwCommWfMsgDueDtRepository;

    public MwCommWfMsgDueDtService(MwCommWfMsgDueDtRepository mwCommWfMsgDueDtRepository) {
        this.mwCommWfMsgDueDtRepository = mwCommWfMsgDueDtRepository;
    }

    /**
     * Save a mwCommWfMsgDueDt.
     *
     * @param mwCommWfMsgDueDt the entity to save
     * @return the persisted entity
     */
    public MwCommWfMsgDueDt save(MwCommWfMsgDueDt mwCommWfMsgDueDt) {
        log.debug("Request to save MwCommWfMsgDueDt : {}", mwCommWfMsgDueDt);
        return mwCommWfMsgDueDtRepository.save(mwCommWfMsgDueDt);
    }

    /**
     * Get all the mwCommWfMsgDueDts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwCommWfMsgDueDt> findAll(Pageable pageable) {
        log.debug("Request to get all MwCommWfMsgDueDts");
        return mwCommWfMsgDueDtRepository.findAll(pageable);
    }

    /**
     * Get one mwCommWfMsgDueDt by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwCommWfMsgDueDt findOne(Long id) {
        log.debug("Request to get MwCommWfMsgDueDt : {}", id);
        return mwCommWfMsgDueDtRepository.findOneBycommWfMsgDueDtSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwCommWfMsgDueDt by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwCommWfMsgDueDt : {}", id);
        MwCommWfMsgDueDt comm = mwCommWfMsgDueDtRepository.findOneBycommWfMsgDueDtSeqAndCrntRecFlg(id, true);
        comm.setCrntRecFlg(false);
        comm.setDelFlg(true);
        comm.setLastUpdDt(Instant.now());
        mwCommWfMsgDueDtRepository.save(comm);
    }

    @Transactional(readOnly = true)
    public List<MwCommWfMsgDueDt> findAllByCurrentRecord() {
        log.debug("Request to get all MwCommWfMsgDueDts");
        return mwCommWfMsgDueDtRepository.findAllByCrntRecFlg(true);
    }

    public long addTaskDueDate(CommWorkflowDto dto, String currUser, long msgKey) {
        long seq = SequenceFinder.findNextVal(Sequences.COMM_WF_MSG_DUE_DT_SEQ);
        MwCommWfMsgDueDt msgDueDt = new MwCommWfMsgDueDt();
        Instant currIns = Instant.now();

        if (dto.taskDueable.equals("After")) {
            msgDueDt.setBefAndAftrFlg(true);
        } else
            msgDueDt.setBefAndAftrFlg(false);
        msgDueDt.setCommWfMsgDueDtSeq(seq);
        msgDueDt.setCrntRecFlg(true);
        msgDueDt.setCrtdBy(currUser);
        msgDueDt.setCrtdDt(currIns);
        msgDueDt.setDelFlg(false);
        msgDueDt.setEffEndDt(currIns);
        msgDueDt.setEffStartDt(currIns);
        msgDueDt.setLastUpdBy(currUser);
        msgDueDt.setLastUpdDt(currIns);
        msgDueDt.setMsgKey(msgKey);
        msgDueDt.setDt((dto.taskDate.toInstant() == null) ? currIns : dto.taskDate.toInstant());
        msgDueDt.setNumOfDays(dto.taskDays);

        return save(msgDueDt).getCommWfMsgDueDtSeq();
    }

    @Transactional
    public long updateTaskDueDate(CommWorkflowDto dto, String currUser, long msgKey) {
        MwCommWfMsgDueDt exMsgDueDt = mwCommWfMsgDueDtRepository.findOneByMsgKey(msgKey);
        Instant currIns = Instant.now();
        exMsgDueDt.setLastUpdBy(currUser);
        exMsgDueDt.setLastUpdDt(currIns);
        if (dto.taskDueable.equals("After")) {
            exMsgDueDt.setBefAndAftrFlg(true);
        } else
            exMsgDueDt.setBefAndAftrFlg(false);
        exMsgDueDt.setCommWfMsgDueDtSeq(exMsgDueDt.getCommWfMsgDueDtSeq());
        exMsgDueDt.setMsgKey(msgKey);
        exMsgDueDt.setDt((dto.taskDate.toInstant() == null) ? currIns : dto.taskDate.toInstant());
        exMsgDueDt.setNumOfDays(dto.taskDays);

        return mwCommWfMsgDueDtRepository.save(exMsgDueDt).getCommWfMsgDueDtSeq();

    }
}
