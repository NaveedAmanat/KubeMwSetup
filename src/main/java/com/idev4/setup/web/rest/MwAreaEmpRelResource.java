package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwAreaEmpRel;
import com.idev4.setup.dto.EmployeeRelationDto;
import com.idev4.setup.service.MwAreaEmpRelService;
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
 * REST controller for managing MwAreaEmpRel.
 */
@RestController
@RequestMapping("/api")
public class MwAreaEmpRelResource {

    private static final String ENTITY_NAME = "mwAreaEmpRel";
    private final Logger log = LoggerFactory.getLogger(MwAreaEmpRelResource.class);
    private final MwAreaEmpRelService mwAreaEmpRelService;

    public MwAreaEmpRelResource(MwAreaEmpRelService mwAreaEmpRelService) {
        this.mwAreaEmpRelService = mwAreaEmpRelService;
    }

    /**
     * POST /mw-area-emp-rels : Create a new mwAreaEmpRel.
     *
     * @param mwAreaEmpRel the mwAreaEmpRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwAreaEmpRel, or with status 400 (Bad Request) if the
     * mwAreaEmpRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-area-emp-rels")
    @Timed
    public ResponseEntity<Map> createMwAreaEmpRel(@RequestBody EmployeeRelationDto dto) throws URISyntaxException {
        log.debug("REST request to save MwAreaEmpRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // if ( dto.regionManager > 0 ) {
        // resp.put( "error", "Area Manager Not" );
        // ResponseEntity.badRequest().body( resp );
        //// if ( dto.coveringRegManagerDateFrom == null || dto.coveringRegManagerDateTo == null ) {
        //// resp.put( "error", "Missing Data for Region Manager Entries !!" );
        //// ResponseEntity.badRequest().body( resp );
        //// }
        // }

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

        mwAreaEmpRelService.assignEmployeesToArea(dto, currUser);
        resp.put("data", "Relation Saved Successfully !!");

        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT /mw-area-emp-rels : Updates an existing mwAreaEmpRel.
     *
     * @param mwAreaEmpRel the mwAreaEmpRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwAreaEmpRel, or with status 400 (Bad Request) if the
     * mwAreaEmpRel is not valid, or with status 500 (Internal Server Error) if the mwAreaEmpRel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-area-emp-rels")
    @Timed
    public ResponseEntity<MwAreaEmpRel> updateMwAreaEmpRel(@RequestBody MwAreaEmpRel mwAreaEmpRel) throws URISyntaxException {
        log.debug("REST request to update MwAreaEmpRel : {}", mwAreaEmpRel);
        if (mwAreaEmpRel.getAreaSeq() == null) {
            return null;
        }
        MwAreaEmpRel result = mwAreaEmpRelService.save(mwAreaEmpRel);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwAreaEmpRel.getAreaSeq().toString()))
            .body(result);
    }

    /**
     * GET /mw-area-emp-rels : get all the mwAreaEmpRels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwAreaEmpRels in body
     */
    @GetMapping("/mw-area-emp-rels")
    @Timed
    public ResponseEntity<List<MwAreaEmpRel>> getAllMwAreaEmpRels(Pageable pageable) {
        log.debug("REST request to get a page of MwAreaEmpRels");
        Page<MwAreaEmpRel> page = mwAreaEmpRelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-area-emp-rels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-area-emp-rels/:id : get the "id" mwAreaEmpRel.
     *
     * @param id the id of the mwAreaEmpRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwAreaEmpRel, or with status 404 (Not Found)
     */
    @GetMapping("/mw-area-emp-rels/{id}")
    @Timed
    public ResponseEntity<MwAreaEmpRel> getMwAreaEmpRel(@PathVariable Long id) {
        log.debug("REST request to get MwAreaEmpRel : {}", id);
        MwAreaEmpRel mwAreaEmpRel = mwAreaEmpRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwAreaEmpRel));
    }

    /**
     * DELETE /mw-area-emp-rels/:id : delete the "id" mwAreaEmpRel.
     *
     * @param id the id of the mwAreaEmpRel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-area-emp-rels/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwAreaEmpRel(@PathVariable Long id) {
        log.debug("REST request to delete MwAreaEmpRel : {}", id);
        mwAreaEmpRelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/get-employees-of-area/{areaSeq}")
    @Timed
    public ResponseEntity<MwAreaEmpRel> getEmployeesOfArea(@PathVariable Long areaSeq) {
        log.debug("REST request to get MwRegEmpRel : {}", areaSeq);
        MwAreaEmpRel mwRegEmpRel = mwAreaEmpRelService.getAssignedEmployeesOfRegion(areaSeq);
        return ResponseEntity.ok().body(mwRegEmpRel);
    }
}
