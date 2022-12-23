package com.idev4.setup.service;

import com.idev4.setup.dto.AccessRecoveryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AccessRecoveryService {

    private final EntityManager em;

    private final Logger log = LoggerFactory.getLogger(DayClosingService.class);

    public AccessRecoveryService(EntityManager em) {
        this.em = em;
    }

    // public List< AccessRecoveryDto > getAccessRecoveryData( long brnch ) {
    // String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
    // Query query = em.createNativeQuery( "select trx.rcvry_trx_seq as txID , \r\n"
    // + " c.clnt_Id as ClientId, concat(c.frst_nm,c.last_nm) as ClientName, \r\n"
    // + " d.pymt_amt as AccessAmount, PYMT_MOD_KEY as paymentMode, trx.INSTR_NUM as insturmentNum, b.brnch_Seq as
    // branchSeq,b.BRNCH_NM,trx.PYMT_DT,pymt.TYP_STR , trx.PRNT_RCVRY_REF \r\n"
    // + " from mw_rcvry_trx trx join mw_rcvry_dtl d on trx.rcvry_trx_seq=d.rcvry_trx_seq and d.crnt_rec_flg=1 \r\n"
    // + " join mw_clnt c on trx.pymt_ref=c.clnt_seq and c.crnt_rec_flg=1 \r\n"
    // + " join mw_port p on c.port_key=p.port_seq and p.crnt_rec_flg=1 \r\n"
    // + " join mw_brnch b on b.brnch_seq=p.brnch_seq and b.crnt_rec_flg=1 \r\n"
    // + " join mw_typs pymt on pymt.typ_seq=trx.RCVRY_TYP_SEQ and pymt.crnt_rec_flg=1\r\n"
    // + " where d.pymt_sched_dtl_seq is null and d.CHRG_TYP_KEY = 241 and trunc(d.crtd_dt)<=sysdate \r\n"
    // + " and trx.crnt_rec_flg=1 and b.BRNCH_SEQ =:brnch and not exists (select exp_ref from mw_exp e \r\n"
    // + " join mw_typs t on t.typ_seq=e.EXPNS_TYP_SEQ and t.typ_id='0005' \r\n"
    // + " and t.typ_ctgry_key=2 and t.crnt_rec_flg=1 where e.crnt_rec_flg=1 and e.del_flg=0 and exp_ref=trx.rcvry_trx_seq) order by 1 desc"
    // )
    // .setParameter( "brnch", brnch );
    // List< Object[] > records = query.getResultList();
    //
    // log.debug( records.toString() );
    //
    // List< AccessRecoveryDto > accessRecoveryDtos = new ArrayList< AccessRecoveryDto >();
    //
    // records.forEach( r -> {
    // AccessRecoveryDto dto = new AccessRecoveryDto();
    // dto.txId = r[ 0 ] == null ? "" : r[ 0 ].toString();
    // dto.clientId = r[ 1 ] == null ? "" : r[ 1 ].toString();
    // dto.clientName = r[ 2 ] == null ? "" : r[ 2 ].toString();
    // dto.accessAmount = r[ 3 ] == null ? "" : r[ 3 ].toString();
    // dto.paymentMode = r[ 4 ] == null ? "" : r[ 4 ].toString();
    // dto.insturmentNum = r[ 5 ] == null ? "" : r[ 5 ].toString();
    // dto.branchSeq = r[ 6 ] == null ? "" : r[ 6 ].toString();
    // dto.branchNm = r[ 7 ] == null ? "" : r[ 7 ].toString();
    // dto.pymtDt = r[ 8 ] == null ? "" : r[ 8 ].toString();
    // dto.agent = r[ 9 ] == null ? "" : r[ 9 ].toString();
    // dto.prntRef = r[ 10 ] == null ? "" : r[ 10 ].toString();
    // accessRecoveryDtos.add( dto );
    // } );
    // return accessRecoveryDtos;
    // }

    public Map<String, Object> getAccessRecoveryData(long brnchSeq, Integer pageIndex, Integer pageSize, String filter,
                                                     Boolean isCount) {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        /*
        Added changes to get transfer clients access recovery Dated: 14-02-2022
         */

        /**
         * @Modified, Naveed
         * @Date, 14-06-2022
         * @Description, SCR - Shifted Clients
         */

        //Modified by Areeba - for adc recoveries - 16-9-2022
        String accessRecoveryScript = " with qry as (\n" +
            "            \n" +
            "             select /*+ materialize */ exp.exp_ref,trx.rcvry_trx_seq as txID ,\n" +
            "            c.clnt_Id as ClientId, concat(c.frst_nm,c.last_nm) as ClientName,\n" +
            "            d.pymt_amt as AccessAmount, PYMT_MOD_KEY as paymentMode, trx.INSTR_NUM as insturmentNum, b.brnch_Seq as branchSeq,b.BRNCH_NM,trx.PYMT_DT,pymt.TYP_STR, pymt.brnch_seq, trx.PRNT_RCVRY_REF,\n" +
            "            trf.clnt_seq trf_clnt_seq\n" +
            "            from mw_rcvry_trx trx join mw_rcvry_dtl d on trx.rcvry_trx_seq=d.rcvry_trx_seq and d.crnt_rec_flg=1\n" +
            "            join mw_clnt c on trx.pymt_ref=c.clnt_seq and c.crnt_rec_flg=1\n" +
            "            join mw_port p on c.port_key=p.port_seq and p.crnt_rec_flg=1\n" +
            "            join mw_brnch b on b.brnch_seq=p.brnch_seq and b.crnt_rec_flg=1\n" +
            "            join mw_typs pymt on pymt.typ_seq=trx.RCVRY_TYP_SEQ and pymt.crnt_rec_flg=1\n" +
            "            left outer join RPTB_PORT_TRF_DETAIL trf on trf.CLNT_SEQ = c.CLNT_SEQ and trx.EFF_START_DT <= trf.TRF_DT and trf.REMARKS = 'EXCESS'\n" +
            "            left outer join (select exp_ref from mw_exp e join mw_typs t on t.typ_seq=e.EXPNS_TYP_SEQ and t.typ_id='0005' and t.typ_ctgry_key=2 and t.crnt_rec_flg=1 where e.crnt_rec_flg=1 and e.del_flg=0\n" +
            "            ) exp on exp.exp_ref=to_char(trx.rcvry_trx_seq)\n" +
            "            where (d.pymt_sched_dtl_seq = -1 or d.pymt_sched_dtl_seq is null) and d.CHRG_TYP_KEY = 241 and trunc(trx.PYMT_DT) <= ('06-JUL-2022')\n" +
            "            and trx.crnt_rec_flg=1 and b.BRNCH_SEQ =:brnchSeq\n" +
            "            \n" +
            "            UNION ALL\n" +
            "            \n" +
            "            select /*+ materialize */ exp.exp_ref,trx.rcvry_trx_seq as txID ,\n" +
            "            c.clnt_Id as ClientId, concat(c.frst_nm,c.last_nm) as ClientName,\n" +
            "            d.pymt_amt as AccessAmount, PYMT_MOD_KEY as paymentMode, trx.INSTR_NUM as insturmentNum, b.brnch_Seq as branchSeq,b.BRNCH_NM,trx.PYMT_DT,pymt.TYP_STR, pymt.brnch_seq, trx.PRNT_RCVRY_REF,\n" +
            "            trf.clnt_seq trf_clnt_seq\n" +
            "            from mw_rcvry_trx trx join mw_rcvry_dtl d on trx.rcvry_trx_seq=d.rcvry_trx_seq and d.crnt_rec_flg=1 \n" +
            "            join mw_clnt c on trx.pymt_ref=c.clnt_seq and c.crnt_rec_flg=1\n" +
            "            JOIN MW_LOAN_APP AP ON AP.PRNT_LOAN_APP_SEQ = TRX.PRNT_LOAN_APP_SEQ AND AP.CRNT_REC_FLG = 1\n" +
            "            JOIN MW_BRNCH B ON B.BRNCH_SEQ = AP.BRNCH_SEQ AND B.CRNT_REC_FLG = 1\n" +
            "            JOIN MW_PORT P ON P.PORT_SEQ =  AP.PORT_SEQ AND P.CRNT_REC_FLG = 1\n" +
            "            join mw_typs pymt on pymt.typ_seq=trx.RCVRY_TYP_SEQ and pymt.crnt_rec_flg=1\n" +
            "            left outer join RPTB_PORT_TRF_DETAIL trf on trf.CLNT_SEQ = c.CLNT_SEQ and trx.EFF_START_DT <= trf.TRF_DT and trf.REMARKS = 'EXCESS'\n" +
            "            left outer join (select exp_ref from mw_exp e join mw_typs t on t.typ_seq=e.EXPNS_TYP_SEQ and t.typ_id='0005' and t.typ_ctgry_key=2 and t.crnt_rec_flg=1 where e.crnt_rec_flg=1 and e.del_flg=0\n" +
            "            ) exp on exp.exp_ref=to_char(trx.rcvry_trx_seq)\n" +
            "            where (d.pymt_sched_dtl_seq = -1 or d.pymt_sched_dtl_seq is null) and d.CHRG_TYP_KEY = 241 and trunc(trx.PYMT_DT)> ('06-JUL-2022')\n" +
            "            and trx.crnt_rec_flg=1 and AP.LOAN_APP_SEQ = ap.PRNT_LOAN_APP_SEQ and b.BRNCH_SEQ =:brnchSeq  ORDER BY 1\n" +
            "            \n" +
            "            ) select txID ,\n" +
            "            ClientId, ClientName,AccessAmount, paymentMode, insturmentNum, branchSeq,BRNCH_NM,PYMT_DT,TYP_STR, brnch_seq,PRNT_RCVRY_REF,\n" +
            "            case when trf_clnt_seq is not null then 'T' else null end as trf_clnt_seq\n" +
            "            from qry where exp_ref is null %% ";

        String accessRecoveryCount = "with qry as (\n" +
            "\n" +
            " select /*+ materialize */ exp.exp_ref,trx.rcvry_trx_seq as txID ,\n" +
            "c.clnt_Id as ClientId, concat(c.frst_nm,c.last_nm) as ClientName,\n" +
            "d.pymt_amt as AccessAmount, PYMT_MOD_KEY as paymentMode, trx.INSTR_NUM as insturmentNum, b.brnch_Seq as branchSeq,b.BRNCH_NM,trx.PYMT_DT,pymt.TYP_STR , trx.PRNT_RCVRY_REF,\n" +
            "trf.clnt_seq trf_clnt_seq\n" +
            "from mw_rcvry_trx trx join mw_rcvry_dtl d on trx.rcvry_trx_seq=d.rcvry_trx_seq and d.crnt_rec_flg=1\n" +
            "join mw_clnt c on trx.pymt_ref=c.clnt_seq and c.crnt_rec_flg=1\n" +
            "join mw_port p on c.port_key=p.port_seq and p.crnt_rec_flg=1\n" +
            "join mw_brnch b on b.brnch_seq=p.brnch_seq and b.crnt_rec_flg=1\n" +
            "join mw_typs pymt on pymt.typ_seq=trx.RCVRY_TYP_SEQ and pymt.crnt_rec_flg=1\n" +
            "left outer join RPTB_PORT_TRF_DETAIL trf on trf.CLNT_SEQ = c.CLNT_SEQ and trx.EFF_START_DT <= trf.TRF_DT and trf.REMARKS = 'EXCESS'\n" +
            "left outer join (select exp_ref from mw_exp e join mw_typs t on t.typ_seq=e.EXPNS_TYP_SEQ and t.typ_id='0005' and t.typ_ctgry_key=2 and t.crnt_rec_flg=1 where e.crnt_rec_flg=1 and e.del_flg=0\n" +
            ") exp on exp.exp_ref=to_char(trx.rcvry_trx_seq)\n" +
            "where (d.pymt_sched_dtl_seq = -1 or d.pymt_sched_dtl_seq is null) and d.CHRG_TYP_KEY = 241 and trunc(trx.PYMT_DT) <= ('06-JUL-2022')\n" +
            "and trx.crnt_rec_flg=1 and b.BRNCH_SEQ =:brnchSeq\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "select /*+ materialize */ exp.exp_ref,trx.rcvry_trx_seq as txID ,\n" +
            "c.clnt_Id as ClientId, concat(c.frst_nm,c.last_nm) as ClientName,\n" +
            "d.pymt_amt as AccessAmount, PYMT_MOD_KEY as paymentMode, trx.INSTR_NUM as insturmentNum, b.brnch_Seq as branchSeq,b.BRNCH_NM,trx.PYMT_DT,pymt.TYP_STR , trx.PRNT_RCVRY_REF,\n" +
            "trf.clnt_seq trf_clnt_seq\n" +
            "from mw_rcvry_trx trx join mw_rcvry_dtl d on trx.rcvry_trx_seq=d.rcvry_trx_seq and d.crnt_rec_flg=1 \n" +
            "join mw_clnt c on trx.pymt_ref=c.clnt_seq and c.crnt_rec_flg=1\n" +
            "JOIN MW_LOAN_APP AP ON AP.PRNT_LOAN_APP_SEQ = TRX.PRNT_LOAN_APP_SEQ AND AP.CRNT_REC_FLG = 1\n" +
            "JOIN MW_BRNCH B ON B.BRNCH_SEQ = AP.BRNCH_SEQ AND B.CRNT_REC_FLG = 1\n" +
            "JOIN MW_PORT P ON P.PORT_SEQ =  AP.PORT_SEQ AND P.CRNT_REC_FLG = 1\n" +
            "join mw_typs pymt on pymt.typ_seq=trx.RCVRY_TYP_SEQ and pymt.crnt_rec_flg=1\n" +
            "left outer join RPTB_PORT_TRF_DETAIL trf on trf.CLNT_SEQ = c.CLNT_SEQ and trx.EFF_START_DT <= trf.TRF_DT and trf.REMARKS = 'EXCESS'\n" +
            "left outer join (select exp_ref from mw_exp e join mw_typs t on t.typ_seq=e.EXPNS_TYP_SEQ and t.typ_id='0005' and t.typ_ctgry_key=2 and t.crnt_rec_flg=1 where e.crnt_rec_flg=1 and e.del_flg=0\n" +
            ") exp on exp.exp_ref=to_char(trx.rcvry_trx_seq)\n" +
            "where (d.pymt_sched_dtl_seq = -1 or d.pymt_sched_dtl_seq is null) and d.CHRG_TYP_KEY = 241 and trunc(trx.PYMT_DT)> ('06-JUL-2022')\n" +
            "and trx.crnt_rec_flg=1 and b.BRNCH_SEQ =:brnchSeq  ORDER BY 1\n" +
            "\n" +
            ") select count(*), sum(AccessAmount) sum_amt \n" +
            "from qry where exp_ref is null %% ";

        String search = "";
        if (filter != null && filter.length() > 0) {
            search = " and ((lower(txID) like '%?%') OR (lower(ClientId) like '%?%')) ".replace("?", filter.toLowerCase());
        }
        accessRecoveryScript = accessRecoveryScript.replaceAll("%%", search);
        accessRecoveryCount = accessRecoveryCount.replaceAll("%%", search);

        List<Object[]> records = em.createNativeQuery(accessRecoveryScript + "\r\norder by 1 desc").setParameter("brnchSeq", brnchSeq)
            .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        List<AccessRecoveryDto> accessRecoveryDtos = new ArrayList<AccessRecoveryDto>();

        records.forEach(r -> {
            AccessRecoveryDto dto = new AccessRecoveryDto();
            dto.txId = r[0] == null ? "" : r[0].toString();
            dto.clientId = r[1] == null ? "" : r[1].toString();
            dto.clientName = r[2] == null ? "" : r[2].toString();
            dto.accessAmount = r[3] == null ? "" : r[3].toString();
            dto.paymentMode = r[4] == null ? "" : r[4].toString();
            dto.insturmentNum = r[5] == null ? "" : r[5].toString();
            dto.branchSeq = r[6] == null ? "" : r[6].toString();
            dto.branchNm = r[7] == null ? "" : r[7].toString();
            dto.pymtDt = r[8] == null ? "" : r[8].toString();
            dto.agent = r[9] == null ? "" : r[9].toString();
            //Added by Areeba
            dto.agentBrnchSeq = r[10] == null ? 0L : new BigDecimal(r[10].toString()).longValue();
            //Ended by Areeba
            dto.prntRef = r[11] == null ? "" : r[11].toString();
            dto.trf_clnt_seq = r[12] == null ? "" : r[12].toString();
            accessRecoveryDtos.add(dto);
        });

        Map<String, Object> resp = new HashMap<>();
        resp.put("accessrcvry", accessRecoveryDtos);

        Long totalCountResult = 0L;
        Long totalAmount = 0L;
        if (isCount.booleanValue()) {
            List<Object[]> totalArray = em.createNativeQuery(accessRecoveryCount).setParameter("brnchSeq", brnchSeq).getResultList();
            Object[] obj = totalArray.get(0);
            totalCountResult = obj[0] == null ? 0 : new BigDecimal(obj[0].toString()).longValue();
            totalAmount = obj[1] == null ? 0 : new BigDecimal(obj[1].toString()).longValue();
        }
        resp.put("count", totalCountResult);
        resp.put("totAmt", totalAmount);
        return resp;
    }
}
