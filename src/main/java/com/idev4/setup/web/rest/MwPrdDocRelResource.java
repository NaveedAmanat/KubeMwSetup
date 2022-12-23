package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdDocRel;
import com.idev4.setup.dto.ProductDocumentRelationDto;
import com.idev4.setup.service.MwPrdDocRelService;
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
public class MwPrdDocRelResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdDocRelResource.class);

    private final MwPrdDocRelService mwPrdDocRelService;

    public MwPrdDocRelResource(MwPrdDocRelService mwPrdDocRelService) {
        this.mwPrdDocRelService = mwPrdDocRelService;
    }

    @PostMapping("/add-new-prd-doc-rel")
    @Timed
    public ResponseEntity<Map> createMwPrdDocRel(@RequestBody ProductDocumentRelationDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdDocRel : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdDocRel prdDocRel = mwPrdDocRelService.addNewPrdDocRel(dto, currUser);
        Map<String, MwPrdDocRel> respData = new HashMap<String, MwPrdDocRel>();
        respData.put("PrdDocRel", prdDocRel);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-prd-doc-rel")
    @Timed
    public ResponseEntity<Map> updateMwDocRel(@RequestBody ProductDocumentRelationDto dto) throws URISyntaxException {
        log.debug("REST request to update MwPrdDocRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdDocRel mwPrdDocRel = mwPrdDocRelService.updateExistingProductDocumentRelation(dto, currUser);
        if (mwPrdDocRel == null) {
            resp.put("error", " Product Document Relation Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwPrdDocRel> respData = new HashMap<String, MwPrdDocRel>();
        respData.put("MwPrdDocRel", mwPrdDocRel);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-doc-rel/{prdDocRelSeq}")
    @Timed
    public ResponseEntity<MwPrdDocRel> getPrdDocRelBySeq(@PathVariable Long prdDocRelSeq) {
        log.debug("REST request to get All MwPrdDocRel : {}", prdDocRelSeq);
        MwPrdDocRel mwPrdDocRel = mwPrdDocRelService.findOne(prdDocRelSeq);
        return ResponseEntity.ok().body(mwPrdDocRel);
    }

    @GetMapping("/mw-prd-doc-rel")
    @Timed
    public ResponseEntity<List> getPrdDocRel() {
        log.debug("REST request to get All MwPrdDocRel : {}");
        List<MwPrdDocRel> mwPrdDocRel = mwPrdDocRelService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwPrdDocRel);
    }

    @GetMapping("/mw-prd-doc-rel-by-prd-seq/{prdSeq}")
    @Timed
    public ResponseEntity<List<MwPrdDocRel>> getPrdDocRelByPrdSeq(@PathVariable Long prdSeq) {
        log.debug("REST request to get All MwPrdDocRel for product seq: {}", prdSeq);
        List<MwPrdDocRel> mwPrdDocRel = mwPrdDocRelService.findAllByPrdSeqAndCrntRecFlg(prdSeq);
        return ResponseEntity.ok().body(mwPrdDocRel);
    }

    @DeleteMapping("/mw-prd-doc-rel/{prdDocRelSeq}")
    @Timed
    public ResponseEntity<Map> deletePrdDocRel(@PathVariable Long prdDocRelSeq) {
        log.debug("REST request to delete MwPrdDocRel : {}", prdDocRelSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdDocRelService.delete(prdDocRelSeq, SecurityContextHolder.getContext().getAuthentication().getName())) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}
