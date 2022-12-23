package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdGrp;
import com.idev4.setup.dto.ProductGroupDto;
import com.idev4.setup.service.MwPrdGrpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing MwPrdGrp.
 */
@RestController
@RequestMapping("/api")
public class MwPrdGrpResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdGrpResource.class);

    private final MwPrdGrpService mwPrdGrpService;

    public MwPrdGrpResource(MwPrdGrpService mwPrdGrpService) {
        this.mwPrdGrpService = mwPrdGrpService;
    }

    @PostMapping("/add-new-prd-grp")
    @Timed
    public ResponseEntity<Map> createMwPrdGrp(@RequestBody ProductGroupDto dto) throws URISyntaxException {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> resp = new HashMap<String, String>();

        MwPrdGrp mwPrdGrp = mwPrdGrpService.save(dto, currUser);
        Map<String, MwPrdGrp> respData = new HashMap<String, MwPrdGrp>();
        respData.put("Product", mwPrdGrp);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-mw-prd-grp")
    @Timed
    public ResponseEntity<Map> updateMwPrdGrp(@RequestBody ProductGroupDto dto) throws URISyntaxException {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> resp = new HashMap<String, String>();

        MwPrdGrp mwPrdGrp = mwPrdGrpService.updateExistingProductGroup(dto, currUser);
        Map<String, MwPrdGrp> respData = new HashMap<String, MwPrdGrp>();
        respData.put("Product Group", mwPrdGrp);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-grp")
    @Timed
    public ResponseEntity<List> getMwPrdGrps() {
        log.debug("REST request to get all MwPrdGrp : {}");
        List<MwPrdGrp> mwPrdGrps = mwPrdGrpService.findAllProductGroups();
        return ResponseEntity.ok().body(mwPrdGrps);
    }

    @GetMapping("/mw-prd-grp/{prdGrpSeq}")
    @Timed
    public ResponseEntity<MwPrdGrp> getMwPrdGrpBySeq(@PathVariable Long prdGrpSeq) {
        log.debug("REST request to get MwPrdGrp : {}", prdGrpSeq);
        MwPrdGrp mwPrdGrp = mwPrdGrpService.findOne(prdGrpSeq);
        return ResponseEntity.ok().body(mwPrdGrp);
    }

    @GetMapping("/mw-prd-grp-history/{prdGrpSeq}")
    @Timed
    public ResponseEntity<List> getMwPrdGrpHistory(@PathVariable Long prdGrpSeq) {
        log.debug("REST request to get MwPrdGrp : {}", prdGrpSeq);
        List<MwPrdGrp> mwPrdGrps = mwPrdGrpService.findAllByPrdGrpSeq(prdGrpSeq);
        return ResponseEntity.ok().body(mwPrdGrps);
    }

    @DeleteMapping("/mw-prd-grp/{prdGrpSeq}")
    @Timed
    public ResponseEntity<Map> deleteMwPrdGrp(@PathVariable Long prdGrpSeq) {
        log.debug("REST request to delete MwPrdGrp : {}", prdGrpSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdGrpService.delete(prdGrpSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/mw-prd-grp-fr-brnch/{brnchSeq}")
    @Timed
    public ResponseEntity<List<MwPrdGrp>> getActiveMwPrdGrpByBrnch(@PathVariable Long brnchSeq) {
        log.debug("REST request to get Active MwPrdGrp for Branch: {}", brnchSeq);
        List<MwPrdGrp> mwPrdGrp = mwPrdGrpService.findActivePrdGrpForBrnch(brnchSeq);
        return ResponseEntity.ok().body(mwPrdGrp);
    }
}
