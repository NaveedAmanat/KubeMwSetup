package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwAsocPrdRel;
import com.idev4.setup.domain.MwPrd;
import com.idev4.setup.dto.ProductDto;
import com.idev4.setup.service.MwAsocPrdRelService;
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
 * REST controller for managing MwPrd.
 */
@RestController
@RequestMapping("/api")
public class MwAsocPrdRelResource {

    private final Logger log = LoggerFactory.getLogger(MwAsocPrdRelResource.class);

    private final MwAsocPrdRelService mwAsocPrdRelService;

    public MwAsocPrdRelResource(MwAsocPrdRelService mwAsocPrdRelService) {
        this.mwAsocPrdRelService = mwAsocPrdRelService;
    }

    @PostMapping("/add-new-asoc-prd-rel")
    @Timed
    public ResponseEntity<Map> createMwPrd(@RequestBody MwAsocPrdRel rel) throws URISyntaxException {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> resp = new HashMap<String, String>();
        MwAsocPrdRel mwPrdRel = mwAsocPrdRelService.addNewRel(rel, currUser);
        Map<String, MwAsocPrdRel> respData = new HashMap<String, MwAsocPrdRel>();
        respData.put("rel", mwPrdRel);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/get-asoc-prd-rel/{prdSeq}")
    @Timed
    public ResponseEntity<List> getMwPrdsRels(@PathVariable Long prdSeq) {
        log.debug("REST request to get all MwPrd : {}");
        List<MwAsocPrdRel> mwPrds = mwAsocPrdRelService.getAllRelForProduct(prdSeq);
        return ResponseEntity.ok().body(mwPrds);
    }

    @DeleteMapping("/mw-asoc-prd-rel/{relSeq}")
    @Timed
    public ResponseEntity<Map> deleteMwPrd(@PathVariable Long relSeq) {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("REST request to delete MwPrd : {}", relSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwAsocPrdRelService.deleteRel(relSeq, currUser)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Not Found!!");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/get-asoc-prds-for-prd/{prdSeq}")
    @Timed
    public ResponseEntity<List> getMwPrds(@PathVariable Long prdSeq) {
        log.debug("REST request to get all MwPrd : {}");
        List<MwPrd> mwPrds = mwAsocPrdRelService.getAssocProductsForProduct(prdSeq);
        return ResponseEntity.ok().body(mwPrds);
    }

    @PostMapping("/get-asoc-prds-for-client")
    @Timed
    public ResponseEntity<List> getMwwPrdsForClient(@RequestBody ProductDto prdDto) {
        log.debug("REST request to get all MwPrd : {}");
        List<ProductDto> mwPrds = mwAsocPrdRelService.getAssocProductsForClient(prdDto);
        return ResponseEntity.ok().body(mwPrds);
    }
}
