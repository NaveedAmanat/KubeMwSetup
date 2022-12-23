package com.idev4.setup.service;

import com.idev4.setup.domain.*;
import com.idev4.setup.dto.AddressCombinationDto;
import com.idev4.setup.dto.UcDto;
import com.idev4.setup.repository.*;
import com.idev4.setup.web.rest.util.Queries;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing MwUc.
 */
@Service
@Transactional
public class MwUcService {

    private final Logger log = LoggerFactory.getLogger(MwUcService.class);

    private final MwUcRepository mwUcRepository;

    private final MwCityUcRelRepository mwCityUcRelRepository;
    private final MwBrnchLocationRelRepository mwBrnchLocationRelRepository;
    private final MwPortLocationRelRepository mwPortLocationRelRepository;
    private final MwAddrRepository mwAddrRepository;

    private final EntityManager em;

    public MwUcService(MwUcRepository mwUcRepository, MwAddrRepository mwAddrRepository,
                       MwBrnchLocationRelRepository mwBrnchLocationRelRepository,
                       MwCityUcRelRepository mwCityUcRelRepository,
                       MwPortLocationRelRepository mwPortLocationRelRepository, EntityManager em) {
        this.mwUcRepository = mwUcRepository;
        this.mwAddrRepository = mwAddrRepository;
        this.mwBrnchLocationRelRepository = mwBrnchLocationRelRepository;
        this.mwCityUcRelRepository = mwCityUcRelRepository;
        this.mwPortLocationRelRepository = mwPortLocationRelRepository;
        this.em = em;
    }

    /**
     * Save a mwUc.
     *
     * @param mwUc the entity to save
     * @return the persisted entity
     */
    public MwUc save(MwUc mwUc) {
        log.debug("Request to save MwUc : {}", mwUc);
        return mwUcRepository.save(mwUc);
    }

    /**
     * Get all the mwUcs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwUc> findAll(Pageable pageable) {
        log.debug("Request to get all MwUcs");
        return mwUcRepository.findAll(pageable);
    }

    /**
     * Get one mwUc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwUc findOne(Long id) {
        log.debug("Request to get MwUc : {}", id);
        return mwUcRepository.findOneByUcSeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwUc> findAllHistory(Long id) {
        log.debug("Request to get MwUc : {}", id);
        return mwUcRepository.findAllByUcSeq(id);
    }

    /**
     * Delete the mwUc by id.
     *
     * @param id the id of the entity
     */
    // Edited by Areeba - 12-2-2022 - Inactive all corresponding relations on uc inactive
    public void delete(Long id, String currUser) {
        log.debug("Request to delete MwUc : {}", id);
        MwUc uc = mwUcRepository.findOneByUcSeqAndCrntRecFlg(id, true);

        List<MwCityUcRel> mwCityUcRels = mwCityUcRelRepository.findAllByUcSeqAndCrntRecFlg(uc.getUcSeq(), true);
        if (mwCityUcRels != null) {
            mwCityUcRels.forEach(mwCityUcRel -> {
                List<MwBrnchLocationRel> mwBrnchLocationRels = mwBrnchLocationRelRepository.findAllByCitySeqAndCrntRecFlg(mwCityUcRel.getCityUcRelSeq(), true);
                if (mwBrnchLocationRels != null) {
                    mwBrnchLocationRels.forEach(mwBrnchLocationRel -> {
                        mwBrnchLocationRel.setCrntRecFlg(false);
                        mwBrnchLocationRel.setDelFlg(true);
                        mwBrnchLocationRel.setLastUpdDt(Instant.now());
                        mwBrnchLocationRel.setLastUpdBy(currUser);

                        mwBrnchLocationRelRepository.save(mwBrnchLocationRel);
                    });
                }

                List<MwPortLocationRel> mwPortLocationRels = mwPortLocationRelRepository.findAllByCitySeqAndCrntRecFlg(mwCityUcRel.getCityUcRelSeq(), true);
                if (mwPortLocationRels != null) {
                    mwPortLocationRels.forEach(mwPortLocationRel -> {
                        mwPortLocationRel.setCrntRecFlg(false);
                        mwPortLocationRel.setDelFlg(true);
                        mwPortLocationRel.setLastUpdDt(Instant.now());
                        mwPortLocationRel.setLastUpdBy(currUser);

                        mwPortLocationRelRepository.save(mwPortLocationRel);
                    });
                }

                List<MwAddr> mwAddrs = mwAddrRepository.findAllBrnchAddrForCityUcRel(mwCityUcRel.getCityUcRelSeq());
                if (mwAddrs != null) {
                    mwAddrs.forEach(mwAddr -> {
                        mwAddr.setCitySeq(-1l);
                        mwAddr.setLastUpdBy(currUser);
                        mwAddr.setLastUpdDt(Instant.now());

                        mwAddrRepository.save(mwAddr);
                    });
                }

                mwCityUcRel.setCrntRecFlg(false);
                mwCityUcRel.setDelFlg(true);
                mwCityUcRel.setLastUpdDt(Instant.now());
                mwCityUcRel.setLastUpdBy(currUser);

                mwCityUcRelRepository.save(mwCityUcRel);
            });
        }

        uc.setCrntRecFlg(false);
        uc.setDelFlg(true);
        uc.setLastUpdDt(Instant.now());
        // Added by Areeba - 27-05-2022
        uc.setLastUpdBy(currUser);
        mwUcRepository.save(uc);
    }

