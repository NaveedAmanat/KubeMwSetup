package com.idev4.setup.service;

import com.idev4.setup.domain.*;
import com.idev4.setup.dto.BranchDto;
import com.idev4.setup.dto.BranchOpeningDto;
import com.idev4.setup.dto.PerdDto;
import com.idev4.setup.dto.PortTrgtDto;
import com.idev4.setup.repository.*;
import com.idev4.setup.web.rest.util.Queries;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing MwBrnch.
 */
@Service
@Transactional
public class MwBrnchService {

    private final Logger log = LoggerFactory.getLogger(MwBrnchService.class);
    private final MwBrnchRepository mwBrnchRepository;
    private final MwPortRepository mwPortRepository;
    private final MwAddrRepository mwAddrRepository;
    private final MwAddrRelRepository mwAddrRelRepository;
    private final MwBrnchAcctSetRepository mwBrnchAcctSetRepository;
    private final MwBrnchLocationRelRepository mwBrnchLocationRelRepository;
    private final MwBrnchPrdRelRepository mwBrnchPrdRelRepository;
    private final MwBrnchRemitRelRepository mwBrnchRemitRelRepository;
    private final MwCityUcRelRepository mwCityUcRelRepository;
    private final MwPortLocationRelRepository mwPortLocationRelRepository;
    private final MwPrdRepository mwPrdRepository;
    private final MwCmntyRepository mwCmntyRepository;
    private final MwUcRepository mwUcRepository;
    private final EntityManager em;
    @Autowired
    private EntityManager entityManager;

    public MwBrnchService(MwBrnchRepository mwBrnchRepository, EntityManager em, MwPortRepository mwPortRepository,
                          MwAddrRepository mwAddrRepository, MwAddrRelRepository mwAddrRelRepository, MwBrnchAcctSetRepository mwBrnchAcctSetRepository,
                          MwBrnchLocationRelRepository mwBrnchLocationRelRepository, MwBrnchPrdRelRepository mwBrnchPrdRelRepository,
                          MwBrnchRemitRelRepository mwBrnchRemitRelRepository, MwCityUcRelRepository mwCityUcRelRepository,
                          MwPortLocationRelRepository mwPortLocationRelRepository, MwPrdRepository mwPrdRepository,
                          MwCmntyRepository mwCmntyRepository, MwUcRepository mwUcRepository) {
        this.mwBrnchRepository = mwBrnchRepository;
        this.mwPortRepository = mwPortRepository;
        this.em = em;
        this.mwAddrRepository = mwAddrRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.mwBrnchAcctSetRepository = mwBrnchAcctSetRepository;
        this.mwBrnchLocationRelRepository = mwBrnchLocationRelRepository;
        this.mwBrnchPrdRelRepository = mwBrnchPrdRelRepository;
        this.mwBrnchRemitRelRepository = mwBrnchRemitRelRepository;
        this.mwCityUcRelRepository = mwCityUcRelRepository;
        this.mwPortLocationRelRepository = mwPortLocationRelRepository;
        this.mwPrdRepository = mwPrdRepository;
        this.mwCmntyRepository = mwCmntyRepository;
        this.mwUcRepository = mwUcRepository;
    }

    /**
     * Save a mwBrnch.
     *
     * @param mwBrnch the entity to save
     * @return the persisted entity
     */
    public MwBrnch save(MwBrnch mwBrnch) {
        log.debug("Request to save MwBrnch : {}", mwBrnch);
        return mwBrnchRepository.save(mwBrnch);
    }

