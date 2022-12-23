package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwRefCdVal;
import com.idev4.setup.dto.CodeValueDto;
import com.idev4.setup.dto.CommonCodeDto;
import com.idev4.setup.service.MwRefCdValService;
import com.idev4.setup.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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
 * REST controller for managing MwRefCdVal.
 */
@RestController
@RequestMapping("/api")
public class MwRefCdValResource {

    private static final String ENTITY_NAME = "mwRefCdVal";
    private final Logger log = LoggerFactory.getLogger(MwRefCdValResource.class);
    private final MwRefCdValService mwRefCdValService;

    public MwRefCdValResource(MwRefCdValService mwRefCdValService) {
        this.mwRefCdValService = mwRefCdValService;
    }

    /**
     * POST /mw-ref-cd-vals : Create a new mwRefCdVal.
     *
     * @param mwRefCdVal the mwRefCdVal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwRefCdVal, or with status 400 (Bad Request) if the
     * mwRefCdVal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-ref-cd-vals")
    @Timed
    public ResponseEntity<Map> createMwRefCdVal(@RequestBody CodeValueDto codeValue) throws URISyntaxException {
        log.debug("REST request to save MwRefCdVal : {}", codeValue);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (codeValue.groupKey < 0) {
            resp.put("error", "Incorrect group Key !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (codeValue.activeStatus == null) {
            resp.put("error", "Active Status Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (codeValue.valueDescription == null || codeValue.valueDescription.isEmpty()) {
            resp.put("error", "Description is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (codeValue.sortOrder == null) {
            resp.put("error", "Sort Order is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwRefCdVal> respData = new HashMap<String, MwRefCdVal>();
        MwRefCdVal value = mwRefCdValService.addNewValueToGroup(codeValue, currUser);
        if (value == null) {
            resp.put("error", "Group Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("group", value);
        return ResponseEntity.ok().body(respData);

    }

    /**
     * PUT /mw-ref-cd-vals : Updates an existing mwRefCdVal.
     *
     * @param mwRefCdVal the mwRefCdVal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwRefCdVal, or with status 400 (Bad Request) if the
     * mwRefCdVal is not valid, or with status 500 (Internal Server Error) if the mwRefCdVal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-ref-cd-vals")
    @Timed
    public ResponseEntity<Map> updateMwRefCdVal(@RequestBody CodeValueDto codeValue) throws URISyntaxException {
        log.debug("REST request to update MwRefCdVal : {}", codeValue);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (codeValue.groupKey < 0) {
            resp.put("error", "Incorrect group Key !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (codeValue.valueDescription == null || codeValue.valueDescription.isEmpty()) {
            resp.put("error", "Value Status Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (codeValue.sortOrder == null) {
            resp.put("error", "Sort Order is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (codeValue.activeStatus == null) {
            resp.put("error", "Active Status Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        Map<String, MwRefCdVal> respData = new HashMap<String, MwRefCdVal>();
        MwRefCdVal value = mwRefCdValService.updateExistingValueToGroup(codeValue, currUser);
        if (value == null) {
            resp.put("error", "Common Code Value or Group Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("group", value);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * GET /mw-ref-cd-vals : get all the mwRefCdVals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwRefCdVals in body
     */
    @GetMapping("/mw-ref-cd-vals")
    @Timed
    public ResponseEntity<List<MwRefCdVal>> getAllMwRefCdVals(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MwRefCdVals");
        List<MwRefCdVal> vals = mwRefCdValService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(vals);
    }

    /**
     * GET /mw-ref-cd-vals/:id : get the "id" mwRefCdVal.
     *
     * @param id the id of the mwRefCdVal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwRefCdVal, or with status 404 (Not Found)
     */
    @GetMapping("/mw-ref-cd-vals/{id}")
    @Timed
    public ResponseEntity<MwRefCdVal> getMwRefCdVal(@PathVariable Long id) {
        log.debug("REST request to get MwRefCdVal : {}", id);
        MwRefCdVal mwRefCdVal = mwRefCdValService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwRefCdVal));
    }

    /**
     * DELETE /mw-ref-cd-vals/:id : delete the "id" mwRefCdVal.
     *
     * @param id the id of the mwRefCdVal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-ref-cd-vals/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwRefCdVal(@PathVariable Long id) {
        log.debug("REST request to delete MwRefCdVal : {}", id);
        mwRefCdValService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/ref-cd-vals-by-group/{groupKey}")
    @Timed
    public ResponseEntity<List<MwRefCdVal>> getMwRefCdValByGroupKey(@PathVariable Long groupKey) {
        log.debug("REST request to get MwRefCdVal : {}", groupKey);
        List<MwRefCdVal> vals = mwRefCdValService.getAllValuesByGroupKey(groupKey);
        return ResponseEntity.ok().body(vals);
    }

    @GetMapping("/ref-cd-vals-by-group-key/{groupKey}")
    @Timed
    public ResponseEntity<List<CommonCodeDto>> getAllMwRefCdValByGroupKey(@PathVariable Long groupKey) {
        log.debug("REST request to get MwRefCdVal : {}", groupKey);
        List<CommonCodeDto> vals = mwRefCdValService.getAllRefCdValuesByGroupKey(groupKey);
        return ResponseEntity.ok().body(vals);
    }

    @GetMapping("/vals-by-group-name")
    @Timed
    public ResponseEntity<List<CommonCodeDto>> getValuesListByGroupName(@RequestParam String groupName) {
        log.debug("REST request to get MwRefCdVal : {}", groupName);
        List<CommonCodeDto> vals = mwRefCdValService.getAllValuesByGroupName(groupName);
        return ResponseEntity.ok().body(vals);
    }

    @GetMapping("/ref-cd-val-by-ref-cd-grp/{refCdGrp}")
    @Timed
    public ResponseEntity<List<CommonCodeDto>> getMwRefCdValByRefCdGrp(@PathVariable String refCdGrp) {
        log.debug("REST request to get MwRefCdVal : {}", refCdGrp);
        List<CommonCodeDto> vals = mwRefCdValService.getAllValuesByRefCdGrp(refCdGrp);
        return ResponseEntity.ok().body(vals);
    }

    // Added by Zohaib Asim - Dated 16-12-2020
    // Get Health Claim Types
    @GetMapping("/hlth-clm-types")
    @Timed
    public ResponseEntity<List<MwRefCdVal>> getHlthClmTypes() {
        log.debug("REST request to get MwRefCdVal : {}");
        List<MwRefCdVal> vals = mwRefCdValService.getHlthClmTypes();
        return ResponseEntity.ok().body(vals);
    }
    // End by Zohaib Asim
}
