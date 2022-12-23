package com.idev4.setup.service;


import com.idev4.setup.domain.MwCommWf;
import com.idev4.setup.domain.MwCommWfMsg;
import com.idev4.setup.domain.MwCommWfMsgDueDt;
import com.idev4.setup.domain.MwCommWfMsgRcpt;
import com.idev4.setup.dto.CommWorkflowDto;
import com.idev4.setup.repository.MwCommWfMsgDueDtRepository;
import com.idev4.setup.repository.MwCommWfMsgRcptRepository;
import com.idev4.setup.repository.MwCommWfMsgRepository;
import com.idev4.setup.repository.MwCommWfRepository;
import com.idev4.setup.web.rest.util.Queries;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.Date;
import java.util.List;


/**
 * Service Implementation for managing MwCommWf.
 */
@Service
@Transactional
public class MwCommWfService {

    private final Logger log = LoggerFactory.getLogger(MwCommWfService.class);

    private final MwCommWfRepository mwCommWfRepository;

    private final MwCommWfMsgRepository mwCommWfMsgRepository;

    private final MwCommWfMsgDueDtRepository mwCommWfMsgDueDtRepository;

    private final MwCommWfMsgRcptRepository mwCommWfMsgRcptRepository;

    private final EntityManager em;

    public MwCommWfService(MwCommWfRepository mwCommWfRepository, MwCommWfMsgRepository mwCommWfMsgRepository,
                           MwCommWfMsgDueDtRepository mwCommWfMsgDueDtRepository, MwCommWfMsgRcptRepository mwCommWfMsgRcptRepository,
                           EntityManager em) {
        this.mwCommWfRepository = mwCommWfRepository;
        this.mwCommWfMsgRepository = mwCommWfMsgRepository;
        this.mwCommWfMsgDueDtRepository = mwCommWfMsgDueDtRepository;
        this.mwCommWfMsgRcptRepository = mwCommWfMsgRcptRepository;
        this.em = em;
    }

    /**
     * Save a mwCommWf.
     *
     * @param mwCommWf the entity to save
     * @return the persisted entity
     */
    public MwCommWf save(MwCommWf mwCommWf) {
        log.debug("Request to save MwCommWf : {}", mwCommWf);
        return mwCommWfRepository.save(mwCommWf);
    }

