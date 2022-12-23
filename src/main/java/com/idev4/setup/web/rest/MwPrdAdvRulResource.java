package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdAdvRul;
import com.idev4.setup.dto.ProductAdvanceRuleDto;
import com.idev4.setup.service.MwPrdAdvRulService;
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
public class MwPrdAdvRulResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdAdvRulResource.class);

    private final MwPrdAdvRulService mwPrdAdvRulService;

    public MwPrdAdvRulResource(MwPrdAdvRulService mwPrdAdvRulService) {
        this.mwPrdAdvRulService = mwPrdAdvRulService;
    }

    @PostMapping("/add-new-prd-adv-rul")
    @Timed
    public ResponseEntity<Map> createMwPrdAdvRul(@RequestBody ProductAdvanceRuleDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdAdvRul : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdAdvRul prdAdvRul = mwPrdAdvRulService.addNewPrdAdvRul(dto, currUser);
        Map<String, MwPrdAdvRul> respData = new HashMap<String, MwPrdAdvRul>();
        respData.put("PrdAdvRul", prdAdvRul);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-prd-adv-rul")
    @Timed
    public ResponseEntity<Map> updateMwAdvRul(@RequestBody ProductAdvanceRuleDto dto) throws URISyntaxException {
        log.debug("REST request to update MwPrdAdvRul : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdAdvRul mwPrdAdvRul = mwPrdAdvRulService.updateExistingProductAdvanceRule(dto, currUser);
        if (mwPrdAdvRul == null) {
            resp.put("error", " Product Advance Rule Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwPrdAdvRul> respData = new HashMap<String, MwPrdAdvRul>();
        respData.put("MwPrdAdvRul", mwPrdAdvRul);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-adv-rul-by-prd-seq/{prdSeq}")
    @Timed
    public ResponseEntity<List<ProductAdvanceRuleDto>> getPrdAdvRulByPrdSeq(@PathVariable Long prdSeq) {
        log.debug("REST request to get All MwPrdAdvRul for product seq: {}", prdSeq);
        List<ProductAdvanceRuleDto> productAdvanceRuleDto = mwPrdAdvRulService.findAllByPrdSeqAndCrntRecFlg(prdSeq);
        return ResponseEntity.ok().body(productAdvanceRuleDto);
    }


    @GetMapping("/mw-prd-adv-rul/{prdAdvRulSeq}")
    @Timed
    public ResponseEntity<MwPrdAdvRul> getPrdAdvRulBySeq(@PathVariable Long prdAdvRulSeq) {
        log.debug("REST request to get All MwPrdAdvRul : {}", prdAdvRulSeq);
        MwPrdAdvRul mwPrdAdvRul = mwPrdAdvRulService.findOne(prdAdvRulSeq);
        return ResponseEntity.ok().body(mwPrdAdvRul);
    }

    @GetMapping("/mw-prd-adv-rul")
    @Timed
    public ResponseEntity<List> getPrdAdvRul() {
        log.debug("REST request to get All MwPrdAdvRul : {}");
        List<MwPrdAdvRul> mwPrdAdvRul = mwPrdAdvRulService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwPrdAdvRul);
    }


    @GetMapping("/mw-prd-adv-rul-history/{prdAdvRulSeq}")
    @Timed
    public ResponseEntity<List> getPrdAdvRulHistory(@PathVariable Long prdAdvRulSeq) {
        log.debug("REST request to get MwPrdAdvRul : {}", prdAdvRulSeq);
        List<MwPrdAdvRul> mwPrdAdvRul = mwPrdAdvRulService.findAllByPrdAdvRulSeq(prdAdvRulSeq);
        return ResponseEntity.ok().body(mwPrdAdvRul);
    }


    @DeleteMapping("/mw-prd-adv-rul/{prdAdvRulSeq}")
    @Timed
    public ResponseEntity<Map> deletePrdAdvRul(@PathVariable Long prdAdvRulSeq) {
        log.debug("REST request to delete MwPrdAdvRul : {}", prdAdvRulSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdAdvRulService.delete(prdAdvRulSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}

