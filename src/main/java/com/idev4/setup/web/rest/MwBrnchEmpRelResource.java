package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwBrnchEmpRel;
import com.idev4.setup.dto.EmployeeRelationDto;
import com.idev4.setup.service.MwBrnchEmpRelService;
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
 * REST controller for managing MwBrnchEmpRel.
 */
@RestController
@RequestMapping("/api")
public class MwBrnchEmpRelResource {

    private static final String ENTITY_NAME = "mwBrnchEmpRel";
    private final Logger log = LoggerFactory.getLogger(MwBrnchEmpRelResource.class);
    private final MwBrnchEmpRelService mwBrnchEmpRelService;

    public MwBrnchEmpRelResource(MwBrnchEmpRelService mwBrnchEmpRelService) {
        this.mwBrnchEmpRelService = mwBrnchEmpRelService;
    }

    /**
     * POST /mw-brnch-emp-rels : Create a new mwBrnchEmpRel.
     *
     * @param mwBrnchEmpRel the mwBrnchEmpRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwBrnchEmpRel, or with status 400 (Bad Request) if the
     * mwBrnchEmpRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-brnch-emp-rels")
    @Timed
    public ResponseEntity<Map> createMwBrnchEmpRel(@RequestBody EmployeeRelationDto dto) throws URISyntaxException {
        log.debug("REST request to save MwBrnchEmpRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (dto.regionManager > 0) {
            if (dto.coveringRegManagerDateFrom == null || dto.coveringRegManagerDateTo == null) {
                resp.put("error", "Missing Data for Region Manager Entries !!");
                ResponseEntity.badRequest().body(resp);
            }
        }

        if (dto.manager > 0) {
            if (dto.coveringManagerDateFrom == null || dto.coveringManagerDateTo == null) {
                resp.put("error", "Missing Data for 2nd Region Manager Entries !!");
                ResponseEntity.badRequest().body(resp);
            }
        }

        if (dto.clerk > 0) {
            if (dto.coveringClerkDateFrom == null || dto.coveringClerkDateTo == null) {
                resp.put("error", "Missing Data for Clerk Entries !!");
                ResponseEntity.badRequest().body(resp);
            }
        }

        mwBrnchEmpRelService.assignEmployeesToBranch(dto, currUser);
        resp.put("data", "Relation Saved Successfully !!");

        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT /mw-brnch-emp-rels : Updates an existing mwBrnchEmpRel.
     *
     * @param mwBrnchEmpRel the mwBrnchEmpRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwBrnchEmpRel, or with status 400 (Bad Request) if the
     * mwBrnchEmpRel is not valid, or with status 500 (Internal Server Error) if the mwBrnchEmpRel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-brnch-emp-rels")
    @Timed
    public ResponseEntity<MwBrnchEmpRel> updateMwBrnchEmpRel(@RequestBody MwBrnchEmpRel mwBrnchEmpRel) throws URISyntaxException {
        log.debug("REST request to update MwBrnchEmpRel : {}", mwBrnchEmpRel);
        if (mwBrnchEmpRel.getBrnchSeq() == null) {
            return null;
        }
        MwBrnchEmpRel result = mwBrnchEmpRelService.save(mwBrnchEmpRel);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwBrnchEmpRel.getBrnchSeq().toString()))
            .body(result);
    }

    /**
     * GET /mw-brnch-emp-rels : get all the mwBrnchEmpRels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwBrnchEmpRels in body
     */
    @GetMapping("/mw-brnch-emp-rels")
    @Timed
    public ResponseEntity<List<MwBrnchEmpRel>> getAllMwBrnchEmpRels(Pageable pageable) {
        log.debug("REST request to get a page of MwBrnchEmpRels");
        Page<MwBrnchEmpRel> page = mwBrnchEmpRelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-brnch-emp-rels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-brnch-emp-rels/:id : get the "id" mwBrnchEmpRel.
     *
     * @param id the id of the mwBrnchEmpRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwBrnchEmpRel, or with status 404 (Not Found)
     */
    @GetMapping("/mw-brnch-emp-rels/{id}")
    @Timed
    public ResponseEntity<MwBrnchEmpRel> getMwBrnchEmpRel(@PathVariable Long id) {
        log.debug("REST request to get MwBrnchEmpRel : {}", id);
        MwBrnchEmpRel mwBrnchEmpRel = mwBrnchEmpRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBrnchEmpRel));
    }

    /**
     * DELETE /mw-brnch-emp-rels/:id : delete the "id" mwBrnchEmpRel.
     *
     * @param id the id of the mwBrnchEmpRel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-brnch-emp-rels/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwBrnchEmpRel(@PathVariable Long id) {
        log.debug("REST request to delete MwBrnchEmpRel : {}", id);
        mwBrnchEmpRelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/get-employees-of-branch/{brnchSeq}")
    @Timed
    public ResponseEntity<List<MwBrnchEmpRel>> getEmployeesOfbrnch(@PathVariable Long brnchSeq) {
        log.debug("REST request to get MwRegEmpRel : {}", brnchSeq);
        List<MwBrnchEmpRel> mwRegEmpRel = mwBrnchEmpRelService.getAssignedEmployeesOfRegion(brnchSeq);
        return ResponseEntity.ok().body(mwRegEmpRel);
    }

    @PostMapping("/add-mw-brnch-emp-rel")
    @Timed
    public ResponseEntity<MwBrnchEmpRel> addMwBrnchEmpRel(@RequestBody EmployeeRelationDto dto) throws URISyntaxException {
        return ResponseEntity.ok()
            .body(mwBrnchEmpRelService.addNewBrnchEmpRel(dto, SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}
