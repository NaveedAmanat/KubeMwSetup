package com.idev4.setup.service;

import com.idev4.setup.domain.MwAcl;
import com.idev4.setup.domain.MwBrnchEmpRel;
import com.idev4.setup.domain.MwEmp;
import com.idev4.setup.domain.MwPortEmpRel;
import com.idev4.setup.dto.EmployeeRelationDto;
import com.idev4.setup.repository.*;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing MwPortEmpRel.
 */
@Service
@Transactional
public class MwPortEmpRelService {

    private final Logger log = LoggerFactory.getLogger(MwPortEmpRelService.class);

    private final MwPortEmpRelRepository mwPortEmpRelRepository;

    private final MwEmpRepository mwEmpRepository;

    private final MwAclRepository mwAclRepository;

    private final MwPortRepository mwPortRepository;

    private final MwBrnchEmpRelRepository mwBrnchEmpRelRepository;

    @Autowired
    private final EntityManager em;

    public MwPortEmpRelService(MwPortEmpRelRepository mwPortEmpRelRepository, MwEmpRepository mwEmpRepository,
                               MwAclRepository mwAclRepository, MwPortRepository mwPortRepository, MwBrnchEmpRelRepository mwBrnchEmpRelRepository, EntityManager em) {
        this.mwPortEmpRelRepository = mwPortEmpRelRepository;
        this.mwEmpRepository = mwEmpRepository;
        this.mwAclRepository = mwAclRepository;
        this.mwPortRepository = mwPortRepository;
        this.mwBrnchEmpRelRepository = mwBrnchEmpRelRepository;
        this.em = em;
    }

    /**
     * Save a mwPortEmpRel.
     *
     * @param mwPortEmpRel the entity to save
     * @return the persisted entity
     */
    public MwPortEmpRel save(MwPortEmpRel mwPortEmpRel) {
        log.debug("Request to save MwPortEmpRel : {}", mwPortEmpRel);
        return mwPortEmpRelRepository.save(mwPortEmpRel);
    }

