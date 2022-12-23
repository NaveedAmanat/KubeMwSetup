package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdLoanTrm;
import com.idev4.setup.dto.ProductLoanTermsDto;
import com.idev4.setup.service.MwPrdLoanTrmService;
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
public class MwPrdLoanTrmResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdLoanTrmResource.class);

    private final MwPrdLoanTrmService mwPrdLoanTrmService;

    public MwPrdLoanTrmResource(MwPrdLoanTrmService mwPrdLoanTrmService) {
        this.mwPrdLoanTrmService = mwPrdLoanTrmService;
    }

    @PostMapping("/add-new-prd-loan-trm")
    @Timed
    public ResponseEntity<Map> createMwPrdLoanTrm(@RequestBody ProductLoanTermsDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdLoanTrm : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdLoanTrm prdLoanTrm = mwPrdLoanTrmService.addNewPrdLoanTrm(dto, currUser);
        Map<String, MwPrdLoanTrm> respData = new HashMap<String, MwPrdLoanTrm>();
        respData.put("PrdLoanTrm", prdLoanTrm);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-prd-loan-trm")
    @Timed
    public ResponseEntity<Map> updateMwLoanTrmTyps(@RequestBody ProductLoanTermsDto dto) throws URISyntaxException {
        log.debug("REST request to update MwPrdLoanTrm : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdLoanTrm mwPrdLoanTrm = mwPrdLoanTrmService.updateExistingProductLoanTerms(dto, currUser);
        if (mwPrdLoanTrm == null) {
            resp.put("error", " Product Loan Terms Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwPrdLoanTrm> respData = new HashMap<String, MwPrdLoanTrm>();
        respData.put("MwPrdLoanTrm", mwPrdLoanTrm);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-loan-trm/{prdLoanTrmSeq}")
    @Timed
    public ResponseEntity<MwPrdLoanTrm> getPrdLoanTrmBySeq(@PathVariable Long prdLoanTrmSeq) {
        log.debug("REST request to get All MwPrdLoanTrm : {}", prdLoanTrmSeq);
        MwPrdLoanTrm mwPrdLoanTrm = mwPrdLoanTrmService.findOne(prdLoanTrmSeq);
        return ResponseEntity.ok().body(mwPrdLoanTrm);
    }

    @GetMapping("/mw-prd-loan-trm")
    @Timed
    public ResponseEntity<List> getPrdLoanTrm() {
        log.debug("REST request to get All MwPrdLoanTrm : {}");
        List<MwPrdLoanTrm> mwPrdLoanTrm = mwPrdLoanTrmService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwPrdLoanTrm);
    }


    @GetMapping("/mw-prd-loan-trm-history/{prdLoanTrmSeq}")
    @Timed
    public ResponseEntity<List> getPrdLoanTrmHistory(@PathVariable Long prdLoanTrmSeq) {
        log.debug("REST request to get MwPrdLoanTrm : {}", prdLoanTrmSeq);
        List<MwPrdLoanTrm> mwPrdLoanTrm = mwPrdLoanTrmService.findAllByPrdTrmSeq(prdLoanTrmSeq);
        return ResponseEntity.ok().body(mwPrdLoanTrm);
    }

    @GetMapping("/mw-prd-loan-trm-by-prd-seq/{prdSeq}")
    @Timed
    public ResponseEntity<List<MwPrdLoanTrm>> getPrdLoanTrmByPrdSeq(@PathVariable Long prdSeq) {
        log.debug("REST request to get All MwPrdLoanTrm for product seq: {}", prdSeq);
        List<MwPrdLoanTrm> mwPrdLoanTrm = mwPrdLoanTrmService.findAllByPrdSeqAndCrntRecFlg(prdSeq);
        return ResponseEntity.ok().body(mwPrdLoanTrm);
    }


    @DeleteMapping("/mw-prd-loan-trm/{prdLoanTrmSeq}")
    @Timed
    public ResponseEntity<Map> deletePrdLoanTrm(@PathVariable Long prdLoanTrmSeq) {
        log.debug("REST request to delete MwPrdLoanTrm : {}", prdLoanTrmSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdLoanTrmService.delete(prdLoanTrmSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}

