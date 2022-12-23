package com.idev4.setup.service;

import com.idev4.setup.dto.GlAccountsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class ErpGlAccountsService {

    private final Logger log = LoggerFactory.getLogger(DayClosingService.class);
    @Autowired
    EntityManager em;

    public List<GlAccountsDTO> getAllerpGlAccounts(Long seq) {
        Query query = em.createNativeQuery("select  lh.cust_acc_desc, lh.cust_segments  from mw_typs typ\n" +
                "                join ledger_heads lh on lh.cust_segments=typ.gl_acct_num where typ.crnt_rec_flg=1\n" +
                "                and (typ.brnch_seq=:brnch_seq or typ.brnch_seq=0)                     \n" +
                "                union --typ dfrd acct                \n" +
                "                select  lh.cust_acc_desc, lh.cust_segments  from mw_typs typ\n" +
                "                join ledger_heads lh on lh.cust_segments=typ.dfrd_acct_num where typ.crnt_rec_flg=1\n" +
                "                and (typ.brnch_seq=:brnch_seq or typ.brnch_seq=0)                 \n" +
                "                union                 \n" +
                "                select  lh.cust_acc_desc, lh.cust_segments \n" +
                "                from mw_typs typ join ledger_heads lh on lh.cust_segments=typ.bddt_acct_num where typ.crnt_rec_flg=1\n" +
                "                and (typ.brnch_seq=:brnch_seq or typ.brnch_seq=0)                 \n" +
                "                union                 \n" +
                "                select  lh.cust_acc_desc, lh.cust_segments  from mw_prd_acct_set prd\n" +
                "                join ledger_heads lh on lh.cust_segments=prd.gl_acct_num where prd.crnt_rec_flg=1                  \n" +
                "                union                \n" +
                "                select  lh.cust_acc_desc, lh.cust_segments  from mw_prd_acct_set prd\n" +
                "                join ledger_heads lh on lh.cust_segments=prd.GL_ACCT_NUM_WO where prd.crnt_rec_flg=1                \n" +
                "                union                \n" +
                "                select  lh.cust_acc_desc, lh.cust_segments  from mw_prd_acct_set prd\n" +
                "                join ledger_heads lh on lh.cust_segments=prd.GL_ACCT_NUM_BDDT where prd.crnt_rec_flg=1                \n" +
                "                union                \n" +
                "                select  lh.cust_acc_desc, lh.cust_segments  from mw_hlth_insr_plan pln \n" +
                "                join ledger_heads lh on lh.cust_segments=pln.gl_acct_num where pln.crnt_rec_flg=1  \n" +
                "                union\n" +
                "                select  lh.cust_acc_desc, lh.cust_segments  from mw_hlth_insr_plan pln \n" +
                "                join ledger_heads lh on lh.cust_segments=pln.dfrd_acct_num where pln.crnt_rec_flg=1  \n" +
                "                union\n" +
                "                select  lh.cust_acc_desc, lh.cust_segments  from mw_hlth_insr_plan pln \n" +
                "                join ledger_heads lh on lh.cust_segments=pln.bddt_acct_num where pln.crnt_rec_flg=1\n")
            .setParameter("brnch_seq", seq);
        List<Object[]> records = query.getResultList();
        List<GlAccountsDTO> dtos = new ArrayList<GlAccountsDTO>();
        records.forEach(r -> {
            GlAccountsDTO dto = new GlAccountsDTO();
            dto.accDesc = r[0] == null ? "" : r[0].toString();
            dto.accNum = r[1] == null ? "" : r[1].toString();
            dtos.add(dto);
        });

        return dtos;
    }

}
