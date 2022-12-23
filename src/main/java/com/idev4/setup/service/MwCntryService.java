package com.idev4.setup.service;

import com.idev4.setup.domain.MwCntry;
import com.idev4.setup.domain.MwSt;
import com.idev4.setup.dto.CountryDto;
import com.idev4.setup.repository.MwCntryRepository;
import com.idev4.setup.repository.MwStRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing MwCntry.
 */
@Service
@Transactional
public class MwCntryService {

    private final Logger log = LoggerFactory.getLogger(MwCntryService.class);

    private final MwCntryRepository mwCntryRepository;

    private final MwStRepository mwStRepository;

    private final EntityManager em;

    public MwCntryService(MwCntryRepository mwCntryRepository, MwStRepository mwStRepository, EntityManager em) {
        this.mwCntryRepository = mwCntryRepository;
        this.mwStRepository = mwStRepository;
        this.em = em;
    }

    /**
     * Save a mwCntry.
     *
     * @param mwCntry the entity to save
     * @return the persisted entity
     */
    public MwCntry save(MwCntry mwCntry) {
        log.debug("Request to save MwCntry : {}", mwCntry);
        return mwCntryRepository.save(mwCntry);
    }

    /**
     * Get all the mwCntries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwCntry> findAll(Pageable pageable) {
        log.debug("Request to get all MwCntries");
        return mwCntryRepository.findAll(pageable);
    }

    /**
     * Get one mwCntry by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwCntry findOne(Long id) {
        log.debug("Request to get MwCntry : {}", id);
        return mwCntryRepository.findOneByCntrySeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwCntry> findAllHistory(Long id) {
        log.debug("Request to get MwCntry : {}", id);
        return mwCntryRepository.findAllByCntrySeq(id);
    }

    /**
     * Delete the mwCntry by id.
     *
     * @param id the id of the entity
     */
    public boolean delete(Long id) {
        log.debug("Request to delete MwCntry : {}", id);
        List<MwSt> sts = mwStRepository.findAllByCntrySeqAndCrntRecFlg(id, true);
        if (sts != null && sts.size() > 0)
            return false;
        MwCntry cntry = mwCntryRepository.findOneByCntrySeqAndCrntRecFlg(id, true);
        cntry.setCrntRecFlg(false);
        cntry.setDelFlg(true);
        cntry.setLastUpdDt(Instant.now());
        mwCntryRepository.save(cntry);
        return true;
    }

    //Edited by Areeba
    @Transactional(readOnly = true)
    public List<MwCntry> findAllByCurrentRecord(String filter) {
        log.debug("Request to get all MwCntries");
        String script = " select * from mw_cntry cntry \n" +
            " where cntry.crnt_rec_flg = 1 ";

        if (filter != null && filter.length() > 0) {
            String search = (" and ((lower(cntry.cntry_nm) LIKE '%?%') " +
                " or (lower(cntry.cntry_cmnt) LIKE '%?%')) ")
                .replace("?", filter.toLowerCase());
            script += search;
        }

        List<MwCntry> cntry = em.createNativeQuery(script, MwCntry.class).getResultList();

        return cntry;
    }

    public MwCntry addNewCountry(CountryDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.CNTRY_SEQ);

        MwCntry cntry = new MwCntry();
        Instant currIns = Instant.now();
        cntry.setCntrySeq(seq);
        cntry.setCntryCd(String.format("%04d", seq));
        cntry.setCntryCmnt(dto.countryDescription);
        cntry.setCntryNm(dto.countryName);
        cntry.setCntryStsKey(200L);
        cntry.setCrntRecFlg(true);
        cntry.setCrtdBy(currUser);
        cntry.setCrtdDt(currIns);
        cntry.setDelFlg(false);
        cntry.setEffStartDt(currIns);
        cntry.setLastUpdBy(currUser);
        cntry.setLastUpdDt(currIns);
        return save(cntry);
    }

    @Transactional
    public MwCntry updateExistingCountry(CountryDto dto, String currUser) {
        MwCntry cntry = mwCntryRepository.findOneByCntrySeqAndCrntRecFlg(dto.countrySeq, true);
        Instant currIns = Instant.now();

        if (cntry == null)
            return null;

        cntry.lastUpdDt(currIns);
        cntry.lastUpdBy(currUser);
        cntry.setCntryNm(dto.countryName);
        cntry.setCntryCmnt(dto.countryDescription);

        return mwCntryRepository.save(cntry);


    }
}
