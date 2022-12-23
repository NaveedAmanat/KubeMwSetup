package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPortLocationRel;
import com.idev4.setup.dto.PortLocationRelDto;
import com.idev4.setup.service.MwPortLocationRelService;
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
 * REST controller for managing MwPortLocationRel.
 */
@RestController
@RequestMapping("/api")
public class MwPortLocationRelResource {

    private static final String ENTITY_NAME = "mwPortLocationRel";
    private final Logger log = LoggerFactory.getLogger(MwPortLocationRelResource.class);
    private final MwPortLocationRelService mwPortLocationRelService;

    public MwPortLocationRelResource(MwPortLocationRelService mwPortLocationRelService) {
        this.mwPortLocationRelService = mwPortLocationRelService;
    }

    /**
     * POST  /mw-port-location-rels : Create a new mwPortLocationRel.
     *
     * @param mwPortLocationRel the mwPortLocationRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwPortLocationRel, or with status 400 (Bad Request) if the mwPortLocationRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-port-location-rels")
    @Timed
    public ResponseEntity<Map> createMwBrnchLocationRel(@RequestBody List<PortLocationRelDto> dtos)
        throws URISyntaxException {
        log.debug("REST request to save MwBrnchLocationRel : {}", dtos.size());
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        for (PortLocationRelDto dto : dtos) {
            if (dto.portSeq <= 0 || dto.citySeq <= 0) {
                resp.put("error", "Bad values Received !!");
                return ResponseEntity.badRequest().body(resp);
            }
        }
        mwPortLocationRelService.addNewPortRels(dtos, currUser);
        resp.put("data", "Location Assigned Successfully !!");
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/add-port-location-rel")
    @Timed
    public ResponseEntity<Map> creatMwBrnchLocationRel(@RequestBody PortLocationRelDto dto)
        throws URISyntaxException {
        log.debug("REST request to save MwBrnchLocationRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();


        if (dto.portSeq <= 0 || dto.citySeq <= 0) {
            resp.put("error", "Bad values Received !!");
            return ResponseEntity.badRequest().body(resp);
        }

        mwPortLocationRelService.addNewPortRel(dto, currUser);
        resp.put("data", "Location Assigned Successfully !!");
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/remove-port-location-rel")
    @Timed
    public ResponseEntity<Map> removeMwBrnchLocationRel(@RequestBody PortLocationRelDto dto)
        throws URISyntaxException {
        log.debug("REST request to save MwBrnchLocationRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dto.portSeq <= 0 || dto.citySeq <= 0) {
            resp.put("error", "Bad values Received !!");
            return ResponseEntity.badRequest().body(resp);
        }
        mwPortLocationRelService.deletePortRel(dto, currUser);
        resp.put("data", "Location Assigned Successfully !!");
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT  /mw-port-location-rels : Updates an existing mwPortLocationRel.
     *
     * @param mwPortLocationRel the mwPortLocationRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwPortLocationRel,
     * or with status 400 (Bad Request) if the mwPortLocationRel is not valid,
     * or with status 500 (Internal Server Error) if the mwPortLocationRel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-port-location-rels")
    @Timed
    public ResponseEntity<MwPortLocationRel> updateMwPortLocationRel(@RequestBody MwPortLocationRel mwPortLocationRel) throws URISyntaxException {
        log.debug("REST request to update MwPortLocationRel : {}", mwPortLocationRel);
        if (mwPortLocationRel.getPortLocationRelSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwPortLocationRel result = mwPortLocationRelService.save(mwPortLocationRel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwPortLocationRel.getPortLocationRelSeq().toString()))
            .body(result);
    }

    /**
     * GET  /mw-port-location-rels : get all the mwPortLocationRels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwPortLocationRels in body
     */
    @GetMapping("/mw-port-location-rels")
    @Timed
    public ResponseEntity<List<MwPortLocationRel>> getAllMwPortLocationRels(Pageable pageable) {
        log.debug("REST request to get a page of MwPortLocationRels");
        Page<MwPortLocationRel> page = mwPortLocationRelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-port-location-rels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mw-port-location-rels/:id : get the "id" mwPortLocationRel.
     *
     * @param id the id of the mwPortLocationRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwPortLocationRel, or with status 404 (Not Found)
     */
    @GetMapping("/mw-port-location-rels/{id}")
    @Timed
    public ResponseEntity<MwPortLocationRel> getMwPortLocationRel(@PathVariable Long id) {
        log.debug("REST request to get MwPortLocationRel : {}", id);
        Optional<MwPortLocationRel> mwPortLocationRel = mwPortLocationRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mwPortLocationRel);
    }

    /**
     * DELETE  /mw-port-location-rels/:id : delete the "id" mwPortLocationRel.
     *
     * @param id the id of the mwPortLocationRel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-port-location-rels/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwPortLocationRel(@PathVariable Long id) {
        log.debug("REST request to delete MwPortLocationRel : {}", id);
        mwPortLocationRelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/get-port-location-options/{brnchSeq}")
    @Timed
    public ResponseEntity<List> getMwBrnchLocations(@PathVariable Long brnchSeq) {
        log.debug("REST request to get MwPortLocationRel : {}", brnchSeq);
        List locs = mwPortLocationRelService.getAddressCombinationsByBrnch(brnchSeq);
        return ResponseEntity.ok().body(locs);
    }

    @GetMapping("/get-port-location-options-filter")
    @Timed
    public ResponseEntity<List> getMwBrnchLocationsFilter(@RequestParam Long id,
                                                          @RequestParam(required = false) String filter) {
        log.debug("REST request to get a page of MwBrnchLocationRel");
        List locs = mwPortLocationRelService.getAddressCombinationsByBrnchAndFilter(id, filter);
        return ResponseEntity.ok().body(locs);
    }

    @GetMapping("/get-port-location-rels/{portSeq}")
    @Timed
    public ResponseEntity<List<MwPortLocationRel>> getMwPortLocationRelByPort(@PathVariable Long portSeq) {
        log.debug("REST request to get MwPortLocationRel : {}", portSeq);
        List<MwPortLocationRel> mwPortLocationRel = mwPortLocationRelService.getCurrentLocations(portSeq);
        return ResponseEntity.ok().body(mwPortLocationRel);
    }
}