    /**
     * Get all the mwPortEmpRels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwPortEmpRel> findAll(Pageable pageable) {
        log.debug("Request to get all MwPortEmpRels");
        return mwPortEmpRelRepository.findAll(pageable);
    }

    /**
     * Get one mwPortEmpRel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwPortEmpRel findOne(Long id) {
        log.debug("Request to get MwPortEmpRel : {}", id);
        return mwPortEmpRelRepository.findOne(id);
    }

    /**
     * Delete the mwPortEmpRel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwPortEmpRel : {}", id);
        mwPortEmpRelRepository.delete(id);
    }

    public List<MwPortEmpRel> getAssignedEmployeesOfPort(long portSeq) {
        List<MwPortEmpRel> emps = mwPortEmpRelRepository.findAllByPortSeqAndCrntRecFlg(portSeq, true);
        // List<Long> empIds =
        // emps.stream().map(emp->emp.getEmpSeq()).collect(Collectors.toList());
        // List<MwEmp> employees = mwEmpRepository.findAll(empIds);
        // Map<String, Object> map = new HashMap<>();
        //
        // map.put("bdo", employees);
        //
        //
        // List<Long> cempIds =
        // emps.stream().map(emp->emp.getCoveringEmpSeq()).collect(Collectors.toList());
        // List<MwEmp> cemployees = mwEmpRepository.findAll(empIds);
        // map.put("covering", cemployees);
        //
        //
        // map.put("emp", emps);
        return emps;
    }

    @Transactional
    public void assignEmployeesToPort(EmployeeRelationDto dto, String currUser) {

        Instant currIns = Instant.now();

        if (dto.regionManager > 0) {
            MwPortEmpRel empRel = new MwPortEmpRel();
            long seq = SequenceFinder.findNextVal(Sequences.PORT_EMP_SEQ);
            empRel.setPortEmpSeq(seq);
            empRel.setCoveringEmpFromDt((dto.coveringRegManagerDateFrom == null) ? null : dto.coveringRegManagerDateFrom.toInstant());
            empRel.setCoveringEmpSeq(dto.coveringRegManager);
            empRel.setCoveringEmpToDt((dto.coveringRegManagerDateTo == null) ? null : dto.coveringRegManagerDateTo.toInstant());
            empRel.setCrntRecFlg(true);
            empRel.setCrtdBy(currUser);
            empRel.setCrtdDt(currIns);
            empRel.setEffStartDt(currIns);
            empRel.setEmpSeq(dto.regionManager);
            empRel.setLastUpdBy(currUser);
            empRel.setLastUpdDt(currIns);
            empRel.setPortSeq(dto.parentKey);
            mwPortEmpRelRepository.save(empRel);
        }

        if (dto.clerk > 0 && dto.coveringClerk > 0 && dto.coveringClerkDateFrom != null && dto.coveringClerkDateTo != null) {
            MwPortEmpRel empRel = new MwPortEmpRel();
            long seq = SequenceFinder.findNextVal(Sequences.PORT_EMP_SEQ);
            empRel.setPortEmpSeq(seq);
            empRel.setCoveringEmpFromDt(dto.coveringClerkDateFrom.toInstant());
            empRel.setCoveringEmpSeq(dto.coveringClerk);
            empRel.setCoveringEmpToDt(dto.coveringClerkDateTo.toInstant());
            empRel.setCrntRecFlg(true);
            empRel.setCrtdBy(currUser);
            empRel.setCrtdDt(currIns);
            empRel.setEffStartDt(currIns);
            empRel.setEmpSeq(dto.clerk);
            empRel.setLastUpdBy(currUser);
            empRel.setLastUpdDt(currIns);
            empRel.setPortSeq(dto.parentKey);
            mwPortEmpRelRepository.save(empRel);
        }

        if (dto.manager > 0 && dto.coveringManager > 0 && dto.coveringManagerDateFrom != null && dto.coveringManagerDateTo != null) {
            MwPortEmpRel empRel = new MwPortEmpRel();
            long seq = SequenceFinder.findNextVal(Sequences.PORT_EMP_SEQ);
            empRel.setPortEmpSeq(seq);
            empRel.setCoveringEmpFromDt(dto.coveringManagerDateFrom.toInstant());
            empRel.setCoveringEmpSeq(dto.coveringManager);
            empRel.setCoveringEmpToDt(dto.coveringManagerDateTo.toInstant());
            empRel.setCrntRecFlg(true);
            empRel.setCrtdBy(currUser);
            empRel.setCrtdDt(currIns);
            empRel.setEffStartDt(currIns);
            empRel.setEmpSeq(dto.manager);
            empRel.setLastUpdBy(currUser);
            empRel.setLastUpdDt(currIns);
            empRel.setPortSeq(dto.parentKey);
            mwPortEmpRelRepository.save(empRel);
        }
        return;
    }

    public ResponseEntity<Map> addNewPortEmpRel(EmployeeRelationDto dto, String currUser) {
        Map<String, String> resp = new HashMap<String, String>();
        MwPortEmpRel empRel = mwPortEmpRelRepository.findOneByEmpSeqAndCrntRecFlg(dto.regionManager, true);
        if (empRel != null) {
            resp.put("error", "Employee Already assigned to (" + empRel.getPortSeq() + ") Portfolio");
            return ResponseEntity.badRequest().body(resp);
        }

        MwBrnchEmpRel brnchempRel = mwBrnchEmpRelRepository.findOneByEmpSeqAndCrntRecFlg(dto.regionManager, true);
        if (brnchempRel != null) {
            resp.put("error", "Employee Already assigned to (" + brnchempRel.getBrnchSeq() + ") Branch");
            return ResponseEntity.badRequest().body(resp);
        }

        // Added by Zohaib Asim - Dated 16-02-2022
        // Employee cannot be assigned if his/her certification is not completed
        Query query = em.createNativeQuery("SELECT CASE WHEN HH.CERTIFICATION_DATE IS NULL THEN 'F' ELSE 'T' END CAN_PROCEED, hh.CERTIFICATION_DATE\n" +
            " FROM HR.HRIS HH JOIN MW_EMP EMP ON EMP.HRID = HH.EMPLOYEE_ID\n" +
            "WHERE EMP.EMP_SEQ = " + dto.regionManager);
        List<Object[]> listObj = query.getResultList();
        if (listObj.size() > 0 && listObj.get(0)[0].toString().equals("F")) {
            resp.put("error", "Employee didn't pass HR Certificate");
            return ResponseEntity.badRequest().body(resp);
        }
        // End

        MwPortEmpRel rel = mwPortEmpRelRepository.findOneByPortSeqAndCrntRecFlg(dto.parentKey, true);
        Long seq = 0L;
        if (rel != null) {
            // exRel.setCrntRecFlg( false );
            // exRel.setLastUpdBy( currUser );
            // exRel.setLastUpdDt( Instant.now() );
            // exRel.setEffEndDt( Instant.now() );
            // mwPortEmpRelRepository.save( exRel );
            // seq = exRel.getPortEmpSeq();
            //
            MwEmp emp = mwEmpRepository.findOneByEmpSeq(rel.getEmpSeq());
            if (emp != null) {
                MwAcl exAcl = mwAclRepository.findOneByUserIdAndPortSeq(emp.getEmpLanId(), dto.parentKey);
                if (exAcl != null)
                    mwAclRepository.delete(exAcl);
            }
            // } else {
            // seq = SequenceFinder.findNextVal( Sequences.PORT_EMP_REL_SEQ );

        } else {
            seq = SequenceFinder.findNextVal(Sequences.PORT_EMP_SEQ);
            rel = new MwPortEmpRel();
            rel.setCrntRecFlg(true);
            rel.setCrtdBy(currUser);
            rel.setCrtdDt(Instant.now());
            rel.setEffStartDt(Instant.now());
            rel.setPortEmpSeq(seq);

        }
        rel.setCoveringEmpToDt(dto.coveringEmpToDate == null ? null : dto.coveringEmpToDate.toInstant());
        rel.setPortSeq(dto.parentKey);
        rel.setCoveringEmpFromDt(dto.coveringEmpFromDate == null ? null : dto.coveringEmpFromDate.toInstant());
        rel.setCoveringEmpSeq(dto.coveringEmpSeq);
        rel.setEmpSeq(dto.regionManager);
        rel.setLastUpdBy(currUser);
        rel.setLastUpdDt(Instant.now());
        mwPortEmpRelRepository.save(rel);

        MwEmp emp = mwEmpRepository.findOneByEmpSeq(dto.regionManager);
        if (emp != null) {
            MwAcl acl = new MwAcl();
            Long aclSeq = SequenceFinder.findNextVal(Sequences.ACL_SEQ);
            acl.setAclSeq(aclSeq);
            acl.setPortSeq(dto.parentKey);
            acl.setUserId(emp.getEmpLanId());
            mwAclRepository.save(acl);
        }

        resp.put("success", "Relation Saved Successfully !!");
        return ResponseEntity.ok().body(resp);
    }

    public ResponseEntity<Map> removePortEmpRel(EmployeeRelationDto dto, String currUser) {
        Map<String, String> resp = new HashMap<String, String>();
        MwPortEmpRel empRel = mwPortEmpRelRepository.findOneByEmpSeqAndCrntRecFlg(dto.regionManager, true);
        if (empRel != null) {
            empRel.setCrntRecFlg(false);
            empRel.setEffEndDt(Instant.now());
            empRel.setLastUpdBy(currUser);
            empRel.setLastUpdDt(Instant.now());
            mwPortEmpRelRepository.delete(empRel);
            resp.put("success", "Unassigned");
            return ResponseEntity.ok().body(resp);
        }
        resp.put("error", "No Portfolio assignment found");
        return ResponseEntity.badRequest().body(resp);
    }

    public ResponseEntity<Map> removePortEmpRelByPortKey(EmployeeRelationDto dto, String currUser) {
        Map<String, String> resp = new HashMap<String, String>();
        MwPortEmpRel empRel = mwPortEmpRelRepository.findOneByPortSeqAndCrntRecFlg(dto.parentKey, true);
        if (empRel != null) {
            empRel.setCrntRecFlg(false);
            empRel.setEffEndDt(Instant.now());
            empRel.setLastUpdBy(currUser);
            empRel.setLastUpdDt(Instant.now());
            mwPortEmpRelRepository.delete(empRel);
            resp.put("success", "Unassigned");
            return ResponseEntity.ok().body(resp);
        }
        resp.put("error", "No Portfolio assignment found");
        return ResponseEntity.badRequest().body(resp);
    }
}
