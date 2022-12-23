package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwBizActy;
import com.idev4.setup.dto.BusinessActyDto;
import com.idev4.setup.service.MwBizActyService;
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
public class MwBizActyResource {

    private final Logger log = LoggerFactory.getLogger(MwBizActyResource.class);

    private final MwBizActyService mwBizActyService;

    public MwBizActyResource(MwBizActyService mwBizActyService) {
        this.mwBizActyService = mwBizActyService;
    }

    @PostMapping("/add-new-biz-acty")
    @Timed
    public ResponseEntity<Map> createMwBizActy(@RequestBody BusinessActyDto dto) throws URISyntaxException {
        log.debug("REST request to save MwBizActy : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwBizActy rul = mwBizActyService.addNewBizActy(dto, currUser);
        Map<String, MwBizActy> respData = new HashMap<String, MwBizActy>();
        respData.put("BizActy", rul);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-biz-acty")
    @Timed
    public ResponseEntity<Map> updateBizActy(@RequestBody BusinessActyDto dto) throws URISyntaxException {
        log.debug("REST request to update MwBizActy : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwBizActy mwBizActy = mwBizActyService.updateExistingBusinessActy(dto, currUser);
        if (mwBizActy == null) {
            resp.put("error", "BizActy Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwBizActy> respData = new HashMap<String, MwBizActy>();
        respData.put("MwBizActy", mwBizActy);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-biz-acty/{bizActySeq}")
    @Timed
    public ResponseEntity<MwBizActy> getBizActyBySeq(@PathVariable Long bizActySeq) {
        log.debug("REST request to get All MwBizActy : {}", bizActySeq);
        MwBizActy mwBizActy = mwBizActyService.findOne(bizActySeq);
        return ResponseEntity.ok().body(mwBizActy);
    }

    @GetMapping("/mw-biz-acty")
    @Timed
    public ResponseEntity<List> getBizActy() {
        log.debug("REST request to get All MwBizActy : {}");
        List<MwBizActy> mwBizActy = mwBizActyService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwBizActy);
    }

    @GetMapping("/mw-biz-acty-history/{bizActySeq}")
    @Timed
    public ResponseEntity<List> getBizActyHistory(@PathVariable Long bizActySeq) {
        log.debug("REST request to get MwBizActy : {}", bizActySeq);
        List<MwBizActy> mwBizActy = mwBizActyService.findAllByBizActySeq(bizActySeq);
        return ResponseEntity.ok().body(mwBizActy);
    }

    @DeleteMapping("/mw-biz-acty/{bizActySeq}")
    @Timed
    public ResponseEntity<Map> deleteBizActy(@PathVariable Long bizActySeq) {
        log.debug("REST request to delete MwBizActy : {}", bizActySeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwBizActyService.delete(bizActySeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/mw-biz-acty-sect/{bizSectSeq}")
    @Timed
    public ResponseEntity<List> getBizActyBySect(@PathVariable Long bizSectSeq) {
        log.debug("REST request to get bizSectSeq : {}", bizSectSeq);
        List<MwBizActy> mwBizActy = mwBizActyService.findAllBySectSeqComplete(bizSectSeq);
        return ResponseEntity.ok().body(mwBizActy);
    }

    @GetMapping("/mw-biz-acty-sect-active/{bizSectSeq}")
    @Timed
    public ResponseEntity<List> getActiveBizActyBySect(@PathVariable Long bizSectSeq) {
        log.debug("REST request to get bizSectSeq : {}", bizSectSeq);
        List<MwBizActy> mwBizActy = mwBizActyService.findAllBySectSeq(bizSectSeq);
        return ResponseEntity.ok().body(mwBizActy);
    }

    @GetMapping("/mw-biz-acty-by-acty-seq/{bizActySeq}")
    @Timed
    public ResponseEntity<List> getBizActyByActySeq(@PathVariable Long bizActySeq) {
        log.debug("REST request to get bizActySeq : {}", bizActySeq);
        List<MwBizActy> mwBizActy = mwBizActyService.findAllByActySeq(bizActySeq);
        return ResponseEntity.ok().body(mwBizActy);
    }
}
