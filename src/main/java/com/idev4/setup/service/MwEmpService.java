package com.idev4.setup.service;

import com.idev4.setup.domain.MwEmp;
import com.idev4.setup.repository.MwEmpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service Implementation for managing MwEmp.
 */
@Service
@Transactional
public class MwEmpService {

    private final Logger log = LoggerFactory.getLogger(MwEmpService.class);

    private final MwEmpRepository mwEmpRepository;

    public MwEmpService(MwEmpRepository mwEmpRepository) {
        this.mwEmpRepository = mwEmpRepository;
    }

    /**
     * Save a mwEmp.
     *
     * @param mwEmp the entity to save
     * @return the persisted entity
     */
    public MwEmp save(MwEmp mwEmp) {
        log.debug("Request to save MwEmp : {}", mwEmp);
        return mwEmpRepository.save(mwEmp);
    }

    /**
     * Get all the mwEmps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwEmp> findAll(Pageable pageable) {
        log.debug("Request to get all MwEmps");
        return mwEmpRepository.findAll(pageable);
    }

    public List<MwEmp> findAll() {
        return mwEmpRepository.findAll();
    }

    /**
     * Get one mwEmp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwEmp findOne(Long id) {
        log.debug("Request to get MwEmp : {}", id);
        return mwEmpRepository.findOne(id);
    }

    /**
     * Delete the mwEmp by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwEmp : {}", id);
        mwEmpRepository.delete(id);
    }

    public List<MwEmp> searchEmployeesFromDbByChars(String chars) {
        return mwEmpRepository.findAllByEmpNmContaining(chars);
    }

    public List<MwEmp> getBrnchEmployees(Long brnchSeq, int typ) {
        List<String> postionIds = new ArrayList<>();
        if (typ == 1) {
            postionIds = new ArrayList<String>(Arrays.asList("015", "032", "056", "063", "084", "064", "024", "177", "178"));
        } else {
            postionIds = new ArrayList<String>(Arrays.asList("258", "224", "225"));
        }
        return mwEmpRepository.findEmpForBranch(brnchSeq, postionIds);
    }
}
