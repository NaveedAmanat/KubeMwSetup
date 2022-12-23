package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwBizSect;
import com.idev4.setup.dto.BusinessSectorDto;
import com.idev4.setup.service.MwBizSectService;
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
public class MwBizSectResource {

    private final Logger log = LoggerFactory.getLogger(MwBizSectResource.class);

    private final MwBizSectService mwBizSectService;

    public MwBizSectResource(MwBizSectService mwBizSectService) {
        this.mwBizSectService = mwBizSectService;
    }

    @PostMapping("/add-new-biz-sect")
    @Timed
    public ResponseEntity<Map> createMwBizSect(@RequestBody BusinessSectorDto dto) throws URISyntaxException {
        log.debug("REST request to save MwBizSect : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwBizSect rul = mwBizSectService.addNewBizSect(dto, currUser);
        Map<String, MwBizSect> respData = new HashMap<String, MwBizSect>();
        respData.put("BizSect", rul);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-biz-sect")
    @Timed
    public ResponseEntity<Map> updateBizSecte(@RequestBody BusinessSectorDto dto) throws URISyntaxException {
        log.debug("REST request to update MwBizSect : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwBizSect mwBizSect = mwBizSectService.updateExistingBusinessSector(dto, currUser);
        if (mwBizSect == null) {
            resp.put("error", "BizSecte Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwBizSect> respData = new HashMap<String, MwBizSect>();
        respData.put("MwBizSect", mwBizSect);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-biz-sect/{bizSectSeq}")
    @Timed
    public ResponseEntity<MwBizSect> getBizSectBySeq(@PathVariable Long bizSectSeq) {
        log.debug("REST request to get All MwBizSect : {}", bizSectSeq);
        MwBizSect mwBizSect = mwBizSectService.findOne(bizSectSeq);
        return ResponseEntity.ok().body(mwBizSect);
    }

    @GetMapping("/mw-biz-sect")
    @Timed
    public ResponseEntity<List> getBizSect() {
        log.debug("REST request to get All MwBizSect : {}");
        List<MwBizSect> mwBizSect = mwBizSectService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwBizSect);
    }


    @GetMapping("/mw-biz-sect-history/{bizSectSeq}")
    @Timed
    public ResponseEntity<List> getBizSectHistory(@PathVariable Long bizSectSeq) {
        log.debug("REST request to get MwBizSect : {}", bizSectSeq);
        List<MwBizSect> mwBizSect = mwBizSectService.findAllByBizSectSeq(bizSectSeq);
        return ResponseEntity.ok().body(mwBizSect);
    }

    @DeleteMapping("/mw-biz-sect/{bizSectSeq}")
    @Timed
    public ResponseEntity<Map> deleteBizSect(@PathVariable Long bizSectSeq) {
        log.debug("REST request to delete MwBizSect : {}", bizSectSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwBizSectService.delete(bizSectSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/mw-biz-sect-prd/{prdSeq}")
    @Timed
    public ResponseEntity<List> getBizSectPrd(@PathVariable Long prdSeq) {
        log.debug("REST request to get MwBizSect : {}", prdSeq);
        List<MwBizSect> mwBizSect = mwBizSectService.findAllByPrdSeq(prdSeq);
        return ResponseEntity.ok().body(mwBizSect);
    }

}

