package com.idev4.setup.service;

import com.idev4.setup.domain.MwTyps;
import com.idev4.setup.dto.TypesDto;
import com.idev4.setup.repository.MwTypsRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

/**
 * Service Implementation for managing MwTyps.
 */
@Service
@Transactional
public class MwTypsService {

    private final Logger log = LoggerFactory.getLogger(MwTypsService.class);
    private final MwTypsRepository mwTypsRepository;
    @Autowired
    private EntityManager entityManager;

    public MwTypsService(MwTypsRepository mwTypsRepository) {
        this.mwTypsRepository = mwTypsRepository;
    }

    public MwTyps save(MwTyps mwTyps) {
        log.debug("Request to save MwTyps : {}", mwTyps);
        return mwTypsRepository.save(mwTyps);
    }

    @Transactional(readOnly = true)
    public MwTyps findOne(Long typSeq) {
        log.debug("Request to get MwTyps : {}", typSeq);
        return mwTypsRepository.findOneByTypSeqAndCrntRecFlg(typSeq, true);
    }

    @Transactional(readOnly = true)
    public MwTyps findOneByTypIdAndTypCtgryKey(TypesDto typesDto) {
        log.debug("Request to get MwTyps : {}", typesDto);
        return mwTypsRepository.findOneByTypIdAndTypCtgryKeyAndCrntRecFlgAndBrnchSeq(typesDto.getTypId(), typesDto.getTypCtgryKey(), true,
            typesDto.getBrnchSeq());
    }

    @Transactional(readOnly = true)
    public List<MwTyps> findAllByTypSeq(Long typSeq) {
        log.debug("Request to get MwTyps : {}", typSeq);
        return mwTypsRepository.findAllByTypSeq(typSeq);
    }

    @Transactional(readOnly = true)
    public List<MwTyps> findAllByCrntRecFlgAndTypCtgryKeyAndBrnchSeq(Long typCtgry, Long brnchSeq) {
        log.debug("Request to get MwTyps : {}", typCtgry);
        return mwTypsRepository.findAllByCrntRecFlgAndTypCtgryKeyAndBrnchSeq(true, typCtgry, brnchSeq);
    }

