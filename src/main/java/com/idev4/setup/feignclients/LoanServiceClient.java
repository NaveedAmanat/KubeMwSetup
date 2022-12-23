package com.idev4.setup.feignclients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.core.HttpHeaders;
import java.util.Map;

//test http://localhost:8080
//live https://apps.kashf.org:8443

@FeignClient(name = "loanservice", url = "http://localhost:8080/loanservice")
public interface LoanServiceClient {

    @RequestMapping(value = "/api/update-client-port/{seq}")
    ResponseEntity<Map> updateClientPortfolio(@PathVariable(value = "seq") Long seq,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}
