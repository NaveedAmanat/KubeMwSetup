package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwBrnchPrdRel;
import com.idev4.setup.dto.BranchPrdRelDto;
import com.idev4.setup.service.MwBrnchPrdRelService;
import com.idev4.setup.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing MwBrnchPrdRel.
 */
@RestController
@RequestMapping("/api")
public class MwBrnchPrdRelResource {

    private static final String ENTITY_NAME = "mwBrnchPrdRel";
    private final Logger log = LoggerFactory.getLogger(MwBrnchPrdRelResource.class);
    private final MwBrnchPrdRelService mwBrnchPrdRelService;

    public MwBrnchPrdRelResource(MwBrnchPrdRelService mwBrnchPrdRelService) {
        this.mwBrnchPrdRelService = mwBrnchPrdRelService;
    }

    /**
     * POST  /mw-brnch-prd-rels : Create a new mwBrnchPrdRel.
     *
     * @param mwBrnchPrdRel the mwBrnchPrdRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwBrnchPrdRel, or with status 400 (Bad Request) if the mwBrnchPrdRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-brnch-prd-rels")
    @Timed
    public ResponseEntity<Map> createMwBrnchPrdRel(@RequestBody List<BranchPrdRelDto> dtos)
        throws URISyntaxException {
        log.debug("REST request to save MwBrnchLocationRel : {}", dtos.size());
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        for (BranchPrdRelDto dto : dtos) {
            if (dto.branchSeq <= 0 || dto.prdSeq <= 0) {
                resp.put("error", "Bad values Received !!");
                return ResponseEntity.badRequest().body(resp);
            }
        }

        mwBrnchPrdRelService.addNewBracnchRels(dtos, currUser);
        resp.put("data", "Products Assigned Successfully !!");
        return ResponseEntity.ok().body(resp);
    }


    @PostMapping("/add-brnch-prd-rel")
    @Timed
    public ResponseEntity<Map> creatMwBrnchPrdRel(@RequestBody BranchPrdRelDto dto)
        throws URISyntaxException {
        log.debug("REST request to save MwBrnchLocationRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (dto.branchSeq <= 0 || dto.prdSeq <= 0) {
            resp.put("error", "Bad values Received !!");
            return ResponseEntity.badRequest().body(resp);
        }

        mwBrnchPrdRelService.addNewBracnchRel(dto, currUser);
        resp.put("data", "Products Assigned Successfully !!");
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/remove-brnch-prd-rel")
    @Timed
    public ResponseEntity<Map> removeMwBrnchPrdRel(@RequestBody BranchPrdRelDto dto)
        throws URISyntaxException {
        log.debug("REST request to save MwBrnchLocationRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (dto.branchSeq <= 0 || dto.prdSeq <= 0) {
            resp.put("error", "Bad values Received !!");
            return ResponseEntity.badRequest().body(resp);
        }

        mwBrnchPrdRelService.removeBracnchRel(dto, currUser);
        resp.put("data", "Products Assigned Successfully !!");
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT  /mw-brnch-prd-rels : Updates an existing mwBrnchPrdRel.
     *
     * @param mwBrnchPrdRel the mwBrnchPrdRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwBrnchPrdRel,
     * or with status 400 (Bad Request) if the mwBrnchPrdRel is not valid,
     * or with status 500 (Internal Server Error) if the mwBrnchPrdRel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-brnch-prd-rels")
    @Timed
    public ResponseEntity<MwBrnchPrdRel> updateMwBrnchPrdRel(@RequestBody MwBrnchPrdRel mwBrnchPrdRel) throws URISyntaxException {
        log.debug("REST request to update MwBrnchPrdRel : {}", mwBrnchPrdRel);
        if (mwBrnchPrdRel.getBrnchPrdRelSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwBrnchPrdRel result = mwBrnchPrdRelService.save(mwBrnchPrdRel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwBrnchPrdRel.getBrnchPrdRelSeq().toString()))
            .body(result);
    }

    /**
     * GET  /mw-brnch-prd-rels : get all the mwBrnchPrdRels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwBrnchPrdRels in body
     */
    @GetMapping("/mw-brnch-prd-rels")
    @Timed
    public ResponseEntity<List<MwBrnchPrdRel>> getAllMwBrnchPrdRels(Pageable pageable) {
        log.debug("REST request to get a page of MwBrnchPrdRels");
        Page<MwBrnchPrdRel> page = mwBrnchPrdRelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-brnch-prd-rels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mw-brnch-prd-rels/:id : get the "id" mwBrnchPrdRel.
     *
     * @param id the id of the mwBrnchPrdRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwBrnchPrdRel, or with status 404 (Not Found)
     */
    @GetMapping("/mw-brnch-prd-rels/{id}")
    @Timed
    public ResponseEntity<MwBrnchPrdRel> getMwBrnchPrdRel(@PathVariable Long id) {
        log.debug("REST request to get MwBrnchPrdRel : {}", id);
        Optional<MwBrnchPrdRel> mwBrnchPrdRel = mwBrnchPrdRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mwBrnchPrdRel);
    }

    /**
     * DELETE  /mw-brnch-prd-rels/:id : delete the "id" mwBrnchPrdRel.
     *
     * @param id the id of the mwBrnchPrdRel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-brnch-prd-rels/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwBrnchPrdRel(@PathVariable Long id) {
        log.debug("REST request to delete MwBrnchPrdRel : {}", id);
        mwBrnchPrdRelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/get-brnch-prd-rels/{branchSeq}")
    @Timed
    public ResponseEntity<List<MwBrnchPrdRel>> getMwBrnchPrdRelByBranch(@PathVariable Long branchSeq) {
        log.debug("REST request to get MwBrnchPrdRel : {}", branchSeq);
        List<MwBrnchPrdRel> mwBrnchLocationRel = mwBrnchPrdRelService.getCurrentProducts(branchSeq);
        return ResponseEntity.ok().body(mwBrnchLocationRel);
    }
}
