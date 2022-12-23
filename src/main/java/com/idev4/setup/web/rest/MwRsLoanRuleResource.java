package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.dto.MwRsLoanRuleDTO;
import com.idev4.setup.service.MwRsLoanRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MwRsLoanRuleResource {

    private final Logger log = LoggerFactory.getLogger(MwRsLoanRuleResource.class);

    @Autowired
    MwRsLoanRuleService mwRsLoanRuleService;

    @GetMapping("/get-mw-rs-loan-rule")
    @Timed
    public ResponseEntity<Map<String, Object>> getMwRsLoanRule(@RequestHeader Map<String, Object> reqHeader,
                                                               @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
                                                               @RequestParam String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to getMwRsLoanRule");

        Map<String, Object> mwRsLoanRule = mwRsLoanRuleService.getMwRsLoanRule(pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(mwRsLoanRule);
    }

    @PostMapping("/add-mw-rs-loan-rule")
    @Timed
    public ResponseEntity<Map> addMwRsLoanRule(@RequestBody MwRsLoanRuleDTO mwRsLoanRuleDTO) throws URISyntaxException {
        log.debug("REST request to save mwBrnchTrgt : {}", mwRsLoanRuleDTO);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = mwRsLoanRuleService.addMwRsLoanRule(mwRsLoanRuleDTO, currUser);

        if (info == 1) {
            respData.put("rule", "Successfully added.");
        } else if (info == 0) {
            respData.put("rule", "Could not be added.");
        }
        return ResponseEntity.ok().body(respData);

    }

    @PutMapping("/delete-mw-rs-loan-rule/{ruleSeq}")
    @Timed
    public ResponseEntity<Map> deleteMwRsLoanRule(@PathVariable Long ruleSeq) throws URISyntaxException {
        log.debug("REST request to delete MwRsLoanRule : {}", ruleSeq);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = mwRsLoanRuleService.deleteMwRsLoanRule(ruleSeq, currUser);

        if (info == 1) {
            respData.put("rule", "Successfully deleted.");
        } else if (info == 0) {
            respData.put("rule", "Could not be deleted.");
        }
        return ResponseEntity.ok().body(respData);

    }

    @PutMapping("/disable-mw-rs-loan-rule/{ruleSeq}")
    @Timed
    public ResponseEntity<Map> disableMwRsLoanRule(@PathVariable Long ruleSeq) throws URISyntaxException {
        log.debug("REST request to disable MwRsLoanRule : {}", ruleSeq);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = mwRsLoanRuleService.disableMwRsLoanRule(ruleSeq, currUser);

        if (info == 1) {
            respData.put("rule", "Successfully disabled.");
        } else if (info == 2) {
            respData.put("rule", "Successfully enabled.");
        } else if (info == 0) {
            respData.put("rule", "Could not be changed.");
            return ResponseEntity.badRequest().body(respData);
        }
        return ResponseEntity.ok().body(respData);

    }

    @PutMapping("/update-mw-rs-loan-rule")
    @Timed
    public ResponseEntity<Map> updateMwRsLoanRule(@RequestBody MwRsLoanRuleDTO mwRsLoanRuleDTO) throws URISyntaxException {
        log.debug("REST request to update MwRsLoanRule : {}", mwRsLoanRuleDTO);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = mwRsLoanRuleService.updateMwRsLoanRule(mwRsLoanRuleDTO, currUser);

        if (info == 1) {
            respData.put("rule", "Successfully deleted.");
        } else if (info == 0) {
            respData.put("rule", "Could not be deleted.");
            return ResponseEntity.badRequest().body(respData);
        }
        return ResponseEntity.ok().body(respData);

    }
}
