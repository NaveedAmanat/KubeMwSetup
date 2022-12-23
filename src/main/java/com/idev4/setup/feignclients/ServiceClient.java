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

@FeignClient(name = "recoverydisbursementservice", url = "http://localhost:8080/recoverydisbursementservice")
public interface ServiceClient {

    @RequestMapping(value = "/api/reverse-expense/{id}/{reason}")
    ResponseEntity<Map> reversExpence(@PathVariable(value = "id") Long id, @PathVariable(value = "reason") String reason,
                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @RequestMapping(value = "/api/defered-application/{id}/{cmnt}/{role}")
    ResponseEntity<Map> defferLoan(@PathVariable(value = "id") Long id, @PathVariable(value = "cmnt") String reason,
                                   @PathVariable(value = "role") String role,
                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @RequestMapping(value = "/api/adjust-loan/{id}")
    ResponseEntity<?> adjustLoan(@PathVariable(value = "id") String id,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * @Added, Naveed
     * @Date, 16-06-2022
     * @Description, SCR - Branch Wise CMS Funds Data Loader
     */
    @RequestMapping(value = "/api/post-expense/{id}")
    ResponseEntity<Map<String, String>> postExpense(@PathVariable(value = "id") Long id,
                                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