    /**
     * Get all the mwBrnches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwBrnch> findAll(Pageable pageable) {
        log.debug("Request to get all MwBrnches");
        return mwBrnchRepository.findAll(pageable);
    }

    /**
     * Get one mwBrnch by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public BranchDto findOne(Long id) {
        log.debug("Request to get MwBrnch : {}", id);
        BranchDto dto = new BranchDto();
        MwBrnch brnch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(id, true);

        MwAddrRel addRel = null;
        MwAddr addr = null;
        if (brnch != null)
            addRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(brnch.getBrnchSeq(), "Branch", true);
        if (addRel != null)
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addRel.getAddrSeq(), true);

        dto.branchCode = brnch.getBrnchCd();
        dto.branchDescription = brnch.getBrnchDscr();
        dto.branchName = brnch.getBrnchNm();
        dto.areaSeq = brnch.getAreaSeq();
        dto.branchStatus = brnch.getBrnchStsKey();
        dto.branchSeq = brnch.getBrnchSeq();
        dto.brnchTypKey = brnch.getBrnchTypKey();

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
    public MwBrnch findOnedomain(Long id) {
        log.debug("Request to get MwBrnch : {}", id);
        MwBrnch brnch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(id, true);
        return mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwBrnch> findAllHistory(Long id) {
        log.debug("Request to get MwBrnch : {}", id);
        return mwBrnchRepository.findAllByBrnchSeq(id);
    }

    /**
     * Delete the mwBrnch by id.
     *
     * @param id the id of the entity
     */
    public boolean delete(Long id) {
        log.debug("Request to delete MwBrnch : {}", id);
        List<MwPort> ports = mwPortRepository.findAllByBrnchSeqAndCrntRecFlg(id, true);
        if (ports != null && ports.size() > 0)
            return false;
        MwBrnch brnch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(id, true);
        brnch.setCrntRecFlg(false);
        brnch.setDelFlg(true);
        brnch.setLastUpdDt(Instant.now());
        mwBrnchRepository.save(brnch);
        return true;
    }

    @Transactional(readOnly = true)
    public List<MwBrnch> findAllByCurrentRecord() {
        log.debug("Request to get all MwBrnches");
        return mwBrnchRepository.findAllByCrntRecFlg(true);
    }

