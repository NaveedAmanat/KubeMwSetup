package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwHlthInsrPlan;
import com.idev4.setup.dto.HlthInsrPlanDto;
import com.idev4.setup.service.MwHlthInsrPlanService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class MwHlthInsrPlanResource {

    private static final String ENTITY_NAME = "mw_hlth_inst_plan";
    private final Logger log = LoggerFactory.getLogger(MwHlthInsrPlanResource.class);
    private final MwHlthInsrPlanService mwHlthInsrPlanService;

    public MwHlthInsrPlanResource(MwHlthInsrPlanService mwHlthInsrPlanService) {
        this.mwHlthInsrPlanService = mwHlthInsrPlanService;
    }

    @PostMapping("/add-new-hlth-insr-plan")
    @Timed
    public ResponseEntity<Map> createMwHlthInsrPlan(@RequestBody HlthInsrPlanDto hlthInsrPlanDto) throws URISyntaxException {
        log.debug("REST request to save MwHlthInsrPlanDto : {}", hlthInsrPlanDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (hlthInsrPlanDto.getPlanNm() == null || hlthInsrPlanDto.getPlanNm().isEmpty()) {
            resp.put("error", "Missing Plan Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (hlthInsrPlanDto.getAnlPremAmt() == null) {
            resp.put("error", "Missing Annual Premium Amount !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (hlthInsrPlanDto.getMaxPlcyAmt() == null) {
            resp.put("error", "Missing Max. Policy Amount !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (hlthInsrPlanDto.getPlanStsKey() == null) {
            resp.put("error", "Missing Status !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwHlthInsrPlan hlthInsrPlan = mwHlthInsrPlanService.addNewHlthInsrPlan(hlthInsrPlanDto, currUser);
        Map<String, MwHlthInsrPlan> respData = new HashMap<String, MwHlthInsrPlan>();
        respData.put("hlthInsrPlan", hlthInsrPlan);
        return ResponseEntity.ok().body(respData);
    }


    @PutMapping("/update-hlth-insr-plan")
    @Timed
    public ResponseEntity<Map> updateMwHlthInsrPlan(@RequestBody HlthInsrPlanDto hlthInsrPlanDto) throws URISyntaxException {
        log.debug("REST request to update MwHlthInsrPlan : {}", hlthInsrPlanDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (hlthInsrPlanDto.getHlthInsrPlanSeq() == null) {
            resp.put("error", "Missing Health Insurance Plan SEQ !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (hlthInsrPlanDto.getPlanNm() == null || hlthInsrPlanDto.getPlanNm().isEmpty()) {
            resp.put("error", "Missing Plan Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (hlthInsrPlanDto.getAnlPremAmt() == null) {
            resp.put("error", "Missing Annual Premium Amount !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (hlthInsrPlanDto.getMaxPlcyAmt() == null) {
            resp.put("error", "Missing Max. Policy Amount !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (hlthInsrPlanDto.getPlanStsKey() == null) {
            resp.put("error", "Missing Status !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwHlthInsrPlan hlthInsrPlan = mwHlthInsrPlanService.updateExistingExpnsTyp(hlthInsrPlanDto, currUser);
        if (hlthInsrPlan == null) {
            resp.put("error", "Health Insurance Plan Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwHlthInsrPlan> respData = new HashMap<String, MwHlthInsrPlan>();
        respData.put("hlthInsrPlan", hlthInsrPlan);
        return ResponseEntity.ok().body(respData);
    }


    /**
     * @param pageable
     * @return
     */
    @CrossOrigin(origins = {"http://localhost:4200"})
    @GetMapping("/mw-hlth-insr-plan")
    @Timed
    public ResponseEntity<List<MwHlthInsrPlan>> getAllMwHlthInsrPlan(Pageable pageable) {
        log.debug("REST request to get a page of MwHlthInsrPlan");
        List<MwHlthInsrPlan> hlthInsrPlan = mwHlthInsrPlanService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(hlthInsrPlan);
    }

    @GetMapping("/mw-hlth-insr-plan/{id}")
    @Timed
    public ResponseEntity<MwHlthInsrPlan> getMwHlthInsrPlan(@PathVariable Long id) {
        log.debug("REST request to get MwHlthInsrPlan : {}", id);
        MwHlthInsrPlan mwHlthInsrPlan = mwHlthInsrPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwHlthInsrPlan));
    }

    @GetMapping("/mw-hlth-insr-plan-history/{id}")
    @Timed
    public ResponseEntity<List> getMwExpnseTypsHistory(@PathVariable Long id) {
        log.debug("REST request to get MwHlthInsrPlan : {}", id);
        List<MwHlthInsrPlan> mwHlthInsrPlan = mwHlthInsrPlanService.findAllHistory(id);
        return ResponseEntity.ok().body(mwHlthInsrPlan);
    }


    @DeleteMapping("/mw-hlth-insr-plan/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwHlthInsrPlan(@PathVariable Long id) {
        log.debug("REST request to delete MwHlthInsrPlan : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwHlthInsrPlanService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }
}