    @Transactional(readOnly = true)
    public List<MwTyps> findAllByCrntRecFlgAndTypCtgryKey(Long typCtgry) {
        log.debug("Request to get MwTyps : {}", typCtgry);
        return mwTypsRepository.findAllByCrntRecFlgAndTypCtgryKey(true, typCtgry);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findAllByCrntRecFlgAndTypCtgryKey(Long typCtgry, Integer pageIndex, Integer pageSize, String filter, Boolean isCount, Long brnchSeq) {

        String rcvryScript = "SELECT t.* FROM mw_typs t where t.typ_ctgry_key=:typ and t.crnt_rec_flg =1 \n" +
            "    AND t.brnch_seq = case when to_number(:brnchSeq)=0 then t.brnch_seq else to_number(:brnchSeq) end ";

        String rcvryCountScript = "SELECT count(*) FROM mw_typs t where t.typ_ctgry_key =:typ and t.crnt_rec_flg =1 \n" +
            "    AND t.brnch_seq = case when to_number(:brnchSeq)=0 then t.brnch_seq else to_number(:brnchSeq) end ";

        if (filter != null && filter.length() > 0) {
            String search = " and ( lower(t.typ_str) LIKE '%?%' )"
                .replace("?", filter.toLowerCase());

            rcvryScript += search;
            rcvryCountScript += search;
        }

        List<MwTyps> allRulesList = entityManager.createNativeQuery(rcvryScript + "\r\norder by 1 desc", MwTyps.class)
            .setParameter("typ", typCtgry).setParameter("brnchSeq", brnchSeq)
            .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("typs", allRulesList);

        Long totalCityCount = 0L;
        if (isCount.booleanValue()) {
            totalCityCount = new BigDecimal(
                entityManager.createNativeQuery(rcvryCountScript)
                    .setParameter("typ", typCtgry)
                    .setParameter("brnchSeq", brnchSeq)
                    .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCityCount);

        return response;
    }

    @Transactional(readOnly = true)
    public List<MwTyps> findAllByCrntRecFlgAndTypCtgryKeyBrnchQise(Long typCtgry, Long brnchSeq) {
        log.debug("Request to get MwTyps : {}", typCtgry);
        return mwTypsRepository.findAllByCrntRecFlgAndTypCtgryKeyAndBrnchSeqIn(true, typCtgry, Arrays.asList(0L, brnchSeq));
    }

    public boolean delete(Long typSeq) {

        MwTyps typs = mwTypsRepository.findOneByTypSeqAndCrntRecFlg(typSeq, true);
        typs.setCrntRecFlg(false);
        typs.setDelFlg(true);
        typs.setLastUpdDt(Instant.now());
        mwTypsRepository.save(typs);
        return true;
    }

    public MwTyps addNewTyp(TypesDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.TYP_SEQ);
        MwTyps typ = new MwTyps();
        Instant currIns = Instant.now();

        typ.setTypSeq(seq);
        typ.setTypId((dto.getTypId() == null) ? String.format("%04d", seq) : dto.getTypId());
        typ.setTypStr(dto.getTypStr());
        typ.setGlAcctNum(dto.getGlAcctNum());
        typ.setTypStsKey(dto.getTypStsKey());
        typ.setTypCtgryKey(dto.getTypCtgryKey());
        typ.setCrtdBy(currUser);
        typ.setCrtdDt(currIns);
        typ.setLastUpdBy(currUser);
        typ.setLastUpdDt(currIns);
        typ.setDelFlg(false);
        typ.setEffStartDt(currIns);
        typ.setCrntRecFlg(true);
        typ.setBrnchSeq(dto.getBrnchSeq());
        typ.setPerdFlg(1L);
        return mwTypsRepository.save(typ);

    }

    @Transactional
    public MwTyps updateExistingTyp(TypesDto dto, String currUser) {
        MwTyps typ = mwTypsRepository.findOneByTypSeqAndCrntRecFlg(dto.getTypSeq(), true);
        Instant currIns = Instant.now();
        if (typ == null) {
            return null;
        }

        typ.setTypCtgryKey(dto.getTypCtgryKey());
        typ.setLastUpdDt(currIns);
        typ.setLastUpdBy(currUser);
        typ.setTypId(dto.getTypId());
        typ.setTypStr(dto.getTypStr());
        typ.setBrnchSeq(dto.getBrnchSeq());
        typ.setTypStsKey(dto.getTypStsKey());
        typ.setGlAcctNum(dto.getGlAcctNum());

        return mwTypsRepository.save(typ);

    }

    // Added By Naveed - Date - 23-02-2022
    // SCR - Upaisa and HBL Konnect Mobile Wallet Payment Mode
    public List<Map<String, String>> getMobileMobileWalletTypes() {
        List<Object[]> types = mwTypsRepository.findAllByMobileWalletModes();
        List<Map<String, String>> lists = new ArrayList();
        if (types != null && types.size() > 0) {
            types.forEach(typ -> {
                Map<String, String> map = new HashMap<>();
                map.put("typStr", typ[0].toString());
                map.put("typId", typ[1].toString());
                lists.add(map);
            });
        }
        return lists;
    }
    // Ended By Naveed


    @Transactional(readOnly = true)
    public Map<String, Object> findAllByCrntRecFlgAndTypCtgryKeyAndBrnchSeq(Long typCtgry, Integer pageIndex, Integer pageSize, String filter, Boolean isCount, Long brnchSeq) {

        String rcvryScript = "SELECT t.* FROM mw_typs t where t.typ_ctgry_key=:typ and t.crnt_rec_flg =1 \n" +
            "    AND t.brnch_seq = case when to_number(:brnchSeq)=-1 then t.brnch_seq else to_number(:brnchSeq) end ";

        String rcvryCountScript = "SELECT count(*) FROM mw_typs t where t.typ_ctgry_key =:typ and t.crnt_rec_flg =1 \n" +
            "    AND t.brnch_seq = case when to_number(:brnchSeq)=-1 then t.brnch_seq else to_number(:brnchSeq) end ";

        if (filter != null && filter.length() > 0) {
            String search = " and ( lower(t.typ_str) LIKE '%?%' )"
                .replace("?", filter.toLowerCase());

            rcvryScript += search;
            rcvryCountScript += search;
        }

        List<MwTyps> allRulesList = entityManager.createNativeQuery(rcvryScript + "\r\norder by 1 desc", MwTyps.class)
            .setParameter("typ", typCtgry).setParameter("brnchSeq", brnchSeq)
            .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("typs", allRulesList);

        Long totalCityCount = 0L;
        if (isCount.booleanValue()) {
            totalCityCount = new BigDecimal(
                entityManager.createNativeQuery(rcvryCountScript)
                    .setParameter("typ", typCtgry)
                    .setParameter("brnchSeq", brnchSeq)
                    .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCityCount);

        return response;
    }
}
