package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwRul;
import com.idev4.setup.dto.RuleDto;
import com.idev4.setup.service.MwRulService;
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
public class MwRulResource {

    private final Logger log = LoggerFactory.getLogger(MwRulResource.class);

    private final MwRulService mwRulService;

    public MwRulResource(MwRulService mwRulService) {
        this.mwRulService = mwRulService;
    }

    @PostMapping("/add-new-rul")
    @Timed
    public ResponseEntity<Map> createMwRul(@RequestBody RuleDto dto) throws URISyntaxException {
        log.debug("REST request to save MwRul : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwRul rul = mwRulService.addNewRul(dto, currUser);
        Map<String, MwRul> respData = new HashMap<String, MwRul>();
        respData.put("Rul", rul);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-rul")
    @Timed
    public ResponseEntity<Map> updateRule(@RequestBody RuleDto dto) throws URISyntaxException {
        log.debug("REST request to update MwRul : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwRul mwRul = mwRulService.updateExistingRule(dto, currUser);
        if (mwRul == null) {
            resp.put("error", "Rule Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwRul> respData = new HashMap<String, MwRul>();
        respData.put("MwRul", mwRul);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-rul/{rulSeq}")
    @Timed
    public ResponseEntity<MwRul> getRulBySeq(@PathVariable Long rulSeq) {
        log.debug("REST request to get All MwRul : {}", rulSeq);
        MwRul mwRul = mwRulService.findOne(rulSeq);
        return ResponseEntity.ok().body(mwRul);
    }

    @GetMapping("/mw-rul")
    @Timed
    public ResponseEntity<List> getRul() {
        log.debug("REST request to get All MwRul : {}");
        List<MwRul> mwRul = mwRulService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwRul);
    }

    @GetMapping("/mw-rul-paged")
    @Timed
    public ResponseEntity<Map<String, Object>> getRul(
        @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
        @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to get All MwRul : {}");
        Map<String, Object> mwRul = mwRulService.findAllByCrntRecFlg(pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(mwRul);
    }


    @GetMapping("/mw-rul-history/{rulSeq}")
    @Timed
    public ResponseEntity<List> getRulHistory(@PathVariable Long rulSeq) {
        log.debug("REST request to get MwRul : {}", rulSeq);
        List<MwRul> mwRul = mwRulService.findAllByRulSeq(rulSeq);
        return ResponseEntity.ok().body(mwRul);
    }

    @DeleteMapping("/mw-rul/{rulSeq}")
    @Timed
    public ResponseEntity<Map> deleteRul(@PathVariable Long rulSeq) {
        log.debug("REST request to delete MwRul : {}", rulSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwRulService.delete(rulSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }
}

