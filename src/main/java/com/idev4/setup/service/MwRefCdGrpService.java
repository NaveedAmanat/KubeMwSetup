package com.idev4.setup.service;

import com.idev4.setup.domain.MwRefCdGrp;
import com.idev4.setup.dto.CodeGroupDto;
import com.idev4.setup.repository.MwRefCdGrpRepository;
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
 * Service Implementation for managing MwRefCdGrp.
 */
@Service
@Transactional
public class MwRefCdGrpService {

    private final Logger log = LoggerFactory.getLogger(MwRefCdGrpService.class);

    private final MwRefCdGrpRepository mwRefCdGrpRepository;

    public MwRefCdGrpService(MwRefCdGrpRepository mwRefCdGrpRepository) {
        this.mwRefCdGrpRepository = mwRefCdGrpRepository;
    }

    /**
     * Save a mwRefCdGrp.
     *
     * @param mwRefCdGrp the entity to save
     * @return the persisted entity
     */
    public MwRefCdGrp save(MwRefCdGrp mwRefCdGrp) {
        log.debug("Request to save MwRefCdGrp : {}", mwRefCdGrp);
        return mwRefCdGrpRepository.save(mwRefCdGrp);
    }

    /**
     * Get all the mwRefCdGrps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwRefCdGrp> findAll(Pageable pageable) {
        log.debug("Request to get all MwRefCdGrps");
        return mwRefCdGrpRepository.findAll(pageable);
    }

    /**
     * Get one mwRefCdGrp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwRefCdGrp findOne(Long id) {
        log.debug("Request to get MwRefCdGrp : {}", id);
        return mwRefCdGrpRepository.findOneByRefCdGrpSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwRefCdGrp by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwRefCdGrp : {}", id);
        MwRefCdGrp grp = mwRefCdGrpRepository.findOneByRefCdGrpSeqAndCrntRecFlg(id, true);
        grp.setCrntRecFlg(false);
        grp.setDelFlg(true);
        grp.setLastUpdDt(Instant.now());
        mwRefCdGrpRepository.save(grp);
    }

    @Transactional(readOnly = true)
    public List<MwRefCdGrp> findAllByCurrentRecord() {
        log.debug("Request to get all MwRefCdGrps");
        return mwRefCdGrpRepository.findAllByCrntRecFlgOrderByRefCdGrpSeqDesc(true);
    }

    public MwRefCdGrp createNewCodesGroup(CodeGroupDto codeGroup, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.REF_GRP_SEQ);
        MwRefCdGrp refCodeGrp = new MwRefCdGrp();
        Instant currIns = Instant.now();
        refCodeGrp.setRefCdGrpSeq(seq);
        refCodeGrp.setCrntRecFlg(true);
        refCodeGrp.setCrtdBy(currUser);
        refCodeGrp.setCrtdDt(currIns);
        refCodeGrp.setDelFlg(false);
        refCodeGrp.setEffStartDt(currIns);
        refCodeGrp.setLastUpdBy(currUser);
        refCodeGrp.setLastUpdDt(currIns);
        refCodeGrp.setRefCdGrp(String.format("%04d", seq));
        refCodeGrp.setRefCdGrpName(codeGroup.groupName);
        refCodeGrp.setRefCdGrpCmnt(codeGroup.groupDescription);
        return mwRefCdGrpRepository.save(refCodeGrp);
    }

    public MwRefCdGrp updateExistingCodesGroup(CodeGroupDto codeGroup, String currUser) {
        MwRefCdGrp refCodeGrp = mwRefCdGrpRepository.findOneByRefCdGrpSeqAndCrntRecFlg(codeGroup.groupSeq, true);
        Instant currIns = Instant.now();
        if (refCodeGrp == null) {
            return null;
        }
        refCodeGrp.setLastUpdBy(currUser);
        refCodeGrp.setLastUpdDt(currIns);
        refCodeGrp.setRefCdGrpName(codeGroup.groupName);
        refCodeGrp.setRefCdGrpCmnt(codeGroup.groupDescription);


        return mwRefCdGrpRepository.save(refCodeGrp);
    }

}
