package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwClntTagList;
import com.idev4.setup.service.MwClntTagListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class MwClntTagListResource {

    private final Logger log = LoggerFactory.getLogger(MwClntHlthInsrClmResource.class);

    @Autowired
    MwClntTagListService mwClntTagListService;

    @GetMapping("/add-clnt-tag-list/{cnic}/{loanAppSeq}")
    @Timed
    public ResponseEntity<Map> addClntTagList(@PathVariable Long cnic, @PathVariable Long loanAppSeq) {
        log.debug("REST request to save MwClntTagList : {}", cnic);

        Map<String, MwClntTagList> resp = new HashMap<String, MwClntTagList>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        MwClntTagList mwClntTagList = mwClntTagListService.addClntTagList(cnic, loanAppSeq, currUser);

        resp.put("mwClntTagList", mwClntTagList);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("/add-clnt-to-tag-list/{cnic}/{tag}")
    @Timed
    public ResponseEntity<Map> addClntToTagList(@PathVariable Long cnic, @PathVariable Long tag) {
        log.debug("REST request to save MwClntTagList : {}", cnic);

        Map<String, MwClntTagList> resp = new HashMap<String, MwClntTagList>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        MwClntTagList mwClntTagList = mwClntTagListService.addClntToTagList(cnic, tag, currUser);

        resp.put("mwClntTagList", mwClntTagList);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("/get-clnt-tag/{cnic}")
    @Timed
    public ResponseEntity<Map> getClntTagList(@PathVariable Long cnic) {
        log.debug("REST request to save MwClntTagList : {}", cnic);
        Map<String, MwClntTagList> resp = new HashMap<String, MwClntTagList>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        MwClntTagList mwClntTagList = mwClntTagListService.getClntTag(cnic);
        resp.put("mwClntTagList", mwClntTagList);
        return ResponseEntity.ok().body(resp);
    }
}
