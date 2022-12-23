package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwExp;
import com.idev4.setup.dto.ExpenseDto;
import com.idev4.setup.service.MwExpService;
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
 * REST controller for managing MwExp.
 */
@RestController
@RequestMapping("/api")
public class MwExpResource {

    private static final String ENTITY_NAME = "mw_exp";
    private final Logger log = LoggerFactory.getLogger(MwExpResource.class);
    private final MwExpService mwExpService;

    public MwExpResource(MwExpService mwExpService) {
        this.mwExpService = mwExpService;
    }

    @PostMapping("/add-new-exp")
    @Timed
    public ResponseEntity<Map> createMwExp(@RequestBody ExpenseDto expenseDto) throws URISyntaxException {
        log.debug("REST request to save Mw_Exp : {}", expenseDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        /*if(!mwExpService.aviblBdgt(expenseDto.getBrnchSeq(),expenseDto.getExpnsAmt()))
        {
        	resp.put("data","Branch Budget Exceed !!");
        	return ResponseEntity.ok().body(resp);
        }*/

        MwExp exp = mwExpService.addNewExp(expenseDto, currUser);
        Map<String, MwExp> respData = new HashMap<String, MwExp>();
        respData.put("exp", exp);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-exp")
    @Timed
    public ResponseEntity<Map> updateMwExp(@RequestBody ExpenseDto expenseDto) throws URISyntaxException {
        log.debug("REST request to update MwExp : {}", expenseDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        /*if(!mwExpService.aviblBdgt(expenseDto.getBrnchSeq(),expenseDto.getExpnsAmt()))
        {
        	resp.put("data","Branch Budget Exceed !!");
        	return ResponseEntity.ok().body(resp);
        }*/

        MwExp exp = mwExpService.updateExistingExp(expenseDto, currUser);
        if (exp == null) {
            resp.put("error", "Expense Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwExp> respData = new HashMap<String, MwExp>();
        respData.put("exp", exp);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/reverse-exp")
    @Timed
    public synchronized ResponseEntity<Map> reverseMwExp(@RequestBody ExpenseDto expenseDto,
                                                         @RequestHeader(value = "Authorization") String token) throws URISyntaxException {
        log.debug("REST request to update MwExp : {}", expenseDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        /*if(!mwExpService.aviblBdgt(expenseDto.getBrnchSeq(),expenseDto.getExpnsAmt()))
        {
            resp.put("data","Branch Budget Exceed !!");
            return ResponseEntity.ok().body(resp);
        }*/

        MwExp exp = mwExpService.reverseExistingExp(expenseDto, currUser, token);
        if (exp == null) {
            resp.put("error", "Expense Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwExp> respData = new HashMap<String, MwExp>();
        respData.put("exp", exp);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * @param pageable
     * @return
     */
    // @GetMapping ( "/mw-exp-by-user/{role}" )
    // @Timed
    // public ResponseEntity< List< MwExp > > getAllMwExpByBranchSeq( @PathVariable String role ) {
    // log.debug( "REST request to get a page of MwExp USER :" + role );
    // List< MwExp > exp = mwExpService.findAllByBrnchSeqAndCrntRecFlg( role );
    // return ResponseEntity.ok().body( exp );
    // }

    // @GetMapping ( "/mw-exp-by-brnch-seq/{seq}" )
    // @Timed
    // public ResponseEntity< List< MwExp > > getAllMwExpByBranchSeq( @PathVariable Long seq ) {
    // log.debug( "REST request to get a page of MwExp Branch Seq :" + seq );
    // return ResponseEntity.ok().body( mwExpService.findAllByBrnchSeqAndCrntRecFlg( seq ) );
    // }
    //
    @GetMapping("/mw-exp-by-brnch-seq")
    @Timed
    public ResponseEntity<Map<String, Object>> getAllMwExpByBranchSeq(@RequestParam Long seq, @RequestParam Integer pageIndex,
                                                                      @RequestParam Integer pageSize, @RequestParam(required = false) String filter, @RequestParam Boolean isCount) {
        log.debug("REST request to get a page of MwExp Branch Seq :" + seq);
        return ResponseEntity.ok().body(mwExpService.findAllByBrnchSeqAndCrntRecFlg(seq, pageIndex, pageSize, filter, isCount));
    }

    @GetMapping("/mw-exp/{id}")
    @Timed
    public ResponseEntity<MwExp> getMwExp(@PathVariable Long id) {
        log.debug("REST request to get MwExp : {}", id);
        MwExp mwExp = mwExpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwExp));
    }

    @GetMapping("/mw-exp-history/{id}")
    @Timed
    public ResponseEntity<List> getMwExpHistory(@PathVariable Long id) {
        log.debug("REST request to get MwExp : {}", id);
        List<MwExp> mwExp = mwExpService.findAllHistory(id);
        return ResponseEntity.ok().body(mwExp);
    }

    @DeleteMapping("/mw-exp/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwExp(@PathVariable Long id) {
        log.debug("REST request to delete MwExp : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwExpService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }
}
