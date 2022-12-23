package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwRegEmpRel;
import com.idev4.setup.dto.EmployeeRelationDto;
import com.idev4.setup.service.MwRegEmpRelService;
import com.idev4.setup.web.rest.errors.BadRequestAlertException;
import com.idev4.setup.web.rest.util.HeaderUtil;
import com.idev4.setup.web.rest.util.PaginationUtil;
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

/**
 * REST controller for managing MwRegEmpRel.
 */
@RestController
@RequestMapping("/api")
public class MwRegEmpRelResource {

    private static final String ENTITY_NAME = "mwRegEmpRel";
    private final Logger log = LoggerFactory.getLogger(MwRegEmpRelResource.class);
    private final MwRegEmpRelService mwRegEmpRelService;

    public MwRegEmpRelResource(MwRegEmpRelService mwRegEmpRelService) {
        this.mwRegEmpRelService = mwRegEmpRelService;
    }

    /**
     * POST /mw-reg-emp-rels : Create a new mwRegEmpRel.
     *
     * @param mwRegEmpRel the mwRegEmpRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwRegEmpRel, or with status 400 (Bad Request) if the
     * mwRegEmpRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-reg-emp-rels")
    @Timed
    public ResponseEntity<Map> createMwRegEmpRel(@RequestBody EmployeeRelationDto dto) throws URISyntaxException {
        log.debug("REST request to save MwRegEmpRel : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // if(dto.regionManager>0){
        // if(dto.coveringRegManagerDateFrom==null || dto.coveringRegManagerDateTo==null){
        // resp.put("error", "Missing Data for Region Manager Entries !!");
        // ResponseEntity.badRequest().body(resp);
        // }
        // }
        //
        // if(dto.manager>0){
        // if(dto.coveringManagerDateFrom==null || dto.coveringManagerDateTo==null){
        // resp.put("error", "Missing Data for 2nd Region Manager Entries !!");
        // ResponseEntity.badRequest().body(resp);
        // }
        // }
        //
        // if(dto.clerk>0){
        // if(dto.coveringClerkDateFrom==null || dto.coveringClerkDateTo==null){
        // resp.put("error", "Missing Data for Clerk Entries !!");
        // ResponseEntity.badRequest().body(resp);
        // }
        // }

        mwRegEmpRelService.assignEmployeesToRegion(dto, currUser);
        resp.put("data", "Relation Saved Successfully !!");

        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT /mw-reg-emp-rels : Updates an existing mwRegEmpRel.
     *
     * @param mwRegEmpRel the mwRegEmpRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwRegEmpRel, or with status 400 (Bad Request) if the
     * mwRegEmpRel is not valid, or with status 500 (Internal Server Error) if the mwRegEmpRel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-reg-emp-rels")
    @Timed
    public ResponseEntity<MwRegEmpRel> updateMwRegEmpRel(@RequestBody MwRegEmpRel mwRegEmpRel) throws URISyntaxException {
        log.debug("REST request to update MwRegEmpRel : {}", mwRegEmpRel);
        if (mwRegEmpRel.getRegEmpSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwRegEmpRel result = mwRegEmpRelService.save(mwRegEmpRel);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwRegEmpRel.getRegEmpSeq().toString()))
            .body(result);
    }

    /**
     * GET /mw-reg-emp-rels : get all the mwRegEmpRels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwRegEmpRels in body
     */
    @GetMapping("/mw-reg-emp-rels")
    @Timed
    public ResponseEntity<List<MwRegEmpRel>> getAllMwRegEmpRels(Pageable pageable) {
        log.debug("REST request to get a page of MwRegEmpRels");
        Page<MwRegEmpRel> page = mwRegEmpRelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-reg-emp-rels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-reg-emp-rels/:id : get the "id" mwRegEmpRel.
     *
     * @param id the id of the mwRegEmpRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwRegEmpRel, or with status 404 (Not Found)
     */
    @GetMapping("/mw-reg-emp-rels/{id}")
    @Timed
    public ResponseEntity<MwRegEmpRel> getMwRegEmpRel(@PathVariable Long id) {
        log.debug("REST request to get MwRegEmpRel : {}", id);
        MwRegEmpRel mwRegEmpRel = mwRegEmpRelService.findOne(id);
        return ResponseEntity.ok().body(mwRegEmpRel);
    }

    /**
     * DELETE /mw-reg-emp-rels/:id : delete the "id" mwRegEmpRel.
     *
     * @param id the id of the mwRegEmpRel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-reg-emp-rels/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwRegEmpRel(@PathVariable Long id) {
        log.debug("REST request to delete MwRegEmpRel : {}", id);
        mwRegEmpRelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/get-employees-of-region/{regSeq}")
    @Timed
    public ResponseEntity<MwRegEmpRel> getEmployeesOfRegion(@PathVariable Long regSeq) {
        log.debug("REST request to get MwRegEmpRel : {}", regSeq);
        MwRegEmpRel mwRegEmpRel = mwRegEmpRelService.getAssignedEmployeesOfRegion(regSeq);
        return ResponseEntity.ok().body(mwRegEmpRel);
    }
}
