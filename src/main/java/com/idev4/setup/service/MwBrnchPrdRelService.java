package com.idev4.setup.service;

import com.idev4.setup.domain.MwBrnchPrdRel;
import com.idev4.setup.dto.BranchPrdRelDto;
import com.idev4.setup.repository.MwBrnchPrdRelRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing MwBrnchPrdRel.
 */
@Service
@Transactional
public class MwBrnchPrdRelService {

    private final Logger log = LoggerFactory.getLogger(MwBrnchPrdRelService.class);

    private final MwBrnchPrdRelRepository mwBrnchPrdRelRepository;

    public MwBrnchPrdRelService(MwBrnchPrdRelRepository mwBrnchPrdRelRepository) {
        this.mwBrnchPrdRelRepository = mwBrnchPrdRelRepository;
    }

    /**
     * Save a mwBrnchPrdRel.
     *
     * @param mwBrnchPrdRel the entity to save
     * @return the persisted entity
     */
    public MwBrnchPrdRel save(MwBrnchPrdRel mwBrnchPrdRel) {
        log.debug("Request to save MwBrnchPrdRel : {}", mwBrnchPrdRel);
        return mwBrnchPrdRelRepository.save(mwBrnchPrdRel);
    }

    /**
     * Get all the mwBrnchPrdRels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwBrnchPrdRel> findAll(Pageable pageable) {
        log.debug("Request to get all MwBrnchPrdRels");
        return mwBrnchPrdRelRepository.findAll(pageable);
    }

    /**
     * Get one mwBrnchPrdRel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MwBrnchPrdRel> findOne(Long id) {
        log.debug("Request to get MwBrnchPrdRel : {}", id);
        return Optional.ofNullable(mwBrnchPrdRelRepository.findOne(id));
    }

    /**
     * Delete the mwBrnchPrdRel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwBrnchPrdRel : {}", id);
        mwBrnchPrdRelRepository.delete(id);
    }

    public void addNewBracnchRels(List<BranchPrdRelDto> dtos, String currUser) {
        Instant currIns = Instant.now();
        if (dtos.size() > 0) {
            List<MwBrnchPrdRel> exPrdRels = mwBrnchPrdRelRepository.findAllByBrnchSeqAndCrntRecFlg(dtos.get(0).branchSeq, true);
            exPrdRels.forEach(rel -> {
                rel.crntRecFlg(false);
                rel.setEffEndDt(currIns);
                rel.setLastUpdBy(currUser);
                rel.setLastUpdDt(currIns);
            });

            mwBrnchPrdRelRepository.save(exPrdRels);
        }
        List<MwBrnchPrdRel> rels = new ArrayList<>();

        dtos.forEach(dto -> {
            MwBrnchPrdRel rel = new MwBrnchPrdRel();
            long seq = SequenceFinder.findNextVal(Sequences.BRNCH_PRD_REL_SEQ);
            rel.setBrnchPrdRelSeq(seq);
            rel.setBrnchSeq(dto.branchSeq);
            rel.setPrdSeq(dto.prdSeq);
            rel.setCrntRecFlg(true);
            rel.setCrtdBy(currUser);
            rel.setCrtdDt(currIns);
            rel.setEffStartDt(currIns);
            rel.setLastUpdBy(currUser);
            rel.setLastUpdDt(currIns);
            rels.add(rel);
        });
        mwBrnchPrdRelRepository.save(rels);
    }

    public void addNewBracnchRel(BranchPrdRelDto dto, String currUser) {
        Instant currIns = Instant.now();
        MwBrnchPrdRel exPrdRel = mwBrnchPrdRelRepository.findOneByBrnchSeqAndPrdSeqAndCrntRecFlg(dto.branchSeq, dto.prdSeq, true);

        long seq;
        if (exPrdRel != null) {
            exPrdRel.crntRecFlg(false);
            exPrdRel.setEffEndDt(currIns);
            exPrdRel.setLastUpdBy(currUser);
            exPrdRel.setLastUpdDt(currIns);
            mwBrnchPrdRelRepository.save(exPrdRel);
            seq = exPrdRel.getBrnchPrdRelSeq();
        } else {
            seq = SequenceFinder.findNextVal(Sequences.BRNCH_PRD_REL_SEQ);
        }

        MwBrnchPrdRel rel = new MwBrnchPrdRel();

        rel.setBrnchPrdRelSeq(seq);
        rel.setBrnchSeq(dto.branchSeq);
        rel.setPrdSeq(dto.prdSeq);
        rel.setCrntRecFlg(true);
        rel.setCrtdBy(currUser);
        rel.setCrtdDt(currIns);
        rel.setEffStartDt(currIns);
        rel.setLastUpdBy(currUser);
        rel.setLastUpdDt(currIns);

        mwBrnchPrdRelRepository.save(rel);
    }

    public void removeBracnchRel(BranchPrdRelDto dto, String currUser) {
        Instant currIns = Instant.now();
        MwBrnchPrdRel exPrdRel = mwBrnchPrdRelRepository.findOneByBrnchSeqAndPrdSeqAndCrntRecFlg(dto.branchSeq, dto.prdSeq, true);
        if (exPrdRel != null) {
            exPrdRel.crntRecFlg(false);
            exPrdRel.setEffEndDt(currIns);
            exPrdRel.setLastUpdBy(currUser);
            exPrdRel.setLastUpdDt(currIns);
            exPrdRel.setDelFlg(true);
            mwBrnchPrdRelRepository.save(exPrdRel);
        }

    }

    public List<MwBrnchPrdRel> getCurrentProducts(long branchSeq) {
        return mwBrnchPrdRelRepository.findAllByBrnchSeqAndCrntRecFlg(branchSeq, true);
    }
}
