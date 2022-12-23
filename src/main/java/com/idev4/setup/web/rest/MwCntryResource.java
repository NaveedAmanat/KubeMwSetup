package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwCntry;
import com.idev4.setup.dto.CountryDto;
import com.idev4.setup.service.MwCntryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MwCntry.
 */
@RestController
@RequestMapping("/api")
public class MwCntryResource {

    private static final String ENTITY_NAME = "mwCntry";
    private final Logger log = LoggerFactory.getLogger(MwCntryResource.class);
    private final MwCntryService mwCntryService;

    public MwCntryResource(MwCntryService mwCntryService) {
        this.mwCntryService = mwCntryService;
    }

    /**
     * POST  /mw-cntries : Create a new mwCntry.
     *
     * @param mwCntry the mwCntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwCntry, or with status 400 (Bad Request) if the mwCntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-cntry")
    @Timed
    public ResponseEntity<Map> createMwCntry(@RequestBody CountryDto countryDto) throws URISyntaxException {
        log.debug("REST request to save MwCntry : {}", countryDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//        if(countryDto.countryCode == null || countryDto.countryCode.isEmpty()) {
//        	resp.put("error", "Missing Country Code !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (countryDto.countryName == null || countryDto.countryName.isEmpty()) {
            resp.put("error", "Missing Country Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (countryDto.countryDescription == null || countryDto.countryDescription.isEmpty()) {
            resp.put("error", "Missing Country Description !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwCntry cntry = mwCntryService.addNewCountry(countryDto, currUser);
        Map<String, MwCntry> respData = new HashMap<String, MwCntry>();
        respData.put("country", cntry);
        return ResponseEntity.ok().body(respData);

    }

    /**
     * PUT  /mw-cntries : Updates an existing mwCntry.
     *
     * @param mwCntry the mwCntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwCntry,
     * or with status 400 (Bad Request) if the mwCntry is not valid,
     * or with status 500 (Internal Server Error) if the mwCntry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-cntries")
    @Timed
    public ResponseEntity<Map> updateMwCntry(@RequestBody CountryDto countryDto) throws URISyntaxException {
        log.debug("REST request to update MwCntry : {}", countryDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//        if(countryDto.countryCode == null || countryDto.countryCode.isEmpty()) {
//        	resp.put("error", "Missing Country Code !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (countryDto.countryName == null || countryDto.countryName.isEmpty()) {
            resp.put("error", "Missing Country Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (countryDto.countryDescription == null || countryDto.countryDescription.isEmpty()) {
            resp.put("error", "Missing Country Description !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwCntry cntry = mwCntryService.updateExistingCountry(countryDto, currUser);
        Map<String, MwCntry> respData = new HashMap<String, MwCntry>();
        if (cntry == null) {
            resp.put("error", "Country Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("country", cntry);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * GET  /mw-cntries : get all the mwCntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwCntries in body
     */
    @GetMapping("/mw-cntries")
    @Timed
    public ResponseEntity<List<MwCntry>> getAllMwCntries(@RequestParam(required = false) String filter) {
        log.debug("REST request to get a page of MwCntries");
        List<MwCntry> cntries = mwCntryService.findAllByCurrentRecord(filter);

        return ResponseEntity.ok().body(cntries);
    }

    /**
     * GET  /mw-cntries/:id : get the "id" mwCntry.
     *
     * @param id the id of the mwCntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwCntry, or with status 404 (Not Found)
     */
    @GetMapping("/mw-cntries/{id}")
    @Timed
    public ResponseEntity<MwCntry> getMwCntry(@PathVariable Long id) {
        log.debug("REST request to get MwCntry : {}", id);
        MwCntry mwCntry = mwCntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwCntry));
    }


    @GetMapping("/cntry-history/{id}")
    @Timed
    public ResponseEntity<List> getMwCntryHistory(@PathVariable Long id) {
        log.debug("REST request to get MwCntry History : {}", id);
        List<MwCntry> mwCntry = mwCntryService.findAllHistory(id);
        return ResponseEntity.ok().body(mwCntry);
    }

    /**
     * DELETE  /mw-cntries/:id : delete the "id" mwCntry.
     *
     * @param id the id of the mwCntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-cntries/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwCntry(@PathVariable Long id) {
        log.debug("REST request to delete MwCntry : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwCntryService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}
