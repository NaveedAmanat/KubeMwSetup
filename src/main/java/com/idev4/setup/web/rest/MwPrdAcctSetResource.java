package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdAcctSet;
import com.idev4.setup.dto.ProductAccountSetupDto;
import com.idev4.setup.service.MwPrdAcctSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MwPrdAcctSetResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdAcctSetResource.class);

    private final MwPrdAcctSetService mwPrdAcctSetService;

    public MwPrdAcctSetResource(MwPrdAcctSetService mwPrdAcctSetService) {
        this.mwPrdAcctSetService = mwPrdAcctSetService;
    }

    @PostMapping("/add-new-prd-acct-set")
    @Timed
    public ResponseEntity<Map> createMwPrdAcctSet(@RequestBody ProductAccountSetupDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdAcctSet : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdAcctSet prdAcctSet = mwPrdAcctSetService.addNewPrdAcctSet(dto, currUser);
        Map<String, MwPrdAcctSet> respData = new HashMap<String, MwPrdAcctSet>();
        respData.put("PrdAcctSet", prdAcctSet);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-prd-acct-set")
    @Timed
    public ResponseEntity<Map> updateMwAcctSet(@RequestBody ProductAccountSetupDto dto) throws URISyntaxException {
        log.debug("REST request to update MwPrdAcctSet : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdAcctSet mwPrdAcctSet = mwPrdAcctSetService.updateExistingProductAccountSetup(dto, currUser);
        if (mwPrdAcctSet == null) {
            resp.put("error", " Product Account setup Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwPrdAcctSet> respData = new HashMap<String, MwPrdAcctSet>();
        respData.put("MwPrdAcctSet", mwPrdAcctSet);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-acct-set/{prdAcctSetSeq}")
    @Timed
    public ResponseEntity<MwPrdAcctSet> getPrdAcctSetBySeq(@PathVariable Long prdAcctSetSeq) {
        log.debug("REST request to get All MwPrdAcctSet : {}", prdAcctSetSeq);
        MwPrdAcctSet mwPrdAcctSet = mwPrdAcctSetService.findOne(prdAcctSetSeq);
        return ResponseEntity.ok().body(mwPrdAcctSet);
    }

    @GetMapping("/mw-prd-acct-set")
    @Timed
    public ResponseEntity<List> getPrdAcctSet() {
        log.debug("REST request to get All MwPrdAcctSet : {}");
        List<MwPrdAcctSet> mwPrdAcctSet = mwPrdAcctSetService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwPrdAcctSet);
    }


    @GetMapping("/mw-prd-acct-set-history/{prdAcctSetSeq}")
    @Timed
    public ResponseEntity<List> getPrdAcctSetHistory(@PathVariable Long prdAcctSetSeq) {
        log.debug("REST request to get MwPrdAcctSet : {}", prdAcctSetSeq);
        List<MwPrdAcctSet> mwPrdAcctSet = mwPrdAcctSetService.findAllByPrdAcctSetSeq(prdAcctSetSeq);
        return ResponseEntity.ok().body(mwPrdAcctSet);
    }


    @GetMapping("/mw-prd-acct-set-by-prd-seq/{prdSeq}")
    @Timed
    public ResponseEntity<List<MwPrdAcctSet>> getPrdAcctSetByPrdSeq(@PathVariable Long prdSeq) {
        log.debug("REST request to get All MwPrdAdvRul for product seq: {}", prdSeq);
        List<MwPrdAcctSet> mwPrdAcctSet = mwPrdAcctSetService.findAllByPrdSeqAndCrntRecFlg(prdSeq);
        return ResponseEntity.ok().body(mwPrdAcctSet);
    }


    @DeleteMapping("/mw-prd-acct-set/{prdAcctSetSeq}")
    @Timed
    public ResponseEntity<Map> deletePrdAcctSet(@PathVariable Long prdAcctSetSeq) {
        log.debug("REST request to delete MwPrdAcctSet : {}", prdAcctSetSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdAcctSetService.delete(prdAcctSetSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}

