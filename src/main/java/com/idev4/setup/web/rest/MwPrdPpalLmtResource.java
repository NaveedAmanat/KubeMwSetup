package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdPpalLmt;
import com.idev4.setup.dto.ProductPrincipalLimitDto;
import com.idev4.setup.service.MwPrdPpalLmtService;
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
public class MwPrdPpalLmtResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdPpalLmtResource.class);

    private final MwPrdPpalLmtService mwPrdPpalLmtService;

    public MwPrdPpalLmtResource(MwPrdPpalLmtService mwPrdPpalLmtService) {
        this.mwPrdPpalLmtService = mwPrdPpalLmtService;
    }

    @PostMapping("/add-new-prd-ppal-lmt")
    @Timed
    public ResponseEntity<Map> createMwPrdPpalLmt(@RequestBody ProductPrincipalLimitDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdPpalLmt : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdPpalLmt prdPpalLmt = mwPrdPpalLmtService.addNewPrdPpalLmt(dto, currUser);
        Map<String, MwPrdPpalLmt> respData = new HashMap<String, MwPrdPpalLmt>();
        respData.put("PrdPpalLmt", prdPpalLmt);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-prd-ppal-lmt")
    @Timed
    public ResponseEntity<Map> updateMwPpalLmt(@RequestBody ProductPrincipalLimitDto dto) throws URISyntaxException {
        log.debug("REST request to update MwPrdPpalLmt : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdPpalLmt mwPrdPpalLmt = mwPrdPpalLmtService.updateExistingProductPrincipalLimits(dto, currUser);
        if (mwPrdPpalLmt == null) {
            resp.put("error", " Product Principal Limit Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwPrdPpalLmt> respData = new HashMap<String, MwPrdPpalLmt>();
        respData.put("MwPrdPpalLmt", mwPrdPpalLmt);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-ppal-lmt/{prdPpalLmtSeq}")
    @Timed
    public ResponseEntity<MwPrdPpalLmt> getPrdPpalLmtBySeq(@PathVariable Long prdPpalLmtSeq) {
        log.debug("REST request to get All MwPrdPpalLmt : {}", prdPpalLmtSeq);
        MwPrdPpalLmt mwPrdPpalLmt = mwPrdPpalLmtService.findOne(prdPpalLmtSeq);
        return ResponseEntity.ok().body(mwPrdPpalLmt);
    }

    @GetMapping("/mw-prd-ppal-lmt")
    @Timed
    public ResponseEntity<List> getPrdPpalLmt() {
        log.debug("REST request to get All MwPrdPpalLmt : {}");
        List<MwPrdPpalLmt> mwPrdPpalLmt = mwPrdPpalLmtService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwPrdPpalLmt);
    }


    @GetMapping("/mw-prd-ppal-lmt-history/{prdPpalLmtSeq}")
    @Timed
    public ResponseEntity<List> getPrdPpalLmtHistory(@PathVariable Long prdPpalLmtSeq) {
        log.debug("REST request to get MwPrdPpalLmt : {}", prdPpalLmtSeq);
        List<MwPrdPpalLmt> mwPrdPpalLmt = mwPrdPpalLmtService.findAllByPrdPpalLmtSeq(prdPpalLmtSeq);
        return ResponseEntity.ok().body(mwPrdPpalLmt);
    }

    @GetMapping("/mw-prd-ppal-lmt-by-prd-seq/{prdSeq}")
    @Timed
    public ResponseEntity<List<MwPrdPpalLmt>> getPrdPpalLmtByPrdSeq(@PathVariable Long prdSeq) {
        log.debug("REST request to get All MwPrdPpalLmt for product seq: {}", prdSeq);
        List<MwPrdPpalLmt> mwPrdPpalLmt = mwPrdPpalLmtService.findAllByPrdSeqAndCrntRecFlg(prdSeq);
        return ResponseEntity.ok().body(mwPrdPpalLmt);
    }

    @DeleteMapping("/mw-prd-ppal-lmt/{prdPpalLmtSeq}")
    @Timed
    public ResponseEntity<Map> deletePrdPpalLmt(@PathVariable Long prdPpalLmtSeq) {
        log.debug("REST request to delete MwPrdPpalLmt : {}", prdPpalLmtSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdPpalLmtService.delete(prdPpalLmtSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}