    @Transactional(readOnly = true)
    public List<MwUc> findAllByCurrentRecord() {
        log.debug("Request to get all MwUcs");
        return mwUcRepository.findAllByCrntRecFlgOrderByUcNm(true);
    }

    //Edited by Areeba - 26-04-2022
    @Transactional(readOnly = true)
    public List<AddressCombinationDto> findAllByCurrentRecordRecords(Long citySeq, String filter) {
        log.debug("Request to get all MwUcs");
        //Query q = em.createNativeQuery( Queries.UCCombo );
        String search = "";

        if (filter != null && filter.length() > 0) {
            search = (" and ( (lower(st_nm) LIKE '%?%') " +
                " or (lower(dist_nm) LIKE '%?%') " +
                " or (lower(thsl_nm) LIKE '%?%') " +
                " or (lower(uc_nm) LIKE '%?%') )")
                .replace("?", filter.toLowerCase());
        }
        String query = Queries.UCCombo2.replace("?", search);
        Query q = em.createNativeQuery(query).setParameter("city", citySeq);

        List<Object[]> cityCombinations = q.getResultList();
        List<AddressCombinationDto> combs = new ArrayList<AddressCombinationDto>();

        for (Object[] comb : cityCombinations) {

            AddressCombinationDto dto = new AddressCombinationDto();
            dto.ucSeq = Long.valueOf(comb[0].toString());
            dto.provinceName = comb[1].toString();
            dto.districtName = comb[2].toString();
            dto.tehsilName = comb[3].toString();
            dto.ucName = comb[4].toString();
            dto.ucComments = comb[5].toString();
            combs.add(dto);
        }
        return combs;
    }

    public MwUc addNewUc(UcDto dto, String currUser) {

        long seq = SequenceFinder.findNextVal(Sequences.UC_SEQ);
        MwUc uc = new MwUc();
        Instant currIns = Instant.now();
        uc.setUcSeq(seq);
        uc.setCrntRecFlg(true);
        uc.setCrtdBy(currUser);
        uc.setCrtdDt(currIns);
        uc.setDelFlg(false);
        uc.setEffStartDt(currIns);
        uc.setLastUpdBy(currUser);
        uc.setLastUpdDt(currIns);
        uc.setThslSeq(dto.thslSeq);
        uc.setUcCd(String.format("%04d", seq));
        uc.setUcCmnt(dto.ucDescription);
        uc.setUcNm(dto.ucName);
        uc.setUcStsKey(dto.statusKey);
        return mwUcRepository.save(uc);
    }

    @Transactional
    public MwUc UpdateExistingUc(UcDto dto, String currUser) {
        MwUc uc = mwUcRepository.findOneByUcSeqAndCrntRecFlg(dto.ucSeq, true);
        Instant currIns = Instant.now();
        if (uc == null) {
            return null;
        }

        uc.setLastUpdDt(currIns);
        uc.setLastUpdBy(currUser);
        uc.setUcNm(dto.ucName);
        uc.setUcCmnt(dto.ucDescription);
        uc.setUcStsKey(dto.statusKey);
        return mwUcRepository.save(uc);


    }

    //Edited by Areeba
    public List<MwUc> getAllUcByThslSeq(long thslSeq, String filter) {
        String script = " select * from mw_uc uc \n" +
            " where uc.crnt_rec_flg = 1 and uc.thsl_seq = :thsl ";

        if (filter != null && filter.length() > 0) {
            String search = (" and ((lower(uc.uc_cd) LIKE '%?%') " +
                " or (lower(uc.uc_nm) LIKE '%?%') " +
                " or (lower(uc.uc_commnets) LIKE '%?%')) ")
                .replace("?", filter.toLowerCase());
            script += search;
        }

        List<MwUc> uc = em.createNativeQuery(script, MwUc.class).setParameter("thsl", thslSeq).getResultList();

        return uc;
    }

    //Added by Areeba - Branch Setup
    public Map<String, Object> makeDataSet(List<UcDto> dto) {
        List<UcDto> listsRecord = new ArrayList<>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        int recCount = 0;
        List<MwUc> ucList = new ArrayList();

        for (int i = 0; i < dto.size(); i++) {
            UcDto uc = dto.get(i);

            MwUc mwUc = new MwUc();

            long seq = SequenceFinder.findNextVal(Sequences.UC_SEQ);
            Instant currIns = Instant.now();

            mwUc.setUcSeq(seq);
            mwUc.setCrntRecFlg(true);
            mwUc.setCrtdBy(currUser);
            mwUc.setCrtdDt(currIns);
            mwUc.setDelFlg(false);
            mwUc.setEffStartDt(currIns);
            mwUc.setLastUpdBy(currUser);
            mwUc.setLastUpdDt(currIns);
            mwUc.setThslSeq(uc.thslSeq);
            mwUc.setUcCd(String.format("%04d", seq));
            mwUc.setUcCmnt(uc.ucDescription.toUpperCase());
            mwUc.setUcNm(uc.ucName.toUpperCase());
            mwUc.setUcStsKey(200L);

            mwUc = mwUcRepository.save(mwUc);
            ucList.add(mwUc);
            recCount++;
        }

        Map<String, Object> response = new HashMap<>();
        String success = "File Uploaded Successfully with Records: " + recCount + "/" + dto.size();
        response.put("success", success);
        response.put("ucs", ucList);
        return response;
    }
    //Ended by Areeba
}
