package com.idev4.setup.service;

import com.idev4.setup.domain.MwThsl;
import com.idev4.setup.domain.MwUc;
import com.idev4.setup.dto.TehsilDto;
import com.idev4.setup.repository.MwThslRepository;
import com.idev4.setup.repository.MwUcRepository;
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
 * Service Implementation for managing MwThsl.
 */
@Service
@Transactional
public class MwThslService {

    private final Logger log = LoggerFactory.getLogger(MwThslService.class);

    private final MwThslRepository mwThslRepository;

    private final MwUcRepository mwUcRepository;

    private final EntityManager em;

    public MwThslService(MwThslRepository mwThslRepository, MwUcRepository mwUcRepository, EntityManager em) {
        this.mwThslRepository = mwThslRepository;
        this.mwUcRepository = mwUcRepository;
        this.em = em;
    }

    /**
     * Save a mwThsl.
     *
     * @param mwThsl the entity to save
     * @return the persisted entity
     */
    public MwThsl save(MwThsl mwThsl) {
        log.debug("Request to save MwThsl : {}", mwThsl);
        return mwThslRepository.save(mwThsl);
    }

    /**
     * Get all the mwThsls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwThsl> findAll(Pageable pageable) {
        log.debug("Request to get all MwThsls");
        return mwThslRepository.findAll(pageable);
    }

    /**
     * Get one mwThsl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwThsl findOne(Long id) {
        log.debug("Request to get MwThsl : {}", id);
        return mwThslRepository.findOneByThslSeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwThsl> findAllHistory(Long id) {
        log.debug("Request to get MwThsl : {}", id);
        return mwThslRepository.findAllByThslSeq(id);
    }

    /**
     * Delete the mwThsl by id.
     *
     * @param id the id of the entity
     */
    public boolean delete(Long id) {
        log.debug("Request to delete MwThsl : {}", id);
        List<MwUc> ucs = mwUcRepository.findAllByThslSeqAndCrntRecFlg(id, true);
        if (ucs != null && ucs.size() > 0)
            return false;
        MwThsl thsl = mwThslRepository.findOneByThslSeqAndCrntRecFlg(id, true);
        thsl.setCrntRecFlg(false);
        thsl.setDelFlg(true);
        thsl.setLastUpdDt(Instant.now());
        mwThslRepository.save(thsl);
        return true;
    }

    @Transactional(readOnly = true)
    public List<MwThsl> findAllByCurrentRecord() {
        log.debug("Request to get all MwThsls");
        return mwThslRepository.findAllByCrntRecFlgOrderByThslNm(true);
    }

    public MwThsl addNewTehsil(TehsilDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.THSL_SEQ);

        MwThsl thsl = new MwThsl();
        Instant currIns = Instant.now();
        thsl.setThslSeq(seq);
        thsl.setCrntRecFlg(true);
        thsl.setCrtdBy(currUser);
        thsl.setCrtdDt(currIns);
        thsl.setDelFlg(false);
        thsl.setDistSeq(dto.districtSeq);
        thsl.setEffStartDt(currIns);
        thsl.setLastUpdBy(currUser);
        thsl.setLastUpdDt(currIns);
        thsl.setThslCd(String.format("%04d", seq));
        thsl.setThslCmnt(dto.thslDescription);
        thsl.setThslNm(dto.thslName);
        thsl.setThslStsKey(dto.thslStatus);

        return mwThslRepository.save(thsl);
    }

    @Transactional
    public MwThsl updateExistingTehsil(TehsilDto dto, String currUser) {

        MwThsl thsl = mwThslRepository.findOneByThslSeqAndCrntRecFlg(dto.thslSeq, true);
        Instant currIns = Instant.now();
        if (thsl == null) {
            return null;
        }

        thsl.setLastUpdDt(currIns);
        thsl.setLastUpdBy(currUser);
        thsl.setThslNm(dto.thslName);
        thsl.setThslCmnt(dto.thslDescription);

        return mwThslRepository.save(thsl);


    }

    //Edited by Areeba
    public List<MwThsl> getTehsilsByDistrict(long distSeq, String filter) {
        String script = " select * from mw_thsl thsl \n" +
            " where thsl.crnt_rec_flg = 1 and thsl.dist_seq = :dist ";

        if (filter != null && filter.length() > 0) {
            String search = (" and ((lower(thsl.thsl_cd) LIKE '%?%') " +
                " or (lower(thsl.thsl_nm) LIKE '%?%') " +
                " or (lower(thsl.thsl_cmnt) LIKE '%?%')) ")
                .replace("?", filter.toLowerCase());
            script += search;
        }

        List<MwThsl> thsl = em.createNativeQuery(script, MwThsl.class).setParameter("dist", distSeq).getResultList();

        return thsl;
    }
}
