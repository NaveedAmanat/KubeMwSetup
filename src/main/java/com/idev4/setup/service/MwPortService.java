package com.idev4.setup.service;

import com.idev4.setup.domain.*;
import com.idev4.setup.dto.AreaDto;
import com.idev4.setup.dto.BranchDto;
import com.idev4.setup.dto.PortDto;
import com.idev4.setup.dto.RegionDto;
import com.idev4.setup.repository.*;
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
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Muhammad Bassam
 * @date, 4-5-2021
 * @description, bug fixed for portfolio transfer screen
 */
@Service
@Transactional
public class MwPortService {

    private final Logger log = LoggerFactory.getLogger(MwPortService.class);

    private final MwPortRepository mwPortRepository;

    private final MwCmntyRepository mwCmntyRepository;

    private final EntityManager em;

    private final MwBrnchRepository mwBrnchRepository;

    private final MwAreaRepository mwAreaRepository;

    private final MwRegRepository mwRegRepository;

    private final MwAclRepository mwAclRepository;

    private final MwPortEmpRelRepository mwPortEmpRelRepository;

    private final MwBrnchEmpRelRepository mwBrnchEmpRelRepository;

    private final MwEmpRepository mwEmpRepository;

    public MwPortService(MwPortRepository mwPortRepository, EntityManager em, MwCmntyRepository mwCmntyRepository,
                         MwBrnchRepository mwBrnchRepository, MwAreaRepository mwAreaRepository, MwRegRepository mwRegRepository,
                         MwAclRepository mwAclRepository, MwPortEmpRelRepository mwPortEmpRelRepository, MwBrnchEmpRelRepository mwBrnchEmpRelRepository,
                         MwEmpRepository mwEmpRepository) {
        this.mwPortRepository = mwPortRepository;
        this.mwCmntyRepository = mwCmntyRepository;
        this.em = em;
        this.mwBrnchRepository = mwBrnchRepository;
        this.mwAreaRepository = mwAreaRepository;
        this.mwRegRepository = mwRegRepository;
        this.mwAclRepository = mwAclRepository;
        this.mwPortEmpRelRepository = mwPortEmpRelRepository;
        this.mwBrnchEmpRelRepository = mwBrnchEmpRelRepository;
        this.mwEmpRepository = mwEmpRepository;
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
    public List<RegionDto> findPortsForUser(String user) {
        Query q = em.createNativeQuery(Queries.getAclForUser + user + "'");
        List<Object[]> results = q.getResultList();
        List<MwPort> ports = new ArrayList<>();
        List<PortDto> portsdto = new ArrayList<>();
        List<MwBrnch> branches = new ArrayList<>();
        List<BranchDto> branchesdto = new ArrayList<>();
        List<MwArea> areas = new ArrayList<>();
        List<AreaDto> areasdto = new ArrayList<>();
        List<MwReg> regions = new ArrayList<>();
        List<RegionDto> regionsdto = new ArrayList<>();
        if (results != null && results.size() > 0) {
            for (Object[] obj : results) {
                BigDecimal bd = new BigDecimal("" + obj[2]);
                MwPort port = findOne(bd.longValue());
                if (port == null)
                    return null;
                PortDto dto = new PortDto();
                dto.portSeq = port.getPortSeq();
                dto.branchSeq = port.getBrnchSeq();
                dto.portfolioName = port.getPortNm();

                MwBrnch branch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(port.getBrnchSeq(), true);
                if (branch != null) {
                    branches.add(branch);
                    BranchDto bdto = new BranchDto();
                    bdto.branchSeq = branch.getBrnchSeq();
                    bdto.areaSeq = branch.getAreaSeq();
                    bdto.branchName = branch.getBrnchNm();
                    branchesdto.add(bdto);
                }
                portsdto.add(dto);
                ports.add(port);
            }
        }
        Set<BranchDto> set = new LinkedHashSet<BranchDto>(branchesdto);

        List<BranchDto> brnches = new ArrayList<>(set);
        brnches.forEach(branch -> {
            portsdto.forEach(port -> {
                if (port.branchSeq == branch.branchSeq) {
                    if (branch.portfolio == null) {
                        branch.portfolio = new ArrayList<PortDto>();
                    }
                    branch.portfolio.add(port);
                }
            });
            MwArea ar = mwAreaRepository.findOneByAreaSeqAndCrntRecFlg(branch.areaSeq, true);
            if (ar != null) {
                areas.add(ar);
                AreaDto adto = new AreaDto();
                adto.areaSeq = ar.getAreaSeq();
                adto.areaName = ar.getAreaNm();
                adto.regionSeq = ar.getRegSeq();
                areasdto.add(adto);
            }
        });

        Set<AreaDto> seta = new LinkedHashSet<AreaDto>(areasdto);

        List<AreaDto> areasFiltered = new ArrayList<>(seta);

        seta.forEach(area -> {
            brnches.forEach(branch -> {
                if (branch.areaSeq == area.areaSeq) {
                    if (area.branches == null) {
                        area.branches = new ArrayList<BranchDto>();
                    }
                    area.branches.add(branch);
                }
            });
            MwReg reg = mwRegRepository.findOneByRegSeqAndCrntRecFlg(area.regionSeq, true);
            if (reg != null) {
                regions.add(reg);
                RegionDto rdto = new RegionDto();
                rdto.regionSeq = reg.getRegSeq();
                rdto.regionName = reg.getRegNm();
                regionsdto.add(rdto);
            }
        });

        Set<RegionDto> setr = new LinkedHashSet<RegionDto>(regionsdto);

        List<RegionDto> regs = new ArrayList<>(setr);

        regs.forEach(region -> {
            areasFiltered.forEach(area -> {
                if (region.regionSeq == area.regionSeq) {
                    if (region.areas == null) {
                        region.areas = new ArrayList<>();
                    }
                    region.areas.add(area);
                }
            });
        });

        return regs;
        //
        // portsdto.forEach(port->{
        //
        // });
        //
        //
        //
        // return ports;
        // log.debug("Request to get MwPort : {}", id);
        // return mwPortRepository.findOneByPortSeqAndCrntRecFlg(id,true);
    }

    @Transactional(readOnly = true)
    public List<MwPort> findAllHistory(Long id) {
        log.debug("Request to get MwPort : {}", id);
        return mwPortRepository.findAllByPortSeq(id);
    }

    /**
     * Delete the mwPort by id.
     *
     * @param id the id of the entity
     */

    // Edited by Areeba - 27-05-2022
    public boolean delete(Long id, String currUser) {
        log.debug("Request to delete MwPort : {}", id);
        List<MwCmnty> cmnts = mwCmntyRepository.findAllByPortSeqAndCrntRecFlg(id, true);
        if (cmnts != null && cmnts.size() > 0)
            return false;
        MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(id, true);
        port.setCrntRecFlg(false);
        port.setDelFlg(true);
        port.setLastUpdDt(Instant.now());

        // Added by Areeba - 27-05-2022
        port.setLastUpdBy(currUser);

        port.setEffEndDt(Instant.now());
        mwPortRepository.save(port);
        return true;
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
        //Added by Areeba - 07-06-2022
        port.setPortTyp(dto.portfolioType);

        return mwPortRepository.save(port);
    }

    @Transactional
    public String updateExistingPortfolioToBranch(PortDto dto, String currUser) {

        long aclSeq = SequenceFinder.findNextVal(Sequences.ACL_SEQ);
        log.debug("\n\n\n\\n\\n acl seq : {}", aclSeq);
        log.debug("\n\n\n\nportfolioSeq : {}", dto.portfolioSeq);
        log.debug("branchSeq : {}", dto.branchSeq);
        log.debug("branchSeq : {}", dto.portfolioName);

        MwPort mwPort = mwPortRepository.findOneByPortSeqAndCrntRecFlg(dto.portfolioSeq, true);
        Instant currIns = Instant.now();
        if (mwPort == null) {
            return null;
        }

        mwPort.setLastUpdDt(currIns);
        mwPort.setLastUpdBy(currUser);
        mwPort.setPortNm(dto.portfolioName);
        mwPort.setPortDscr(dto.portfolioComment);
        mwPort.setPortStsKey(dto.portfolioStatus);
        //Added by Areeba - 07-06-2022
        mwPort.setPortTyp(dto.portfolioType);

        if (dto.branchSeq != mwPort.getBrnchSeq().longValue()) {

            MwBrnchEmpRel exbrel = mwBrnchEmpRelRepository.findOneByBrnchSeqAndCrntRecFlg(mwPort.getBrnchSeq(), true);
            MwEmp exemp = mwEmpRepository.findOneByEmpSeq(exbrel.getEmpSeq());
            MwAcl exAcl = mwAclRepository.findOneByUserIdAndPortSeq(exemp.getEmpLanId(), mwPort.getPortSeq());
            if (exAcl != null) mwAclRepository.delete(exAcl);

            MwBrnchEmpRel brel = mwBrnchEmpRelRepository.findOneByBrnchSeqAndCrntRecFlg(dto.branchSeq, true);
            MwEmp emp = mwEmpRepository.findOneByEmpSeq(brel.getEmpSeq());
            MwAcl acl = new MwAcl();
            //long aclSeq = SequenceFinder.findNextVal(Sequences.ACL_SEQ);
            acl.setAclSeq(aclSeq);
            acl.setUserId(emp.getEmpLanId());
            acl.setPortSeq(mwPort.getPortSeq());

            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("port_trf_pkg.prc_portfolio_transfer_port");
            storedProcedure.registerStoredProcedureParameter("P_FROM_PORT_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("L_FROM_BRNCH_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("L_TO_BRNCH_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_LOGIN_USER", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("V_RTN_MSG", String.class, ParameterMode.OUT);
//			Integer[] loanIds = loans.stream().map(am -> am.getLoanAppSeq()).toArray(Integer[]::new);
            storedProcedure.setParameter("P_FROM_PORT_SEQ", dto.portfolioSeq);
            storedProcedure.setParameter("L_FROM_BRNCH_SEQ", mwPort.getBrnchSeq().longValue());
            storedProcedure.setParameter("L_TO_BRNCH_SEQ", dto.branchSeq);
            storedProcedure.setParameter("P_LOGIN_USER", currUser);
            // execute SP
            storedProcedure.execute();
            // get result
            String resp = (String) storedProcedure.getOutputParameterValue("V_RTN_MSG");
            if (resp != null && resp.toLowerCase().contains("success")) {
                mwPort.setBrnchSeq(dto.branchSeq);
                mwAclRepository.save(acl);
                mwPortRepository.save(mwPort);
                return "Successfully Transfered";
            } else if (resp != null) {
                return resp;
            }
        }
        // Added by Areeba - 27-05-2022
        else {
            mwPortRepository.save(mwPort);
            return "Portfolio updated successfully";
        }
        // Ended by Areeba
        return "Portfolio issue, Contact Admin !!";
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
}
