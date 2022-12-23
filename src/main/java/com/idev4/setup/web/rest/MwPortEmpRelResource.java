package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPortEmpRel;
import com.idev4.setup.dto.EmployeeRelationDto;
import com.idev4.setup.service.MwPortEmpRelService;
import com.idev4.setup.web.rest.util.HeaderUtil;
import com.idev4.setup.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MwPortEmpRel.
 */
@RestController
@RequestMapping("/api")
public class MwPortEmpRelResource {

    private static final String ENTITY_NAME = "mwPortEmpRel";
    private final Logger log = LoggerFactory.getLogger(MwPortEmpRelResource.class);
    private final MwPortEmpRelService mwPortEmpRelService;

    public MwPortEmpRelResource(MwPortEmpRelService mwPortEmpRelService) {
        this.mwPortEmpRelService = mwPortEmpRelService;
    }

    /**
     * POST /mw-port-emp-rels : Create a new mwPortEmpRel.
     *
     * @param mwPortEmpRel the mwPortEmpRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwPortEmpRel, or with status 400 (Bad Request) if the
     * mwPortEmpRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-port-emp-rels")
    @Timed
    public ResponseEntity<Map> createMwPortEmpRel(@RequestBody EmployeeRelationDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPortEmpRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dto.regionManager == null)
            return mwPortEmpRelService.removePortEmpRelByPortKey(dto, currUser);
        return mwPortEmpRelService.addNewPortEmpRel(dto, currUser);
    }

    /**
     * PUT /mw-port-emp-rels : Updates an existing mwPortEmpRel.
     *
     * @param mwPortEmpRel the mwPortEmpRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwPortEmpRel, or with status 400 (Bad Request) if the
     * mwPortEmpRel is not valid, or with status 500 (Internal Server Error) if the mwPortEmpRel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-port-emp-rels")
    @Timed
    public ResponseEntity<MwPortEmpRel> updateMwPortEmpRel(@RequestBody MwPortEmpRel mwPortEmpRel) throws URISyntaxException {
        log.debug("REST request to update MwPortEmpRel : {}", mwPortEmpRel);
        if (mwPortEmpRel.getPortSeq() == null) {
            return null;
        }
        MwPortEmpRel result = mwPortEmpRelService.save(mwPortEmpRel);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwPortEmpRel.getPortSeq().toString()))
            .body(result);
    }

    /**
     * GET /mw-port-emp-rels : get all the mwPortEmpRels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwPortEmpRels in body
     */
    @GetMapping("/mw-port-emp-rels")
    @Timed
    public ResponseEntity<List<MwPortEmpRel>> getAllMwPortEmpRels(Pageable pageable) {
        log.debug("REST request to get a page of MwPortEmpRels");
        Page<MwPortEmpRel> page = mwPortEmpRelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-port-emp-rels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-port-emp-rels/:id : get the "id" mwPortEmpRel.
     *
     * @param id the id of the mwPortEmpRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwPortEmpRel, or with status 404 (Not Found)
     */
    @GetMapping("/mw-port-emp-rels/{id}")
    @Timed
    public ResponseEntity<MwPortEmpRel> getMwPortEmpRel(@PathVariable Long id) {
        log.debug("REST request to get MwPortEmpRel : {}", id);
        MwPortEmpRel mwPortEmpRel = mwPortEmpRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwPortEmpRel));
    }

    /**
     * DELETE /mw-port-emp-rels/:id : delete the "id" mwPortEmpRel.
     *
     * @param id the id of the mwPortEmpRel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-port-emp-rels/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwPortEmpRel(@PathVariable Long id) {
        log.debug("REST request to delete MwPortEmpRel : {}", id);
        mwPortEmpRelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/get-employees-of-port/{portSeq}")
    @Timed
    public ResponseEntity<List<MwPortEmpRel>> getEmployeesOfport(@PathVariable Long portSeq) {
        log.debug("REST request to get MwRegEmpRel : {}", portSeq);
        List<MwPortEmpRel> mwRegEmpRel = mwPortEmpRelService.getAssignedEmployeesOfPort(portSeq);
        return ResponseEntity.ok().body(mwRegEmpRel);
    }
}
