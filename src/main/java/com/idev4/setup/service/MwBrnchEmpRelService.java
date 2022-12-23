package com.idev4.setup.service;

import com.idev4.setup.domain.MwAcl;
import com.idev4.setup.domain.MwBrnchEmpRel;
import com.idev4.setup.domain.MwEmp;
import com.idev4.setup.domain.MwPort;
import com.idev4.setup.dto.EmployeeRelationDto;
import com.idev4.setup.repository.MwAclRepository;
import com.idev4.setup.repository.MwBrnchEmpRelRepository;
import com.idev4.setup.repository.MwEmpRepository;
import com.idev4.setup.repository.MwPortRepository;
import com.idev4.setup.web.rest.util.Common;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing MwBrnchEmpRel.
 */
@Service
@Transactional
public class MwBrnchEmpRelService {

    private final Logger log = LoggerFactory.getLogger(MwBrnchEmpRelService.class);

    private final MwBrnchEmpRelRepository mwBrnchEmpRelRepository;

    private final MwEmpRepository mwEmpRepository;

    private final MwAclRepository mwAclRepository;

    private final MwPortRepository mwPortRepository;

    public MwBrnchEmpRelService(MwBrnchEmpRelRepository mwBrnchEmpRelRepository, MwEmpRepository mwEmpRepository,
                                MwAclRepository mwAclRepository, MwPortRepository mwPortRepository) {
        this.mwBrnchEmpRelRepository = mwBrnchEmpRelRepository;
        this.mwEmpRepository = mwEmpRepository;
        this.mwAclRepository = mwAclRepository;
        this.mwPortRepository = mwPortRepository;
    }

    /**
     * Save a mwBrnchEmpRel.
     *
     * @param mwBrnchEmpRel the entity to save
     * @return the persisted entity
     */
    public MwBrnchEmpRel save(MwBrnchEmpRel mwBrnchEmpRel) {
        log.debug("Request to save MwBrnchEmpRel : {}", mwBrnchEmpRel);
        return mwBrnchEmpRelRepository.save(mwBrnchEmpRel);
    }

