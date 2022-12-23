package com.idev4.setup.service;

import com.idev4.setup.domain.MwCommWfMsg;
import com.idev4.setup.dto.CommWorkflowDto;
import com.idev4.setup.repository.MwCommWfMsgRepository;
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
 * Service Implementation for managing MwCommWfMsg.
 */
@Service
@Transactional
public class MwCommWfMsgService {

    private final Logger log = LoggerFactory.getLogger(MwCommWfMsgService.class);

    private final MwCommWfMsgRepository mwCommWfMsgRepository;

    private final MwCommWfMsgDueDtService mwCommWfMsgDueDtService;

    private final MwCommWfMsgRcptService mwCommWfMsgRcptService;


    public MwCommWfMsgService(MwCommWfMsgRepository mwCommWfMsgRepository, MwCommWfMsgDueDtService mwCommWfMsgDueDtService,
                              MwCommWfMsgRcptService mwCommWfMsgRcptService) {
        this.mwCommWfMsgRepository = mwCommWfMsgRepository;
        this.mwCommWfMsgDueDtService = mwCommWfMsgDueDtService;
        this.mwCommWfMsgRcptService = mwCommWfMsgRcptService;
    }

    /**
     * Save a mwCommWfMsg.
     *
     * @param mwCommWfMsg the entity to save
     * @return the persisted entity
     */
    public MwCommWfMsg save(MwCommWfMsg mwCommWfMsg) {
        log.debug("Request to save MwCommWfMsg : {}", mwCommWfMsg);
        return mwCommWfMsgRepository.save(mwCommWfMsg);
    }

