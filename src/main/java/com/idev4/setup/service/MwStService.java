package com.idev4.setup.service;

import com.idev4.setup.domain.MwDist;
import com.idev4.setup.domain.MwSt;
import com.idev4.setup.dto.ProvinceDto;
import com.idev4.setup.repository.MwDistRepository;
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
 * Service Implementation for managing MwSt.
 */
@Service
@Transactional
public class MwStService {

    private final Logger log = LoggerFactory.getLogger(MwStService.class);

    private final MwStRepository mwStRepository;

    private final MwDistRepository mwDistRepository;

    private final EntityManager em;

    public MwStService(MwStRepository mwStRepository, MwDistRepository mwDistRepository, EntityManager em) {
        this.mwStRepository = mwStRepository;
        this.mwDistRepository = mwDistRepository;
        this.em = em;
    }

    /**
     * Save a mwSt.
     *
     * @param mwSt the entity to save
     * @return the persisted entity
     */
    public MwSt save(MwSt mwSt) {
        log.debug("Request to save MwSt : {}", mwSt);
        return mwStRepository.save(mwSt);
    }

    /**
     * Get all the mwSts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwSt> findAll(Pageable pageable) {
        log.debug("Request to get all MwSts");
        return mwStRepository.findAll(pageable);
    }

    /**
     * Get one mwSt by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwSt findOne(Long id) {
        log.debug("Request to get MwSt : {}", id);
        return mwStRepository.findOneByStSeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwSt> findAllHistory(Long id) {
        log.debug("Request to get MwSt : {}", id);
        return mwStRepository.findAllByStSeq(id);
    }

    /**
     * Delete the mwSt by id.
     *
     * @param id the id of the entity
     */
    public boolean delete(Long id) {
        log.debug("Request to delete MwSt : {}", id);
        List<MwDist> dists = mwDistRepository.findAllByStSeqAndCrntRecFlg(id, true);
        if (dists != null && dists.size() > 0)
            return false;
        MwSt st = mwStRepository.findOneByStSeqAndCrntRecFlg(id, true);
        st.setCrntRecFlg(false);
        st.setDelFlg(true);
        st.setLastUpdDt(Instant.now());
        mwStRepository.save(st);
        return true;
    }

    @Transactional(readOnly = true)
    public List<MwSt> findAllByCurrentRecord() {
        log.debug("Request to get all MwSts");
        return mwStRepository.findAllByCrntRecFlg(true);
    }

    public MwSt addNewState(ProvinceDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.ST_SEQ);
        MwSt state = new MwSt();
        Instant currIns = Instant.now();

        state.setStSeq(seq);
        state.setCntrySeq(dto.countrySeq);
        state.setCrntRecFlg(true);
        state.setCrtdBy(currUser);
        state.setCrtdDt(currIns);
        state.setDelFlg(false);
        state.setEffStartDt(currIns);
        state.setLastUpdBy(currUser);
        state.setLastUpdDt(currIns);
        state.setStCd(String.format("%04d", seq));
        state.setStCmnt(dto.provDescription);
        state.setStNm(dto.provName);
        state.setStStsKey(200L);

        return mwStRepository.save(state);
    }

    @Transactional
    public MwSt updateExistingState(ProvinceDto dto, String currUser) {

        MwSt state = mwStRepository.findOneByStSeqAndCrntRecFlg(dto.provSeq, true);
        Instant currIns = Instant.now();
        if (state == null)
            return null;

        state.lastUpdDt(currIns);
        state.lastUpdBy(currUser);
        state.setStNm(dto.provName);
        state.setStCmnt(dto.provDescription);

        return mwStRepository.save(state);

    }

    //Edited by Areeba
    public List<MwSt> getStatesByCountrySeq(long countrySeq, String filter) {
        String script = " select * from mw_st st \n" +
            " where st.crnt_rec_flg = 1 and st.cntry_seq = :cntry ";

        if (filter != null && filter.length() > 0) {
            String search = (" and ((lower(st.st_nm) LIKE '%?%') " +
                " or (lower(st.st_dscr) LIKE '%?%')) ")
                .replace("?", filter.toLowerCase());
            script += search;
        }

        List<MwSt> state = em.createNativeQuery(script, MwSt.class).setParameter("cntry", countrySeq).getResultList();

        return state;
    }
}
