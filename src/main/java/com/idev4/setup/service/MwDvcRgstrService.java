package com.idev4.setup.service;

import com.idev4.setup.domain.MwBrnch;
import com.idev4.setup.domain.MwDvcRgstr;
import com.idev4.setup.domain.MwPort;
import com.idev4.setup.repository.MwBrnchRepository;
import com.idev4.setup.repository.MwDvcRgstryRepository;
import com.idev4.setup.repository.MwPortRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class MwDvcRgstrService {

    private static final Long branchTyp = 2L;
    private static final Long portTyp = 1L;
    private final Logger log = LoggerFactory.getLogger(MwDvcRgstrService.class);
    private final MwDvcRgstryRepository mwDvcRgstryRepository;
    private final MwBrnchRepository mwBrnchRepository;

    private final MwPortRepository mwPortRepository;

    public MwDvcRgstrService(MwDvcRgstryRepository mwDvcRgstryRepository, MwBrnchRepository mwBrnchRepository,
                             MwPortRepository mwPortRepository) {
        this.mwDvcRgstryRepository = mwDvcRgstryRepository;
        this.mwBrnchRepository = mwBrnchRepository;
        this.mwPortRepository = mwPortRepository;
    }

    public MwDvcRgstr save(MwDvcRgstr mwDvcRgstr) {
        log.debug("Request to save MwDvcRgstr : {}", mwDvcRgstr);
        return mwDvcRgstryRepository.save(mwDvcRgstr);
    }

    @Transactional(readOnly = true)
    public MwDvcRgstr findOne(Long dvcRgstrSeq) {
        log.debug("Request to get MwDvcRgstr : {}", dvcRgstrSeq);
        return mwDvcRgstryRepository.findOneByDvcRgstrSeqAndCrntRecFlg(dvcRgstrSeq, true);
    }

    @Transactional(readOnly = true)
    public MwDvcRgstr findOneForBrnchSeq(Long brnchSeq) {
        log.debug("Request to get MwDvcRgstr For Brnch : {}", brnchSeq);
        return mwDvcRgstryRepository.findOneByEntyTypFlgAndEntyTypSeqAndCrntRecFlg(branchTyp, brnchSeq, true);
    }

    @Transactional(readOnly = true)
    public MwDvcRgstr findOneForPortSeq(Long portSeq) {
        log.debug("Request to get MwDvcRgstr For Port : {}", portSeq);
        return mwDvcRgstryRepository.findOneByEntyTypFlgAndEntyTypSeqAndCrntRecFlg(portTyp, portSeq, true);
    }

    @Transactional(readOnly = true)
    public MwDvcRgstr findOneForEntyFlgAndTypSeq(Long flg, Long seq) {
        return mwDvcRgstryRepository.findOneByEntyTypFlgAndEntyTypSeqAndCrntRecFlg(flg, seq, true);
    }

    public ResponseEntity saveMwDvcRgstr(MwDvcRgstr dvcRgstr, String user) {
        MwDvcRgstr exRgstr = mwDvcRgstryRepository.findOneByDvcAddrAndCrntRecFlg(dvcRgstr.getDvcAddr(), true);
        if (exRgstr != null) {
            if (exRgstr.getEntyTypFlg() == 1) {
                MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(exRgstr.getEntyTypSeq(), true);
                return ResponseEntity.badRequest()
                    .body("{\"error\":\"Device Already Registered To " + port.getPortNm() + " Portfolio.\"}");
            }
            MwBrnch brnch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(exRgstr.getEntyTypSeq(), true);
            return ResponseEntity.badRequest().body("{\"error\":\"Device Already Registered To " + brnch.getBrnchNm() + " Branch.\"}");
        }

        if (!dvcRgstr.getPhNum().isEmpty() || dvcRgstr.getPhNum() != null) {
            MwDvcRgstr exRgstr2 = mwDvcRgstryRepository.findOneByPhNumAndCrntRecFlg(dvcRgstr.getPhNum(), true);
            if (exRgstr2 != null) {
                if (exRgstr2.getEntyTypFlg() == 1) {
                    MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(exRgstr2.getEntyTypSeq(), true);
                    return ResponseEntity.badRequest()
                        .body("{\"error\":\"Phone Number Already Registered With " + port.getPortNm() + " Portfolio.\"}");
                }
                MwBrnch brnch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(exRgstr2.getEntyTypSeq(), true);
                return ResponseEntity.badRequest().body("{\"error\":\"Phone Number Already Registered With " + brnch.getBrnchNm() + " Branch.\"}");
            }
        }

        long seq = 0L;
        exRgstr = mwDvcRgstryRepository.findOneByEntyTypFlgAndEntyTypSeqAndCrntRecFlg(dvcRgstr.getEntyTypFlg(), dvcRgstr.getEntyTypSeq(),
            true);
        if (exRgstr != null) {
            exRgstr.setCrntRecFlg(false);
            exRgstr.setDelFlg(true);
            exRgstr.setEffEndDt(Instant.now());
            exRgstr.setLastUpdDt(Instant.now());
            exRgstr.setLastUpdBy(user);
            mwDvcRgstryRepository.save(exRgstr);
            seq = exRgstr.getDvcRgstrSeq();
        } else {
            seq = SequenceFinder.findNextVal(Sequences.DVC_RGSTR_SEQ);
        }
        MwDvcRgstr dvc = new MwDvcRgstr();
        dvc.setCrntRecFlg(true);
        dvc.setCrtdBy(dvcRgstr.getCrtdBy());
        dvc.setCrtdDt(Instant.now());
        dvc.setDvcAddr(dvcRgstr.getDvcAddr());
        dvc.setDvcRgstrSeq(seq);
        dvc.setEffStartDt(Instant.now());
        dvc.setEntyTypFlg(dvcRgstr.getEntyTypFlg());
        dvc.setEntyTypSeq(dvcRgstr.getEntyTypSeq());
        dvc.setLastUpdBy(user);
        dvc.setLastUpdDt(Instant.now());
        //Added by Areeba - 7-6-2022
        dvc.setPhNum(dvcRgstr.getPhNum());
        //Ended by Areeba
        mwDvcRgstryRepository.save(dvc);
        return ResponseEntity.ok().body("{\"body\":\"Device Registered\"}");
    }

    public ResponseEntity unRegisterDevice(MwDvcRgstr dvcRgstr, String user) {
        MwDvcRgstr exRgstr = mwDvcRgstryRepository.findOneByDvcAddrAndCrntRecFlg(dvcRgstr.getDvcAddr(), true);
        if (exRgstr == null)
            return ResponseEntity.badRequest().body("{\"error\":\"Device Not Found.\"}");
        exRgstr.setCrntRecFlg(false);
        exRgstr.setDelFlg(true);
        exRgstr.setEffEndDt(Instant.now());
        exRgstr.setLastUpdDt(Instant.now());
        exRgstr.setLastUpdBy(user);
        mwDvcRgstryRepository.save(exRgstr);
        return ResponseEntity.ok().body("{\"body\":\"Device Unregistered\"}");
    }

    // Added by Areeba - 29-06-2022
    public String getLastDvcPhNum(Long entyTypSeq) {
        MwDvcRgstr rgstr = mwDvcRgstryRepository.findLastActiveByEntyTypSeq(entyTypSeq);
        if (rgstr == null)
            return "null";
        else
            return rgstr.getPhNum();
    }
    // Ended by Areeba

}
