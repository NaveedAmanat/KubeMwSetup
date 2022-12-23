package com.idev4.setup.service;

import com.idev4.setup.domain.MwAppAuth;
import com.idev4.setup.domain.MwAppMod;
import com.idev4.setup.domain.MwUsrRol;
import com.idev4.setup.dto.AppAuthDto;
import com.idev4.setup.dto.AppMod;
import com.idev4.setup.repository.MwAppAuthRepository;
import com.idev4.setup.repository.MwAppModRepository;
import com.idev4.setup.repository.MwAppSbModRepository;
import com.idev4.setup.repository.MwUsrRolRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing MwArea.
 */
@Service
@Transactional
public class MwUserManagementService {

    private final Logger log = LoggerFactory.getLogger(MwUserManagementService.class);

    private final MwUsrRolRepository mwUsrRolRepository;

    private final MwAppAuthRepository mwAppAuthRepository;

    private final MwAppModRepository mwAppModRepository;

    private final MwAppSbModRepository mwAppSbModRepository;

    private final EntityManager em;

    public MwUserManagementService(EntityManager em, MwUsrRolRepository mwUsrRolRepository, MwAppAuthRepository mwAppAuthRepository,
                                   MwAppModRepository mwAppModRepository, MwAppSbModRepository mwAppSbModRepository) {
        this.mwUsrRolRepository = mwUsrRolRepository;
        this.mwAppAuthRepository = mwAppAuthRepository;
        this.em = em;
        this.mwAppModRepository = mwAppModRepository;
        this.mwAppSbModRepository = mwAppSbModRepository;
    }

    public List<MwUsrRol> getRoles() {
        return mwUsrRolRepository.findAllByCrntRecFlg(true);
    }

    public MwAppAuth saveAppAuth(AppAuthDto dto) {
        MwAppAuth exAuth = mwAppAuthRepository.findOneByUsrRolSeqAndSbModSeqAndCrntRecFlg(dto.usrRolSeq, dto.sbModSeq, true);
        if (!dto.checked && exAuth != null) {
            exAuth.setCrntRecFlg(false);
            exAuth.setDelFlg(true);
            exAuth.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            exAuth.setLastUpdDt(Instant.now());
            exAuth.setEffEndDt(Instant.now());
            return mwAppAuthRepository.save(exAuth);
        } else if (dto.checked && exAuth == null) {
            long seq = SequenceFinder.findNextVal(Sequences.APP_AUTH_SEQ);
            MwAppAuth auth = new MwAppAuth();
            auth.setAftrClsng(dto.aftrClsng);
            auth.setAppAuthSeq(seq);
            auth.setCrntRecFlg(true);
            auth.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            auth.setCrtdDt(Instant.now());
            auth.setDelFlg(false);
            auth.setDelPrmFlg(dto.delPrmFlg);
            auth.setEffStartDt(Instant.now());
            auth.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            auth.setLastUpdDt(Instant.now());
            auth.setReadPrmFlg(dto.readPrmFlg);
            auth.setSbModSeq(dto.sbModSeq);
            auth.setUsrRolSeq(dto.usrRolSeq);
            auth.setWrtPrmFlg(dto.wrtPrmFlg);
            return mwAppAuthRepository.save(auth);
        }
        return null;
    }

    public List<MwAppAuth> findAllAppAuthForUserRol(Long role) {
        return mwAppAuthRepository.findAllByUsrRolSeqAndCrntRecFlg(role, true);
    }

    public List<AppMod> getAllModAndSubMod() {
        List<MwAppMod> mods = mwAppModRepository.findAllByCrntRecFlg(true);
        List<AppMod> resp = new ArrayList<>();
        mods.forEach(mod -> {
            AppMod m = new AppMod();
            m.mod = mod;
            m.subMods = mwAppSbModRepository.findAllByModSeqAndCrntRecFlg(mod.getModSeq(), true);
            resp.add(m);
        });
        return resp;
    }
}
