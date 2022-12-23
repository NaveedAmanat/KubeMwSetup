package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwQstnr;
import com.idev4.setup.dto.QuestionnaireDto;
import com.idev4.setup.service.MwQstnrService;
import com.idev4.setup.web.rest.util.HeaderUtil;
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

/**
 * REST controller for managing MwQstnr.
 */
@RestController
@RequestMapping("/api")
public class MwQstnrResource {

    private static final String ENTITY_NAME = "mwQstnr";
    private final Logger log = LoggerFactory.getLogger(MwQstnrResource.class);
    private final MwQstnrService mwQstnrService;

    public MwQstnrResource(MwQstnrService mwQstnrService) {
        this.mwQstnrService = mwQstnrService;
    }

    @PostMapping("/add-new-qstnr")
    @Timed
    public ResponseEntity<Map> createMwQstnr(@RequestBody QuestionnaireDto qstnrDto) throws URISyntaxException {
        log.debug("REST request to save MwQstnr : {}", qstnrDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwQstnr qstnr = mwQstnrService.addNewQstnr(qstnrDto, currUser);
        Map<String, MwQstnr> respData = new HashMap<String, MwQstnr>();
        respData.put("qstnr", qstnr);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-qstnr")
    @Timed
    public ResponseEntity<Map> updateMwQstnr(@RequestBody QuestionnaireDto qstnrDto) throws URISyntaxException {
        log.debug("REST request to update MwQstnr : {}", qstnrDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwQstnr qstnr = mwQstnrService.UpdateExistingQstnr(qstnrDto, currUser);
        if (qstnr == null) {
            resp.put("error", "Questionnaire Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwQstnr> respData = new HashMap<String, MwQstnr>();
        respData.put("qstnr", qstnr);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-qstnr")
    @Timed
    public ResponseEntity<List<MwQstnr>> getAllMwQstnr(Pageable pageable) {
        log.debug("REST request to get a page of MwQstnr");
        List<MwQstnr> qstnr = mwQstnrService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(qstnr);
    }

    @GetMapping("/mw-qstnr/{qstnrSeq}")
    @Timed
    public ResponseEntity<MwQstnr> getMwQstnr(@PathVariable Long qstnrSeq) {
        log.debug("REST request to get MwQstnr : {}", qstnrSeq);
        MwQstnr mwQstnr = mwQstnrService.findOne(qstnrSeq);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwQstnr));
    }

    @GetMapping("/qstnr-history/{qstnrSeq}")
    @Timed
    public ResponseEntity<List> getMwQstnrHistory(@PathVariable Long qstnrSeq) {
        log.debug("REST request to get MwQstnr : {}", qstnrSeq);
        List<MwQstnr> mwQstnr = mwQstnrService.findAllHistory(qstnrSeq);
        return ResponseEntity.ok().body(mwQstnr);
    }

    @DeleteMapping("/mw-qstnr/{qstnrSeq}")
    @Timed
    public ResponseEntity<Void> deleteMwQstnr(@PathVariable Long qstnrSeq) {
        log.debug("REST request to delete MwQstnr : {}", qstnrSeq);
        mwQstnrService.delete(qstnrSeq);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, qstnrSeq.toString())).build();
    }
}
