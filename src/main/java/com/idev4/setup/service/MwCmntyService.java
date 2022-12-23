package com.idev4.setup.service;

import com.idev4.setup.domain.MwAddr;
import com.idev4.setup.domain.MwAddrRel;
import com.idev4.setup.domain.MwCmnty;
import com.idev4.setup.domain.MwPort;
import com.idev4.setup.dto.CommunityDto;
import com.idev4.setup.repository.MwAddrRelRepository;
import com.idev4.setup.repository.MwAddrRepository;
import com.idev4.setup.repository.MwCmntyRepository;
import com.idev4.setup.repository.MwPortRepository;
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
 * Service Implementation for managing MwCmnty.
 */
@Service
@Transactional
public class MwCmntyService {

    private final Logger log = LoggerFactory.getLogger(MwCmntyService.class);

    private final MwCmntyRepository mwCmntyRepository;

    private final MwPortRepository mwPortRepository;

    private final MwAddrRepository mwAddrRepository;

    private final MwAddrRelRepository mwAddrRelRepository;

    private final EntityManager em;

    public MwCmntyService(MwCmntyRepository mwCmntyRepository, MwPortRepository mwPortRepository,
                          MwAddrRepository mwAddrRepository, MwAddrRelRepository mwAddrRelRepository, EntityManager em) {
        this.mwCmntyRepository = mwCmntyRepository;
        this.mwPortRepository = mwPortRepository;
        this.mwAddrRepository = mwAddrRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.em = em;
    }

    /**
     * Save a mwCmnty.
     *
     * @param mwCmnty the entity to save
     * @return the persisted entity
     */
    public MwCmnty save(MwCmnty mwCmnty) {
        log.debug("Request to save MwCmnty : {}", mwCmnty);
        return mwCmntyRepository.save(mwCmnty);
    }

