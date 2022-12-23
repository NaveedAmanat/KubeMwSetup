package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwEmp;
import com.idev4.setup.service.MwEmpService;
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
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MwEmp.
 */
@RestController
@RequestMapping("/api")
public class MwEmpResource {

    private static final String ENTITY_NAME = "mwEmp";
    private final Logger log = LoggerFactory.getLogger(MwEmpResource.class);
    private final MwEmpService mwEmpService;

    public MwEmpResource(MwEmpService mwEmpService) {
        this.mwEmpService = mwEmpService;
    }

    /**
     * POST /mw-emps : Create a new mwEmp.
     *
     * @param mwEmp the mwEmp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwEmp, or with status 400 (Bad Request) if the mwEmp has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-emps")
    @Timed
    public ResponseEntity<MwEmp> createMwEmp(@RequestBody MwEmp mwEmp) throws URISyntaxException {
        log.debug("REST request to save MwEmp : {}", mwEmp);
        return null;
    }

    /**
     * PUT /mw-emps : Updates an existing mwEmp.
     *
     * @param mwEmp the mwEmp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwEmp, or with status 400 (Bad Request) if the mwEmp is not
     * valid, or with status 500 (Internal Server Error) if the mwEmp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-emps")
    @Timed
    public ResponseEntity<MwEmp> updateMwEmp(@RequestBody MwEmp mwEmp) throws URISyntaxException {
        log.debug("REST request to update MwEmp : {}", mwEmp);
        return null;
    }

    /**
     * GET /mw-emps : get all the mwEmps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwEmps in body
     */
    @GetMapping("/mw-emps")
    @Timed
    public ResponseEntity<List<MwEmp>> getAllMwEmps(Pageable pageable) {
        log.debug("REST request to get a page of MwEmps");
        Page<MwEmp> page = mwEmpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-emps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/mw-all-emps")
    @Timed
    public ResponseEntity<List<MwEmp>> getAllMwEmps() {
        return new ResponseEntity<>(mwEmpService.findAll(), HttpStatus.OK);
    }

    /**
     * GET /mw-emps/:id : get the "id" mwEmp.
     *
     * @param id the id of the mwEmp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwEmp, or with status 404 (Not Found)
     */
    @GetMapping("/mw-emps/{id}")
    @Timed
    public ResponseEntity<MwEmp> getMwEmp(@PathVariable Long id) {
        log.debug("REST request to get MwEmp : {}", id);
        MwEmp mwEmp = mwEmpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwEmp));
    }

    /**
     * DELETE /mw-emps/:id : delete the "id" mwEmp.
     *
     * @param id the id of the mwEmp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-emps/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwEmp(@PathVariable Long id) {
        log.debug("REST request to delete MwEmp : {}", id);
        mwEmpService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/find-employee-by-chars")
    @Timed
    public ResponseEntity<List<MwEmp>> getMwEmp(@RequestParam String chars) {
        log.debug("REST request to get MwEmp : {}", chars);
        List<MwEmp> mwEmp = mwEmpService.searchEmployeesFromDbByChars(chars);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwEmp));
    }

    @GetMapping("/mw-emps/{brnchSeq}/{typ}")
    @Timed
    public ResponseEntity<List<MwEmp>> getMwEmpForBranch(@PathVariable Long brnchSeq, @PathVariable int typ) {
        log.debug("REST request to get MwEmp : ", brnchSeq);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwEmpService.getBrnchEmployees(brnchSeq, typ)));
    }
}
