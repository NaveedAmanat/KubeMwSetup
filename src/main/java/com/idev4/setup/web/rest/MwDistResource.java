package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwDist;
import com.idev4.setup.dto.DistrictDto;
import com.idev4.setup.service.MwDistService;
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
 * REST controller for managing MwDist.
 */
@RestController
@RequestMapping("/api")
public class MwDistResource {

    private static final String ENTITY_NAME = "mwDist";
    private final Logger log = LoggerFactory.getLogger(MwDistResource.class);
    private final MwDistService mwDistService;

    public MwDistResource(MwDistService mwDistService) {
        this.mwDistService = mwDistService;
    }

    /**
     * POST  /mw-dists : Create a new mwDist.
     *
     * @param mwDist the mwDist to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwDist, or with status 400 (Bad Request) if the mwDist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-new-dist")
    @Timed
    public ResponseEntity<Map> createMwDist(@RequestBody DistrictDto districtDto) throws URISyntaxException {
        log.debug("REST request to save MwDist : {}", districtDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//        if(districtDto.districtCode == null || districtDto.districtCode.isEmpty()) {
//        	resp.put("error", "Missing district Code !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (districtDto.districtName == null || districtDto.districtName.isEmpty()) {
            resp.put("error", "Missing district Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (districtDto.districtDescription == null || districtDto.districtDescription.isEmpty()) {
            resp.put("error", "Missing district Description !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwDist dist = mwDistService.addNewDistrict(districtDto, currUser);
        Map<String, MwDist> respData = new HashMap<String, MwDist>();
        respData.put("district", dist);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * PUT  /mw-dists : Updates an existing mwDist.
     *
     * @param mwDist the mwDist to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwDist,
     * or with status 400 (Bad Request) if the mwDist is not valid,
     * or with status 500 (Internal Server Error) if the mwDist couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-dists")
    @Timed
    public ResponseEntity<Map> updateMwDist(@RequestBody DistrictDto districtDto) throws URISyntaxException {
        log.debug("REST request to update MwDist : {}", districtDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//        if(districtDto.districtCode == null || districtDto.districtCode.isEmpty()) {
//        	resp.put("error", "Missing district Code !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (districtDto.districtName == null || districtDto.districtName.isEmpty()) {
            resp.put("error", "Missing district Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (districtDto.districtDescription == null || districtDto.districtDescription.isEmpty()) {
            resp.put("error", "Missing district Description !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwDist dist = mwDistService.updateExistingDistrict(districtDto, currUser);
        if (dist == null) {
            resp.put("error", "District Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwDist> respData = new HashMap<String, MwDist>();
        respData.put("district", dist);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * GET  /mw-dists : get all the mwDists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwDists in body
     */
    @GetMapping("/mw-dists")
    @Timed
    public ResponseEntity<List<MwDist>> getAllMwDists(Pageable pageable) {
        log.debug("REST request to get a page of MwDists");
        List<MwDist> dists = mwDistService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(dists);
    }

    /**
     * GET  /mw-dists/:id : get the "id" mwDist.
     *
     * @param id the id of the mwDist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwDist, or with status 404 (Not Found)
     */
    @GetMapping("/mw-dists/{id}")
    @Timed
    public ResponseEntity<MwDist> getMwDist(@PathVariable Long id) {
        log.debug("REST request to get MwDist : {}", id);
        MwDist mwDist = mwDistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwDist));
    }

    @GetMapping("/dists-history/{id}")
    @Timed
    public ResponseEntity<List> getMwDistHistory(@PathVariable Long id) {
        log.debug("REST request to get MwDist : {}", id);
        List<MwDist> mwDist = mwDistService.findAllHistory(id);
        return ResponseEntity.ok().body(mwDist);
    }

    /**
     * DELETE  /mw-dists/:id : delete the "id" mwDist.
     *
     * @param id the id of the mwDist to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-dists/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwDist(@PathVariable Long id) {
        log.debug("REST request to delete MwDist : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwDistService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }

    @GetMapping("/get-dists-by-state/{stateSeq}")
    @Timed
    public ResponseEntity<List<MwDist>> getMwDistByStateSeq(@PathVariable Long stateSeq,
                                                            @RequestParam(required = false) String filter) {
        log.debug("REST request to get MwDist : {}", stateSeq);
        List<MwDist> districts = mwDistService.getDistrictsByStateSeq(stateSeq, filter);
        return ResponseEntity.ok().body(districts);
    }
}
