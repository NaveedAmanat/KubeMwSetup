package com.idev4.setup.service;

import com.idev4.setup.domain.MwAddr;
import com.idev4.setup.domain.MwAddrRel;
import com.idev4.setup.domain.MwArea;
import com.idev4.setup.domain.MwBrnch;
import com.idev4.setup.dto.AreaDto;
import com.idev4.setup.repository.MwAddrRelRepository;
import com.idev4.setup.repository.MwAddrRepository;
import com.idev4.setup.repository.MwAreaRepository;
import com.idev4.setup.repository.MwBrnchRepository;
import com.idev4.setup.web.rest.util.Queries;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing MwArea.
 */
@Service
@Transactional
public class MwAreaService {

    private final Logger log = LoggerFactory.getLogger(MwAreaService.class);
    private final MwAreaRepository mwAreaRepository;
    private final MwBrnchRepository mwBrnchRepository;
    private final MwAddrRepository mwAddrRepository;
    private final MwAddrRelRepository mwAddrRelRepository;
    private final EntityManager em;
    @Autowired
    private EntityManager entityManager;

    public MwAreaService(MwAreaRepository mwAreaRepository, EntityManager em, MwBrnchRepository mwBrnchRepository,
                         MwAddrRepository mwAddrRepository, MwAddrRelRepository mwAddrRelRepository) {
        this.mwAreaRepository = mwAreaRepository;
        this.mwBrnchRepository = mwBrnchRepository;
        this.em = em;
        this.mwAddrRepository = mwAddrRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
    }

    /**
     * Save a mwArea.
     *
     * @param mwArea the entity to save
     * @return the persisted entity
     */
    public MwArea save(MwArea mwArea) {
        log.debug("Request to save MwArea : {}", mwArea);
        return mwAreaRepository.save(mwArea);
    }

    /**
     * Get all the mwAreas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwArea> findAll(Pageable pageable) {
        log.debug("Request to get all MwAreas");
        return mwAreaRepository.findAll(pageable);
    }

    /**
     * Get one mwArea by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AreaDto findOne(Long id) {
        log.debug("Request to get MwArea : {}", id);
        MwArea area = mwAreaRepository.findOneByAreaSeqAndCrntRecFlg(id, true);
        AreaDto dto = new AreaDto();
        MwAddrRel addRel = null;
        MwAddr addr = null;
        if (area != null)
            addRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(area.getAreaSeq(), "Area", true);
        if (addRel != null)
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addRel.getAddrSeq(), true);

        dto.areaId = area.getAreaCd();
        dto.areaDescription = area.getAreaDscr();
        dto.areaName = area.getAreaNm();
        dto.areaSeq = area.getAreaSeq();
        dto.areaStatus = area.getAreaStsKey();
        dto.regionSeq = area.getRegSeq();

        if (addr != null) {
            Query q = em.createNativeQuery(Queries.entityAddress + addr.getCitySeq());

            List<Object[]> cityCombinations = q.getResultList();

            for (Object[] comb : cityCombinations) {
                dto.country = Long.valueOf(comb[0].toString());
                dto.countryName = comb[1].toString();
                dto.province = Long.valueOf(comb[2].toString());
                dto.provinceName = comb[3].toString();
                dto.district = Long.valueOf(comb[4].toString());
                dto.districtName = comb[5].toString();
                dto.tehsil = Long.valueOf(comb[6].toString());
                dto.tehsilName = comb[7].toString();
                dto.uc = Long.valueOf(comb[8].toString());
                dto.ucName = comb[9].toString() + " " + comb[10].toString();
                dto.city = Long.valueOf(comb[11].toString());
                dto.cityName = comb[12].toString();
            }

            dto.addrSeq = addr.getAddrSeq();
            dto.houseNum = addr.getHseNum();
            dto.sreet_area = addr.getStrt();
            dto.community = addr.getCmntySeq();
            dto.village = addr.getVlg();
            dto.otherDetails = addr.getOthDtl();
        }

        return dto;
    }

    @Transactional(readOnly = true)
    public List<MwArea> findAllHistory(Long id) {
        log.debug("Request to get MwArea : {}", id);
        return mwAreaRepository.findAllByAreaSeq(id);
    }

    /**
     * Delete the mwArea by id.
     *
     * @param id the id of the entity
     */
    public boolean delete(Long id) {
        log.debug("Request to delete MwArea : {}", id);
        List<MwBrnch> branches = mwBrnchRepository.findAllByAreaSeqAndCrntRecFlg(id, true);
        if (branches != null && branches.size() > 0)
            return false;
        MwArea area = mwAreaRepository.findOneByAreaSeqAndCrntRecFlg(id, true);
        area.setCrntRecFlg(false);
        area.setDelFlg(true);
        area.setLastUpdDt(Instant.now());
        mwAreaRepository.save(area);
        return true;
    }

