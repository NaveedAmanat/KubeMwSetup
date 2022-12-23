package com.idev4.setup.service;

import com.idev4.setup.domain.MwRegEmpRel;
import com.idev4.setup.dto.EmployeeRelationDto;
import com.idev4.setup.repository.MwEmpRepository;
import com.idev4.setup.repository.MwRegEmpRelRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing MwRegEmpRel.
 */
@Service
@Transactional
public class MwRegEmpRelService {

    private final Logger log = LoggerFactory.getLogger(MwRegEmpRelService.class);

    private final MwRegEmpRelRepository mwRegEmpRelRepository;

    private final MwEmpRepository mwEmpRepository;

    public MwRegEmpRelService(MwRegEmpRelRepository mwRegEmpRelRepository, MwEmpRepository mwEmpRepository) {
        this.mwRegEmpRelRepository = mwRegEmpRelRepository;
        this.mwEmpRepository = mwEmpRepository;
    }

    /**
     * Save a mwRegEmpRel.
     *
     * @param mwRegEmpRel the entity to save
     * @return the persisted entity
     */
    public MwRegEmpRel save(MwRegEmpRel mwRegEmpRel) {
        log.debug("Request to save MwRegEmpRel : {}", mwRegEmpRel);
        return mwRegEmpRelRepository.save(mwRegEmpRel);
    }

    /**
     * Get all the mwRegEmpRels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwRegEmpRel> findAll(Pageable pageable) {
        log.debug("Request to get all MwRegEmpRels");
        return mwRegEmpRelRepository.findAll(pageable);
    }

    /**
     * Get one mwRegEmpRel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwRegEmpRel findOne(Long id) {
        log.debug("Request to get MwRegEmpRel : {}", id);
        return mwRegEmpRelRepository.findOne(id);
    }

    /**
     * Delete the mwRegEmpRel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwRegEmpRel : {}", id);
        mwRegEmpRelRepository.delete(id);
    }

    public MwRegEmpRel getAssignedEmployeesOfRegion(long regSeq) {
        MwRegEmpRel emp = mwRegEmpRelRepository.findOneByRegSeqAndCrntRecFlg(regSeq, true);
        /*List<Long> empIds = emps.stream().map(emp->emp.getEmpSeq()).collect(Collectors.toList());
        List<Long> cvrEmpIds = emps.stream().map(emp->emp.getCoveringEmpSeq()).collect(Collectors.toList());
        List<MwEmp> employees = mwEmpRepository.findAll(empIds);
        List<MwEmp> cvrEmployees = mwEmpRepository.findAll(cvrEmpIds);*/
        return emp;
    }

    @Transactional
    public void assignEmployeesToRegion(EmployeeRelationDto dto, String currUser) {

        Instant currIns = Instant.now();
        MwRegEmpRel exRel = mwRegEmpRelRepository.findOneByRegSeqAndCrntRecFlg(dto.parentKey, true);
        long seq = 0;
        if (exRel != null) {
            exRel.setCrntRecFlg(false);
            exRel.setLastUpdBy(currUser);
            exRel.setLastUpdDt(currIns);
            seq = exRel.getRegEmpSeq();
            mwRegEmpRelRepository.save(exRel);
        } else {
            seq = SequenceFinder.findNextVal(Sequences.REG_EMP_REL_SEQ);
        }
        if (dto.regionManager != null) {
            MwRegEmpRel empRel = new MwRegEmpRel();
            empRel.setRegEmpSeq(seq);
            // empRel.setCoveringEmpFromDt(dto.coveringRegManagerDateFrom.toInstant());
            // empRel.setCoveringEmpSeq(dto.coveringRegManager);
            // empRel.setCoveringEmpToDt(dto.coveringRegManagerDateTo.toInstant());
            empRel.setCrntRecFlg(true);
            empRel.setCrtdBy(currUser);
            empRel.setCrtdDt(currIns);
            empRel.setEffStartDt(currIns);
            empRel.setEmpSeq(dto.regionManager);
            empRel.setLastUpdBy(currUser);
            empRel.setLastUpdDt(currIns);
            empRel.setRegSeq(dto.parentKey);
            mwRegEmpRelRepository.save(empRel);
        }
        // if(dto.regionManager>0 && dto.coveringRegManager>0 && dto.coveringRegManagerDateFrom!=null &&
        // dto.coveringRegManagerDateTo!=null){
        // MwRegEmpRel empRel = new MwRegEmpRel();
        // long seq = SequenceFinder.findNextVal(Sequences.REG_EMP_REL_SEQ);
        // empRel.setRegEmpSeq(seq);
        // empRel.setCoveringEmpFromDt(dto.coveringRegManagerDateFrom.toInstant());
        // empRel.setCoveringEmpSeq(dto.coveringRegManager);
        // empRel.setCoveringEmpToDt(dto.coveringRegManagerDateTo.toInstant());
        // empRel.setCrntRecFlg(true);
        // empRel.setCrtdBy(currUser);
        // empRel.setCrtdDt(currIns);
        // empRel.setEffStartDt(currIns);
        // empRel.setEmpSeq(dto.regionManager);
        // empRel.setLastUpdBy(currUser);
        // empRel.setLastUpdDt(currIns);
        // empRel.setRegSeq(dto.parentKey);
        // mwRegEmpRelRepository.save(empRel);
        // }
        //
        // if(dto.clerk>0 && dto.coveringClerk>0 && dto.coveringClerkDateFrom!=null && dto.coveringClerkDateTo!=null){
        // MwRegEmpRel empRel = new MwRegEmpRel();
        // long seq = SequenceFinder.findNextVal(Sequences.REG_EMP_REL_SEQ);
        // empRel.setRegEmpSeq(seq);
        // empRel.setCoveringEmpFromDt(dto.coveringClerkDateFrom.toInstant());
        // empRel.setCoveringEmpSeq(dto.coveringClerk);
        // empRel.setCoveringEmpToDt(dto.coveringClerkDateTo.toInstant());
        // empRel.setCrntRecFlg(true);
        // empRel.setCrtdBy(currUser);
        // empRel.setCrtdDt(currIns);
        // empRel.setEffStartDt(currIns);
        // empRel.setEmpSeq(dto.clerk);
        // empRel.setLastUpdBy(currUser);
        // empRel.setLastUpdDt(currIns);
        // empRel.setRegSeq(dto.parentKey);
        // mwRegEmpRelRepository.save(empRel);
        // }
        //
        // if(dto.manager>0 && dto.coveringManager>0 && dto.coveringManagerDateFrom!=null && dto.coveringManagerDateTo!=null){
        // MwRegEmpRel empRel = new MwRegEmpRel();
        // long seq = SequenceFinder.findNextVal(Sequences.REG_EMP_REL_SEQ);
        // empRel.setRegEmpSeq(seq);
        // empRel.setCoveringEmpFromDt(dto.coveringManagerDateFrom.toInstant());
        // empRel.setCoveringEmpSeq(dto.coveringManager);
        // empRel.setCoveringEmpToDt(dto.coveringManagerDateTo.toInstant());
        // empRel.setCrntRecFlg(true);
        // empRel.setCrtdBy(currUser);
        // empRel.setCrtdDt(currIns);
        // empRel.setEffStartDt(currIns);
        // empRel.setEmpSeq(dto.manager);
        // empRel.setLastUpdBy(currUser);
        // empRel.setLastUpdDt(currIns);
        // empRel.setRegSeq(dto.parentKey);
        // mwRegEmpRelRepository.save(empRel);
        // }
        return;
    }
}
