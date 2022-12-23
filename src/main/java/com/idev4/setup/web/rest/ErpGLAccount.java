package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.dto.GlAccountsDTO;
import com.idev4.setup.service.ErpGlAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ErpGLAccount {

    private final Logger log = LoggerFactory.getLogger(FileLoaderResource.class);

    @Autowired
    ErpGlAccountsService erpGlAccountsService;

    @GetMapping("/get-gl-accounts/{seq}")
    @Timed
    public ResponseEntity<List<GlAccountsDTO>> getFileData(@PathVariable Long seq, Pageable pageable) {
        log.debug("Get All ERP GL Accounts");
        List<GlAccountsDTO> mwRcvryLoadStgs = erpGlAccountsService.getAllerpGlAccounts(seq);
        return ResponseEntity.ok().body(mwRcvryLoadStgs);
    }

}
