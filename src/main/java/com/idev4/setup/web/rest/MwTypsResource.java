package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwTyps;
import com.idev4.setup.dto.TypesDto;
import com.idev4.setup.service.MwTypsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing MwTyps.
 */
@RestController
@RequestMapping("/api")
public class MwTypsResource {

    private static final String ENTITY_NAME = "mw_typs";
    private final Logger log = LoggerFactory.getLogger(MwTypsResource.class);
    private final MwTypsService mwTypsService;

    public MwTypsResource(MwTypsService mwTypsService) {
        this.mwTypsService = mwTypsService;
    }

    @PostMapping("/add-new-typ")
    @Timed
    public ResponseEntity<Map> createMwTyp(@RequestBody TypesDto typesDto) throws URISyntaxException {
        log.debug("REST request to save Mw_Typ : {}", typesDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (typesDto.getTypCtgryKey() == null) {
            resp.put("error", "Missing  Type Category!!");
            return ResponseEntity.badRequest().body(resp);
        }
//        if ( typesDto.getTypId() == null || typesDto.getTypId().isEmpty() ) {
//            resp.put( "error", "Missing  Type Id !!" );
//            return ResponseEntity.badRequest().body( resp );
//        }
        if (typesDto.getTypStr() == null || typesDto.getTypStr().isEmpty()) {
            resp.put("error", "Missing Type String !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (typesDto.getGlAcctNum() == null || typesDto.getGlAcctNum().isEmpty()) {
            resp.put("error", "Missing GL Account Mapping !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (typesDto.getTypStsKey() == null) {
            resp.put("error", "Missing Status !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwTyps Typs = mwTypsService.addNewTyp(typesDto, currUser);
        Map<String, MwTyps> respData = new HashMap<String, MwTyps>();
        respData.put("Type", Typs);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-typ")
    @Timed
    public ResponseEntity<Map> updateMwChrgTyps(@RequestBody TypesDto typesDto) throws URISyntaxException {
        log.debug("REST request to update MwTyps : {}", typesDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (typesDto.getTypSeq() == null) {
            resp.put("error", "Missing  Type SEQ!!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (typesDto.getTypCtgryKey() == null) {
            resp.put("error", "Missing  Type Category!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (typesDto.getTypId() == null || typesDto.getTypId().isEmpty()) {
            resp.put("error", "Missing  Type Id !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (typesDto.getTypStr() == null || typesDto.getTypStr().isEmpty()) {
            resp.put("error", "Missing Type String !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (typesDto.getGlAcctNum() == null || typesDto.getGlAcctNum().isEmpty()) {
            resp.put("error", "Missing GL Account Mapping !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (typesDto.getTypStsKey() == null) {
            resp.put("error", "Missing Status !!");
            return ResponseEntity.badRequest().body(resp);
        }

        MwTyps Typs = mwTypsService.updateExistingTyp(typesDto, currUser);
        if (Typs == null) {
            resp.put("error", " Type Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwTyps> respData = new HashMap<String, MwTyps>();
        respData.put("Typs", Typs);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-typs/{ctgryId}")
    @Timed
    public ResponseEntity<List> getMwTypsByCtgry(@PathVariable Long ctgryId) {
        log.debug("REST request to get MwTyps : {}", ctgryId);
        List<MwTyps> mwTyps = mwTypsService.findAllByCrntRecFlgAndTypCtgryKey(ctgryId);
        return ResponseEntity.ok().body(mwTyps);
    }

    @GetMapping("/mw-typs")
    @Timed
    public ResponseEntity<Map<String, Object>> getMwTypsByCtgry(
        @RequestParam Long ctgryId, @RequestParam Integer pageIndex,
        @RequestParam Integer pageSize, @RequestParam(required = false) String filter,
        @RequestParam Boolean isCount, @RequestParam Long brnchSeq) {
        log.debug("REST request to get MwTyps : {}", ctgryId);
        Map<String, Object> mwTyps = mwTypsService.findAllByCrntRecFlgAndTypCtgryKey(ctgryId, pageIndex, pageSize, filter, isCount, brnchSeq);
        return ResponseEntity.ok().body(mwTyps);
    }

    @GetMapping("/mw-typs-consolidated")
    @Timed
    public ResponseEntity<Map<String, Object>> getMwTypsByCtgryAndBrnch(
        @RequestParam Long ctgryId, @RequestParam Integer pageIndex,
        @RequestParam Integer pageSize, @RequestParam(required = false) String filter,
        @RequestParam Boolean isCount, @RequestParam Long brnchSeq) {
        log.debug("REST request to get MwTyps : {}", ctgryId);
        Map<String, Object> mwTyps = mwTypsService.findAllByCrntRecFlgAndTypCtgryKeyAndBrnchSeq(ctgryId, pageIndex, pageSize, filter, isCount, brnchSeq);
        return ResponseEntity.ok().body(mwTyps);
    }

    @GetMapping("/mw-typs-brnch-wise/{ctgryId}/{brnchSeq}")
    @Timed
    public ResponseEntity<List> getMwTypsByCtgryBrnchWise(@PathVariable Long ctgryId, @PathVariable Long brnchSeq) {
        log.debug("REST request to get MwTyps : {}", ctgryId);
        // Edited by Areeba - 27-05-2022
        List<MwTyps> mwTyps = mwTypsService.findAllByCrntRecFlgAndTypCtgryKeyAndBrnchSeq(ctgryId, brnchSeq);
        return ResponseEntity.ok().body(mwTyps);
    }

    @GetMapping("/mw-typs-by-seq/{typSeq}")
    @Timed
    public ResponseEntity<MwTyps> getMwTypBySeq(@PathVariable Long typSeq) {
        log.debug("REST request to get MwTyps : {}", typSeq);
        MwTyps mwTyps = mwTypsService.findOne(typSeq);
        return ResponseEntity.ok().body(mwTyps);
    }

    @PostMapping("/mw-typs-by-id-and-ctgry")
    @Timed
    public ResponseEntity<MwTyps> getMwTypByTypIdAndTypCtgryId(@RequestBody TypesDto typesDto) {
        log.debug("REST request to get MwTyps : {}", typesDto);
        MwTyps mwTyps = mwTypsService.findOneByTypIdAndTypCtgryKey(typesDto);
        return ResponseEntity.ok().body(mwTyps);
    }

    @GetMapping("/mw-typs-history/{seq}")
    @Timed
    public ResponseEntity<List> getMweTypsHistory(@PathVariable Long seq) {
        log.debug("REST request to get MwTyps : {}", seq);
        List<MwTyps> mwTyps = mwTypsService.findAllByTypSeq(seq);
        return ResponseEntity.ok().body(mwTyps);
    }

    @DeleteMapping("/mw-typs/{seq}")
    @Timed
    public ResponseEntity<Map> deleteMwTyps(@PathVariable Long seq) {
        log.debug("REST request to delete MwTyps : {}", seq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwTypsService.delete(seq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }

    // Added By Naveed - Date - 23-02-2022
    // SCR - Upaisa and HBL Konnect Mobile Wallet Payment Mode
    @GetMapping("/mw-typs-mobile-wallet")
    @Timed
    public ResponseEntity<List> mobileMobileWalletTypes() {
        log.debug("REST request to get Mobile Wallet Modes");
        List<Map<String, String>> mwTyps = mwTypsService.getMobileMobileWalletTypes();
        return ResponseEntity.ok().body(mwTyps);
    }
    // Ended By Naveed
}
