package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwClntHlthInsrClm;
import com.idev4.setup.dto.ClientHealthInsuranceClaimDto;
import com.idev4.setup.service.MwClntHlthInsrClmService;
import com.idev4.setup.web.rest.util.HeaderUtil;
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

/**
 * REST controller for managing MwClntHlthInsrClm.
 */
@RestController
@RequestMapping("/api")
public class MwClntHlthInsrClmResource {

    private static final String ENTITY_NAME = "mwClntHlthInsrClm";
    private final Logger log = LoggerFactory.getLogger(MwClntHlthInsrClmResource.class);
    private final MwClntHlthInsrClmService mwClntHlthInsrClmService;

    public MwClntHlthInsrClmResource(MwClntHlthInsrClmService mwClntHlthInsrClmService) {
        this.mwClntHlthInsrClmService = mwClntHlthInsrClmService;
    }

    @PostMapping("/add-new-hlth-insr-clm")
    @Timed
    public ResponseEntity<Map> createMwClntHlthInsrClm(@RequestBody ClientHealthInsuranceClaimDto dto) throws URISyntaxException {
        log.debug("REST request to save MwClntHlthInsrClm : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwClntHlthInsrClm mwClntHlthInsrClm = mwClntHlthInsrClmService.addNewClntHlthInsrClm(dto, currUser);
        Map<String, MwClntHlthInsrClm> respData = new HashMap<String, MwClntHlthInsrClm>();
        respData.put("MwClntHlthInsrClm", mwClntHlthInsrClm);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-hlth-insr-clm")
    @Timed
    public ResponseEntity<Map> updateMwClntHlthInsrClm(@RequestBody ClientHealthInsuranceClaimDto dto) throws URISyntaxException {
        log.debug("REST request to update MwClntHlthInsrClm : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwClntHlthInsrClm mwClntHlthInsrClm = mwClntHlthInsrClmService.UpdateExistingClntHlthInsrClm(dto, currUser);
        if (mwClntHlthInsrClm == null) {
            resp.put("error", "Health Insurance Cliam Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwClntHlthInsrClm> respData = new HashMap<String, MwClntHlthInsrClm>();
        respData.put("MwClntHlthInsrClm", mwClntHlthInsrClm);
        return ResponseEntity.ok().body(respData);
    }

    // @GetMapping ( "/all-mw-hlth-insr-clm/{user:.+}" )
    // @Timed
    // public ResponseEntity< List< ClntHlthInsrClmsDto > > getAllMwClntHlthInsrClm( Pageable pageable, @PathVariable String user ) {
    // log.debug( "REST request to get a page of MwClntHlthInsrClm" );
    // List< ClntHlthInsrClmsDto > mwClntHlthInsrClms = mwClntHlthInsrClmService.findAllByCurrentRecord( user );
    // return ResponseEntity.ok().body( mwClntHlthInsrClms );
    // }

    // Modified by Zohaib Asim - Dated 16-12-2020
    // Health Claim Type parameter added
    @GetMapping("/all-mw-hlth-insr-clm")
    @Timed
    public ResponseEntity<Map<String, Object>> getAllMwClntHlthInsrClm(@RequestParam String userId, @RequestParam Long brnchSeq,
                                                                       @RequestParam Long hlthClmType,
                                                                       @RequestParam Integer pageIndex, @RequestParam Integer pageSize, @RequestParam String filter, @RequestParam Boolean isCount) {
        log.debug("REST request to get a page of MwClntHlthInsrClm");
        Map<String, Object> mwClntHlthInsrClms = mwClntHlthInsrClmService.findAllByCurrentRecord(userId, brnchSeq, hlthClmType, pageIndex, pageSize,
            filter, isCount);
        return ResponseEntity.ok().body(mwClntHlthInsrClms);
    }

    @GetMapping("/mw-hlth-insr-clm/{clntHlthClmSeq}")
    @Timed
    public ResponseEntity<MwClntHlthInsrClm> getMwClntHlthInsrClm(@PathVariable Long clntHlthClmSeq) {
        log.debug("REST request to get MwClntHlthInsrClm : {}", clntHlthClmSeq);
        MwClntHlthInsrClm mwClntHlthInsrClm = mwClntHlthInsrClmService.findOne(clntHlthClmSeq);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwClntHlthInsrClm));
    }

    @GetMapping("/is-claim-paid/{clntHlthClmSeq}")
    @Timed
    public ResponseEntity<Map> isClaimPaid(@PathVariable Long clntHlthClmSeq) {
        log.debug("REST request to get MwClntHlthInsrClm : {}", clntHlthClmSeq);
        Map<String, Boolean> resp = new HashMap<String, Boolean>();
        resp.put("paid", mwClntHlthInsrClmService.isKSZBPaid(clntHlthClmSeq));
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("/mw-hlth-insr-clm-history/{clntHlthClmSeq}")
    @Timed
    public ResponseEntity<List> getMwClntHlthInsrClmHistory(@PathVariable Long clntHlthClmSeq) {
        log.debug("REST request to get MwClntHlthInsrClm : {}", clntHlthClmSeq);
        List<MwClntHlthInsrClm> mwClntHlthInsrClm = mwClntHlthInsrClmService.findAllHistory(clntHlthClmSeq);
        return ResponseEntity.ok().body(mwClntHlthInsrClm);
    }

    @GetMapping("/get-client-name/{clntSeq}")
    @Timed
    public ResponseEntity<String> getClientName(@PathVariable Long clntSeq) {
        log.debug("REST request to get Client : {}", clntSeq);
        String name = mwClntHlthInsrClmService.findClientName(clntSeq);
        return ResponseEntity.ok().body(name);
    }

    @GetMapping("/get-all-client-name")
    @Timed
    public ResponseEntity<List<String>> getAllClientName() {
        List<String> name = mwClntHlthInsrClmService.findAllClientName();
        return ResponseEntity.ok().body(name);
    }

    @DeleteMapping("/mw-hlth-insr-clm/{clntHlthClmSeq}")
    @Timed
    public ResponseEntity<Void> deleteMwClntHlthInsrClm(@PathVariable Long clntHlthClmSeq,
                                                        @RequestHeader(value = "Authorization") String token) {
        log.debug("REST request to delete MwClntHlthInsrClm : {}", clntHlthClmSeq);
        mwClntHlthInsrClmService.delete(clntHlthClmSeq, token);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, clntHlthClmSeq.toString())).build();
    }
}
