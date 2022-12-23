package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwSt;
import com.idev4.setup.dto.ProvinceDto;
import com.idev4.setup.service.MwStService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MwSt.
 */
@RestController
@RequestMapping("/api")
public class MwStResource {

    private static final String ENTITY_NAME = "mwSt";
    private final Logger log = LoggerFactory.getLogger(MwStResource.class);
    private final MwStService mwStService;

    public MwStResource(MwStService mwStService) {
        this.mwStService = mwStService;
    }

    /**
     * POST  /mw-sts : Create a new mwSt.
     *
     * @param mwSt the mwSt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwSt, or with status 400 (Bad Request) if the mwSt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-new-sts")
    @Timed
    public ResponseEntity<Map> createMwSt(@RequestBody ProvinceDto provDto) throws URISyntaxException {
        log.debug("REST request to save MwSt : {}", provDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//    	if(provDto.provCode == null || provDto.provCode.isEmpty()) {
//        	resp.put("error", "Missing Province Code !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (provDto.provName == null || provDto.provName.isEmpty()) {
            resp.put("error", "Missing Province Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (provDto.provDescription == null || provDto.provDescription.isEmpty()) {
            resp.put("error", "Missing Province Description !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwSt province = mwStService.addNewState(provDto, currUser);
        Map<String, MwSt> respData = new HashMap<String, MwSt>();
        respData.put("Province", province);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * PUT  /mw-sts : Updates an existing mwSt.
     *
     * @param mwSt the mwSt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwSt,
     * or with status 400 (Bad Request) if the mwSt is not valid,
     * or with status 500 (Internal Server Error) if the mwSt couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-sts")
    @Timed
    public ResponseEntity<Map> updateMwSt(@RequestBody ProvinceDto provDto) throws URISyntaxException {
        log.debug("REST request to update MwSt : {}", provDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//    	if(provDto.provCode == null || provDto.provCode.isEmpty()) {
//        	resp.put("error", "Missing Province Code !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (provDto.provName == null || provDto.provName.isEmpty()) {
            resp.put("error", "Missing Province Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (provDto.provDescription == null || provDto.provDescription.isEmpty()) {
            resp.put("error", "Missing Province Description !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwSt province = mwStService.updateExistingState(provDto, currUser);
        if (province == null) {
            resp.put("error", "State Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwSt> respData = new HashMap<String, MwSt>();
        respData.put("Province", province);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * GET  /mw-sts : get all the mwSts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwSts in body
     */
    @GetMapping("/mw-sts")
    @Timed
    public ResponseEntity<List<MwSt>> getAllMwSts(Pageable pageable) {
        log.debug("REST request to get a page of MwSts");
        List<MwSt> sts = mwStService.findAllByCurrentRecord();

        return ResponseEntity.ok().body(sts);
    }

    /**
     * GET  /mw-sts/:id : get the "id" mwSt.
     *
     * @param id the id of the mwSt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwSt, or with status 404 (Not Found)
     */
    @GetMapping("/mw-sts/{id}")
    @Timed
    public ResponseEntity<MwSt> getMwSt(@PathVariable Long id) {
        log.debug("REST request to get MwSt : {}", id);
        MwSt mwSt = mwStService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwSt));
    }

    @GetMapping("/sts-history/{id}")
    @Timed
    public ResponseEntity<List> getMwStHistory(@PathVariable Long id) {
        log.debug("REST request to get MwSt : {}", id);
        List<MwSt> mwSt = mwStService.findAllHistory(id);
        return ResponseEntity.ok().body(mwSt);
    }

    /**
     * DELETE  /mw-sts/:id : delete the "id" mwSt.
     *
     * @param id the id of the mwSt to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-sts/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwSt(@PathVariable Long id) {
        log.debug("REST request to delete MwSt : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwStService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }

    @GetMapping("/get-states-by-country/{countrySeq}")
    @Timed
    public ResponseEntity<List<MwSt>> getMwStByCountry(@PathVariable Long countrySeq,
                                                       @RequestParam(required = false) String filter) {
        log.debug("REST request to get MwSt : {}", countrySeq);
        List<MwSt> states = mwStService.getStatesByCountrySeq(countrySeq, filter);
        return ResponseEntity.ok().body(states);
    }
}
