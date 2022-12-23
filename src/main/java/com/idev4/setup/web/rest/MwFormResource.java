package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwForm;
import com.idev4.setup.dto.FormDto;
import com.idev4.setup.service.MwFormService;
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
 * REST controller for managing MwForm.
 */
@RestController
@RequestMapping("/api")
public class MwFormResource {

    private static final String ENTITY_NAME = "mw_form";
    private final Logger log = LoggerFactory.getLogger(MwFormResource.class);
    private final MwFormService mwFormService;

    public MwFormResource(MwFormService mwFormService) {
        this.mwFormService = mwFormService;
    }

    @PostMapping("/add-new-form")
    @Timed
    public ResponseEntity<Map> createMwForm(@RequestBody FormDto formDto) throws URISyntaxException {
        log.debug("REST request to save Mw_Form : {}", formDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (formDto.getFormId() == null || formDto.getFormId().isEmpty()) {
            resp.put("error", "Missing Form Id !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (formDto.getFormNm() == null || formDto.getFormNm().isEmpty()) {
            resp.put("error", "Missing Form !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (formDto.getFormUrl() == null || formDto.getFormUrl().isEmpty()) {
            resp.put("error", "Missing Form URL !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (formDto.getFormCls() == null || formDto.getFormCls().isEmpty()) {
            resp.put("error", "Missing Form Class !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (formDto.getFormStsKey() == null) {
            resp.put("error", "Missing Status !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwForm form = mwFormService.addNewFrom(formDto, currUser);
        Map<String, MwForm> respData = new HashMap<String, MwForm>();
        respData.put("form", form);
        return ResponseEntity.ok().body(respData);
    }


    @PutMapping("/update-form")
    @Timed
    public ResponseEntity<Map> updateMwForm(@RequestBody FormDto formDto) throws URISyntaxException {
        log.debug("REST request to update MwForm : {}", formDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (formDto.getFormSeq() == null) {
            resp.put("error", "Missing Form SEQ !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (formDto.getFormId() == null || formDto.getFormId().isEmpty()) {
            resp.put("error", "Missing Form Id !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (formDto.getFormNm() == null || formDto.getFormNm().isEmpty()) {
            resp.put("error", "Missing Form !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (formDto.getFormUrl() == null || formDto.getFormUrl().isEmpty()) {
            resp.put("error", "Missing Form URL !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (formDto.getFormCls() == null || formDto.getFormCls().isEmpty()) {
            resp.put("error", "Missing Form Class !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (formDto.getFormStsKey() == null) {
            resp.put("error", "Missing Status !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwForm form = mwFormService.updateExistingForm(formDto, currUser);
        if (form == null) {
            resp.put("error", "Form Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwForm> respData = new HashMap<String, MwForm>();
        respData.put("form", form);
        return ResponseEntity.ok().body(respData);
    }


    /**
     * @param pageable
     * @return
     */
    @GetMapping("/mw-form")
    @Timed
    public ResponseEntity<List<MwForm>> getAllMwForm(Pageable pageable) {
        log.debug("REST request to get a page of MwForm");
        List<MwForm> form = mwFormService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(form);
    }

    @GetMapping("/mw-form/{id}")
    @Timed
    public ResponseEntity<MwForm> getMwForm(@PathVariable Long id) {
        log.debug("REST request to get MwForm : {}", id);
        MwForm mwForm = mwFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwForm));
    }

    @GetMapping("/mw-form-history/{id}")
    @Timed
    public ResponseEntity<List> getMwFormHistory(@PathVariable Long id) {
        log.debug("REST request to get MwForm : {}", id);
        List<MwForm> mwForm = mwFormService.findAllHistory(id);
        return ResponseEntity.ok().body(mwForm);
    }


    @DeleteMapping("/mw-form/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwForm(@PathVariable Long id) {
        log.debug("REST request to delete MwForm : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwFormService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}