    // Added by Areeba - 27-05-2022
    @Transactional(readOnly = true)
    public Map<String, Object> findAllMwBranches(Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {
        log.debug("Request to get all MwBranches");
        // return mwCityRepository.findAllByCrntRecFlg( true );

        String brnchScript = "SELECT b.* FROM mw_brnch b where b.crnt_rec_flg = 1";
        String brnchCountScript = "SELECT count(*) FROM mw_brnch b where b.crnt_rec_flg = 1";

        if (filter != null && filter.length() > 0) {
            String search = " and( lower(b.brnch_nm) LIKE '%?%' )"
                .replace("?", filter.toLowerCase());

            brnchScript += search;
            brnchCountScript += search;
        }

        List<MwBrnch> allBrnchsList = entityManager.createNativeQuery(brnchScript + "\r\norder by 1 desc", MwBrnch.class)
            .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("brnchs", allBrnchsList);

        Long totalBrnchCount = 0L;
        if (isCount.booleanValue()) {
            totalBrnchCount = new BigDecimal(
                entityManager.createNativeQuery(brnchCountScript)
                    .getSingleResult().toString()).longValue();
        }
        response.put("count", totalBrnchCount);

        return response;
    }
    // Ended by Areeba

    @Transactional(readOnly = true)
    public List<MwBrnch> findAllBrnchForUsr() {
        log.debug("Request to get all MwBrnches");
        return mwBrnchRepository.findAllForUsr(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public MwBrnch addNewBranch(BranchDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.BRNCH_SEQ);
        MwBrnch branch = new MwBrnch();
        Instant currIns = Instant.now();
        branch.setBrnchSeq(seq);
        branch.setAreaSeq(dto.areaSeq);
        branch.setBrnchCd(String.format("%04d", seq));
        branch.setBrnchDscr(dto.branchDescription);
        branch.setBrnchNm(dto.branchName);
        branch.setBrnchTypKey(dto.brnchTypKey);
        branch.setCrntRecFlg(true);
        branch.setCrtdBy(currUser);
        branch.setCrtdDt(currIns);
        branch.setDelFlg(false);
        branch.setEffStartDt(currIns);
        branch.setLastUpdBy(currUser);
        branch.setLastUpdDt(currIns);
        branch.setBrnchStsKey(dto.branchStatus);
        return mwBrnchRepository.save(branch);
    }

    //Added by Areeba - Branch Setup
    //Adds and Updates a branch
    public MwBrnch addNewBranchSetup(BranchOpeningDto dto, String currUser) {

        MwBrnch branch;
        branch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(dto.brnchSeq, true);
        Instant currIns = Instant.now();

        if (branch != null) {
            branch.setAreaSeq(dto.areaSeq);
            branch.setBrnchNm(dto.brnchNm);
            branch.setBrnchDscr(dto.brnchDscr);
            branch.setBrnchStsKey(dto.brnchStsKey);
            branch.setBrnchTypKey(dto.brnchTypKey);
            branch.setBrnchPhNum(dto.brnchPhNum);
            branch.setLastUpdBy(currUser);
            branch.setLastUpdDt(currIns);
            branch.setEmail(dto.email);
            branch.setMfcibCmpnySeq(dto.mfcibCmpnySeq);
            branch.setHrLocCd(dto.hrLocCd);
            branch.setMobStrtDt(dto.mobStrtDt);
            branch.setMobEndDt(dto.mobEndDt);

            return mwBrnchRepository.save(branch);
        } else {
            long brnchSeq = SequenceFinder.findNextVal(Sequences.BRNCH_SEQ);

            branch = new MwBrnch();
            branch.setBrnchSeq(brnchSeq);
            branch.setEffStartDt(currIns);
            branch.setAreaSeq(dto.areaSeq);
            branch.setBrnchCd(String.format("%04d", brnchSeq));
            branch.setBrnchNm(dto.brnchNm);
            branch.setBrnchDscr(dto.brnchDscr);
            branch.setBrnchStsKey(dto.brnchStsKey);
            branch.setBrnchTypKey(dto.brnchTypKey);
            branch.setBrnchPhNum(dto.brnchPhNum);
            branch.setCrtdBy(currUser);
            branch.setCrtdDt(currIns);
            branch.setLastUpdBy(currUser);
            branch.setLastUpdDt(currIns);
            branch.setDelFlg(false);
            branch.setCrntRecFlg(true);
            branch.setEffEndDt(null);
            branch.setCsFlg(null);
            branch.setAppVrsnCd(285L);
            branch.setEmail(dto.email);
            branch.setMfcibCmpnySeq(dto.mfcibCmpnySeq);
            branch.setHrLocCd(dto.hrLocCd);
            branch.setMobStrtDt(dto.mobStrtDt);
            branch.setMobEndDt(dto.mobEndDt);

            return mwBrnchRepository.save(branch);
        }
    }
    //Ended by Areeba

    @Transactional
    public MwBrnch updateExistingBranch(BranchDto dto, String currUser) {

        MwBrnch branch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(dto.branchSeq, true);
        Instant currIns = Instant.now();
        if (branch == null)
            return null;

        branch.setLastUpdDt(currIns);
        branch.setLastUpdBy(currUser);
        branch.setAreaSeq(dto.areaSeq);
        branch.setBrnchNm(dto.branchName);
        branch.setBrnchDscr(dto.branchDescription);
        branch.setBrnchStsKey(dto.branchStatus);
        branch.setBrnchTypKey(dto.brnchTypKey);

        return mwBrnchRepository.save(branch);

    }

    public List<MwBrnch> getBranchesByArea(long areaSeq) {
        return mwBrnchRepository.findAllByAreaSeqAndCrntRecFlgOrderByBrnchSeqDesc(areaSeq, true);
    }

    public MwBrnch updateStatus(long regSeq, String currUser) {
        Instant currIns = Instant.now();
        MwBrnch reg = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(regSeq, true);
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
        if (reg.getBrnchStsKey() == actSeq)
            reg.setBrnchStsKey(inactSeq);
        else
            reg.setBrnchStsKey(actSeq);
        reg.setLastUpdBy(currUser);
        reg.setLastUpdDt(currIns);
        return save(reg);
    }

    public List<PerdDto> getPerdForBrnch(Long brnchSeq, Long prdGrpSeq) {
        List<PerdDto> resp = new ArrayList<PerdDto>();
        Query q = em.createNativeQuery(
                "select perd.perd_key, trgt.trgt_clients , trgt.trgt_amt , trgt.brnch_targets_seq from mw_perd perd join mw_brnch_trgt trgt on trgt.trgt_perd = perd.perd_key and trgt.del_flg=0 and "
                    + " trgt.brnch_seq=:brnchSeq and prd_seq=:prdGrpSeq\r\n" + "order by perd_key desc ")
            .setParameter("brnchSeq", brnchSeq).setParameter("prdGrpSeq", prdGrpSeq);
        List<Object[]> perds = q.getResultList();
        for (Object[] st : perds) {
            PerdDto dto = new PerdDto();
            dto.perdKey = Long.valueOf(st[0].toString());
            dto.clients = Long.valueOf(st[1].toString());
            dto.amount = Long.valueOf(st[2].toString());
            dto.brnchTrgtSeq = Long.valueOf(st[3].toString());
            resp.add(dto);
        }
        return resp;
    }

    public List<PortTrgtDto> getPortTrgtForBrnch(Long brnchSeq, Long brnchTrgtSeq) {
        List<PortTrgtDto> resp = new ArrayList<PortTrgtDto>();
        Query q = em.createNativeQuery("select port.port_seq, emp.emp_nm , trgt.TRGT_CLNTS , trgt.TRGT_AMT from mw_emp emp \r\n"
            + "join mw_port_emp_rel prel on prel.emp_seq=emp.emp_seq and prel.crnt_rec_flg=1 --and prel.del_flg=0\r\n"
            + "join mw_port port on port.port_seq=prel.port_seq and port.crnt_rec_flg=1 and port.port_sts_key=200\r\n"
            + "left outer join mw_port_trgt trgt on trgt.port_seq=port.port_seq and trgt.crnt_rec_flg=1 and trgt.BRNCH_TRGTS_SEQ=:brnchTrgtSeq\r\n"
            + "where port.brnch_seq=:brnchSeq ").setParameter("brnchSeq", brnchSeq).setParameter("brnchTrgtSeq", brnchTrgtSeq);
        List<Object[]> trgts = q.getResultList();
        for (Object[] st : trgts) {
            PortTrgtDto dto = new PortTrgtDto();
            dto.portId = st[0].toString();
            dto.empNm = st[1].toString();
            dto.trgtClnt = (st[2] == null) ? null : Long.valueOf(st[2].toString());
            dto.trgtAmnt = (st[3] == null) ? null : Long.valueOf(st[3].toString());
            dto.brnchTrgtsSeq = brnchTrgtSeq;
            resp.add(dto);
        }
        return resp;
    }

    // public List< PerdDto > getPortTrgtForBrnch( Long brnchSeq, Long prdGrpSeq ) {
    // List< PerdDto > resp = new ArrayList< PerdDto >();
    // Query q = em.createNativeQuery(
    // "select perd.perd_key, trgt.trgt_clients , trgt.trgt_amt from mw_perd perd join mw_brnch_trgt trgt on trgt.trgt_perd = perd.perd_key
    // and trgt.del_flg=0 and "
    // + " trgt.brnch_seq=:brnchSeq and prd_seq=:prdGrpSeq\r\n" + "order by perd_key desc " )
    // .setParameter( "brnchSeq", brnchSeq ).setParameter( "prdGrpSeq", value );
    // List< Object[] > perds = q.getResultList();
    // for ( Object[] st : perds ) {
    // PerdDto dto = new PerdDto();
    // dto.perdKey = Long.valueOf( st[ 0 ].toString() );
    // dto.clients = Long.valueOf( st[ 1 ].toString() );
    // dto.amount = Long.valueOf( st[ 2 ].toString() );
    // }
    // return resp;
    // }

    public List<MwBrnch> getAllBrnchByRegion(long regSeq) {
        return mwBrnchRepository.findAllBrnchByRegion(regSeq);
    }

    //Added by Areeba - Branch Setup
    public Integer addNewBranchAddrInfo(BranchOpeningDto dto, String currUser) {
        Instant currIns = Instant.now();

        if (dto == null) {
            return 0;
        }

        dto.ucs.forEach(uc -> {
            long cityUcRelSeq = 0;
            MwCityUcRel exCityUcRel = mwCityUcRelRepository.findOneByCitySeqAndUcSeqAndCrntRecFlg(dto.citySeq, uc.getUcSeq(), true);
            if (exCityUcRel == null) {

                MwCityUcRel cityUcRel = new MwCityUcRel();
                cityUcRelSeq = SequenceFinder.findNextVal(Sequences.City_UC_REL_SEQ);

                cityUcRel.setCityUcRelSeq(cityUcRelSeq);
                cityUcRel.setCitySeq(dto.citySeq);
                cityUcRel.setEffStartDt(currIns);
                cityUcRel.setUcSeq(uc.getUcSeq());
                cityUcRel.setCrtdBy(currUser);
                cityUcRel.setCrtdDt(currIns);
                cityUcRel.setLastUpdBy(currUser);
                cityUcRel.setLastUpdDt(currIns);
                cityUcRel.setDelFlg(false);
                cityUcRel.setEffEndDt(null);
                cityUcRel.setCrntRecFlg(true);

                mwCityUcRelRepository.save(cityUcRel);
            } else {
                cityUcRelSeq = exCityUcRel.getCityUcRelSeq();
            }
            MwBrnchLocationRel exBrnchLocationRel = mwBrnchLocationRelRepository.findOneByBrnchSeqAndCitySeqAndCrntRecFlg(dto.brnchSeq, cityUcRelSeq, true);
            if (exBrnchLocationRel == null) {

                MwBrnchLocationRel brnchLocationRel = new MwBrnchLocationRel();
                long brnchLocationRelSeq = SequenceFinder.findNextVal(Sequences.BRNCH_LOCATION_REL_SEQ);

                brnchLocationRel.setBrnchLocationRelSeq(brnchLocationRelSeq);
                brnchLocationRel.setEffStartDt(currIns);
                brnchLocationRel.setBrnchSeq(dto.brnchSeq);
                brnchLocationRel.setCitySeq(cityUcRelSeq);  //cityuc
                brnchLocationRel.setCrtdBy(currUser);
                brnchLocationRel.setCrtdDt(currIns);
                brnchLocationRel.setLastUpdBy(currUser);
                brnchLocationRel.setLastUpdDt(currIns);
                brnchLocationRel.setDelFlg(false);
                brnchLocationRel.setEffEndDt(null);
                brnchLocationRel.setCrntRecFlg(true);

                mwBrnchLocationRelRepository.save(brnchLocationRel);
            }
        });

        MwCityUcRel cityuc = mwCityUcRelRepository.findOneByCitySeqAndUcSeqAndCrntRecFlg(dto.citySeq, dto.ucSeq, true);

        dto.ports.forEach(port -> {
            MwPortLocationRel exPortLocationRel = mwPortLocationRelRepository.findOneByPortSeqAndCitySeqAndCrntRecFlg(port.getPortSeq(), cityuc.getCityUcRelSeq(), true);
            if (exPortLocationRel == null) {

                MwPortLocationRel portLocationRel = new MwPortLocationRel();
                long portLocationRelSeq = SequenceFinder.findNextVal(Sequences.PORT_LOCATION_REL_SEQ);

                portLocationRel.setPortLocationRelSeq(portLocationRelSeq);
                portLocationRel.setEffStartDt(currIns);
                portLocationRel.setPortSeq(port.getPortSeq());
                portLocationRel.setCitySeq(cityuc.getCityUcRelSeq()); //cityuc
                portLocationRel.setCrtdBy(currUser);
                portLocationRel.setCrtdDt(currIns);
                portLocationRel.setLastUpdBy(currUser);
                portLocationRel.setLastUpdDt(currIns);
                portLocationRel.setDelFlg(false);
                portLocationRel.setEffEndDt(null);
                portLocationRel.setCrntRecFlg(true);

                mwPortLocationRelRepository.save(portLocationRel);
            }
        });

        MwAddrRel exAddrRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.brnchSeq, "Branch", true);
        MwAddr addr = null;
        if (exAddrRel == null) {

            addr = new MwAddr();
            long addrSeq = SequenceFinder.findNextVal(Sequences.ADDR_SEQ);

            addr.setAddrSeq(addrSeq);
            addr.setEffStartDt(currIns);
            addr.setHseNum(dto.hseNum);
            addr.setStrt(dto.strt);
            addr.setOthDtl(dto.othdtl);
            addr.setCitySeq(cityuc.getCityUcRelSeq());  //cityuc
            addr.setAddrTypKey(dto.addrTypKey);
            addr.setCmntySeq(dto.cmntySeq);
            addr.setVlg(dto.vlg);
            addr.setLongitude(dto.longi);
            addr.setLatitude(dto.lati);
            addr.setCrtdBy(currUser);
            addr.setCrtdDt(currIns);
            addr.setLastUpdBy(currUser);
            addr.setLastUpdDt(currIns);
            addr.setDelFlg(false);
            addr.setEffEndDt(null);
            addr.setCrntRecFlg(true);
            addr.setSyncFlg(null);

            mwAddrRepository.save(addr);

            MwAddrRel addrRel = new MwAddrRel();
            long addRelSeq = SequenceFinder.findNextVal(Sequences.ADDR_REL_SEQ);

            addrRel.setAddrRelSeq(addRelSeq);
            addrRel.setEffStartDt(currIns);
            addrRel.setAddrSeq(addrSeq);
            addrRel.setEntySeq(dto.brnchSeq);  //brnchseq
            addrRel.setEntyType("Branch"); //branch string
            addrRel.setCrtdBy(currUser);
            addrRel.setCrtdDt(currIns);
            addrRel.setLastUpdBy(currUser);
            addrRel.setLastUpdDt(currIns);
            addrRel.setDelFlg(false);
            addrRel.setEffEndDt(null);
            addrRel.setCrntRecFlg(true);
            addrRel.setSyncFlg(null);

            mwAddrRelRepository.save(addrRel);
        } else {
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(exAddrRel.getAddrSeq(), true);
            addr.setHseNum(dto.hseNum);
            addr.setStrt(dto.strt);
            addr.setOthDtl(dto.othdtl);
            addr.setCitySeq(cityuc.getCityUcRelSeq());  //cityuc
            addr.setAddrTypKey(dto.addrTypKey);
            addr.setCmntySeq(dto.cmntySeq);
            addr.setVlg(dto.vlg);
            addr.setLongitude(dto.longi);
            addr.setLatitude(dto.lati);
            addr.setLastUpdBy(currUser);
            addr.setLastUpdDt(currIns);

            mwAddrRepository.save(addr);

        }

        return 1;
    }

    @Transactional(readOnly = true)
    public BranchOpeningDto getMwBrnchData(Long id) {
        log.debug("Request to get MwBrnch : {}", id);
        BranchOpeningDto dto = new BranchOpeningDto();
        MwBrnch brnch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(id, true);
        //MwBrnchLocationRel brnchLocationRel = null;
        MwBrnchAcctSet acctSet = null;
        MwAddrRel addRel = null;
        MwAddr addr = null;
        MwCityUcRel cityUcRel = null;
        MwBrnchRemitRel remitRel = null;
        //List<MwBrnchPrdRel> prdRel = null;
        List<MwPrd> prd = null;
        List<MwPort> ports = null;
        List<MwCmnty> cmnties = null;
        List<MwUc> ucs = null;

        if (brnch != null) {
            addRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(brnch.getBrnchSeq(), "Branch", true);
            acctSet = mwBrnchAcctSetRepository.findOneByBrnchSeqAndCrntRecFlg(id, true);
            remitRel = mwBrnchRemitRelRepository.findOneByBrnchSeqAndCrntRecFlg(id, true);
            prd = mwPrdRepository.findAllMwPrdForBranch(id);
            ports = mwPortRepository.findAllByBrnchSeqAndCrntRecFlgOrderByPortNm(id, true);
            ucs = mwUcRepository.findAllMwUcForBranch(id);
            cmnties = mwCmntyRepository.findAllMwCmntyForBranch(id);

            dto.brnchSeq = brnch.getBrnchSeq();
            dto.areaSeq = brnch.getAreaSeq();
            dto.brnchNm = brnch.getBrnchNm();
            dto.brnchDscr = brnch.getBrnchDscr();
            dto.brnchStsKey = brnch.getBrnchStsKey();
            dto.brnchTypKey = brnch.getBrnchTypKey();
            dto.brnchPhNum = brnch.getBrnchPhNum();
            dto.email = brnch.getEmail();
            dto.mfcibCmpnySeq = brnch.getMfcibCmpnySeq();
            dto.hrLocCd = brnch.getHrLocCd();
            dto.mobStrtDt = brnch.getMobStrtDt();
            dto.mobEndDt = brnch.getMobEndDt();
        }
        if (addRel != null) {
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addRel.getAddrSeq(), true);
        }
        if (addr != null) {
            //brnchLocationRel = mwBrnchLocationRelRepository.findOneByBrnchSeqAndCitySeqAndCrntRecFlg(id, addr.getCitySeq(), true);
            cityUcRel = mwCityUcRelRepository.findOneByCityUcRelSeqAndCrntRecFlg(addr.getCitySeq(), true);

            dto.hseNum = addr.getHseNum();
            dto.strt = addr.getStrt();
            dto.othdtl = addr.getOthDtl();
            dto.addrTypKey = addr.getAddrTypKey();
            dto.cmntySeq = addr.getCmntySeq();
            dto.vlg = addr.getVlg();
            dto.longi = addr.getLongitude();
            dto.lati = addr.getLatitude();
        }

        if (acctSet != null) {
            dto.brnchAcctSetSeq = acctSet.getBrnchAcctSetSeq();
            dto.bankNm = acctSet.getBankName();
            dto.bankBrnch = acctSet.getBankBrnch();
            dto.acctNm = acctSet.getAcctNm();
            dto.acctNum = acctSet.getAcctNum();
            dto.iban = acctSet.getIban();
            dto.bankCode = acctSet.getBankCode();
            dto.ibftBankCode = acctSet.getIbftBankCode();
        }
        if (cityUcRel != null) {
            dto.citySeq = cityUcRel.getCitySeq();
            dto.ucSeq = cityUcRel.getUcSeq();
        }
        if (prd != null)
            dto.products = prd;

        if (ports != null)
            dto.ports = ports;

        if (cmnties != null)
            dto.communities = cmnties;

        if (ucs != null)
            dto.ucs = ucs;

        if (remitRel != null) {
            dto.brnchRemitSeq = remitRel.getBrnchRemitSeq();
            dto.pymtTypSeq = remitRel.getPymtTypSeq();
            dto.remitBankBrnch = remitRel.getBankBrnch();
            dto.remitIban = remitRel.getIban();
        }

        return dto;
    }
    //Ended by Areeba

