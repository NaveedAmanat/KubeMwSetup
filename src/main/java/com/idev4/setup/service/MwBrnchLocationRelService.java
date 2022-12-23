package com.idev4.setup.service;

import com.idev4.setup.domain.MwBrnchLocationRel;
import com.idev4.setup.domain.MwPortLocationRel;
import com.idev4.setup.dto.BranchLocationRelDto;
import com.idev4.setup.repository.MwBrnchLocationRelRepository;
import com.idev4.setup.repository.MwPortLocationRelRepository;
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

/**
 * Service Implementation for managing MwBrnchLocationRel.
 */
@Service
@Transactional
public class MwBrnchLocationRelService {

    private final Logger log = LoggerFactory.getLogger(MwBrnchLocationRelService.class);

    private final MwBrnchLocationRelRepository mwBrnchLocationRelRepository;

    private final MwPortLocationRelRepository mwPortLocationRelRepository;

    public MwBrnchLocationRelService(MwBrnchLocationRelRepository mwBrnchLocationRelRepository,
                                     MwPortLocationRelRepository mwPortLocationRelRepository) {
        this.mwBrnchLocationRelRepository = mwBrnchLocationRelRepository;
        this.mwPortLocationRelRepository = mwPortLocationRelRepository;
    }

    /**
     * Save a mwBrnchLocationRel.
     *
     * @param mwBrnchLocationRel the entity to save
     * @return the persisted entity
     */
    public MwBrnchLocationRel save(MwBrnchLocationRel mwBrnchLocationRel) {
        log.debug("Request to save MwBrnchLocationRel : {}", mwBrnchLocationRel);
        return mwBrnchLocationRelRepository.save(mwBrnchLocationRel);
    }

    /**
     * Get all the mwBrnchLocationRels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwBrnchLocationRel> findAll(Pageable pageable) {
        log.debug("Request to get all MwBrnchLocationRels");
        return mwBrnchLocationRelRepository.findAll(pageable);
    }

    /**
     * Get one mwBrnchLocationRel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwBrnchLocationRel findOne(Long id) {
        log.debug("Request to get MwBrnchLocationRel : {}", id);
        return mwBrnchLocationRelRepository.findOne(id);
    }

    /**
     * Delete the mwBrnchLocationRel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwBrnchLocationRel : {}", id);
        mwBrnchLocationRelRepository.delete(id);
    }

    public void addNewBracnchRels(List<BranchLocationRelDto> dtos, String currUser) {
        Instant currIns = Instant.now();
        List<MwBrnchLocationRel> rels = new ArrayList<>();
        dtos.forEach(dto -> {
            MwBrnchLocationRel rel = new MwBrnchLocationRel();
            long seq = SequenceFinder.findNextVal(Sequences.BRNCH_LOCATION_REL_SEQ);
            rel.setBrnchLocationRelSeq(seq);
            rel.setBrnchSeq(dto.branchSeq);
            rel.setCitySeq(dto.citySeq);
            rel.setCrntRecFlg(true);
            rel.setCrtdBy(currUser);
            rel.setCrtdDt(currIns);
            rel.setDelFlg(false);
            rel.setEffStartDt(currIns);
            rel.setLastUpdBy(currUser);
            rel.setLastUpdDt(currIns);
            rels.add(rel);
        });
        mwBrnchLocationRelRepository.save(rels);
    }

    public void addNewBracnchRel(BranchLocationRelDto dto, String currUser) {
        Instant currIns = Instant.now();
        MwBrnchLocationRel exRel = mwBrnchLocationRelRepository.findOneByBrnchSeqAndCitySeqAndCrntRecFlg(dto.branchSeq, dto.citySeq,
            true);
        long seq;
        if (exRel != null) {
            exRel.crntRecFlg(false);
            exRel.setEffEndDt(currIns);
            exRel.setLastUpdBy(currUser);
            exRel.setLastUpdDt(currIns);
            mwBrnchLocationRelRepository.save(exRel);
            seq = exRel.getBrnchLocationRelSeq();
        } else {
            seq = SequenceFinder.findNextVal(Sequences.BRNCH_LOCATION_REL_SEQ);
        }
        MwBrnchLocationRel rel = new MwBrnchLocationRel();
        rel.setBrnchLocationRelSeq(seq);
        rel.setBrnchSeq(dto.branchSeq);
        rel.setCitySeq(dto.citySeq);
        rel.setCrntRecFlg(true);
        rel.setCrtdBy(currUser);
        rel.setCrtdDt(currIns);
        rel.setDelFlg(false);
        rel.setEffStartDt(currIns);
        rel.setLastUpdBy(currUser);
        rel.setLastUpdDt(currIns);
        mwBrnchLocationRelRepository.save(rel);
    }

    public void deleteBracnchRel(BranchLocationRelDto dto, String currUser) {
        Instant currIns = Instant.now();
        MwBrnchLocationRel exRel = mwBrnchLocationRelRepository.findOneByBrnchSeqAndCitySeqAndCrntRecFlg(dto.branchSeq, dto.citySeq,
            true);

        if (exRel != null) {
            exRel.crntRecFlg(false);
            exRel.setEffEndDt(currIns);
            exRel.setLastUpdBy(currUser);
            exRel.setLastUpdDt(currIns);
            exRel.delFlg(false);
            mwBrnchLocationRelRepository.save(exRel);
        }

        List<MwPortLocationRel> rels = mwPortLocationRelRepository.findAllRelForBrnchAndCityUcRel(dto.branchSeq, dto.citySeq);
        rels.forEach(prel -> {
            prel.crntRecFlg(false);
            prel.setEffEndDt(currIns);
            prel.setLastUpdBy(currUser);
            prel.setLastUpdDt(currIns);
            prel.delFlg(false);
        });
        mwPortLocationRelRepository.save(rels);
    }

    public List<MwBrnchLocationRel> getCurrentLocations(long branchSeq) {
        return mwBrnchLocationRelRepository.findAllByBrnchSeqAndCrntRecFlg(branchSeq, true);
    }
}
