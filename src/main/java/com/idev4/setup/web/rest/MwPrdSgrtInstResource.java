package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdSgrtInst;
import com.idev4.setup.dto.ProductSegregateInstallmentDto;
import com.idev4.setup.service.MwPrdSgrtInstService;
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
public class MwPrdSgrtInstResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdSgrtInstResource.class);

    private final MwPrdSgrtInstService mwPrdSgrtInstService;

    public MwPrdSgrtInstResource(MwPrdSgrtInstService mwPrdSgrtInstService) {
        this.mwPrdSgrtInstService = mwPrdSgrtInstService;
    }

    @PostMapping("/add-new-prd-sgrt-inst")
    @Timed
    public ResponseEntity<Map> createMwPrdSgrtInst(@RequestBody ProductSegregateInstallmentDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdSgrtInst : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdSgrtInst prdSgrtInst = mwPrdSgrtInstService.addNewPrdSgrtInst(dto, currUser);
        Map<String, MwPrdSgrtInst> respData = new HashMap<String, MwPrdSgrtInst>();
        respData.put("PrdSgrtInst", prdSgrtInst);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-prd-sgrt-inst")
    @Timed
    public ResponseEntity<Map> updateMwSgrtInstTyps(@RequestBody ProductSegregateInstallmentDto dto) throws URISyntaxException {
        log.debug("REST request to update MwPrdSgrtInst : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdSgrtInst mwPrdSgrtInst = mwPrdSgrtInstService.updateExistingPrdSgrtInst(dto, currUser);
        if (mwPrdSgrtInst == null) {
            resp.put("error", " Product Segregate in Installments Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwPrdSgrtInst> respData = new HashMap<String, MwPrdSgrtInst>();
        respData.put("MwPrdSgrtInst", mwPrdSgrtInst);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-sgrt-inst/{prdSgrtInstSeq}")
    @Timed
    public ResponseEntity<MwPrdSgrtInst> getPrdSgrtInstBySeq(@PathVariable Long prdSgrtInstSeq) {
        log.debug("REST request to get All MwPrdSgrtInst : {}", prdSgrtInstSeq);
        MwPrdSgrtInst mwPrdSgrtInst = mwPrdSgrtInstService.findOne(prdSgrtInstSeq);
        return ResponseEntity.ok().body(mwPrdSgrtInst);
    }

    @GetMapping("/mw-prd-sgrt-inst")
    @Timed
    public ResponseEntity<List> getPrdSgrtInst() {
        log.debug("REST request to get All MwPrdSgrtInst : {}");
        List<MwPrdSgrtInst> mwPrdSgrtInst = mwPrdSgrtInstService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwPrdSgrtInst);
    }


    @GetMapping("/mw-prd-sgrt-inst-history/{prdSgrtInstSeq}")
    @Timed
    public ResponseEntity<List> getPrdSgrtInstHistory(@PathVariable Long prdSgrtInstSeq) {
        log.debug("REST request to get MwPrdSgrtInst : {}", prdSgrtInstSeq);
        List<MwPrdSgrtInst> mwPrdSgrtInst = mwPrdSgrtInstService.findAllByPrdSgrtInstSeq(prdSgrtInstSeq);
        return ResponseEntity.ok().body(mwPrdSgrtInst);
    }


    @PostMapping("/mw-prd-sgrt-inst-enty-seq-and-key")
    @Timed
    public ResponseEntity<List<MwPrdSgrtInst>> getPrdSgrtInstBySgrtEntySeqAndEntyTyp(@RequestBody ProductSegregateInstallmentDto dto) {
        log.debug("REST request to get All MwPrdSgrtInst for Sgrt Enty Seq : {}", dto.getSgrtEntySeq());
        List<MwPrdSgrtInst> mwPrdSgrtInst = mwPrdSgrtInstService.findAllBySgrtEntySeqAndEntyTyp(dto.getSgrtEntySeq(), dto.getEntyTypStr());
        return ResponseEntity.ok().body(mwPrdSgrtInst);
    }

    @DeleteMapping("/mw-prd-sgrt-inst/{prdSgrtInstSeq}")
    @Timed
    public ResponseEntity<Map> deletePrdSgrtInst(@PathVariable Long prdSgrtInstSeq) {
        log.debug("REST request to delete MwPrdSgrtInst : {}", prdSgrtInstSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdSgrtInstService.delete(prdSgrtInstSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }
}