    /**
     * Get all the mwCommWfs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwCommWf> findAll(Pageable pageable) {
        log.debug("Request to get all MwCommWfs");
        return mwCommWfRepository.findAll(pageable);
    }

    /**
     * Get one mwCommWf by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwCommWf findOne(Long id) {
        log.debug("Request to get MwCommWf : {}", id);
        return mwCommWfRepository.findOneByCommWfSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwCommWf by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwCommWf : {}", id);
        MwCommWf comm = mwCommWfRepository.findOneByCommWfSeqAndCrntRecFlg(id, true);
        comm.setCrntRecFlg(false);
        comm.setDelFlg(true);
        mwCommWfRepository.save(comm);
    }

    @Transactional(readOnly = true)
    public List<MwCommWf> findAllByCurrentRecord() {
        log.debug("Request to get all MwCommWfs");
        return mwCommWfRepository.findAllByCrntRecFlg(true);
    }

    public CommWorkflowDto getWorkAction(long seq) {
        MwCommWf wf = mwCommWfRepository.findOneByCommWfSeqAndCrntRecFlg(seq, true);
        CommWorkflowDto dto = new CommWorkflowDto();
        dto.ruleName = wf.getCommWfNm();
        dto.ruleCriteria = wf.getCommWfRulStr();
        dto.workflowSeq = wf.getCommWfSeq();
        dto.workflowComments = wf.getCommWfCmnt();
        dto.functionKey = wf.getMwFuncKey();
        MwCommWfMsg wfMsg = mwCommWfMsgRepository.findOneByCommWfSeqAndCrntRecFlg(wf.getCommWfSeq(), true);

        if (wfMsg != null) {
            dto.actionSeq = wfMsg.getCommWfMsgSeq();
            MwCommWfMsgDueDt dueDt = mwCommWfMsgDueDtRepository.findOneByMsgKeyAndCrntRecFlg(dto.actionSeq, true);
            if (dueDt != null) {
                if (dueDt.isBefAndAftrFlg()) {
                    dto.taskDueable = "After";
                }
                dto.taskDate = Date.from(dueDt.getDt());
                dto.taskDays = dueDt.getNumOfDays();
                dto.taskActionComments = wfMsg.getMsgStr();
                MwCommWfMsgRcpt mwCommWfMsg = mwCommWfMsgRcptRepository.findOneByMsgKeyAndCrntRecFlg(dto.actionSeq, true);
                dto.taskAssignTo = mwCommWfMsg.getRcptAddr();
                dto.action = "New Task";
                return dto;
            } else {
                List<MwCommWfMsgRcpt> mwCommWfMsg = mwCommWfMsgRcptRepository.findAllByMsgKeyAndCrntRecFlg(dto.actionSeq, true);
                if (mwCommWfMsg != null) {
                    mwCommWfMsg.forEach((recp) -> {
                        if (recp.getRcptAddr() != null) {
                            if (recp.getRcptAddr().equals("CoBorrowerPhone")) {
                                dto.coBorrowerPhone = true;
                                dto.action = "New SMS";
                                dto.messageText = wfMsg.getMsgStr();
                            } else if (recp.getRcptAddr().equals("ClientPhone")) {
                                dto.isClientPhone = true;
                                dto.action = "New SMS";
                                dto.messageText = wfMsg.getMsgStr();
                            } else if (recp.getRcptAddr().equals("SpousePhone")) {
                                dto.spousePhone = true;
                                dto.action = "New SMS";
                                dto.messageText = wfMsg.getMsgStr();
                            } else if (recp.getRcptAddr().equals("isGroup1")) {
                                dto.isGroup1 = true;
                                dto.action = "Email Alert";
                                dto.emailText = wfMsg.getMsgStr();
                                dto.emailSubject = wfMsg.getMsgTitle();
                            } else if (recp.getRcptAddr().equals("isGroup2")) {
                                dto.isGroup2 = true;
                                dto.action = "Email Alert";
                                dto.emailText = wfMsg.getMsgStr();
                                dto.emailSubject = wfMsg.getMsgTitle();
                            } else if (recp.getRcptAddr().equals("isGroup3")) {
                                dto.isGroup3 = true;
                                dto.action = "Email Alert";
                                dto.emailText = wfMsg.getMsgStr();
                                dto.emailSubject = wfMsg.getMsgTitle();
                            } else if (recp.getRcptAddr().contains("@")) {
                                dto.emailAddress = recp.getRcptAddr();
                                dto.action = "Email Alert";
                                dto.emailText = wfMsg.getMsgStr();
                                dto.emailSubject = wfMsg.getMsgTitle();
                            } else {
                                dto.individualPhone = recp.getRcptAddr();
                                dto.action = "New SMS";
                                dto.messageText = wfMsg.getMsgStr();
                            }
                        }

                    });
                }
                return dto;
            }
        }

        return dto;
    }

    public long addNewCommWorkFlow(CommWorkflowDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.COMM_WF_SEQ);
        MwCommWf commWf = new MwCommWf();
        Instant currIns = Instant.now();
        commWf.setCommWfSeq(seq);
        commWf.setCommWfId("00000" + seq);
        commWf.setCommWfNm(dto.ruleName);
        commWf.setCommWfRulStr(dto.ruleCriteria);
        commWf.setCommWfCmnt(dto.workflowComments);
        commWf.setCrntRecFlg(true);
        commWf.setCrtdBy(currUser);
        commWf.setCrtdDt(currIns);
        commWf.setDelFlg(false);
        commWf.setEffStartDt(currIns);
        commWf.setLastUpdBy(currUser);
        commWf.setLastUpdDt(currIns);
        commWf.setMwFuncKey(dto.functionKey);

        String query = Queries.statusSeq;
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();

        long actSeq = 0;
        for (Object[] st : result) {
            if (st[1].toString().toLowerCase().equals("active")) {
                actSeq = Long.valueOf(st[2].toString());
            }
        }

        commWf.setCommWfStsKey(actSeq);


        return mwCommWfRepository.save(commWf).getCommWfSeq();
    }

    @Transactional
    public long updateExsitingCommWorkFlow(CommWorkflowDto dto, String currUser) {

        MwCommWf commWf = mwCommWfRepository.findOneByCommWfSeqAndCrntRecFlg(dto.commWorkflowSeq, true);
        Instant currIns = Instant.now();
        if (commWf == null) {
            return 0;
        }
        commWf.setLastUpdDt(currIns);
        commWf.setLastUpdBy(currUser);
        commWf.setCommWfSeq(dto.commWorkflowSeq);
        commWf.setCommWfNm(dto.ruleName);
        commWf.setCommWfRulStr(dto.ruleCriteria);
        commWf.setCommWfCmnt(dto.workflowComments);
        commWf.setCommWfId("00000" + dto.commWorkflowSeq);

        return mwCommWfRepository.save(commWf).getCommWfSeq();

    }
}
