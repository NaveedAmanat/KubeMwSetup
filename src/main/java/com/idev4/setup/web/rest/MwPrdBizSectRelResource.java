package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdBizSectRel;
import com.idev4.setup.dto.ProductBusinessSectorRelationDto;
import com.idev4.setup.service.MwPrdBizSectRelService;
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
public class MwPrdBizSectRelResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdBizSectRelResource.class);

    private final MwPrdBizSectRelService mwPrdBizSectRelService;

    public MwPrdBizSectRelResource(MwPrdBizSectRelService mwPrdBizSectRelService) {
        this.mwPrdBizSectRelService = mwPrdBizSectRelService;
    }

    @PostMapping("/add-new-prd-biz-sect-rel")
    @Timed
    public ResponseEntity<Map> createMwPrdBizSectRel(@RequestBody ProductBusinessSectorRelationDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdBizSectRel : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdBizSectRel prdBizSectRel = mwPrdBizSectRelService.addNewPrdBizSectRel(dto, currUser);
        Map<String, MwPrdBizSectRel> respData = new HashMap<String, MwPrdBizSectRel>();
        respData.put("PrdBizSectRel", prdBizSectRel);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-prd-biz-sect-rel")
    @Timed
    public ResponseEntity<Map> updateMwBizSectRel(@RequestBody ProductBusinessSectorRelationDto dto) throws URISyntaxException {
        log.debug("REST request to update MwPrdBizSectRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdBizSectRel mwPrdBizSectRel = mwPrdBizSectRelService.updateExistingProductBusinessSectorRelation(dto, currUser);
        if (mwPrdBizSectRel == null) {
            resp.put("error", " Product Business Sector Relation Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwPrdBizSectRel> respData = new HashMap<String, MwPrdBizSectRel>();
        respData.put("MwPrdBizSectRel", mwPrdBizSectRel);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-biz-sect-rel/{prdBizSectRelSeq}")
    @Timed
    public ResponseEntity<MwPrdBizSectRel> getPrdBizSectRelBySeq(@PathVariable Long prdBizSectRelSeq) {
        log.debug("REST request to get All MwPrdBizSectRel : {}", prdBizSectRelSeq);
        MwPrdBizSectRel mwPrdBizSectRel = mwPrdBizSectRelService.findOne(prdBizSectRelSeq);
        return ResponseEntity.ok().body(mwPrdBizSectRel);
    }

    @GetMapping("/mw-prd-biz-sect-rel")
    @Timed
    public ResponseEntity<List> getPrdBizSectRel() {
        log.debug("REST request to get All MwPrdBizSectRel : {}");
        List<MwPrdBizSectRel> mwPrdBizSectRel = mwPrdBizSectRelService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwPrdBizSectRel);
    }


    @GetMapping("/mw-prd-biz-sect-rel-history/{prdBizSectRelSeq}")
    @Timed
    public ResponseEntity<List> getPrdBizSectRelHistory(@PathVariable Long prdBizSectRelSeq) {
        log.debug("REST request to get MwPrdBizSectRel : {}", prdBizSectRelSeq);
        List<MwPrdBizSectRel> mwPrdBizSectRel = mwPrdBizSectRelService.findAllByPrdBizSectRelSeq(prdBizSectRelSeq);
        return ResponseEntity.ok().body(mwPrdBizSectRel);
    }

    @GetMapping("/mw-prd-biz-sect-rel-by-prd-seq/{prdSeq}")
    @Timed
    public ResponseEntity<List<MwPrdBizSectRel>> getPrdBizSectRelByPrdSeq(@PathVariable Long prdSeq) {
        log.debug("REST request to get All MwPrdBizSectRel for product seq: {}", prdSeq);
        List<MwPrdBizSectRel> mwPrdBizSectRel = mwPrdBizSectRelService.findAllByPrdSeqAndCrntRecFlg(prdSeq);
        return ResponseEntity.ok().body(mwPrdBizSectRel);
    }


    @DeleteMapping("/mw-prd-biz-sect-rel/{prdBizSectRelSeq}")
    @Timed
    public ResponseEntity<Map> deletePrdBizSectRel(@PathVariable Long prdBizSectRelSeq) {
        log.debug("REST request to delete MwPrdBizSectRel : {}", prdBizSectRelSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdBizSectRelService.delete(prdBizSectRelSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}

