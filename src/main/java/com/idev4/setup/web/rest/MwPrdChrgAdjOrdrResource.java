package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdChrgAdjOrdr;
import com.idev4.setup.dto.ProductChargesAdjustmentOrderDto;
import com.idev4.setup.service.MwPrdChrgAdjOrdrService;
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
public class MwPrdChrgAdjOrdrResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdChrgAdjOrdrResource.class);

    private final MwPrdChrgAdjOrdrService mwPrdChrgAdjOrdrService;

    public MwPrdChrgAdjOrdrResource(MwPrdChrgAdjOrdrService mwPrdChrgAdjOrdrService) {
        this.mwPrdChrgAdjOrdrService = mwPrdChrgAdjOrdrService;
    }

    @PostMapping("/add-new-prd-chrg-adj-ordr")
    @Timed
    public ResponseEntity<Map> createMwPrdChrgAdjOrdr(@RequestBody ProductChargesAdjustmentOrderDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdChrgAdjOrdr : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdChrgAdjOrdr prdChrgAdjOrdr = mwPrdChrgAdjOrdrService.addNewPrdChrgAdjOrdr(dto, currUser);
        Map<String, MwPrdChrgAdjOrdr> respData = new HashMap<String, MwPrdChrgAdjOrdr>();
        respData.put("PrdChrgAdjOrdr", prdChrgAdjOrdr);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-prd-chrg-adj-ordr")
    @Timed
    public ResponseEntity<Map> updateMwChrgAdjOrdrTyps(@RequestBody ProductChargesAdjustmentOrderDto dto) throws URISyntaxException {
        log.debug("REST request to update MwPrdChrgAdjOrdr : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdChrgAdjOrdr mwPrdChrgAdjOrdr = mwPrdChrgAdjOrdrService.updateExistingProductPrincipalLimis(dto, currUser);
        if (mwPrdChrgAdjOrdr == null) {
            resp.put("error", " Product Charges Adjustment Order Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwPrdChrgAdjOrdr> respData = new HashMap<String, MwPrdChrgAdjOrdr>();
        respData.put("MwPrdChrgAdjOrdr", mwPrdChrgAdjOrdr);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-chrg-adj-ordr/{prdChrgAdjOrdrSeq}")
    @Timed
    public ResponseEntity<MwPrdChrgAdjOrdr> getPrdChrgAdjOrdrBySeq(@PathVariable Long prdChrgAdjOrdrSeq) {
        log.debug("REST request to get All MwPrdChrgAdjOrdr : {}", prdChrgAdjOrdrSeq);
        MwPrdChrgAdjOrdr mwPrdChrgAdjOrdr = mwPrdChrgAdjOrdrService.findOne(prdChrgAdjOrdrSeq);
        return ResponseEntity.ok().body(mwPrdChrgAdjOrdr);
    }

    @GetMapping("/mw-prd-chrg-adj-ordr")
    @Timed
    public ResponseEntity<List> getPrdChrgAdjOrdr() {
        log.debug("REST request to get All MwPrdChrgAdjOrdr : {}");
        List<MwPrdChrgAdjOrdr> mwPrdChrgAdjOrdr = mwPrdChrgAdjOrdrService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwPrdChrgAdjOrdr);
    }


    @GetMapping("/mw-prd-chrg-adj-ordr-history/{prdChrgAdjOrdrSeq}")
    @Timed
    public ResponseEntity<List> getPrdChrgAdjOrdrHistory(@PathVariable Long prdChrgAdjOrdrSeq) {
        log.debug("REST request to get MwPrdChrgAdjOrdr : {}", prdChrgAdjOrdrSeq);
        List<MwPrdChrgAdjOrdr> mwPrdChrgAdjOrdr = mwPrdChrgAdjOrdrService.findAllByPrdChrgAdjOrdrSeq(prdChrgAdjOrdrSeq);
        return ResponseEntity.ok().body(mwPrdChrgAdjOrdr);
    }

    @GetMapping("/mw-prd-chrg-adj-ordr-by-prd-seq/{prdSeq}")
    @Timed
    public ResponseEntity<List<MwPrdChrgAdjOrdr>> getPrdChrgAdjOrdrByPrdSeq(@PathVariable Long prdSeq) {
        log.debug("REST request to get All MwPrdChrgAdjOrdr for product seq: {}", prdSeq);
        List<MwPrdChrgAdjOrdr> mwPrdChrgAdjOrdr = mwPrdChrgAdjOrdrService.findAllByPrdSeqAndCrntRecFlg(prdSeq);
        return ResponseEntity.ok().body(mwPrdChrgAdjOrdr);
    }

    @DeleteMapping("/mw-prd-chrg-adj-ordr/{prdChrgAdjOrdrSeq}")
    @Timed
    public ResponseEntity<Map> deletePrdChrgAdjOrdr(@PathVariable Long prdChrgAdjOrdrSeq) {
        log.debug("REST request to delete MwPrdChrgAdjOrdr : {}", prdChrgAdjOrdrSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdChrgAdjOrdrService.delete(prdChrgAdjOrdrSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }
}

