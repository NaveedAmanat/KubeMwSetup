package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwBrnchRemitRel;
import com.idev4.setup.dto.BranchRemitRelationDto;
import com.idev4.setup.service.MwBrnchRemitRelService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MwBrnchRemitRelResource {

    private final Logger log = LoggerFactory.getLogger(MwBrnchRemitRelResource.class);

    private final MwBrnchRemitRelService mwBrnchRemitRelService;

    public MwBrnchRemitRelResource(MwBrnchRemitRelService mwBrnchRemitRelService) {
        this.mwBrnchRemitRelService = mwBrnchRemitRelService;
    }

    @PostMapping("/add-new-brnch-remit-rel")
    @Timed
    public ResponseEntity<Map> createMwBrnchRemitRel(@RequestBody BranchRemitRelationDto dto) throws URISyntaxException {
        log.debug("REST request to save MwBrnchRemitRel : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();


        MwBrnchRemitRel mwBrnchRemitRel = mwBrnchRemitRelService.addNewMwBrnchRemitRel(dto, currUser);
        Map<String, MwBrnchRemitRel> respData = new HashMap<String, MwBrnchRemitRel>();
        respData.put("mwBrnchRemitRel", mwBrnchRemitRel);
        return ResponseEntity.ok().body(respData);
    }


    @PutMapping("/update-branch-remit-rel")
    @Timed
    public ResponseEntity<Map> updateMwBrnchRemitRel(@RequestBody BranchRemitRelationDto dto) throws URISyntaxException {
        log.debug("REST request to update MwBrnchRemitRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();


        MwBrnchRemitRel mwBrnchRemitRel = mwBrnchRemitRelService.updateExistingMwBrnchRemitRel(dto, currUser);
        if (mwBrnchRemitRel == null) {
            resp.put("error", "MwBrnchRemitRel Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwBrnchRemitRel> respData = new HashMap<String, MwBrnchRemitRel>();
        respData.put("mwBrnchRemitRel", mwBrnchRemitRel);
        return ResponseEntity.ok().body(respData);
    }


    /**
     * @param pageable
     * @return
     */
    @GetMapping("/mw-brnch-remit-rel-by-brnch-seq/{brnchSeq}")
    @Timed
    public ResponseEntity<List<MwBrnchRemitRel>> getAllMwBrnchRemitRel(@PathVariable Long brnchSeq) {
        log.debug("REST request to get a page of MwBrnchRemitRel");
        List<MwBrnchRemitRel> form = mwBrnchRemitRelService.findAllByBranchSeqCurrentRecord(brnchSeq);
        return ResponseEntity.ok().body(form);
    }

    @GetMapping("/mw-brnch-remit-rel/{id}")
    @Timed
    public ResponseEntity<MwBrnchRemitRel> getMwBrnchRemitRel(@PathVariable Long id) {
        log.debug("REST request to get MwBrnchRemitRel : {}", id);
        MwBrnchRemitRel mwBrnchRemitRel = mwBrnchRemitRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBrnchRemitRel));
    }

    @DeleteMapping("/mw-brnch-remit-rel/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwBrnchRemitRel(@PathVariable Long id) {
        log.debug("REST request to delete MwBrnchRemitRel : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwBrnchRemitRelService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}