    /**
     * Get all the mwCommWfMsgs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwCommWfMsg> findAll(Pageable pageable) {
        log.debug("Request to get all MwCommWfMsgs");
        return mwCommWfMsgRepository.findAll(pageable);
    }

    /**
     * Get one mwCommWfMsg by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwCommWfMsg findOne(Long id) {
        log.debug("Request to get MwCommWfMsg : {}", id);
        return mwCommWfMsgRepository.findOneByCommWfMsgSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwCommWfMsg by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwCommWfMsg : {}", id);
        MwCommWfMsg comm = mwCommWfMsgRepository.findOneByCommWfMsgSeqAndCrntRecFlg(id, true);
        comm.setCrntRecFlg(false);
        comm.setDelFlg(true);
        mwCommWfMsgRepository.save(comm);
    }

    @Transactional(readOnly = true)
    public List<MwCommWfMsg> findAllByCurrentRecord() {
        log.debug("Request to get all MwCommWfMsgs");
        return mwCommWfMsgRepository.findAllByCrntRecFlg(true);
    }

    @Transactional
    public long addNewWorkFlowActionTask(CommWorkflowDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.COMM_WF_MSG_SEQ);
        if (mwCommWfMsgRepository.findOneByCommWfSeqAndCrntRecFlg(dto.workflowSeq, true) != null)
            return 0;

        MwCommWfMsg wfMsg = new MwCommWfMsg();
        Instant currIns = Instant.now();
        wfMsg.setCommWfMsgSeq(seq);
        wfMsg.setCrntRecFlg(true);
        wfMsg.setCrtdBy(currUser);
        wfMsg.setCrtdDt(currIns);
        wfMsg.setDelFlg(false);
        wfMsg.setEffStartDt(currIns);
        wfMsg.setLastUpdBy(currUser);
        wfMsg.setLastUpdDt(currIns);
        //wfMsg.setMsgDueDt(dto.taskDueable);
        wfMsg.setMsgStr(dto.taskActionComments);
        wfMsg.setMsgTitle(dto.taskSubject);
        wfMsg.setCommWfSeq(dto.workflowSeq);
        //wfMsg.setMsgStsKey(dto.taskStatus);
        // wfMsg.setMsgTypKey(dto.action);


        long wfMsgSeq = save(wfMsg).getCommWfMsgSeq();
        mwCommWfMsgRcptService.addNewMessageRecepient(dto, currUser, wfMsgSeq);
        return mwCommWfMsgDueDtService.addTaskDueDate(dto, currUser, wfMsgSeq);
    }

    @Transactional
    public long updateExistingWorkFlowActionTask(CommWorkflowDto dto, String currUser) {
        MwCommWfMsg exWfMsg = mwCommWfMsgRepository.findOneByCommWfMsgSeqAndCrntRecFlg(dto.actionSeq, true);
        Instant currIns = Instant.now();
        if (exWfMsg == null) {
            return 0;
        }

        exWfMsg.setCrntRecFlg(false);
        exWfMsg.setEffEndDt(currIns);
        exWfMsg.setLastUpdBy(currUser);
        exWfMsg.setLastUpdDt(currIns);
        mwCommWfMsgRepository.save(exWfMsg);

        MwCommWfMsg wfMsg = new MwCommWfMsg();

        wfMsg.setCommWfMsgSeq(exWfMsg.getCommWfMsgSeq());
        wfMsg.setCrntRecFlg(true);
        wfMsg.setCrtdBy(currUser);
        wfMsg.setCrtdDt(currIns);
        wfMsg.setDelFlg(false);
        wfMsg.setEffStartDt(currIns);
        wfMsg.setLastUpdBy(currUser);
        wfMsg.setLastUpdDt(currIns);
        wfMsg.setMsgDueDt(dto.taskDueable);
        wfMsg.setMsgStr(dto.taskActionComments);
        wfMsg.setMsgTitle(dto.taskSubject);
        wfMsg.setCommWfSeq(dto.workflowSeq);


        long wfMsgSeq = mwCommWfMsgRepository.save(wfMsg).getCommWfMsgSeq();
        mwCommWfMsgRcptService.updateExistingMessageRecepient(dto, currUser, wfMsgSeq);
        return mwCommWfMsgDueDtService.updateTaskDueDate(dto, currUser, wfMsgSeq);
    }

    public long addNewWorkFlowActionSms(CommWorkflowDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.COMM_WF_MSG_SEQ);

        if (mwCommWfMsgRepository.findOneByCommWfSeqAndCrntRecFlg(dto.workflowSeq, true) != null)
            return 0;
        MwCommWfMsg wfMsg = new MwCommWfMsg();
        Instant currIns = Instant.now();
        wfMsg.commWfMsgSeq(seq);
        wfMsg.setCrntRecFlg(true);
        wfMsg.setCrtdBy(currUser);
        wfMsg.setCrtdDt(currIns);
        wfMsg.setDelFlg(false);
        wfMsg.setEffStartDt(currIns);
        wfMsg.setLastUpdBy(currUser);
        wfMsg.setLastUpdDt(currIns);
        wfMsg.setMsgDueDt(dto.taskDueable);
        wfMsg.setMsgStr(dto.messageText);
        wfMsg.setCommWfSeq(dto.workflowSeq);


        long wfMsgSeq = mwCommWfMsgRepository.save(wfMsg).getCommWfMsgSeq();

        return mwCommWfMsgRcptService.addNewMessageRecepient(dto, currUser, wfMsgSeq);
    }

    public long updateExistingWorkFlowActionSms(CommWorkflowDto dto, String currUser) {
        MwCommWfMsg exWfMsg = mwCommWfMsgRepository.findOneByCommWfMsgSeqAndCrntRecFlg(dto.actionSeq, true);
        Instant currIns = Instant.now();
        if (exWfMsg == null) {
            return 0;
        }

        exWfMsg.setEffEndDt(currIns);
        exWfMsg.setLastUpdBy(currUser);
        exWfMsg.setMsgDueDt(dto.taskDueable);
        exWfMsg.setMsgStr(dto.taskActionComments);
        exWfMsg.setMsgTitle(dto.taskSubject);
        exWfMsg.setCommWfSeq(dto.workflowSeq);
        mwCommWfMsgRepository.save(exWfMsg);


        long wfMsgSeq = mwCommWfMsgRepository.save(exWfMsg).getCommWfMsgSeq();
        mwCommWfMsgRcptService.updateExistingMessageRecepient(dto, currUser, wfMsgSeq);
        return mwCommWfMsgDueDtService.updateTaskDueDate(dto, currUser, wfMsgSeq);

    }

    public long addNewWorkFlowActionEmail(CommWorkflowDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.COMM_WF_MSG_SEQ);
        if (mwCommWfMsgRepository.findOneByCommWfSeqAndCrntRecFlg(dto.workflowSeq, true) != null)
            return 0;
        MwCommWfMsg wfMsg = new MwCommWfMsg();
        Instant currIns = Instant.now();
        wfMsg.setCommWfMsgSeq(seq);
        wfMsg.setCrntRecFlg(true);
        wfMsg.setCrtdBy(currUser);
        wfMsg.setCrtdDt(currIns);
        wfMsg.setDelFlg(false);
        wfMsg.setEffStartDt(currIns);
        wfMsg.setLastUpdBy(currUser);
        wfMsg.setLastUpdDt(currIns);
        wfMsg.setMsgStr(dto.emailText);
        wfMsg.setMsgTitle(dto.emailSubject);
        wfMsg.setCommWfSeq(dto.workflowSeq);


        long wfMsgSeq = mwCommWfMsgRepository.save(wfMsg).getCommWfMsgSeq();

        return mwCommWfMsgRcptService.addNewMessageRecepient(dto, currUser, wfMsgSeq);
    }

    public long updateExistingWorkFlowActionEmail(CommWorkflowDto dto, String currUser) {
        MwCommWfMsg exWfMsg = mwCommWfMsgRepository.findOneByCommWfMsgSeqAndCrntRecFlg(dto.actionSeq, true);
        Instant currIns = Instant.now();
        if (exWfMsg == null) {
            return 0;
        }

        exWfMsg.setLastUpdBy(currUser);
        exWfMsg.setLastUpdDt(currIns);
        exWfMsg.setMsgDueDt(dto.taskDueable);
        exWfMsg.setMsgStr(dto.messageText);
        exWfMsg.setCommWfSeq(dto.workflowSeq);


        long wfMsgSeq = mwCommWfMsgRepository.save(exWfMsg).getCommWfMsgSeq();

        return mwCommWfMsgRcptService.updateExistingMessageRecepient(dto, currUser, wfMsgSeq);
    }
}
