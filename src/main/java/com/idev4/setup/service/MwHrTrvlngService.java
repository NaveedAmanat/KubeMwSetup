package com.idev4.setup.service;

import com.idev4.setup.domain.MwHrTrvlng;
import com.idev4.setup.dto.MwHrTrvlngDTO;
import com.idev4.setup.repository.MwHrTrvlngRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/*Authored by Areeba
HR Travelling SCR
Dated - 07-06-2022
*/

@Service
@Transactional
public class MwHrTrvlngService {

    private final Logger log = LoggerFactory.getLogger(MwHrTrvlngService.class);

    private final MwHrTrvlngRepository mwHrTrvlngRepository;

    private final EntityManager em;

    public MwHrTrvlngService(MwHrTrvlngRepository mwHrTrvlngRepository, EntityManager em) {
        this.mwHrTrvlngRepository = mwHrTrvlngRepository;
        this.em = em;
    }


    public List<MwHrTrvlngDTO> getAllTrvlng() {
        String script = " SELECT HR_TRVLNG_SEQ, REF_CD_TRVLNG_ROL, REF_CD_CALC_TYP, \n" +
            " REF_CD_PORT_TYP_SEQ, FROM_DT, TO_DT, AMT\n" +
            " FROM MW_HR_TRVLNG \n" +
            " WHERE CRNT_REC_FLG = 1 ";
        Query q = em.createNativeQuery(script);

        List<Object[]> trvlngList = q.getResultList();
        List<MwHrTrvlngDTO> trvlng = new ArrayList<MwHrTrvlngDTO>();

        trvlngList.forEach(l -> {

            MwHrTrvlngDTO dto = new MwHrTrvlngDTO();
            dto.hrTrvlngSeq = l[0] == null ? 0 : new BigDecimal(l[0].toString()).longValue();
            dto.refCdTrvlngRol = l[1] == null ? 0 : new BigDecimal(l[1].toString()).longValue();
            dto.refCdCalcTyp = l[2] == null ? 0 : new BigDecimal(l[2].toString()).longValue();
            dto.refCdPortTypSeq = l[3] == null ? 0 : new BigDecimal(l[3].toString()).longValue();
            dto.fromDt = (java.util.Date) l[4];
            dto.toDt = (java.util.Date) l[5];
            dto.amt = l[6] == null ? 0 : new BigDecimal(l[6].toString()).longValue();

            trvlng.add(dto);
        });

        log.debug("Result set is: {}", q.getMaxResults());
        return trvlng;
    }

