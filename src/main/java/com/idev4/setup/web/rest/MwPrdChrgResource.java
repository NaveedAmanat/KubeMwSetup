package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrdChrg;
import com.idev4.setup.dto.ProductChargesDto;
import com.idev4.setup.service.MwPrdChrgService;
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
public class MwPrdChrgResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdChrgResource.class);

    private final MwPrdChrgService mwPrdChrgService;

    public MwPrdChrgResource(MwPrdChrgService mwPrdChrgService) {
        this.mwPrdChrgService = mwPrdChrgService;
    }

    @PostMapping("/add-new-prd-chrg")
    @Timed
    public ResponseEntity<Map> createMwPrdChrg(@RequestBody ProductChargesDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdChrg : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdChrg prdChrg = mwPrdChrgService.addNewPrdChrg(dto, currUser);
        Map<String, MwPrdChrg> respData = new HashMap<String, MwPrdChrg>();
        respData.put("PrdChrg", prdChrg);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-prd-chrg")
    @Timed
    public ResponseEntity<Map> updateMwChrgTyps(@RequestBody ProductChargesDto dto) throws URISyntaxException {
        log.debug("REST request to update MwPrdChrg : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwPrdChrg mwPrdChrg = mwPrdChrgService.updateExistingProductCharges(dto, currUser);
        if (mwPrdChrg == null) {
            resp.put("error", " Product Charges Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwPrdChrg> respData = new HashMap<String, MwPrdChrg>();
        respData.put("MwPrdChrg", mwPrdChrg);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd-chrg/{prdChrgSeq}")
    @Timed
    public ResponseEntity<ProductChargesDto> getPrdChrgBySeq(@PathVariable Long prdChrgSeq) {
        log.debug("REST request to get All MwPrdChrg : {}", prdChrgSeq);
        ProductChargesDto productChargesDto = mwPrdChrgService.findOne(prdChrgSeq);
        return ResponseEntity.ok().body(productChargesDto);
    }

    @GetMapping("/mw-prd-chrg")
    @Timed
    public ResponseEntity<List> getPrdChrg() {
        log.debug("REST request to get All MwPrdChrg : {}");
        List<ProductChargesDto> productChargesDtos = mwPrdChrgService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(productChargesDtos);
    }

    @GetMapping("/mw-prd-chrg-history/{prdChrgSeq}")
    @Timed
    public ResponseEntity<List> getPrdChrgHistory(@PathVariable Long prdChrgSeq) {
        log.debug("REST request to get MwPrdChrg : {}", prdChrgSeq);
        List<ProductChargesDto> productChargesDtos = mwPrdChrgService.findAllByPrdChrgSeq(prdChrgSeq);
        return ResponseEntity.ok().body(productChargesDtos);
    }

    @GetMapping("/mw-prd-chrg-by-prd-seq/{prdSeq}")
    @Timed
    public ResponseEntity<List<ProductChargesDto>> getPrdChrgByPrdSeq(@PathVariable Long prdSeq) {
        log.debug("REST request to get All MwPrdChrg for product seq: {}", prdSeq);
        List<ProductChargesDto> productChargesDtos = mwPrdChrgService.findAllByPrdSeqAndCrntRecFlg(prdSeq);
        return ResponseEntity.ok().body(productChargesDtos);
    }

    @DeleteMapping("/mw-prd-chrg/{prdChrgSeq}")
    @Timed
    public ResponseEntity<Map> deletePrdChrg(@PathVariable Long prdChrgSeq) {
        log.debug("REST request to delete MwPrdChrg : {}", prdChrgSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdChrgService.delete(prdChrgSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @PostMapping("/add-new-prd-chrg-slbs")
    @Timed
    public ResponseEntity addChrgSlbs(@RequestBody ProductChargesDto dto) throws URISyntaxException {
        log.debug("REST request to save MwPrdChrg : {}", dto);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dto.slbs != null) {
            dto.slbs.forEach(slb -> {
                if (slb.prdChrgSlbSeq == null && slb.crntRecFlg)
                    mwPrdChrgService.addNewPrdChrgSlb(slb, currUser);
                else if (slb.prdChrgSlbSeq != null && slb.crntRecFlg)
                    mwPrdChrgService.editPrdChrgSlb(slb, currUser);
                else if (slb.prdChrgSlbSeq != null && slb.delFlg)
                    mwPrdChrgService.deletePrdChrgSlb(slb, currUser);
            });
        }
        return ResponseEntity.ok().body("{\"body\":\"Success\"}");
    }

    // @PostMapping ( "/add-new-prd-chrg-slb" )
    // @Timed
    // public ResponseEntity addChrgSlbs( @RequestBody ProductChargesDto dto ) throws URISyntaxException {
    // log.debug( "REST request to save MwPrdChrg : {}", dto );
    //
    // String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
    // if ( dto.slbs != null ) {
    // dto.slbs.forEach( slb -> {
    // if ( slb.prdChrgSlbSeq == null )
    // mwPrdChrgService.addNewPrdChrgSlb( slb, currUser );
    // else
    // mwPrdChrgService.editPrdChrgSlb( slb, currUser );
    // } );
    // }
    // return ResponseEntity.ok().body( "{\"body\":\"Success\"}" );
    // }
}
