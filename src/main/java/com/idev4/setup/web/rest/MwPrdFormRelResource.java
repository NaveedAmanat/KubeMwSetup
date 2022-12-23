package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdFormRel;
import com.idev4.setup.dto.ProductFormRelationDto;
import com.idev4.setup.service.MwPrdFormRelService;
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
public class MwPrdFormRelResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdFormRelResource.class);

    private final MwPrdFormRelService mwPrdFormRelService;

    public MwPrdFormRelResource(MwPrdFormRelService mwPrdFormRelService) {
        this.mwPrdFormRelService = mwPrdFormRelService;
    }

    @PostMapping("/add-new-prd-form-rel")
    @Timed
    public ResponseEntity<Map> createMwPrdFormRel(@RequestBody ProductFormRelationDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdFormRel : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdFormRel prdFormRel = mwPrdFormRelService.addNewPrdFormRel(dto, currUser);
        Map<String, MwPrdFormRel> respData = new HashMap<String, MwPrdFormRel>();
        respData.put("PrdFormRel", prdFormRel);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-prd-form-rel")
    @Timed
    public ResponseEntity<Map> updateMwFormRel(@RequestBody ProductFormRelationDto dto) throws URISyntaxException {
        log.debug("REST request to update MwPrdFormRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdFormRel mwPrdFormRel = mwPrdFormRelService.updateExistingProductFormRelation(dto, currUser);
        if (mwPrdFormRel == null) {
            resp.put("error", " Product Form Relation Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwPrdFormRel> respData = new HashMap<String, MwPrdFormRel>();
        respData.put("MwPrdFormRel", mwPrdFormRel);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-form-rel/{prdFormRelSeq}")
    @Timed
    public ResponseEntity<MwPrdFormRel> getPrdFormRelBySeq(@PathVariable Long prdFormRelSeq) {
        log.debug("REST request to get All MwPrdFormRel : {}", prdFormRelSeq);
        MwPrdFormRel mwPrdFormRel = mwPrdFormRelService.findOne(prdFormRelSeq);
        return ResponseEntity.ok().body(mwPrdFormRel);
    }

    @GetMapping("/mw-prd-form-rel")
    @Timed
    public ResponseEntity<List> getPrdFormRel() {
        log.debug("REST request to get All MwPrdFormRel : {}");
        List<MwPrdFormRel> mwPrdFormRel = mwPrdFormRelService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwPrdFormRel);
    }

    @GetMapping("/mw-prd-form-rel-by-prd-seq/{prdSeq}")
    @Timed
    public ResponseEntity<List<MwPrdFormRel>> getPrdFormRelByPrdSeq(@PathVariable Long prdSeq) {
        log.debug("REST request to get All MwPrdFormRel for product seq: {}", prdSeq);
        List<MwPrdFormRel> mwPrdFormRel = mwPrdFormRelService.findAllByPrdSeqAndCrntRecFlg(prdSeq);
        return ResponseEntity.ok().body(mwPrdFormRel);
    }

    @DeleteMapping("/mw-prd-form-rel/{prdFormRelSeq}")
    @Timed
    public ResponseEntity<Map> deletePrdFormRel(@PathVariable Long prdFormRelSeq) {
        log.debug("REST request to delete MwPrdFormRel : {}", prdFormRelSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdFormRelService.delete(prdFormRelSeq, SecurityContextHolder.getContext().getAuthentication().getName())) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}
