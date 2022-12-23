package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwAppAuth;
import com.idev4.setup.dto.AppAuthDto;
import com.idev4.setup.service.MwUserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing MwAddr.
 */
@RestController
@RequestMapping("/api")
public class MwUsrManagementResource {

    private final Logger log = LoggerFactory.getLogger(MwUsrManagementResource.class);

    private final MwUserManagementService mwUserManagementService;

    public MwUsrManagementResource(MwUserManagementService mwUserManagementService) {
        this.mwUserManagementService = mwUserManagementService;
    }

    @GetMapping("/get-user-role")
    public List getRole() {
        return mwUserManagementService.getRoles();
    }

    @GetMapping("/mw-mods")
    @Timed
    public ResponseEntity<List> getAllModAndSubMod() {
        return ResponseEntity.ok().body(mwUserManagementService.getAllModAndSubMod());
    }

    @GetMapping("/mw-auth/{roleSeq}")
    @Timed
    public ResponseEntity<List> getAllModAndSubMod(@PathVariable Long roleSeq) {
        return ResponseEntity.ok().body(mwUserManagementService.findAllAppAuthForUserRol(roleSeq));
    }

    @PostMapping("/update-app-auth")
    @Timed
    public ResponseEntity<MwAppAuth> addAppAuth(@RequestBody AppAuthDto dto) throws URISyntaxException {
        log.debug("REST request to save MwAppAuth : {}", dto);
        return ResponseEntity.ok().body(mwUserManagementService.saveAppAuth(dto));
    }
}
