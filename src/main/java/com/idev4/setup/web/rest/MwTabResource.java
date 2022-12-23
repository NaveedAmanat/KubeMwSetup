package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.dto.tab.SyncDto;
import com.idev4.setup.service.MwTabService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@RestController
@RequestMapping("/api")
public class MwTabResource {

    private static MwTabService mwTabService;
    private final Logger log = LoggerFactory.getLogger(MwTabResource.class);

    public MwTabResource(MwTabService mwTabService) {
        this.mwTabService = mwTabService;
    }

    @GetMapping("/get-data-for-device/{mac}")
    @Timed
    public ResponseEntity getSetupDataForDevice(HttpServletRequest request,
                                                @PathVariable String mac) throws ParseException {
        log.info("Request from Remote Address: " + request.getRemoteAddr());
        SyncDto syncDto = mwTabService.getDataForDeviceRegistered(mac);
        return ResponseEntity.ok().body(syncDto);
    }

    @GetMapping("/get-data-for-device/{mac}/{completeDataRequest}")
    @Timed
    public ResponseEntity getSetupDataForDevice(HttpServletRequest request,
                                                @PathVariable String mac, @PathVariable Boolean completeDataRequest)
        throws ParseException, SQLException, IOException {
        log.info("Request from Remote Address: " + request.getRemoteAddr());
        SyncDto syncDto = mwTabService.getDataForDeviceRegistered(mac, completeDataRequest);
        return ResponseEntity.ok().body(syncDto);
    }

    @GetMapping("/update-setup-sync-date/{mac}")
    @Timed
    public ResponseEntity updateLoanSyncDate(@PathVariable String mac) {
        String str = mwTabService.markSyncDate(mac);
        if (str == null)
            return ResponseEntity.badRequest().body("{'error': 'Device Not Registered'}");
        return ResponseEntity.ok().body("{'message': 'Success'}");
    }

    /*Added By Rizwan Mahfooz - Dated 26-1-2022
     * Because now there is a function in database to perform setup sync*/
    @GetMapping("/get-data-for-device-tablet/{mac}/{completeDataRequest}")
    @Timed
    public ResponseEntity getSetupDataForDeviceTablet(HttpServletRequest request,
                                                      @PathVariable String mac, @PathVariable Boolean completeDataRequest)
        throws ParseException, SQLException, IOException {
        Object syncDto = mwTabService.getDataForDeviceRegisteredTablet(mac, completeDataRequest);
        return ResponseEntity.ok().body(syncDto);
    }
    //End

}