    /**
     * Get all the mwCmnties.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwCmnty> findAll(Pageable pageable) {
        log.debug("Request to get all MwCmnties");
        return mwCmntyRepository.findAll(pageable);
    }

    /**
     * Get one mwCmnty by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwCmnty findOne(Long id) {
        log.debug("Request to get MwCmnty : {}", id);
        return mwCmntyRepository.findOneByCmntySeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwCmnty> findAllHistory(Long id) {
        log.debug("Request to get MwCmnty : {}", id);
        return mwCmntyRepository.findAllByCmntySeq(id);
    }

    /**
     * Delete the mwCmnty by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id, String currUser) {
        log.debug("Request to delete MwCmnty : {}", id);
        MwCmnty cmnty = mwCmntyRepository.findOneByCmntySeqAndCrntRecFlg(id, true);

        Instant currIns = Instant.now();
        MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(cmnty.getPortSeq(), true);
        if (port != null) {
            MwAddrRel mwAddrRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(port.getBrnchSeq(), "Branch", true);
            if (mwAddrRel != null) {
                MwAddr mwAddr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(mwAddrRel.getAddrSeq(), true);
                if (mwAddr != null) {
                    mwAddr.setCmntySeq(-1l);
                    mwAddr.setLastUpdBy(currUser);
                    mwAddr.setLastUpdDt(currIns);

                    mwAddrRepository.save(mwAddr);
                }
            }
        }
        cmnty.setCrntRecFlg(false);
        cmnty.setDelFlg(true);
        cmnty.setLastUpdDt(currIns);
        cmnty.setLastUpdBy(currUser);
        mwCmntyRepository.save(cmnty);
    }

    @Transactional(readOnly = true)
    public List<MwCmnty> findAllByCurrentRecord() {
        log.debug("Request to get all MwCmnties");
        return mwCmntyRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public List<MwCmnty> getCommunitiesOfAllowedArea() {
        log.debug("Request to get all MwCmnties");
        return mwCmntyRepository.findAllByCrntRecFlg(true).subList(0, 100);
    }

    public MwCmnty addNewCommunity(CommunityDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.CMNTY_SEQ);
        MwCmnty cmnty = new MwCmnty();
        Instant currIns = Instant.now();
        cmnty.setCmntySeq(seq);
        cmnty.setCmntyCmnt(dto.cmntyDescription);
        cmnty.setCmntyNm(dto.cmntyName);
        cmnty.setCmntyCd(String.format("%04d", seq));
        cmnty.setCmntyStsKey(dto.cmntyStatus);
        cmnty.setCrntRecFlg(true);
        cmnty.setCrtdBy(currUser);
        cmnty.setCrtdDt(currIns);
        cmnty.setDelFlg(false);
        cmnty.setEffStartDt(currIns);
        cmnty.setLastUpdBy(currUser);
        cmnty.setLastUpdDt(currIns);
        cmnty.setPortSeq(dto.portfolioSeq);

        return mwCmntyRepository.save(cmnty);

    }

    @Transactional
    public MwCmnty updateExistingCommunity(CommunityDto dto, String currUser) {
        MwCmnty mwCmnty = mwCmntyRepository.findOneByCmntySeqAndCrntRecFlg(dto.comSeq, true);
        Instant currIns = Instant.now();
        if (mwCmnty == null)
            return null;

        mwCmnty.setLastUpdDt(currIns);
        mwCmnty.setLastUpdBy(currUser);
        mwCmnty.setCmntyNm(dto.cmntyName);
        mwCmnty.setCmntyCmnt(dto.cmntyDescription);
        mwCmnty.setCmntyStsKey(dto.cmntyStatus);

        return mwCmntyRepository.save(mwCmnty);


    }

    public List<MwCmnty> findCommunitesByPortfolioSeq(long portfolioSeq) {
        return mwCmntyRepository.findAllByPortSeqAndCrntRecFlg(portfolioSeq, true);
    }

    public MwCmnty updateStatus(long regSeq, String currUser) {
        Instant currIns = Instant.now();
        MwCmnty reg = findOne(regSeq);
        String query = Queries.statusSeq;
        Query q = em.createNativeQuery(query);
        List<Object[]> statuses = q.getResultList();
        long actSeq = 0;
        long inactSeq = 0;
        for (Object[] st : statuses) {
            if (st[0].toString().toLowerCase().equals("active")) {
                actSeq = Long.valueOf(st[1].toString());
            } else {
                inactSeq = Long.valueOf(st[1].toString());
            }
        }
        if (reg.getCmntyStsKey() == actSeq)
            reg.setCmntyStsKey(inactSeq);
        else
            reg.setCmntyStsKey(actSeq);
        reg.setLastUpdBy(currUser);
        reg.setLastUpdDt(currIns);
        return save(reg);
    }

    //Added by Areeba - Branch Setup
    public Map<String, Object> makeDataSet(List<CommunityDto> dto) {
        List<CommunityDto> listsRecord = new ArrayList<>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        int recCount = 0;
        List<MwCmnty> cmntyList = new ArrayList();

        for (int i = 0; i < dto.size(); i++) {
            CommunityDto cmnty = dto.get(i);

            MwCmnty mwCmnty = new MwCmnty();

            long seq = SequenceFinder.findNextVal(Sequences.CMNTY_SEQ);
            Instant currIns = Instant.now();

            mwCmnty.setCmntySeq(seq);
            mwCmnty.setCmntyCmnt(cmnty.cmntyDescription);
            mwCmnty.setCmntyNm(cmnty.cmntyName);
            mwCmnty.setCmntyCd(String.format("%04d", seq));
            mwCmnty.setCmntyStsKey(200L);
            mwCmnty.setCrntRecFlg(true);
            mwCmnty.setCrtdBy(currUser);
            mwCmnty.setCrtdDt(currIns);
            mwCmnty.setDelFlg(false);
            mwCmnty.setEffStartDt(currIns);
            mwCmnty.setLastUpdBy(currUser);
            mwCmnty.setLastUpdDt(currIns);
            mwCmnty.setPortSeq(cmnty.portfolioSeq);

            mwCmnty = mwCmntyRepository.save(mwCmnty);
            cmntyList.add(mwCmnty);
            recCount++;
        }

        Map<String, Object> response = new HashMap<>();
        String success = "File Uploaded Successfully with Records: " + recCount + "/" + dto.size();
        response.put("success", success);
        response.put("cmntys", cmntyList);
        return response;
    }
    //Ended by Areeba
}
