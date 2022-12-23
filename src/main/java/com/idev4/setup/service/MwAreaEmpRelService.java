package com.idev4.setup.service;

import com.idev4.setup.domain.MwAreaEmpRel;
import com.idev4.setup.dto.EmployeeRelationDto;
import com.idev4.setup.repository.MwAreaEmpRelRepository;
import com.idev4.setup.repository.MwEmpRepository;
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
 * Service Implementation for managing MwAreaEmpRel.
 */
@Service
@Transactional
public class MwAreaEmpRelService {

    private final Logger log = LoggerFactory.getLogger(MwAreaEmpRelService.class);

    private final MwAreaEmpRelRepository mwAreaEmpRelRepository;

    private final MwEmpRepository mwEmpRepository;

    public MwAreaEmpRelService(MwAreaEmpRelRepository mwAreaEmpRelRepository, MwEmpRepository mwEmpRepository) {
        this.mwAreaEmpRelRepository = mwAreaEmpRelRepository;
        this.mwEmpRepository = mwEmpRepository;
    }

    /**
     * Save a mwAreaEmpRel.
     *
     * @param mwAreaEmpRel the entity to save
     * @return the persisted entity
     */
    public MwAreaEmpRel save(MwAreaEmpRel mwAreaEmpRel) {
        log.debug("Request to save MwAreaEmpRel : {}", mwAreaEmpRel);
        return mwAreaEmpRelRepository.save(mwAreaEmpRel);
    }

    /**
     * Get all the mwAreaEmpRels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwAreaEmpRel> findAll(Pageable pageable) {
        log.debug("Request to get all MwAreaEmpRels");
        return mwAreaEmpRelRepository.findAll(pageable);
    }

    /**
     * Get one mwAreaEmpRel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwAreaEmpRel findOne(Long id) {
        log.debug("Request to get MwAreaEmpRel : {}", id);
        return mwAreaEmpRelRepository.findOne(id);
    }

    /**
     * Delete the mwAreaEmpRel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwAreaEmpRel : {}", id);
        mwAreaEmpRelRepository.delete(id);
    }

    public MwAreaEmpRel getAssignedEmployeesOfRegion(long areaSeq) {
        MwAreaEmpRel emp = mwAreaEmpRelRepository.findOneByAreaSeqAndCrntRecFlg(areaSeq, true);
        // List<Long> empIds = emps.stream().map(emp->emp.getEmpSeq()).collect(Collectors.toList());
        // List<MwEmp> employees = mwEmpRepository.findAll(empIds);
        return emp;
    }

    @Transactional
    public boolean assignEmployeesToArea(EmployeeRelationDto dto, String currUser) {

        Instant currIns = Instant.now();
        MwAreaEmpRel exRel = mwAreaEmpRelRepository.findOneByAreaSeqAndCrntRecFlg(dto.parentKey, true);
        long seq = 0;
        if (exRel != null) {
            exRel.setCrntRecFlg(false);
            exRel.setLastUpdBy(currUser);
            exRel.setLastUpdDt(currIns);
            seq = exRel.getAreaEmpSeq();
            mwAreaEmpRelRepository.save(exRel);
        } else {
            seq = SequenceFinder.findNextVal(Sequences.AREA_EMP_REL_SEQ);
        }
        if (dto.regionManager != null) {
            MwAreaEmpRel empRel = new MwAreaEmpRel();
            empRel.setAreaEmpSeq(seq);
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
            empRel.setAreaSeq(dto.parentKey);
            mwAreaEmpRelRepository.save(empRel);
        }

        // if(dto.clerk>0 && dto.coveringClerk>0 && dto.coveringClerkDateFrom!=null && dto.coveringClerkDateTo!=null){
        // MwAreaEmpRel empRel = new MwAreaEmpRel();
        // long seq = SequenceFinder.findNextVal(Sequences.AREA_EMP_REL_SEQ);
        // empRel.setAreaEmpSeq(seq);
        // empRel.setCoveringEmpFromDt(dto.coveringClerkDateFrom.toInstant());
        // empRel.setCoveringEmpSeq(dto.coveringClerk);
        // empRel.setCoveringEmpToDt(dto.coveringClerkDateTo.toInstant());
        // empRel.setCrntRecFlg(true);s
        // empRel.setCrtdBy(currUser);
        // empRel.setCrtdDt(currIns);
        // empRel.setEffStartDt(currIns);
        // empRel.setEmpSeq(dto.clerk);
        // empRel.setLastUpdBy(currUser);
        // empRel.setLastUpdDt(currIns);
        // empRel.setAreaSeq(dto.parentKey);
        // mwAreaEmpRelRepository.save(empRel);
        // }
        //
        // if(dto.manager>0 && dto.coveringManager>0 && dto.coveringManagerDateFrom!=null && dto.coveringManagerDateTo!=null){
        // MwAreaEmpRel empRel = new MwAreaEmpRel();
        // long seq = SequenceFinder.findNextVal(Sequences.AREA_EMP_REL_SEQ);
        // empRel.setAreaEmpSeq(seq);
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
        // empRel.setAreaSeq(dto.parentKey);
        // mwAreaEmpRelRepository.save(empRel);
        // }
        return true;
    }
}
