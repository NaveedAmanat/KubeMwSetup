package com.idev4.setup.service;

import com.idev4.setup.domain.MwAprvlWf;
import com.idev4.setup.dto.AprovalWorkflowDto;
import com.idev4.setup.repository.MwAprvlWfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing MwAprvlWf.
 */
@Service
@Transactional
public class MwAprvlWfService {

    private final Logger log = LoggerFactory.getLogger(MwAprvlWfService.class);

    private final MwAprvlWfRepository mwAprvlWfRepository;

    public MwAprvlWfService(MwAprvlWfRepository mwAprvlWfRepository) {
        this.mwAprvlWfRepository = mwAprvlWfRepository;
    }

    /**
     * Save a mwAprvlWf.
     *
     * @param mwAprvlWf the entity to save
     * @return the persisted entity
     */
    public MwAprvlWf save(MwAprvlWf mwAprvlWf) {
        log.debug("Request to save MwAprvlWf : {}", mwAprvlWf);
        return mwAprvlWfRepository.save(mwAprvlWf);
    }

    /**
     * Get all the mwAprvlWfs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwAprvlWf> findAll(Pageable pageable) {
        log.debug("Request to get all MwAprvlWfs");
        return mwAprvlWfRepository.findAll(pageable);
    }

    /**
     * Get one mwAprvlWf by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwAprvlWf findOne(Long id) {
        log.debug("Request to get MwAprvlWf : {}", id);
        return mwAprvlWfRepository.findOne(id);
    }

    /**
     * Delete the mwAprvlWf by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwAprvlWf : {}", id);
        MwAprvlWf wf = mwAprvlWfRepository.findOne(id);
        wf.setCrntRecFlg(false);
        wf.setDelFlg(true);
        wf.setLastUpdDt(Instant.now());
        mwAprvlWfRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<MwAprvlWf> findAllByCurrentRecord() {
        log.debug("Request to get all MwAprvlWfs");
        return mwAprvlWfRepository.findAllByCrntRecFlg(true);
    }

    public long addNewApprovalWorkflow(AprovalWorkflowDto dto, String currUser) {
        MwAprvlWf aprvl = new MwAprvlWf();
        Instant currIns = Instant.now();

        aprvl.setAprvlWfId(dto.workflowId);
        aprvl.setAprvlWfNm(dto.description);
        aprvl.setAprvlWfRulStr(dto.ruleCriteria);
        // aprvl.setAprvlWfStsKey(dto.statusKey);
        aprvl.setCrntRecFlg(true);
        aprvl.setCrtdBy(currUser);
        aprvl.setCrtdDt(currIns);
        aprvl.setDelFlg(false);
        aprvl.setEffStartDt(currIns);
        aprvl.setLastUpdBy(currUser);
        aprvl.setLastUpdDt(currIns);
        return save(aprvl).getAprvlWfSeq();
    }

    public long updateExistingApprovalWorkflow(AprovalWorkflowDto dto, String currUser) {

        if (findOne(dto.aprvlWorkflowSeq) == null)
            return 0;

        MwAprvlWf aprvl = findOne(dto.aprvlWorkflowSeq);
        Instant currIns = Instant.now();

        aprvl.setAprvlWfSeq(dto.aprvlWorkflowSeq);
        aprvl.setAprvlWfId(dto.workflowId);
        aprvl.setAprvlWfNm(dto.description);
        aprvl.setAprvlWfRulStr(dto.ruleCriteria);
        // aprvl.setAprvlWfStsKey(dto.statusKey);
        aprvl.setLastUpdBy(currUser);
        aprvl.setLastUpdDt(currIns);
        return save(aprvl).getAprvlWfSeq();
    }
}
