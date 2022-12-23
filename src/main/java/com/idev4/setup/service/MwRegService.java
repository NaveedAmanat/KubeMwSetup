package com.idev4.setup.service;

import com.idev4.setup.domain.MwAddr;
import com.idev4.setup.domain.MwAddrRel;
import com.idev4.setup.domain.MwArea;
import com.idev4.setup.domain.MwReg;
import com.idev4.setup.dto.RegionDto;
import com.idev4.setup.repository.MwAddrRelRepository;
import com.idev4.setup.repository.MwAddrRepository;
import com.idev4.setup.repository.MwAreaRepository;
import com.idev4.setup.repository.MwRegRepository;
import com.idev4.setup.web.rest.util.Queries;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing MwReg.
 */
@Service
@Transactional
public class MwRegService {

    private final Logger log = LoggerFactory.getLogger(MwRegService.class);

    private final MwRegRepository mwRegRepository;

    private final MwAreaRepository mwAreaRepository;

    private final MwAddrRepository mwAddrRepository;

    private final MwAddrRelRepository mwAddrRelRepository;

    private final EntityManager em;

    public MwRegService(MwRegRepository mwRegRepository, EntityManager em, MwAreaRepository mwAreaRepository,
                        MwAddrRepository mwAddrRepository, MwAddrRelRepository mwAddrRelRepository) {
        this.mwRegRepository = mwRegRepository;
        this.mwAreaRepository = mwAreaRepository;
        this.em = em;
        this.mwAddrRepository = mwAddrRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
    }

    /**
     * Save a mwReg.
     *
     * @param mwReg the entity to save
     * @return the persisted entity
     */
    public MwReg save(MwReg mwReg) {
        log.debug("Request to save MwReg : {}", mwReg);
        return mwRegRepository.save(mwReg);
    }

    /**
     * Get all the mwRegs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwReg> findAll(Pageable pageable) {
        log.debug("Request to get all MwRegs");
        return mwRegRepository.findAll(pageable);
    }

    /**
     * Get one mwReg by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RegionDto findOne(Long id) {
        log.debug("Request to get MwReg : {}", id);
        MwReg reg = mwRegRepository.findOneByRegSeqAndCrntRecFlg(id, true);
        MwAddrRel addRel = null;
        MwAddr addr = null;
        if (reg != null)
            addRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(reg.getRegSeq(), "Region", true);
        if (addRel != null)
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addRel.getAddrSeq(), true);

        RegionDto dto = new RegionDto();
        dto.regionSeq = reg.getRegSeq();
        dto.regionCode = reg.getRegCd();
        dto.regionDescription = reg.getRegDscr();
        dto.regionName = reg.getRegNm();
        dto.regionStatus = reg.getRegStsKey();

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
    public List<MwReg> findAllHistory(Long id) {
        log.debug("Request to get MwReg : {}", id);
        return mwRegRepository.findAllByRegSeq(id);
    }

    /**
     * Delete the mwReg by id.
     *
     * @param id the id of the entity
     */
    public boolean delete(Long id) {
        log.debug("Request to delete MwReg : {}", id);
        List<MwArea> areas = mwAreaRepository.findAllByRegSeqAndCrntRecFlg(id, true);
        if (areas != null && areas.size() > 0)
            return false;
        MwReg reg = mwRegRepository.findOneByRegSeqAndCrntRecFlg(id, true);
        reg.setCrntRecFlg(false);
        reg.setDelFlg(true);
        reg.setLastUpdDt(Instant.now());
        mwRegRepository.save(reg);
        return true;
    }

    @Transactional(readOnly = true)
    public List<MwReg> findAllByCurrentRecord() {
        log.debug("Request to get all MwRegs");
        return mwRegRepository.findAllByCrntRecFlgOrderByRegSeqDesc(true);
    }

    public MwReg addNewRegion(RegionDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.REG_SEQ);
        MwReg reg = new MwReg();
        Instant currIns = Instant.now();
        reg.setRegSeq(seq);
        reg.setCrntRecFlg(true);
        reg.setCrtdBy(currUser);
        reg.setCrtdDt(currIns);
        reg.setDelFlg(false);
        reg.setEffStartDt(currIns);
        reg.setLastUpdBy(currUser);
        reg.setLastUpdDt(currIns);
        reg.setRegDscr(dto.regionDescription);
        reg.setRegNm(dto.regionName);
        reg.setRegCd(String.format("%04d", seq));
        reg.setRegStsKey(dto.regionStatus);
        // reg.setRegTyp(dto.regionType);

        return save(reg);
    }

    @Transactional
    public MwReg updateExitingRegion(RegionDto dto, String currUser) {
        MwReg reg = mwRegRepository.findOneByRegSeqAndCrntRecFlg(dto.regionSeq, true);
        Instant currIns = Instant.now();
        if (reg == null) {
            return null;
        }

        reg.setLastUpdDt(currIns);
        reg.setLastUpdBy(currUser);
        reg.setRegNm(dto.regionName);
        reg.setRegDscr(dto.regionDescription);
        reg.setRegStsKey(dto.regionStatus);

        return mwRegRepository.save(reg);

    }

    public MwReg updateStatus(long regSeq, String currUser) {
        Instant currIns = Instant.now();

        MwReg reg = mwRegRepository.findOneByRegSeqAndCrntRecFlg(regSeq, true);
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
        if (reg.getRegStsKey() == actSeq)
            reg.setRegStsKey(inactSeq);
        else
            reg.setRegStsKey(actSeq);
        reg.setLastUpdBy(currUser);
        reg.setLastUpdDt(currIns);

        return save(reg);
    }

}
