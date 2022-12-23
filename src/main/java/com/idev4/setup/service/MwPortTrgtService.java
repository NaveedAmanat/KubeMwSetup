package com.idev4.setup.service;

import com.idev4.setup.domain.MwPort;
import com.idev4.setup.domain.MwPortTrgt;
import com.idev4.setup.dto.PortDto;
import com.idev4.setup.dto.PortTrgtReqDto;
import com.idev4.setup.repository.MwPortRepository;
import com.idev4.setup.repository.MwPortTrgtRepository;
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
import java.util.List;

/**
 * Service Implementation for managing MwPort.
 */
@Service
@Transactional
public class MwPortTrgtService {

    private final Logger log = LoggerFactory.getLogger(MwPortTrgtService.class);

    private final MwPortRepository mwPortRepository;

    private final MwPortTrgtRepository mwPortTrgtRepository;

    private final EntityManager em;

    public MwPortTrgtService(MwPortRepository mwPortRepository, EntityManager em, MwPortTrgtRepository mwPortTrgtRepository) {
        this.mwPortRepository = mwPortRepository;
        this.em = em;
        this.mwPortTrgtRepository = mwPortTrgtRepository;
    }

    /**
     * Save a mwPort.
     *
     * @param mwPort the entity to save
     * @return the persisted entity
     */
    public MwPort save(MwPort mwPort) {
        log.debug("Request to save MwPort : {}", mwPort);
        return mwPortRepository.save(mwPort);
    }

    /**
     * Get all the mwPorts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwPort> findAll(Pageable pageable) {
        log.debug("Request to get all MwPorts");
        return mwPortRepository.findAll(pageable);
    }

    /**
     * Get one mwPort by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwPort findOne(Long id) {
        log.debug("Request to get MwPort : {}", id);
        return mwPortRepository.findOneByPortSeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwPort> findAllByCurrentRecord() {
        log.debug("Request to get all MwPorts");
        return mwPortRepository.findAllByCrntRecFlg(true);
    }

    public MwPort addNewPortfolioToBranch(PortDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PORT_SEQ);
        MwPort port = new MwPort();
        Instant currIns = Instant.now();
        port.setPortSeq(seq);
        port.setBrnchSeq(dto.branchSeq);
        port.setCrntRecFlg(true);
        port.setCrtdBy(currUser);
        port.setCrtdDt(currIns);
        port.setDelFlg(false);
        port.setEffStartDt(currIns);
        port.setLastUpdBy(currUser);
        port.setLastUpdDt(currIns);
        port.setPortCd(String.format("%04d", seq));
        port.setPortDscr(dto.portfolioComment);
        port.setPortNm(dto.portfolioName);
        port.setPortStsKey(dto.portfolioStatus);

        return mwPortRepository.save(port);
    }

    @Transactional
    public MwPort updateExistingPortfolioToBranch(PortDto dto, String currUser) {
        MwPort exPort = mwPortRepository.findOneByPortSeqAndCrntRecFlg(dto.portfolioSeq, true);
        Instant currIns = Instant.now();
        if (exPort == null) {
            return null;
        }


        MwPort port = new MwPort();

        port.setPortSeq(dto.portfolioSeq);
        port.setBrnchSeq(dto.branchSeq);
        port.setCrntRecFlg(true);
        port.setCrtdBy(currUser);
        port.setCrtdDt(currIns);
        port.setDelFlg(false);
        port.setEffStartDt(currIns);
        port.setLastUpdBy(currUser);
        port.setLastUpdDt(currIns);
        port.setPortCd(exPort.getPortCd());
        port.setPortDscr(dto.portfolioComment);
        port.setPortNm(dto.portfolioName);
        port.setPortStsKey(dto.portfolioStatus);

        return mwPortRepository.save(port);
    }

    public List<MwPort> getPortfolioByBranch(long branchSeq) {
        return mwPortRepository.findAllByBrnchSeqAndCrntRecFlg(branchSeq, true);
    }

    public MwPort updateStatus(long regSeq, String currUser) {
        Instant currIns = Instant.now();
        MwPort reg = findOne(regSeq);
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
        if (reg.getPortStsKey() == actSeq)
            reg.setPortStsKey(inactSeq);
        else
            reg.setPortStsKey(actSeq);
        reg.setLastUpdBy(currUser);
        reg.setLastUpdDt(currIns);
        return save(reg);
    }

    public List savePortTrgtArray(List<PortTrgtReqDto> dtos) {
        List<MwPortTrgt> trgts = new ArrayList<>();
        // updated by Yousaf  Date 25-11-2021- purpose updated targets are not saved first
        MwPortTrgt exTrgt = null;
        for (PortTrgtReqDto dto : dtos) {
            MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(Long.parseLong(dto.portId), true);
            if (port != null) {
                exTrgt = mwPortTrgtRepository.findOneByBrnchTrgtsSeqAndPortSeqAndCrntRecFlg(dto.brnchTrgtsSeq,
                    port.getPortSeq(), true);
                if (exTrgt != null) {
                    mwPortTrgtRepository.delete(exTrgt); //ex targets delete first
                }
                MwPortTrgt trgt = new MwPortTrgt();
                trgt.setBrnchTrgtsSeq(dto.brnchTrgtsSeq);
                trgt.setCrntRecFlg(true);
                trgt.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                trgt.setCrtdDt(Instant.now());
                trgt.setDelFlg(false);
                trgt.setEffStartDt(Instant.now());
                trgt.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                trgt.setLastUpdDt(Instant.now());
                trgt.setPortSeq(port.getPortSeq());
                long seq = SequenceFinder.findNextVal(Sequences.PORT_TRGT_SEQ);
                trgt.setPortTrgtSeq(seq);
                trgt.setTrgtAmt(dto.trgtAmt);
                trgt.setTrgtClnts(dto.trgtClnt);
                trgts.add(trgt);
            }
        }
        return mwPortTrgtRepository.save(trgts);
    }
}
