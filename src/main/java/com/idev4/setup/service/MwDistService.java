package com.idev4.setup.service;

import com.idev4.setup.domain.MwDist;
import com.idev4.setup.domain.MwThsl;
import com.idev4.setup.dto.DistrictDto;
import com.idev4.setup.repository.MwDistRepository;
import com.idev4.setup.repository.MwThslRepository;
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
 * Service Implementation for managing MwDist.
 */
@Service
@Transactional
public class MwDistService {

    private final Logger log = LoggerFactory.getLogger(MwDistService.class);

    private final MwDistRepository mwDistRepository;

    private final MwThslRepository mwThslRepository;

    private final EntityManager em;

    public MwDistService(MwDistRepository mwDistRepository, MwThslRepository mwThslRepository, EntityManager em) {
        this.mwDistRepository = mwDistRepository;
        this.mwThslRepository = mwThslRepository;
        this.em = em;
    }

    /**
     * Save a mwDist.
     *
     * @param mwDist the entity to save
     * @return the persisted entity
     */
    public MwDist save(MwDist mwDist) {
        log.debug("Request to save MwDist : {}", mwDist);
        return mwDistRepository.save(mwDist);
    }

    /**
     * Get all the mwDists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwDist> findAll(Pageable pageable) {
        log.debug("Request to get all MwDists");
        return mwDistRepository.findAll(pageable);
    }

    /**
     * Get one mwDist by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwDist findOne(Long id) {
        log.debug("Request to get MwDist : {}", id);
        return mwDistRepository.findOneByDistSeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwDist> findAllHistory(Long id) {
        log.debug("Request to get MwDist : {}", id);
        return mwDistRepository.findAllByDistSeq(id);
    }

    /**
     * Delete the mwDist by id.
     *
     * @param id the id of the entity
     */
    public boolean delete(Long id) {
        log.debug("Request to delete MwDist : {}", id);
        List<MwThsl> thsls = mwThslRepository.findAllByDistSeqAndCrntRecFlg(id, true);
        if (thsls != null && thsls.size() > 0)
            return false;
        MwDist dist = mwDistRepository.findOneByDistSeqAndCrntRecFlg(id, true);
        dist.setCrntRecFlg(false);
        dist.setDelFlg(true);
        dist.lastUpdDt(Instant.now());
        mwDistRepository.save(dist);
        return true;
    }

    @Transactional(readOnly = true)
    public List<MwDist> findAllByCurrentRecord() {
        log.debug("Request to get all MwDists");
        return mwDistRepository.findAllByCrntRecFlgOrderByDistNm(true);
    }

    public MwDist addNewDistrict(DistrictDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.DIST_SEQ);
        MwDist dist = new MwDist();
        Instant currIns = Instant.now();
        dist.setDistSeq(seq);
        dist.setCrntRecFlg(true);
        dist.setCrtdBy(currUser);
        dist.setCrtdDt(currIns);
        dist.setDelFlg(false);
        dist.setDistCd(String.format("%04d", seq));
        dist.setDistCmnt(dto.districtDescription);
        dist.setDistNm(dto.districtName);
        dist.setEffStartDt(currIns);
        dist.setLastUpdBy(currUser);
        dist.setLastUpdDt(currIns);
        dist.setStSeq(dto.provinceSeq);
        dist.setDistStsKey(dto.districtStatus);

        return mwDistRepository.save(dist);

    }

    @Transactional
    public MwDist updateExistingDistrict(DistrictDto dto, String currUser) {
        MwDist dist = mwDistRepository.findOneByDistSeqAndCrntRecFlg(dto.districtSeq, true);
        Instant currIns = Instant.now();
        if (dist == null) {
            return null;
        }

        dist.setLastUpdDt(currIns);
        dist.setLastUpdBy(currUser);
        dist.setDistNm(dto.districtName);
        dist.setDistCmnt(dto.districtDescription);

        return mwDistRepository.save(dist);

    }

    //Edited by Areeba
    public List<MwDist> getDistrictsByStateSeq(long stateSeq, String filter) {
        String script = " select * from mw_dist dist \n" +
            " where dist.crnt_rec_flg = 1 and dist.st_seq = :state ";

        if (filter != null && filter.length() > 0) {
            String search = (" and ((lower(dist.dist_cd) LIKE '%?%') " +
                " or (lower(dist.dist_nm) LIKE '%?%') " +
                " or (lower(dist.dist_cmnt) LIKE '%?%')) ")
                .replace("?", filter.toLowerCase());
            script += search;
        }

        List<MwDist> dist = em.createNativeQuery(script, MwDist.class).setParameter("state", stateSeq).getResultList();

        return dist;
    }
}
