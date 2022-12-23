package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwBrnchLocationRel;
import com.idev4.setup.dto.BranchLocationRelDto;
import com.idev4.setup.service.MwBrnchLocationRelService;
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
 * REST controller for managing MwBrnchLocationRel.
 */
@RestController
@RequestMapping("/api")
public class MwBrnchLocationRelResource {

    private static final String ENTITY_NAME = "mwBrnchLocationRel";
    private final Logger log = LoggerFactory.getLogger(MwBrnchLocationRelResource.class);
    private final MwBrnchLocationRelService mwBrnchLocationRelService;

    public MwBrnchLocationRelResource(MwBrnchLocationRelService mwBrnchLocationRelService) {
        this.mwBrnchLocationRelService = mwBrnchLocationRelService;
    }

    /**
     * POST /mw-brnch-location-rels : Create a new mwBrnchLocationRel.
     *
     * @param mwBrnchLocationRel the mwBrnchLocationRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     * mwBrnchLocationRel, or with status 400 (Bad Request) if the
     * mwBrnchLocationRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-brnch-location-rels")
    @Timed
    public ResponseEntity<Map> createMwBrnchLocationRel(@RequestBody List<BranchLocationRelDto> dtos)
        throws URISyntaxException {
        log.debug("REST request to save MwBrnchLocationRel : {}", dtos.size());
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        for (BranchLocationRelDto dto : dtos) {
            if (dto.branchSeq <= 0 || dto.citySeq <= 0) {
                resp.put("error", "Bad values Received !!");
                return ResponseEntity.badRequest().body(resp);
            }
        }

        mwBrnchLocationRelService.addNewBracnchRels(dtos, currUser);
        resp.put("data", "Location Assigned Successfully !!");
        return ResponseEntity.ok().body(resp);
    }


    @PostMapping("/add-brnch-location-rel")
    @Timed
    public ResponseEntity<Map> creatMwBrnchLocationRel(@RequestBody BranchLocationRelDto dto)
        throws URISyntaxException {
        log.debug("REST request to save MwBrnchLocationRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dto.branchSeq <= 0 || dto.citySeq <= 0) {
            resp.put("error", "Bad values Received !!");
            return ResponseEntity.badRequest().body(resp);
        }

        mwBrnchLocationRelService.addNewBracnchRel(dto, currUser);
        resp.put("data", "Location Assigned Successfully !!");
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/remove-brnch-location-rels")
    @Timed
    public ResponseEntity<Map> removeMwBrnchLocationRel(@RequestBody BranchLocationRelDto dto)
        throws URISyntaxException {
        log.debug("REST request to save MwBrnchLocationRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dto.branchSeq <= 0 || dto.citySeq <= 0) {
            resp.put("error", "Bad values Received !!");
            return ResponseEntity.badRequest().body(resp);
        }

        mwBrnchLocationRelService.deleteBracnchRel(dto, currUser);
        resp.put("data", "Location Removed Successfully !!");
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT /mw-brnch-location-rels : Updates an existing mwBrnchLocationRel.
     *
     * @param mwBrnchLocationRel the mwBrnchLocationRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * mwBrnchLocationRel, or with status 400 (Bad Request) if the
     * mwBrnchLocationRel is not valid, or with status 500 (Internal Server
     * Error) if the mwBrnchLocationRel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-brnch-location-rels")
    @Timed
    public ResponseEntity<MwBrnchLocationRel> updateMwBrnchLocationRel(
        @RequestBody MwBrnchLocationRel mwBrnchLocationRel) throws URISyntaxException {
        log.debug("REST request to update MwBrnchLocationRel : {}", mwBrnchLocationRel);
        if (mwBrnchLocationRel.getBrnchLocationRelSeq() == null) {
            return null;
        }
        MwBrnchLocationRel result = mwBrnchLocationRelService.save(mwBrnchLocationRel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwBrnchLocationRel.getBrnchLocationRelSeq().toString()))
            .body(result);
    }

    /**
     * GET /mw-brnch-location-rels : get all the mwBrnchLocationRels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * mwBrnchLocationRels in body
     */
    @GetMapping("/mw-brnch-location-rels")
    @Timed
    public ResponseEntity<List<MwBrnchLocationRel>> getAllMwBrnchLocationRels(Pageable pageable) {
        log.debug("REST request to get a page of MwBrnchLocationRels");
        Page<MwBrnchLocationRel> page = mwBrnchLocationRelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-brnch-location-rels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-brnch-location-rels/:id : get the "id" mwBrnchLocationRel.
     *
     * @param id the id of the mwBrnchLocationRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * mwBrnchLocationRel, or with status 404 (Not Found)
     */
    @GetMapping("/mw-brnch-location-rels/{id}")
    @Timed
    public ResponseEntity<MwBrnchLocationRel> getMwBrnchLocationRel(@PathVariable Long id) {
        log.debug("REST request to get MwBrnchLocationRel : {}", id);
        MwBrnchLocationRel mwBrnchLocationRel = mwBrnchLocationRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBrnchLocationRel));
    }

    /**
     * DELETE /mw-brnch-location-rels/:id : delete the "id" mwBrnchLocationRel.
     *
     * @param id the id of the mwBrnchLocationRel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-brnch-location-rels/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwBrnchLocationRel(@PathVariable Long id) {
        log.debug("REST request to delete MwBrnchLocationRel : {}", id);
        mwBrnchLocationRelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/get-brnch-location-rels/{branchSeq}")
    @Timed
    public ResponseEntity<List<MwBrnchLocationRel>> getMwBrnchLocationRelByBranch(@PathVariable Long branchSeq) {
        log.debug("REST request to get MwBrnchLocationRel : {}", branchSeq);
        List<MwBrnchLocationRel> mwBrnchLocationRel = mwBrnchLocationRelService.getCurrentLocations(branchSeq);
        return ResponseEntity.ok().body(mwBrnchLocationRel);
    }
}
