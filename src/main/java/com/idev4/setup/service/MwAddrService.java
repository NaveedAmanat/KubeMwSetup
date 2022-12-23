package com.idev4.setup.service;

import com.idev4.setup.domain.MwAddr;
import com.idev4.setup.domain.MwAddrRel;
import com.idev4.setup.dto.AddressCombinationDto;
import com.idev4.setup.dto.AddressDto;
import com.idev4.setup.repository.MwAddrRelRepository;
import com.idev4.setup.repository.MwAddrRepository;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing MwAddr.
 */
@Service
@Transactional
public class MwAddrService {

    private final Logger log = LoggerFactory.getLogger(MwAddrService.class);

    private final MwAddrRepository mwAddrRepository;

    private final MwAddrRelRepository mwAddrRelRepository;

    private final MwAddrRelService mwAddrRelService;

    private final EntityManager em;

    public MwAddrService(MwAddrRepository mwAddrRepository, MwAddrRelService mwAddrRelService, EntityManager em,
                         MwAddrRelRepository mwAddrRelRepository) {
        this.mwAddrRepository = mwAddrRepository;
        this.mwAddrRelService = mwAddrRelService;
        this.em = em;
        this.mwAddrRelRepository = mwAddrRelRepository;
    }

    /**
     * Save a mwAddr.
     *
     * @param mwAddr the entity to save
     * @return the persisted entity
     */
    public MwAddr save(MwAddr mwAddr) {
        log.debug("Request to save MwAddr : {}", mwAddr);
        return mwAddrRepository.save(mwAddr);
    }

    /**
     * Get all the mwAddrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwAddr> findAll(Pageable pageable) {
        log.debug("Request to get all MwAddrs");
        return mwAddrRepository.findAll(pageable);
    }

    /**
     * Get one mwAddr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwAddr findOne(Long id) {
        log.debug("Request to get MwAddr : {}", id);
        return mwAddrRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public MwAddr findOneByAddrSeq(Long id) {
        log.debug("Request to get MwAddr : {}", id);
        return mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwAddr by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwAddr : {}", id);
        mwAddrRepository.delete(id);
    }

    public void deleteByEntity(MwAddr mwAddr) {
        log.debug("Request to delete MwAddr : {}", mwAddr.getAddrSeq());
        mwAddrRepository.delete(mwAddr);
    }

    public List findAddressOptions() {
        Query q = em.createNativeQuery(Queries.addressCombs);

        List<Object[]> cityCombinations = q.getResultList();
        List<AddressCombinationDto> combs = new ArrayList<AddressCombinationDto>();

        for (Object[] comb : cityCombinations) {

            AddressCombinationDto dto = new AddressCombinationDto();
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
            combs.add(dto);
        }

        log.debug("Result set is: {}", q.getMaxResults());
        // return q.getResultList();
        return combs;
    }

    @Transactional
    public long saveAddress(AddressDto dto, String curUser, String entity) {
        MwAddrRel rel = null;
        long entitySeq = 0;
        if (entity.equals("Client")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.clientSeq, "Client", true);
            entitySeq = dto.clientSeq;
        } else if (entity.equals("Nominee")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.nomineeSeq, "Nominee", true);
            entitySeq = dto.nomineeSeq;
        } else if (entity.equals("CoBorrower")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.coBorrowerSeq, "CoBorrower", true);
            entitySeq = dto.coBorrowerSeq;
        } else if (entity.equals("Business")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.bizSeq, "Business", true);
            entitySeq = dto.bizSeq;
        } else if (entity.equals("ClientRel")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.clientRelSeq, "ClientRel", true);
            entitySeq = dto.clientRelSeq;
        } else if (entity.equals("SchoolAppraisal")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.schAprslSeq, "SchoolAppraisal", true);
            entitySeq = dto.schAprslSeq;
        } else {
            return 0;
        }

        long seq = SequenceFinder.findNextVal(Sequences.ADDR_SEQ);

        if (rel != null)
            return 0;

        MwAddr addr = new MwAddr();
        Instant curIns = Instant.now();
        addr.setAddrSeq(seq);
        addr.setCrntRecFlg(true);
        addr.setCrtdBy(curUser);
        addr.setCrtdDt(curIns);
        addr.setDelFlg(false);
        addr.setEffStartDt(curIns);
        addr.setHseNum(dto.houseNum);
        addr.setLastUpdBy(curUser);
        addr.setLastUpdDt(curIns);
        addr.setLatitude(dto.lat);
        addr.setLongitude(dto.lon);
        addr.setCitySeq(dto.city);
        addr.setCmntySeq(dto.community);
        addr.setOthDtl(dto.otherDetails);
        addr.setStrt(dto.sreet_area);
        addr.setVlg(dto.village);
        addr.setLastUpdBy(curUser);
        addr.setLastUpdDt(curIns);
        // addr.setAddrTypKey("PERMANENT");

        long addrSeq = mwAddrRepository.save(addr).getAddrSeq();
        seq = SequenceFinder.findNextVal(Sequences.ADDR_REL_SEQ);
        MwAddrRel addrRel = new MwAddrRel();
        addrRel.setAddrRelSeq(seq);
        addrRel.setAddrSeq(addrSeq);
        addrRel.setCrntRecFlg(true);
        addrRel.setCrtdBy(curUser);
        addrRel.setCrtdDt(curIns);
        addrRel.setDelFlg(false);
        addrRel.setEffStartDt(curIns);
        addrRel.setEntySeq(entitySeq);
        addrRel.setEntyType(entity);
        addrRel.setLastUpdBy(curUser);
        addrRel.setLastUpdDt(curIns);

        mwAddrRelService.save(addrRel).getAddrRelSeq();
        return addrSeq;
    }

    @Transactional
    public long updateAddress(AddressDto dto, String curUser) {
        MwAddr exAddr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(dto.addrSeq, true);
        Instant curIns = Instant.now();
        if (exAddr == null)
            return 0;

        exAddr.setCrntRecFlg(false);
        exAddr.setEffEndDt(curIns);
        exAddr.setLastUpdBy(curUser);
        exAddr.setLastUpdDt(curIns);
        mwAddrRepository.save(exAddr);

        MwAddr addr = new MwAddr();
        addr.setAddrSeq(dto.addrSeq);
        addr.setCrntRecFlg(true);
        addr.setCrtdBy(curUser);
        addr.setCrtdDt(curIns);
        addr.setDelFlg(false);
        addr.setEffStartDt(curIns);
        addr.setHseNum(dto.houseNum);
        addr.setLastUpdBy(curUser);
        addr.setLastUpdDt(curIns);
        addr.setLatitude(dto.lat);
        addr.setLongitude(dto.lon);
        addr.setCitySeq(dto.city);
        addr.setCmntySeq(dto.community);
        addr.setOthDtl(dto.otherDetails);
        addr.setStrt(dto.sreet_area);
        addr.setVlg(dto.village);
        addr.setLastUpdBy(curUser);
        addr.setLastUpdDt(curIns);
        // addr.setAddrTypKey("PERMANENT");

        mwAddrRepository.save(addr).getAddrSeq();
        return dto.addrSeq;
    }
}