    public void removeFromBrnch(Long id, String currUser, Long citySeq, Long brnchSeq) {
        log.debug("Request to remove MwUc : {}", id);
        MwUc uc = mwUcRepository.findOneByUcSeqAndCrntRecFlg(id, true);

        Instant currIns = Instant.now();
        List<MwCityUcRel> cityUcRels = mwCityUcRelRepository.findAllByCitySeqAndUcSeqAndCrntRecFlg(citySeq, uc.getUcSeq(), true);
        if (cityUcRels != null) {
            cityUcRels.forEach(cityuc -> {

                MwAddrRel mwAddrRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(brnchSeq, "Branch", true);
                if (mwAddrRel != null) {
                    MwAddr mwAddr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(mwAddrRel.getAddrSeq(), true);
                    if (mwAddr != null) {
                        mwAddr.setCitySeq(-1l);
                        mwAddr.setLastUpdBy(currUser);
                        mwAddr.setLastUpdDt(currIns);

                        mwAddrRepository.save(mwAddr);
                    }
                }

                List<MwPort> ports = mwPortRepository.findAllByBrnchSeqAndCrntRecFlg(brnchSeq, true);
                if (ports != null) {
                    ports.forEach(mwPort -> {
                        MwPortLocationRel exPortLocationRel = mwPortLocationRelRepository.findOneByPortSeqAndCitySeqAndCrntRecFlg(mwPort.getPortSeq(), cityuc.getCityUcRelSeq(), true);
                        if (exPortLocationRel != null) {
                            exPortLocationRel.setLastUpdBy(currUser);
                            exPortLocationRel.setLastUpdDt(currIns);
                            exPortLocationRel.setDelFlg(true);
                            exPortLocationRel.setEffEndDt(null);
                            exPortLocationRel.setCrntRecFlg(false);

                            mwPortLocationRelRepository.save(exPortLocationRel);
                        }
                    });
                }
                MwBrnchLocationRel exBrnchLocationRel = mwBrnchLocationRelRepository.findOneByBrnchSeqAndCitySeqAndCrntRecFlg(brnchSeq, cityuc.getCityUcRelSeq(), true);
                if (exBrnchLocationRel != null) {
                    exBrnchLocationRel.setLastUpdBy(currUser);
                    exBrnchLocationRel.setLastUpdDt(currIns);
                    exBrnchLocationRel.setDelFlg(true);
                    exBrnchLocationRel.setEffEndDt(null);
                    exBrnchLocationRel.setCrntRecFlg(false);

                    mwBrnchLocationRelRepository.save(exBrnchLocationRel);
                }
            });
        }
    }

