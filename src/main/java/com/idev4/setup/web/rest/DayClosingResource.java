package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwBrnchClsng;
import com.idev4.setup.dto.*;
import com.idev4.setup.feignclients.GatewayServiceClient;
import com.idev4.setup.repository.MwBrnchClsngRepository;
import com.idev4.setup.service.DayClosingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DayClosingResource {

    private final Logger log = LoggerFactory.getLogger(DayClosingResource.class);

    private final DayClosingService dayClosingService;

    @Autowired
    MwBrnchClsngRepository mwBrnchClsngRepository;

    @Autowired
    GatewayServiceClient gatewayServiceClient;

    public DayClosingResource(DayClosingService dayClosingService) {
        this.dayClosingService = dayClosingService;
    }

    @GetMapping("/get-recovery-day-closing")
    @Timed
    public ResponseEntity<List<RecoveryDto>> getRecoveryClosingData() {
        List<RecoveryDto> recoveryDtos = dayClosingService.getRecoveryClosingData();
        return ResponseEntity.ok().body(recoveryDtos);
    }

    @GetMapping("/get-disbursement-day-closing/{activeUser}")
    @Timed
    public ResponseEntity<List<DisbursementDto>> getDisbursementClosingData(@PathVariable String activeUser) {
        List<DisbursementDto> disbursementDtos = dayClosingService.getDisbursementClosingData(activeUser);
        return ResponseEntity.ok().body(disbursementDtos);
    }

    @GetMapping("/get-expense-day-closing/{activeUser}")
    @Timed
    public ResponseEntity<List<ExpenseClosingDto>> getExpenseClosingData(@PathVariable String activeUser) {
        List<ExpenseClosingDto> expenseClosingDtos = dayClosingService.getExpenseClosingData(activeUser);
        return ResponseEntity.ok().body(expenseClosingDtos);
    }

    @GetMapping("/get-insuranceclaim-day-closing")
    @Timed
    public ResponseEntity<List<InsuranceClaimClosingDto>> getInsuranceClaimClosingData() {
        List<InsuranceClaimClosingDto> insuranceClaimClosingDtos = dayClosingService.getInsuranceClaimClosingData();
        return ResponseEntity.ok().body(insuranceClaimClosingDtos);
    }

    @GetMapping("/close-branch/{id}")
    @Timed
    public ResponseEntity<?> closeBranch(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) {

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> resp = new HashMap<String, String>();
        MwBrnchClsng clsng = mwBrnchClsngRepository.findOneByBrnchSeqAndBrnchOpnFlg(id, true);
        List<Screen> screens = new ArrayList<Screen>();
        if (clsng == null) {
            resp.put("error", "Seems Incorrect Branch Seq !!");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        List<RecoveryDto> recoveryDtos = dayClosingService.getRecoveryClosingData();
        if (recoveryDtos.size() > 0) {
            resp.put("error", "Unposted Recoveries Found");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        List<DisbursementDto> disbursementDtos = dayClosingService.getDisbursementClosingData(currUser);
        if (disbursementDtos.size() > 0) {
            resp.put("error", "Unposted Disbursements Found");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        List<ExpenseClosingDto> expenseClosingDtos = dayClosingService.getExpenseClosingData(currUser);
        if (expenseClosingDtos.size() > 0) {
            resp.put("error", "Unposted Expenses Found");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        // List< InsuranceClaimClosingDto > insuranceClaimClosingDtos = dayClosingService.getInsuranceClaimClosingData();
        // if ( insuranceClaimClosingDtos.size() > 0 ) {
        // resp.put( "error", "Unposted Insurance Claims" );
        // return new ResponseEntity<>( resp, HttpStatus.BAD_REQUEST );
        // }
        clsng.setBrnchClsdDt(LocalDateTime.from(LocalDateTime.now(ZoneId.of("Asia/Karachi"))));
        clsng.setBrnchOpnFlg(false);
        clsng.setBrnchClsdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        mwBrnchClsngRepository.save(clsng);
        ResponseEntity<List<Module>> mods = gatewayServiceClient.getModsForUser(3L, true, token);

        return new ResponseEntity<>(mods, HttpStatus.OK);
    }

    public class Screen {

        public String name;

        public String url;

        public boolean readFlag;

        public boolean writeFlag;

        public boolean deleteFlag;

        public Screen() {
            super();
        }

        public Screen(String name, String url, boolean readFlag, boolean writeFlag, boolean deleteFlag) {
            super();
            this.name = name;
            this.url = url;
            this.readFlag = readFlag;
            this.writeFlag = writeFlag;
            this.deleteFlag = deleteFlag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isReadFlag() {
            return readFlag;
        }

        public void setReadFlag(boolean readFlag) {
            this.readFlag = readFlag;
        }

        public boolean isWriteFlag() {
            return writeFlag;
        }

        public void setWriteFlag(boolean writeFlag) {
            this.writeFlag = writeFlag;
        }

        public boolean isDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(boolean deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

    }

}
