package com.idev4.setup.feignclients;

import com.idev4.setup.dto.Module;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.core.HttpHeaders;
import java.util.List;

//test http://localhost:8080
//live https://apps.kashf.org:8443

@FeignClient(name = "kashaf", url = "http://localhost:8080/kashaf")
public interface GatewayServiceClient {

    @RequestMapping(value = "/api/mw-mods-for-user-rol/{id}/{closeFlg}")
    ResponseEntity<List<Module>> getModsForUser(@PathVariable(value = "id") Long id,
                                                @PathVariable(value = "closeFlg") Boolean closeFlg, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}