    @Transactional(readOnly = true)
    public List<MwArea> findAllByCurrentRecord() {
        log.debug("Request to get all MwAreas");
        return mwAreaRepository.findAllByCrntRecFlgOrderByAreaNm(true);
    }

    public MwArea addNewArea(AreaDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.AREA_SEQ);
        MwArea area = new MwArea();
        Instant currIns = Instant.now();
        area.setAreaSeq(seq);
        area.setAreaCd(String.format("%04d", seq));
        area.setAreaDscr(dto.areaDescription);
        area.setAreaNm(dto.areaName);
        area.setCrntRecFlg(true);
        area.setCrtdBy(currUser);
        area.setCrtdDt(currIns);
        area.setDelFlg(false);
        area.setEffStartDt(currIns);
        area.setLastUpdBy(currUser);
        area.setLastUpdDt(currIns);
        area.setRegSeq(dto.regionSeq);
        area.setAreaStsKey(dto.areaStatus);
        return mwAreaRepository.save(area);
    }

    @Transactional
    public MwArea updateExistingNewArea(AreaDto dto, String currUser) {

        MwArea area = mwAreaRepository.findOneByAreaSeqAndCrntRecFlg(dto.areaSeq, true);
        Instant currIns = Instant.now();
        if (area == null)
            return null;

        area.setLastUpdDt(currIns);
        area.setLastUpdBy(currUser);
        area.setRegSeq(dto.regionSeq);
        area.setAreaNm(dto.areaName);
        area.setAreaDscr(dto.areaDescription);
        area.setAreaStsKey(dto.areaStatus);

        return mwAreaRepository.save(area);

    }

    public List<MwArea> getAreasByOrganization(long regSeq) {
        return mwAreaRepository.findAllByRegSeqAndCrntRecFlgOrderByAreaSeqDesc(regSeq, true);
    }

    public MwArea updateStatus(long regSeq, String currUser) {
        Instant currIns = Instant.now();
        MwArea reg = mwAreaRepository.findOneByAreaSeqAndCrntRecFlg(regSeq, true);
        String query = Queries.statusSeq;
        Query q = em.createNativeQuery(query);
        List<Object[]> statuses = q.getResultList();
        long actSeq = 0;
        long inactSeq = 0;
        for (Object[] st : statuses) {
            if (st[1].toString().toLowerCase().equals("active")) {
                actSeq = Long.valueOf(st[2].toString());
            } else {
                inactSeq = Long.valueOf(st[2].toString());
            }
        }
        if (reg.getAreaStsKey() == actSeq)
            reg.setAreaStsKey(inactSeq);
        else
            reg.setAreaStsKey(actSeq);
        reg.setLastUpdBy(currUser);
        reg.setLastUpdDt(currIns);
        return save(reg);
    }

    @Transactional(readOnly = true)
    public List<Object[]> findAllMwAreaNames() {
        log.debug("Request to get all MwArea");
        // return mwCityRepository.findAllByCrntRecFlg( true );

        String areaScript = "SELECT a.area_seq, a.area_nm FROM mw_area a where a.crnt_rec_flg = 1";

        List<Object[]> allAreaList = entityManager.createNativeQuery(areaScript + "\r\norder by 1 asc")
            .getResultList();

//        Map<String, Object> response = new HashMap<>();
//        response.put("area", allAreaList);

        return allAreaList;
    }
}
