package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.dto.MwHrTrvlngDTO;
import com.idev4.setup.service.MwHrTrvlngService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Authored by Areeba
HR Travelling SCR
Dated - 07-06-2022
*/

@RestController
@RequestMapping("/api")
public class MwHrTrvlngResource {

    private final Logger log = LoggerFactory.getLogger(MwHrTrvlngResource.class);

    private final MwHrTrvlngService mwHrTrvlngService;

    public MwHrTrvlngResource(MwHrTrvlngService mwHrTrvlngService) {
        this.mwHrTrvlngService = mwHrTrvlngService;
    }

    @GetMapping("/get-all-trvlng")
    public List<MwHrTrvlngDTO> getAllTrvlng() {
        log.debug("getAllTrvlng");
        return mwHrTrvlngService.getAllTrvlng();
    }

    @GetMapping("/get-trvlng/{trvlngRol}")
    public List<MwHrTrvlngDTO> getTrvlngByPortfolio(@PathVariable Long trvlngRol) {
        log.debug("getTrvlngByPortfolio");
        return mwHrTrvlngService.getTrvlng(trvlngRol);
    }

    @PostMapping("/add-trvlng")
    @Timed
    public ResponseEntity<Map> addTrvlng(@RequestBody MwHrTrvlngDTO hrTrvlngDTO) throws URISyntaxException {
        log.debug("REST request to save MwHrTrvlng : {}", hrTrvlngDTO);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = mwHrTrvlngService.addNewTrvlng(hrTrvlngDTO, currUser);

        if (info == 1) {
            respData.put("trvlng", "Successfully added.");
        } else if (info == 0) {
            respData.put("trvlng", "Could not be added.");
        }
        return ResponseEntity.ok().body(respData);

    }

    @PutMapping("/delete-trvlng/{hrTrvlngSeq}")
    @Timed
    public ResponseEntity<Map> deleteTrvlng(@PathVariable Long hrTrvlngSeq) throws URISyntaxException {
        log.debug("REST request to delete MwHrTrvlng : {}", hrTrvlngSeq);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = mwHrTrvlngService.deleteTrvlng(hrTrvlngSeq, currUser);

        if (info == 1) {
            respData.put("trvlng", "Successfully deleted.");
        } else if (info == 0) {
            respData.put("trvlng", "Could not be deleted.");
        }
        return ResponseEntity.ok().body(respData);

    }


}
