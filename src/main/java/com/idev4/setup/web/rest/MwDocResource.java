package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwDoc;
import com.idev4.setup.dto.DocumentDto;
import com.idev4.setup.service.MwDocService;
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
public class MwDocResource {

    private final Logger log = LoggerFactory.getLogger(MwDocResource.class);

    private final MwDocService mwDocService;

    public MwDocResource(MwDocService mwDocService) {
        this.mwDocService = mwDocService;
    }

    @PostMapping("/add-new-doc")
    @Timed
    public ResponseEntity<Map> createMwDoc(@RequestBody DocumentDto dto) throws URISyntaxException {
        log.debug("REST request to save MwDoc : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwDoc rul = mwDocService.addNewDoc(dto, currUser);
        Map<String, MwDoc> respData = new HashMap<String, MwDoc>();
        respData.put("Doc", rul);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-doc")
    @Timed
    public ResponseEntity<Map> updateDoce(@RequestBody DocumentDto dto) throws URISyntaxException {
        log.debug("REST request to update MwDoc : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwDoc mwDoc = mwDocService.updateExistingDocument(dto, currUser);
        if (mwDoc == null) {
            resp.put("error", "Document Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwDoc> respData = new HashMap<String, MwDoc>();
        respData.put("MwDoc", mwDoc);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-doc/{docSeq}")
    @Timed
    public ResponseEntity<MwDoc> getDocBySeq(@PathVariable Long docSeq) {
        log.debug("REST request to get All MwDoc : {}", docSeq);
        MwDoc mwDoc = mwDocService.findOne(docSeq);
        return ResponseEntity.ok().body(mwDoc);
    }

    @GetMapping("/mw-doc")
    @Timed
    public ResponseEntity<List> getDoc() {
        log.debug("REST request to get All MwDoc : {}");
        List<MwDoc> mwDoc = mwDocService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwDoc);
    }


    @GetMapping("/mw-doc-history/{docSeq}")
    @Timed
    public ResponseEntity<List> getDocHistory(@PathVariable Long docSeq) {
        log.debug("REST request to get MwDoc : {}", docSeq);
        List<MwDoc> mwDoc = mwDocService.findAllByDocSeq(docSeq);
        return ResponseEntity.ok().body(mwDoc);
    }

    @DeleteMapping("/mw-doc/{docSeq}")
    @Timed
    public ResponseEntity<Map> deleteDoc(@PathVariable Long docSeq) {
        log.debug("REST request to delete MwDoc : {}", docSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwDocService.delete(docSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }
}

