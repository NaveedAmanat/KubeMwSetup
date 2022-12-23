package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.dto.AppDto;
import com.idev4.setup.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransferResource {

    private final Logger log = LoggerFactory.getLogger(TransferResource.class);

    private final TransferService transferService;

    public TransferResource(TransferService transferService) {
        this.transferService = transferService;
    }

//    @GetMapping ( "/all-transfers/{activeUser}" )
//    @Timed
//    public ResponseEntity< List< AppDto > > getTrasferData( @PathVariable String activeUser ) {
//        List< AppDto > appDtos = transferService.findAllByCrntRecFlg( activeUser );
//        return ResponseEntity.ok().body( appDtos );
//    }

    /* Added by Yousaf Ali - Dated 18-Jan-2022
     * Portfolio Transfer */
    @GetMapping("/all-transfers")
    @Timed
    public ResponseEntity<Map<String, Object>> getTrasferData(
        @RequestParam String userId, @RequestParam Long brnchSeq, @RequestParam Long portSeq,
        @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
        @RequestParam(required = false) String filter, @RequestParam Boolean isCount) {
        Map<String, Object> appDtos = transferService.findAllByCrntRecFlg(userId, brnchSeq, portSeq, pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(appDtos);
    }

    @PutMapping("/update-port")
    @Timed
    public ResponseEntity<Boolean> updatePortfolio(@RequestBody AppDto[] dtos) {
        boolean flag = transferService.updatePortfolio(dtos);
        return ResponseEntity.ok().body(flag);
    }
}