    //Added by Areeba - Branch Setup
    public Integer addUCsToBrnch(BranchOpeningDto dto, String currUser) {
        Instant currIns = Instant.now();

        if (dto == null) {
            return 0;
        }

        dto.ucs.forEach(uc -> {
            long cityUcRelSeq = 0;
            MwCityUcRel exCityUcRel = mwCityUcRelRepository.findOneByCitySeqAndUcSeqAndCrntRecFlg(dto.citySeq, uc.getUcSeq(), true);
            if (exCityUcRel == null) {

                MwCityUcRel cityUcRel = new MwCityUcRel();
                cityUcRelSeq = SequenceFinder.findNextVal(Sequences.City_UC_REL_SEQ);

                cityUcRel.setCityUcRelSeq(cityUcRelSeq);
                cityUcRel.setCitySeq(dto.citySeq);
                cityUcRel.setEffStartDt(currIns);
                cityUcRel.setUcSeq(uc.getUcSeq());
                cityUcRel.setCrtdBy(currUser);
                cityUcRel.setCrtdDt(currIns);
                cityUcRel.setLastUpdBy(currUser);
                cityUcRel.setLastUpdDt(currIns);
                cityUcRel.setDelFlg(false);
                cityUcRel.setEffEndDt(null);
                cityUcRel.setCrntRecFlg(true);

                mwCityUcRelRepository.save(cityUcRel);
            } else {
                cityUcRelSeq = exCityUcRel.getCityUcRelSeq();
            }
            MwBrnchLocationRel exBrnchLocationRel = mwBrnchLocationRelRepository.findOneByBrnchSeqAndCitySeqAndCrntRecFlg(dto.brnchSeq, cityUcRelSeq, true);
            if (exBrnchLocationRel == null) {

                MwBrnchLocationRel brnchLocationRel = new MwBrnchLocationRel();
                long brnchLocationRelSeq = SequenceFinder.findNextVal(Sequences.BRNCH_LOCATION_REL_SEQ);

                brnchLocationRel.setBrnchLocationRelSeq(brnchLocationRelSeq);
                brnchLocationRel.setEffStartDt(currIns);
                brnchLocationRel.setBrnchSeq(dto.brnchSeq);
                brnchLocationRel.setCitySeq(cityUcRelSeq);  //cityuc
                brnchLocationRel.setCrtdBy(currUser);
                brnchLocationRel.setCrtdDt(currIns);
                brnchLocationRel.setLastUpdBy(currUser);
                brnchLocationRel.setLastUpdDt(currIns);
                brnchLocationRel.setDelFlg(false);
                brnchLocationRel.setEffEndDt(null);
                brnchLocationRel.setCrntRecFlg(true);

                mwBrnchLocationRelRepository.save(brnchLocationRel);
            }
        });
        return 1;
    }
}
