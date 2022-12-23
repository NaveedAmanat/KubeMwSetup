package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.service.AccessRecoveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccessRecoveryResource {

    private final Logger log = LoggerFactory.getLogger(AccessRecoveryResource.class);

    private final AccessRecoveryService accessRecoveryService;

    public AccessRecoveryResource(AccessRecoveryService accessRecoveryService) {
        this.accessRecoveryService = accessRecoveryService;
    }

//    @GetMapping ( "/get-access-rcvry/{branch}" )
//    @Timed
//    public ResponseEntity< List< AccessRecoveryDto > > getAccessRecoveryData( @PathVariable long branch ) {
//        List< AccessRecoveryDto > accessRecoveryDtos = accessRecoveryService.getAccessRecoveryData( branch );
//        return ResponseEntity.ok().body( accessRecoveryDtos );
//    }

    @GetMapping("/get-access-rcvry")
    @Timed
    public ResponseEntity<Map<String, Object>> getAccessRecoveryData(
        @RequestParam long branchSeq, @RequestParam Integer pageIndex,
        @RequestParam Integer pageSize, @RequestParam(required = false) String filter,
        @RequestParam Boolean isCount) {
        Map<String, Object> accessRecoveryDtos = accessRecoveryService.getAccessRecoveryData(branchSeq, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(accessRecoveryDtos);
    }
}