    /**
     * Get all the mwBrnchEmpRels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwBrnchEmpRel> findAll(Pageable pageable) {
        log.debug("Request to get all MwBrnchEmpRels");
        return mwBrnchEmpRelRepository.findAll(pageable);
    }

    /**
     * Get one mwBrnchEmpRel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwBrnchEmpRel findOne(Long id) {
        log.debug("Request to get MwBrnchEmpRel : {}", id);
        return mwBrnchEmpRelRepository.findOne(id);
    }

    /**
     * Delete the mwBrnchEmpRel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwBrnchEmpRel : {}", id);
        mwBrnchEmpRelRepository.delete(id);
    }

    public List<MwBrnchEmpRel> getAssignedEmployeesOfRegion(long branchSeq) {
        List<MwBrnchEmpRel> emps = mwBrnchEmpRelRepository.findAllByBrnchSeqAndCrntRecFlg(branchSeq, true);
        // List<Long> empIds = emps.stream().map(emp->emp.getEmpSeq()).collect(Collectors.toList());
        // List<MwEmp> employees = mwEmpRepository.findAll(empIds);
        return emps;
    }

    @Transactional
    public void assignEmployeesToBranch(EmployeeRelationDto dto, String currUser) {

        Instant currIns = Instant.now();

        if (dto.regionManager > 0 && dto.coveringRegManager > 0 && dto.coveringRegManagerDateFrom != null
            && dto.coveringRegManagerDateTo != null) {
            MwBrnchEmpRel empRel = new MwBrnchEmpRel();
            long seq = SequenceFinder.findNextVal(Sequences.BRNCH_EMP_REL_SEQ);
            empRel.setBrnchEmpSeq(seq);
            empRel.setCoveringEmpFromDt(dto.coveringRegManagerDateFrom.toInstant());
            empRel.setCoveringEmpSeq(dto.coveringRegManager);
            empRel.setCoveringEmpToDt(dto.coveringRegManagerDateTo.toInstant());
            empRel.setCrntRecFlg(true);
            empRel.setCrtdBy(currUser);
            empRel.setCrtdDt(currIns);
            empRel.setEffStartDt(currIns);
            empRel.setEmpSeq(dto.regionManager);
            empRel.setLastUpdBy(currUser);
            empRel.setLastUpdDt(currIns);
            empRel.setBrnchSeq(dto.parentKey);
            empRel.setDelFlg(false);
            mwBrnchEmpRelRepository.save(empRel);
        }

        if (dto.clerk > 0 && dto.coveringClerk > 0 && dto.coveringClerkDateFrom != null && dto.coveringClerkDateTo != null) {
            MwBrnchEmpRel empRel = new MwBrnchEmpRel();
            long seq = SequenceFinder.findNextVal(Sequences.BRNCH_EMP_REL_SEQ);
            empRel.setBrnchEmpSeq(seq);
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
            empRel.setBrnchSeq(dto.parentKey);
            empRel.setDelFlg(false);
            mwBrnchEmpRelRepository.save(empRel);
        }

        if (dto.manager > 0 && dto.coveringManager > 0 && dto.coveringManagerDateFrom != null && dto.coveringManagerDateTo != null) {
            MwBrnchEmpRel empRel = new MwBrnchEmpRel();
            long seq = SequenceFinder.findNextVal(Sequences.BRNCH_EMP_REL_SEQ);
            empRel.setBrnchEmpSeq(seq);
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
            empRel.setBrnchSeq(dto.parentKey);
            empRel.setDelFlg(false);
            mwBrnchEmpRelRepository.save(empRel);
        }
        return;
    }

    public MwBrnchEmpRel addNewBrnchEmpRel(EmployeeRelationDto dto, String currUser) {
        MwBrnchEmpRel exRel = mwBrnchEmpRelRepository.findOneByBrnchSeqAndCrntRecFlgAndDelFlg(dto.brnchSeq, true, false);
        long seq = 0L;
        if (exRel != null) {
            exRel.setCrntRecFlg(false);
            exRel.setLastUpdBy(currUser);
            exRel.setLastUpdDt(Instant.now());
            exRel.setDelFlg(true);
            mwBrnchEmpRelRepository.save(exRel);
            seq = exRel.getBrnchEmpSeq();

            MwEmp emp = mwEmpRepository.findOneByEmpSeq(exRel.getEmpSeq());
            if (emp != null) {
                List<MwPort> ports = mwPortRepository.findAllByBrnchSeqAndCrntRecFlg(dto.brnchSeq, true);
                List<Long> portSeqs = new ArrayList<>();
                ports.forEach(port -> {
                    portSeqs.add(port.getPortSeq());
                });
                List<MwAcl> exAcls = mwAclRepository.findAllByUserIdAndPortSeqIn(emp.getEmpLanId(), portSeqs);
                mwAclRepository.delete(exAcls);
            }
        } else {
            seq = SequenceFinder.findNextVal(Sequences.BRNCH_EMP_REL_SEQ);
            List<MwPort> ports = mwPortRepository.findAllByBrnchSeqAndCrntRecFlg(dto.brnchSeq, true);
            List<MwAcl> acls = new ArrayList<>();
            ports.forEach(port -> {
                MwAcl acl = new MwAcl();
                Long aclSeq = SequenceFinder.findNextVal(Sequences.ACL_SEQ);
                acl.setAclSeq(aclSeq);
                acl.setPortSeq(port.getPortSeq());
                acl.setUserId(Common.adminRoleLanId);
                acls.add(acl);
            });
            mwAclRepository.save(acls);
        }
        MwBrnchEmpRel rel = new MwBrnchEmpRel();
        rel.setBrnchEmpSeq(seq);
        rel.setBrnchSeq(dto.brnchSeq);
        rel.setCoveringEmpFromDt(dto.coveringEmpFromDate == null ? null : dto.coveringEmpFromDate.toInstant());
        rel.setCoveringEmpSeq(dto.coveringEmpSeq);
        rel.setCoveringEmpToDt(dto.coveringEmpToDate == null ? null : dto.coveringEmpToDate.toInstant());
        rel.setCrntRecFlg(true);
        rel.setCrtdBy(currUser);
        rel.setCrtdDt(Instant.now());
        rel.setEffStartDt(Instant.now());
        rel.setEmpSeq(dto.empSeq);
        rel.setLastUpdBy(currUser);
        rel.setLastUpdDt(Instant.now());
        rel.setDelFlg(false);
        mwBrnchEmpRelRepository.save(rel);

        MwEmp emp = mwEmpRepository.findOneByEmpSeq(dto.empSeq);
        if (emp != null) {
            List<MwPort> ports = mwPortRepository.findAllByBrnchSeqAndCrntRecFlg(dto.brnchSeq, true);
            List<MwAcl> acls = new ArrayList<>();
            ports.forEach(port -> {
                MwAcl acl = new MwAcl();
                Long aclSeq = SequenceFinder.findNextVal(Sequences.ACL_SEQ);
                acl.setAclSeq(aclSeq);
                acl.setPortSeq(port.getPortSeq());
                acl.setUserId(emp.getEmpLanId());
                acls.add(acl);
            });
            mwAclRepository.save(acls);
        }
        return rel;
    }

}
