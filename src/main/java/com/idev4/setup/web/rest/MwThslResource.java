package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwThsl;
import com.idev4.setup.dto.TehsilDto;
import com.idev4.setup.service.MwThslService;
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
 * REST controller for managing MwThsl.
 */
@RestController
@RequestMapping("/api")
public class MwThslResource {

    private static final String ENTITY_NAME = "mwThsl";
    private final Logger log = LoggerFactory.getLogger(MwThslResource.class);
    private final MwThslService mwThslService;

    public MwThslResource(MwThslService mwThslService) {
        this.mwThslService = mwThslService;
    }

    /**
     * POST  /mw-thsls : Create a new mwThsl.
     *
     * @param mwThsl the mwThsl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwThsl, or with status 400 (Bad Request) if the mwThsl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-new-thsl")
    @Timed
    public ResponseEntity<Map> createMwThsl(@RequestBody TehsilDto thslDto) throws URISyntaxException {
        log.debug("REST request to save MwThsl : {}", thslDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//    	if(thslDto.thslCode == null || thslDto.thslCode.isEmpty()) {
//        	resp.put("error", "Missing thslince Code !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (thslDto.thslName == null || thslDto.thslName.isEmpty()) {
            resp.put("error", "Missing tehsil Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (thslDto.thslDescription == null || thslDto.thslDescription.isEmpty()) {
            resp.put("error", "Missing tehsil Description !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwThsl thsl = mwThslService.addNewTehsil(thslDto, currUser);
        Map<String, MwThsl> respData = new HashMap<String, MwThsl>();
        respData.put("tehsil", thsl);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * PUT  /mw-thsls : Updates an existing mwThsl.
     *
     * @param mwThsl the mwThsl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwThsl,
     * or with status 400 (Bad Request) if the mwThsl is not valid,
     * or with status 500 (Internal Server Error) if the mwThsl couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-thsl")
    @Timed
    public ResponseEntity<Map> updateMwThsl(@RequestBody TehsilDto thslDto) throws URISyntaxException {
        log.debug("REST request to update MwThsl : {}", thslDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//    	if(thslDto.thslCode == null || thslDto.thslCode.isEmpty()) {
//        	resp.put("error", "Missing thslince Code !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (thslDto.thslName == null || thslDto.thslName.isEmpty()) {
            resp.put("error", "Missing tehsil Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (thslDto.thslDescription == null || thslDto.thslDescription.isEmpty()) {
            resp.put("error", "Missing tehsil Description !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwThsl thsl = mwThslService.updateExistingTehsil(thslDto, currUser);
        if (thsl == null) {
            resp.put("error", "Tehsil Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwThsl> respData = new HashMap<String, MwThsl>();
        respData.put("tehsil", thsl);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * GET  /mw-thsls : get all the mwThsls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwThsls in body
     */
    @GetMapping("/mw-thsls")
    @Timed
    public ResponseEntity<List<MwThsl>> getAllMwThsls(Pageable pageable) {
        log.debug("REST request to get a page of MwThsls");
        List<MwThsl> thsls = mwThslService.findAllByCurrentRecord();

        return ResponseEntity.ok().body(thsls);
    }

    /**
     * GET  /mw-thsls/:id : get the "id" mwThsl.
     *
     * @param id the id of the mwThsl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwThsl, or with status 404 (Not Found)
     */
    @GetMapping("/mw-thsls/{id}")
    @Timed
    public ResponseEntity<MwThsl> getMwThsl(@PathVariable Long id) {
        log.debug("REST request to get MwThsl : {}", id);
        MwThsl mwThsl = mwThslService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwThsl));
    }

    @GetMapping("/thsl-history/{id}")
    @Timed
    public ResponseEntity<List> getMwThslHistory(@PathVariable Long id) {
        log.debug("REST request to get MwThsl : {}", id);
        List<MwThsl> mwThsl = mwThslService.findAllHistory(id);
        return ResponseEntity.ok().body(mwThsl);
    }

    /**
     * DELETE  /mw-thsls/:id : delete the "id" mwThsl.
     *
     * @param id the id of the mwThsl to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-thsls/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwThsl(@PathVariable Long id) {
        log.debug("REST request to delete MwThsl : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwThslService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }


    @GetMapping("/get-states-by-district/{distSeq}")
    @Timed
    public ResponseEntity<List<MwThsl>> getMwStByCountry(@PathVariable Long distSeq,
                                                         @RequestParam(required = false) String filter) {
        log.debug("REST request to get MwSt : {}", distSeq);
        List<MwThsl> tehsils = mwThslService.getTehsilsByDistrict(distSeq, filter);
        return ResponseEntity.ok().body(tehsils);
    }
}