    public List<MwHrTrvlngDTO> getTrvlng(Long trvlngRol) {
        String script;
        script = " SELECT TRVLNG.HR_TRVLNG_SEQ,\n" +
            "         RCV.REF_CD_SEQ\n" +
            "             AS TRVLNG_ROL_SEQ,\n" +
            "         RCV.REF_CD_DSCR\n" +
            "             AS TRVLNG_ROL,\n" +
            "         (SELECT RCV.REF_CD_SEQ\n" +
            "            FROM MW_REF_CD_VAL RCV\n" +
            "           WHERE RCV.REF_CD_SEQ = TRVLNG.REF_CD_PORT_TYP_SEQ)\n" +
            "             AS PORT_TYP_SEQ,\n" +
            "         (SELECT RCV.REF_CD_DSCR\n" +
            "            FROM MW_REF_CD_VAL RCV\n" +
            "           WHERE RCV.REF_CD_SEQ = TRVLNG.REF_CD_PORT_TYP_SEQ)\n" +
            "             AS PORT_TYP,\n" +
            "         (SELECT RCV.REF_CD_SEQ\n" +
            "            FROM MW_REF_CD_VAL RCV\n" +
            "           WHERE RCV.REF_CD_SEQ = TRVLNG.REF_CD_CALC_TYP)\n" +
            "             AS CALC_TYP_SEQ,\n" +
            "         (SELECT RCV.REF_CD_DSCR\n" +
            "            FROM MW_REF_CD_VAL RCV\n" +
            "           WHERE RCV.REF_CD_SEQ = TRVLNG.REF_CD_CALC_TYP)\n" +
            "             AS CALC_TYP,\n" +
            "         TRVLNG.AMT," +
            "         TRVLNG.FROM_DT,\n" +
            "         TRVLNG.TO_DT\n" +
            "    FROM MW_HR_TRVLNG TRVLNG\n" +
            "         JOIN MW_REF_CD_VAL RCV ON RCV.REF_CD_SEQ = TRVLNG.REF_CD_TRVLNG_ROL\n" +
            "   WHERE TRVLNG.REF_CD_TRVLNG_ROL = :rol AND TRVLNG.CRNT_REC_FLG = 1\n" +
            " ORDER BY TRVLNG.FROM_DT DESC ";

        Query q = em.createNativeQuery(script).setParameter("rol", trvlngRol);

        List<Object[]> trvlngList = q.getResultList();
        List<MwHrTrvlngDTO> trvlng = new ArrayList<MwHrTrvlngDTO>();

        trvlngList.forEach(l -> {

            MwHrTrvlngDTO dto = new MwHrTrvlngDTO();
            dto.hrTrvlngSeq = l[0] == null ? 0 : new BigDecimal(l[0].toString()).longValue();
            dto.refCdTrvlngRol = l[1] == null ? 0 : new BigDecimal(l[1].toString()).longValue();
            dto.rol = l[2] == null ? "" : l[2].toString();
            dto.refCdPortTypSeq = l[3] == null ? 0 : new BigDecimal(l[3].toString()).longValue();
            dto.portTyp = l[4] == null ? "" : l[4].toString();
            dto.refCdCalcTyp = l[5] == null ? 0 : new BigDecimal(l[5].toString()).longValue();
            dto.calcTyp = l[6] == null ? "" : l[6].toString();
            dto.amt = l[7] == null ? 0 : new BigDecimal(l[7].toString()).longValue();
            dto.fromDt = (java.util.Date) l[8];
            dto.toDt = (java.util.Date) l[9];

            trvlng.add(dto);
        });

        log.debug("Result set is: {}", q.getMaxResults());
        return trvlng;
    }

    public Integer addNewTrvlng(MwHrTrvlngDTO dto, String currUser) {
        Instant currIns = Instant.now();

        if (dto == null) {
            return 0;
        }

        if (dto.hrTrvlngSeq != null) {
            MwHrTrvlng trvlng = mwHrTrvlngRepository.findOneByHrTrvlngSeqAndCrntRecFlg(dto.hrTrvlngSeq, true);
            if (trvlng != null) {
                trvlng.setCrntRecFlg(false);
                trvlng.setLastUpdBy(currUser);
                trvlng.setLastUpdDt(currIns);

                mwHrTrvlngRepository.save(trvlng);
            }
        }

        MwHrTrvlng trvlng = new MwHrTrvlng();
        long trvlngSeq = SequenceFinder.findNextVal(Sequences.HR_TRVLNG_SEQ);

        trvlng.setHrTrvlngSeq(trvlngSeq);
        trvlng.setRefCdTrvlngRol(dto.refCdTrvlngRol);
        trvlng.setRefCdCalcTyp(dto.refCdCalcTyp);
        trvlng.setRefCdPortTypSeq(dto.refCdPortTypSeq);
        //trvlng.setFromDt( dto.fromDt.toInstant() );
        trvlng.setFromDt(dto.fromDt);
        trvlng.setToDt(dto.toDt);
        trvlng.setAmt(dto.amt);
        trvlng.setCrntRecFlg(true);
        trvlng.setCrtdBy(currUser);
        trvlng.setCrtdDt(currIns);
        trvlng.setLastUpdBy(currUser);
        trvlng.setLastUpdDt(currIns);

        mwHrTrvlngRepository.save(trvlng);

        return 1;
    }

    public Integer deleteTrvlng(Long seq, String currUser) {
        Instant currIns = Instant.now();

        if (seq != null) {
            MwHrTrvlng trvlng = mwHrTrvlngRepository.findOneByHrTrvlngSeqAndCrntRecFlg(seq, true);
            if (trvlng != null) {
                trvlng.setCrntRecFlg(false);
                trvlng.setLastUpdBy(currUser);
                trvlng.setLastUpdDt(currIns);

                mwHrTrvlngRepository.save(trvlng);
                return 1;
            }
        }
        return 0;
    }

}
