package com.idev4.setup.service;

import com.idev4.setup.domain.MwCommWfMsgRcpt;
import com.idev4.setup.dto.CommWorkflowDto;
import com.idev4.setup.repository.MwCommWfMsgRcptRepository;
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
 * Service Implementation for managing MwCommWfMsgRcpt.
 */
@Service
@Transactional
public class MwCommWfMsgRcptService {

    private final Logger log = LoggerFactory.getLogger(MwCommWfMsgRcptService.class);

    private final MwCommWfMsgRcptRepository mwCommWfMsgRcptRepository;

    public MwCommWfMsgRcptService(MwCommWfMsgRcptRepository mwCommWfMsgRcptRepository) {
        this.mwCommWfMsgRcptRepository = mwCommWfMsgRcptRepository;
    }

    /**
     * Save a mwCommWfMsgRcpt.
     *
     * @param mwCommWfMsgRcpt the entity to save
     * @return the persisted entity
     */
    public MwCommWfMsgRcpt save(MwCommWfMsgRcpt mwCommWfMsgRcpt) {
        log.debug("Request to save MwCommWfMsgRcpt : {}", mwCommWfMsgRcpt);
        return mwCommWfMsgRcptRepository.save(mwCommWfMsgRcpt);
    }

    /**
     * Get all the mwCommWfMsgRcpts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwCommWfMsgRcpt> findAll(Pageable pageable) {
        log.debug("Request to get all MwCommWfMsgRcpts");
        return mwCommWfMsgRcptRepository.findAll(pageable);
    }

    /**
     * Get one mwCommWfMsgRcpt by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwCommWfMsgRcpt findOne(Long id) {
        log.debug("Request to get MwCommWfMsgRcpt : {}", id);
        return mwCommWfMsgRcptRepository.findOneByCommWfMsgRcptSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwCommWfMsgRcpt by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwCommWfMsgRcpt : {}", id);
        MwCommWfMsgRcpt comm = mwCommWfMsgRcptRepository.findOneByCommWfMsgRcptSeqAndCrntRecFlg(id, true);
        comm.setCrntRecFlg(false);
        comm.setDelFlg(true);
        comm.setLastUpdDt(Instant.now());
        mwCommWfMsgRcptRepository.save(comm);
    }

    @Transactional(readOnly = true)
    public List<MwCommWfMsgRcpt> findAllByCurrentRecord() {
        log.debug("Request to get all MwCommWfMsgRcpts");
        return mwCommWfMsgRcptRepository.findAllByCrntRecFlg(true);
    }

    public long addNewMessageRecepient(CommWorkflowDto dto, String currUser, long msgKey) {

        if (dto.action.equals("New Task")) {
            MwCommWfMsgRcpt recp = createObject(msgKey, currUser);

            if (dto.taskAssignTo != null)
                recp.setRcptAddr(dto.taskAssignTo);

            return mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
        } else if (dto.action.equals("New SMS")) {

            if (dto.individualPhone != null && !dto.individualPhone.isEmpty()) {
                MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                recp.setRcptAddr(dto.individualPhone);
                return mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
            } else {
                if (dto.coBorrowerPhone) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("CoBorrowerPhone");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }
                if (dto.isClientPhone) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("ClientPhone");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }
                if (dto.spousePhone) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("SpousePhone");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }

                return 1;
            }

        } else {
            if (dto.emailAddress != null && !dto.emailAddress.isEmpty()) {
                MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                recp.setRcptAddr(dto.emailAddress);
                return mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
            } else {
                if (dto.isGroup1) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("isGroup1");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }
                if (dto.isGroup2) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("isGroup2");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }
                if (dto.isGroup3) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("isGroup3");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }
            }

            return 1;
        }

        // recp.setRcptAddrTypKey(Long);
        // recp.setRcptTypKey(Long);
    }

    public MwCommWfMsgRcpt createObject(long msgKey, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.COMM_WF_MSG_RCPT_SEQ);
        MwCommWfMsgRcpt recp = new MwCommWfMsgRcpt();
        Instant currIns = Instant.now();
        recp.setCommWfMsgRcptSeq(seq);
        recp.setCrntRecFlg(true);
        recp.setCrtdBy(currUser);
        recp.setCrtdDt(currIns);
        recp.setDelFlg(false);
        recp.setEffStartDt(currIns);
        recp.setLastUpdBy(currUser);
        recp.setLastUpdDt(currIns);
        recp.setMsgKey(msgKey);
        return recp;
    }

    @Transactional
    public long updateExistingMessageRecepient(CommWorkflowDto dto, String currUser, long msgKey) {
        List<MwCommWfMsgRcpt> exRcps = mwCommWfMsgRcptRepository.findAllByMsgKey(msgKey);
        Instant currIns = Instant.now();
        exRcps.forEach(rcp -> {
            rcp.setCrntRecFlg(false);
            rcp.setEffEndDt(currIns);
            rcp.setLastUpdBy(currUser);
            rcp.setLastUpdDt(currIns);
        });

        mwCommWfMsgRcptRepository.save(exRcps);


        if (dto.action.equals("New Task")) {
            MwCommWfMsgRcpt recp = createObject(msgKey, currUser);

            if (dto.taskAssignTo != null)
                recp.setRcptAddr(dto.taskAssignTo);

            return mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
        } else if (dto.action.equals("New SMS")) {

            if (dto.individualPhone != null) {
                MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                recp.setRcptAddr(dto.individualPhone);
                return mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
            } else {
                if (dto.coBorrowerPhone) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("CoBorrowerPhone");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }
                if (dto.isClientPhone) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("ClientPhone");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }
                if (dto.spousePhone) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("SpousePhone");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }

                return 1;
            }

        } else {
            if (dto.emailAddress != null) {
                MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                recp.setRcptAddr(dto.emailAddress);
                return mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
            } else {
                if (dto.isGroup1) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("isGroup1");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }
                if (dto.isGroup2) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("isGroup2");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }
                if (dto.isGroup3) {
                    MwCommWfMsgRcpt recp = createObject(msgKey, currUser);
                    recp.setRcptAddr("isGroup3");
                    mwCommWfMsgRcptRepository.save(recp).getCommWfMsgRcptSeq();
                }
            }

            return 1;
        }
    }
}
