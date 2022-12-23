package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwDvcRgstr;
import com.idev4.setup.service.MwDvcRgstrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class MwDvcRgstrResource {

    private final Logger log = LoggerFactory.getLogger(MwDvcRgstrResource.class);

    private final MwDvcRgstrService mwDvcRgstrService;

    public MwDvcRgstrResource(MwDvcRgstrService mwDvcRgstrService) {
        this.mwDvcRgstrService = mwDvcRgstrService;
    }

    @PostMapping("/register-dvc")
    @Timed
    public ResponseEntity createMwDoc(@RequestBody MwDvcRgstr dvcRgstr) throws URISyntaxException {
        log.debug("REST request to save MwDvcRgstr : {}", dvcRgstr);
        return mwDvcRgstrService.saveMwDvcRgstr(dvcRgstr, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PutMapping("/unregister-dvc")
    @Timed
    public ResponseEntity updateDoce(@RequestBody MwDvcRgstr dvcRgstr) throws URISyntaxException {
        log.debug("REST request to update MwDvcRgstr : {}", dvcRgstr);
        return mwDvcRgstrService.unRegisterDevice(dvcRgstr, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("/get-registerd-device/{typFlg}/{typSeq}")
    @Timed
    public ResponseEntity<MwDvcRgstr> findOneForEntyFlgAndTypSeq(@PathVariable Long typFlg, @PathVariable Long typSeq) {
        MwDvcRgstr dvcRgstr = mwDvcRgstrService.findOneForEntyFlgAndTypSeq(typFlg, typSeq);
        return ResponseEntity.ok().body(dvcRgstr);
    }

    // Added by Areeba - 29-06-2022
    @GetMapping("/get-last-device-ph-num/{entyTypSeq}")
    @Timed
    public ResponseEntity<String> getLastDvcPhNum(@PathVariable Long entyTypSeq) {
        String dvcRgstr = mwDvcRgstrService.getLastDvcPhNum(entyTypSeq);
        return ResponseEntity.ok().body(dvcRgstr);
    }
    // Ended by Areeba
}
