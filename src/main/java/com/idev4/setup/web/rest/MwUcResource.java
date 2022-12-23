package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwUc;
import com.idev4.setup.dto.AddressCombinationDto;
import com.idev4.setup.dto.UcDto;
import com.idev4.setup.service.MwUcService;
import com.idev4.setup.web.rest.util.HeaderUtil;
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
 * REST controller for managing MwUc.
 */
@RestController
@RequestMapping("/api")
public class MwUcResource {

    private static final String ENTITY_NAME = "mwUc";
    private final Logger log = LoggerFactory.getLogger(MwUcResource.class);
    private final MwUcService mwUcService;

    public MwUcResource(MwUcService mwUcService) {
        this.mwUcService = mwUcService;
    }

    /**
     * POST  /mw-ucs : Create a new mwUc.
     *
     * @param mwUc the mwUc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwUc, or with status 400 (Bad Request) if the mwUc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-new-uc")
    @Timed
    public ResponseEntity<Map> createMwUc(@RequestBody UcDto ucDto) throws URISyntaxException {
        log.debug("REST request to save MwUc : {}", ucDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//        if(ucDto.ucCode == null || ucDto.ucCode.isEmpty()) {
//        	resp.put("error", "Missing uc Code !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (ucDto.ucName == null || ucDto.ucName.isEmpty()) {
            resp.put("error", "Missing uc Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (ucDto.ucDescription == null || ucDto.ucDescription.isEmpty()) {
            resp.put("error", "Missing uc Description !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwUc uc = mwUcService.addNewUc(ucDto, currUser);
        Map<String, MwUc> respData = new HashMap<String, MwUc>();
        respData.put("uc", uc);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * PUT  /mw-ucs : Updates an existing mwUc.
     *
     * @param mwUc the mwUc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwUc,
     * or with status 400 (Bad Request) if the mwUc is not valid,
     * or with status 500 (Internal Server Error) if the mwUc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-ucs")
    @Timed
    public ResponseEntity<Map> updateMwUc(@RequestBody UcDto ucDto) throws URISyntaxException {
        log.debug("REST request to update MwUc : {}", ucDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//        if(ucDto.ucCode == null || ucDto.ucCode.isEmpty()) {
//        	resp.put("error", "Missing uc Code !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (ucDto.ucName == null || ucDto.ucName.isEmpty()) {
            resp.put("error", "Missing uc Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (ucDto.ucDescription == null || ucDto.ucDescription.isEmpty()) {
            resp.put("error", "Missing uc Description !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwUc uc = mwUcService.UpdateExistingUc(ucDto, currUser);
        if (uc == null) {
            resp.put("error", "UC Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwUc> respData = new HashMap<String, MwUc>();
        respData.put("uc", uc);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * GET  /mw-ucs : get all the mwUcs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwUcs in body
     */
    @GetMapping("/mw-ucs")
    @Timed
    public ResponseEntity<List<MwUc>> getAllMwUcs(Pageable pageable) {
        log.debug("REST request to get a page of MwUcs");
        List<MwUc> ucs = mwUcService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(ucs);
    }

    @GetMapping("/mw-ucs-combinations")
    @Timed
    public ResponseEntity<List<AddressCombinationDto>> getAllMwUcsCombo(@RequestParam Long citySeq,
                                                                        @RequestParam(required = false) String filter) {
        log.debug("REST request to get a page of MwUcs");
        List<AddressCombinationDto> ucs = mwUcService.findAllByCurrentRecordRecords(citySeq, filter);
        return ResponseEntity.ok().body(ucs);
    }

    /**
     * GET  /mw-ucs/:id : get the "id" mwUc.
     *
     * @param id the id of the mwUc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwUc, or with status 404 (Not Found)
     */
    @GetMapping("/mw-ucs/{id}")
    @Timed
    public ResponseEntity<MwUc> getMwUc(@PathVariable Long id) {
        log.debug("REST request to get MwUc : {}", id);
        MwUc mwUc = mwUcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwUc));
    }

    @GetMapping("/uc-history/{id}")
    @Timed
    public ResponseEntity<List> getMwUcHistory(@PathVariable Long id) {
        log.debug("REST request to get MwUc : {}", id);
        List<MwUc> mwUc = mwUcService.findAllHistory(id);
        return ResponseEntity.ok().body(mwUc);
    }

    /**
     * DELETE  /mw-ucs/:id : delete the "id" mwUc.
     *
     * @param id the id of the mwUc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    // Edited by Areeba - 27-05-2022
    @DeleteMapping("/mw-ucs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwUc(@PathVariable Long id) {
        log.debug("REST request to delete MwUc : {}", id);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        mwUcService.delete(id, currUser);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/get-ucs-by-thsl/{thslSeq}")
    @Timed
    public ResponseEntity<List<MwUc>> getMwUcByThslSeq(@PathVariable Long thslSeq,
                                                       @RequestParam(required = false) String filter) {
        log.debug("REST request to get MwUc : {}", thslSeq);
        List<MwUc> ucs = mwUcService.getAllUcByThslSeq(thslSeq, filter);
        return ResponseEntity.ok().body(ucs);
    }

    //Added by Areeba - Branch Setup
    @PostMapping("/upload-uc")
    @Timed
    public ResponseEntity<Map> uploadUc(@RequestBody List<UcDto> dto) {

        Map<String, Object> resp = mwUcService.makeDataSet(dto);

        return ResponseEntity.ok().body(resp);
    }
    //Ended by Areeba
}
