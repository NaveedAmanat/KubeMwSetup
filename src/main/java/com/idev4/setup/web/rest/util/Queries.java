package com.idev4.setup.web.rest.util;

public class Queries {

    public static String getAclForUser = "select * from mw_acl where user_id='";

    public static String statusSeq = "select grp.ref_cd_grp_nm, val.ref_cd_dscr, val.ref_cd_seq from mw_ref_cd_grp grp,"
        + " mw_ref_cd_val val where val.ref_cd_grp_key = grp.ref_cd_grp_seq AND grp.ref_cd_grp_nm = 'STATUS'";

    public static String productsListing = "select product_SEQ,product_name,installments,ref_cd_dscr as calculation_type,"
        + "service_charges,minimum_amount,maximum_amount,condition from  mw_ref_cd_val v join "
        + "(select p.prd_SEQ as product_SEQ,p.prd_nm as product_name,v.ref_cd_dscr as installments, r.rul_Condition_str as condition , "
        + "c.chrg_calc_typ_Key,c.CHRG_VAL as service_charges, l.min_amt as minimum_amount,l.max_amt as maximum_amount "
        + "from MW_prd_ppal_lmt l join mw_prd p on p.prd_SEQ=l.prd_SEQ join MW_prd_rul r on "
        + "r.rul_seq=l.rul_seq join MW_PRD_CHRG c on " + "c.rul_seq=l.rul_seq join MW_prd_loan_trm t on "
        + "r.rul_seq=t.rul_seq and p.prd_seq=t.prd_seq "
        + "join mw_ref_cd_val v on v.ref_cd_seq=t.trms_key where p.crnt_rec_flg=1 and r.crnt_rec_flg=1 and c.crnt_rec_flg=1 and l.crnt_rec_flg=1 and t.crnt_rec_flg=1)tbl on v.ref_cd_seq=tbl.chrg_calc_typ_Key";

    public static String geographyQuery = "select cntry.cntry_seq,cntry.cntry_nm, st.st_seq,st.st_nm,dist.dist_seq,dist.dist_nm,"
        + "thsl.thsl_seq, thsl.thsl_nm,uc.uc_seq ,uc.uc_cd,uc.uc_nm,uc.uc_commnets from mw_cntry cntry,mw_st st, "
        + "mw_dist dist,mw_thsl thsl, mw_uc uc where uc.thsl_SEQ=thsl.thsl_SEQ and thsl.dist_SEQ=dist.dist_SEQ and "
        + "dist.st_SEQ=st.st_SEQ and st.cntry_SEQ=cntry.cntry_SEQ and cntry.crnt_rec_flg=1 and st.crnt_rec_flg=1 and dist.crnt_rec_flg=1 and thsl.crnt_rec_flg=1 and uc.crnt_rec_flg=1";

    public static String getOrgFromCityUcRelSeq = "SELECT REL.city_uc_rel_seq, rel.city_seq, city.city_nm, rel.uc_seq, uc.uc_nm, thsl.thsl_seq, thsl.thsl_nm, dist.dist_seq, dist.dist_nm, st.st_seq, st.st_nm, cntry.cntry_seq, cntry.cntry_nm FROM mw_city_uc_rel rel JOIN mw_city city ON rel.city_seq = city.city_seq AND city.crnt_rec_flg = 1 JOIN mw_uc uc ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1 JOIN mw_thsl thsl ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1 JOIN mw_dist dist ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1 JOIN mw_st st ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1 JOIN mw_cntry cntry ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1 WHERE REL.city_uc_rel_seq=";

    public static String orgnizationQuery = "select reg.reg_seq,reg_nm, area.area_seq, area.area_nm, branch.brnch_seq,branch.brnch_nm,port.port_seq,"
        + "port_nm, cmnty.cmnty_seq, cmnty.cmnty_nm from mw_reg reg, mw_area area, mw_brnch branch, mw_port port, mw_cmnty cmnty where "
        + "area.reg_seq = reg.reg_seq and branch.area_seq = area.area_seq and port.brnch_seq = branch.brnch_seq and cmnty.port_seq ="
        + "port.port_seq AND reg.crnt_rec_flg=1 and area.crnt_rec_flg=1 and branch.crnt_rec_flg=1 and port.crnt_rec_flg=1 and cmnty.crnt_rec_flg=1 and port.port_seq =";

    public static String otherFiltersQuery = "select grp.ref_cd_grp_seq, grp.ref_cd_grp, grp.ref_cd_grp_nm, val.ref_cd_seq, val.ref_cd_grp_key, "
        + "val.ref_cd_dscr from mw_ref_cd_grp grp, mw_ref_cd_val val where val.ref_cd_grp_key = grp.ref_cd_grp_seq AND (grp.REF_CD_GRP_NM='GENDER'"
        + " OR grp.REF_CD_GRP_NM = 'MARITAL_STATUS')";

    public static String addressCombs = "select st.st_cd as cd ,st.st_nm as nm, st.st_cd,st.st_nm,dst.dist_cd,dst.dist_nm, thsl.thsl_cd, thsl.thsl_nm,uc.uc_cd,uc.uc_nm,uc.uc_commnets,rel.city_uc_rel_seq,cty.city_nm from mw_city_uc_rel rel, mw_city cty, mw_uc uc, mw_thsl thsl, mw_dist dst, mw_st st where cty.city_seq = rel.city_seq and rel.uc_seq = uc.uc_seq and uc.thsl_seq = thsl.thsl_seq and thsl.dist_seq = dst.dist_seq and dst.st_seq = st.st_seq and rel.del_flg = 0 and cty.del_flg = 0 and uc.del_flg = 0 and thsl.del_flg = 0 and dst.del_flg = 0 and st.del_flg = 0 and rel.crnt_rec_flg = 1 and cty.crnt_rec_flg = 1 and uc.crnt_rec_flg = 1 and thsl.crnt_rec_flg = 1 and dst.crnt_rec_flg = 1 and st.crnt_rec_flg = 1";

    //Added by Areeba
    public static String addressCombs2 = "select st.st_cd as cd ,st.st_nm as nm, st.st_cd,st.st_nm,dst.dist_cd,dst.dist_nm, thsl.thsl_cd, thsl.thsl_nm,uc.uc_cd,uc.uc_nm,uc.uc_commnets,rel.city_uc_rel_seq,cty.city_nm from mw_city_uc_rel rel, mw_city cty, mw_uc uc, mw_thsl thsl, mw_dist dst, mw_st st where cty.city_seq = rel.city_seq and rel.uc_seq = uc.uc_seq and uc.thsl_seq = thsl.thsl_seq and thsl.dist_seq = dst.dist_seq and dst.st_seq = st.st_seq and rel.del_flg = 0 and cty.del_flg = 0 and uc.del_flg = 0 and thsl.del_flg = 0 and dst.del_flg = 0 and st.del_flg = 0 and rel.crnt_rec_flg = 1 and cty.crnt_rec_flg = 1 and uc.crnt_rec_flg = 1 and thsl.crnt_rec_flg = 1 and dst.crnt_rec_flg = 1 and st.crnt_rec_flg = 1 ?";

    public static String UCCombo = "select uc.uc_seq, st.st_nm, dst.dist_nm, thsl.thsl_nm, uc.uc_nm from mw_uc uc, mw_thsl thsl, mw_dist dst, mw_st st where uc.thsl_seq = thsl.thsl_seq and thsl.dist_seq = dst.dist_seq and dst.st_seq = st.st_seq and uc.del_flg = 0 and thsl.del_flg = 0 and dst.del_flg = 0 and st.del_flg = 0 and uc.crnt_rec_flg = 1 and thsl.crnt_rec_flg = 1 and dst.crnt_rec_flg = 1 and st.crnt_rec_flg = 1";

    //Added by Areeba
    public static String UCCombo2 = " select uc_seq, st_nm, dist_nm, thsl_nm, uc_nm, uc_commnets from (\n" +
        " select uc_seq, st_nm, dist_nm, thsl_nm, uc_nm, uc_commnets,'checked' as chk\n" +
        " from(\n" +
        " select uc.uc_seq, st.st_nm, dst.dist_nm, thsl.thsl_nm, uc.uc_nm, uc.uc_commnets\n" +
        " from mw_thsl thsl, mw_dist dst, mw_st st, mw_uc uc\n" +
        " join mw_city_uc_rel cur on cur.uc_seq = uc.uc_seq\n" +
        " where uc.thsl_seq = thsl.thsl_seq and thsl.dist_seq = dst.dist_seq and dst.st_seq = st.st_seq \n" +
        " and uc.del_flg = 0 and thsl.del_flg = 0 and dst.del_flg = 0 and st.del_flg = 0 \n" +
        " and uc.crnt_rec_flg = 1 and thsl.crnt_rec_flg = 1 and dst.crnt_rec_flg = 1 and st.crnt_rec_flg = 1\n" +
        " and cur.city_seq = :city \n" +
        " and cur.del_flg = 0 \n" +
        " and cur.crnt_rec_flg = 1\n" +
        " )\n" +
        " UNION\n" +
        " select uc_seq, st_nm, dist_nm, thsl_nm, uc_nm, uc_commnets,'unchecked' as chk\n" +
        " from(\n" +
        " select uc.uc_seq, st.st_nm, dst.dist_nm, thsl.thsl_nm, uc.uc_nm, uc.uc_commnets from mw_uc uc, mw_thsl thsl, mw_dist dst, mw_st st where uc.thsl_seq = thsl.thsl_seq and thsl.dist_seq = dst.dist_seq and dst.st_seq = st.st_seq and uc.del_flg = 0 and thsl.del_flg = 0 and dst.del_flg = 0 and st.del_flg = 0 and uc.crnt_rec_flg = 1 and thsl.crnt_rec_flg = 1 and dst.crnt_rec_flg = 1 and st.crnt_rec_flg = 1\n" +
        " and uc.uc_seq not in (\n" +
        " select uc.uc_seq\n" +
        " from mw_thsl thsl, mw_dist dst, mw_st st, mw_uc uc\n" +
        " join mw_city_uc_rel cur on cur.uc_seq = uc.uc_seq\n" +
        " where uc.thsl_seq = thsl.thsl_seq and thsl.dist_seq = dst.dist_seq and dst.st_seq = st.st_seq \n" +
        " and uc.del_flg = 0 and thsl.del_flg = 0 and dst.del_flg = 0 and st.del_flg = 0 \n" +
        " and uc.crnt_rec_flg = 1 and thsl.crnt_rec_flg = 1 and dst.crnt_rec_flg = 1 and st.crnt_rec_flg = 1\n" +
        " and cur.city_seq = :city \n" +
        " and cur.del_flg = 0 \n" +
        " and cur.crnt_rec_flg = 1 )\n" +
        " )\n" +
        " order by chk) " +
        " where st_nm <> 'Invalid'\n" +
        "          and dist_nm <> 'invalid'\n" +
        "          and thsl_nm <> 'invalid'\n" +
        "          and uc_nm <> 'invalid' ? ";
    //Ended by Areeba
    public static String entityAddress = "select cntry.cntry_cd,cntry.cntry_nm, st.st_cd,st.st_nm,dist.dist_cd,dist.dist_nm, thsl.thsl_cd,"
        + "thsl.thsl_nm,uc.uc_cd,uc.uc_nm,uc.uc_commnets,city.city_cd,city.city_nm from mw_cntry cntry,mw_st st, "
        + "mw_dist dist,mw_thsl thsl, mw_uc uc, mw_city city where city.uc_SEQ=uc.uc_seq and"
        + " uc.thsl_SEQ=thsl.thsl_SEQ and thsl.dist_SEQ=dist.dist_SEQ and dist.st_SEQ=st.st_SEQ and "
        + "st.cntry_SEQ=cntry.cntry_SEQ and cntry.crnt_rec_flg=1 and st.crnt_rec_flg=1 and dist.crnt_rec_flg=1 and thsl.crnt_rec_flg=1 and uc.crnt_rec_flg=1 and city.crnt_rec_flg=1 and city.city_seq  = ";

    public static String CLNT_LOAN_GTT = "insert into CLNT_LOAN_GTT (SELECT c.clnt_seq,    c.clnt_id, \r\n"
        + "              c.cnic_num,    c.cnic_expry_dt,    c.frst_nm,    c.last_nm,    c.nick_nm,    c.mthr_madn_nm, \r\n"
        + "              c.fthr_frst_nm,    c.fthr_last_nm,    c.spz_frst_nm,    c.spz_last_nm,    c.dob,    c.num_of_dpnd, \r\n"
        + "              c.erng_memb,    c.hse_hld_memb,    c.num_of_chldrn,    c.num_of_erng_memb,    c.yrs_res, \r\n"
        + "              c.mnths_res,    c.port_key,    c.gndr_key,    c.mrtl_sts_key,    c.edu_lvl_key,    c.occ_key, \r\n"
        + "              c.natr_of_dis_key,    c.clnt_sts_key,    c.res_typ_key,    c.dis_flg,    c.slf_pdc_flg, \r\n"
        + "              c.crnt_addr_perm_flg,    c.ph_num,    l.loan_app_seq,    l.loan_id,    l.loan_cycl_num, \r\n"
        + "              l.rqstd_loan_amt,    l.aprvd_loan_amt,    l.prd_seq,    l.rcmnd_loan_amt,    l.loan_app_sts, \r\n"
        + "              l.cmnt,    l.REJECTION_REASON_CD,    l.port_seq  FROM mw_clnt c  JOIN mw_loan_app l \r\n"
        + "            ON l.clnt_seq=c.clnt_seq  WHERE l.crnt_rec_flg=1 AND c.crnt_rec_flg=1 AND c.clnt_seq =:clntSeq and l.loan_app_seq=:loanAppSeq)";

    public static String checkRule = "select 1 from CLNT_LOAN_GTT where ";

    public static String advRule = "Select * from vw_loan_app where loan_app_seq=";

    /* Added by Yousaf Ali - Dated 18-Jan-2022
     * Portfolio Transfer */
    public static String tranfersData = "SELECT 1, clnt.frst_nm, clnt.last_nm, clnt.clnt_seq, clnt.cnic_num, (\n" +
        "    \t\t            SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = clnt.gndr_key AND crnt_rec_flg = 1\n" +
        "    \t\t            ) AS gender_key, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = clnt.mrtl_sts_key\n" +
        "    \t\t            AND crnt_rec_flg = 1 ) AS marital_sts,\n" +
        "    \t\t            ad.hse_num, cit.city_nm, uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm, port.port_nm,\n" +
        "    \t\t            brnch.brnch_nm, area.area_nm, reg.reg_nm, cmnty.cmnty_nm, ad.oth_dtl, ad.vlg, clnt.clnt_id, brnch.brnch_seq\n" +
        "    \t\t            FROM mw_clnt clnt \n" +
        "    \t\t            Join mw_loan_app mla on mla.clnt_seq=clnt.clnt_seq and mla.crnt_rec_flg=1 and mla.loan_app_sts = 703\n" +
        "                        JOIN mw_dsbmt_vchr_hdr hdr on hdr.LOAN_APP_SEQ = mla.LOAN_APP_SEQ and hdr.crnt_rec_flg =1                        \n" +
        "    \t\t            join mw_prd mp on mp.prd_seq=mla.prd_seq and mp.crnt_rec_flg=1 and mp.prd_typ_key!=1165\n" +
        "    \t\t            JOIN mw_port port ON port.port_seq = clnt.port_key AND port.crnt_rec_flg = 1\n" +
        "    \t\t            JOIN mw_acl acl ON acl.port_seq = clnt.port_key AND acl.user_id = :userId\n" +
        "    \t\t            LEFT OUTER JOIN mw_addr_rel ar ON ar.enty_key = clnt.clnt_seq AND ar.crnt_rec_flg = 1 AND ar.enty_typ = 'Client'\n" +
        "    \t\t            LEFT OUTER JOIN mw_addr ad ON ad.addr_seq = ar.addr_seq AND ad.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_city_uc_rel rel ON rel.city_uc_rel_seq = ad.city_seq\n" +
        "    \t\t            LEFT OUTER JOIN mw_city cit ON cit.city_seq = rel.city_seq AND cit.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_uc uc ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_thsl thsl ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_dist dist ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_st st ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_cntry cntry ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_brnch brnch ON brnch.brnch_seq = port.brnch_seq AND brnch.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_area area ON area.area_seq = brnch.area_seq AND area.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_reg reg ON reg.reg_seq = area.reg_seq AND reg.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_cmnty cmnty ON cmnty.cmnty_seq = ad.cmnty_seq AND cmnty.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_port_emp_rel perel ON perel.port_seq = port.port_seq AND perel.crnt_rec_flg = 1\n" +
        "    \t\t            LEFT OUTER JOIN mw_emp emp ON emp.emp_seq = perel.emp_seq \n" +
        "    \t\t            WHERE clnt.crnt_rec_flg = 1\n" +
        "    \t\t            and port.brnch_seq=:brnchSeq AND port.port_seq = (CASE WHEN :portSeq = -1 THEN port.port_seq ELSE :portSeq END)\n" +
        "    \t\t            and not exists \n" +
        "    \t\t            (select dth.clnt_seq from mw_dth_rpt dth where dth.clnt_seq = clnt.clnt_seq and dth.crnt_rec_flg=1\n" +
        "    \t\t            and trunc(dth.dt_of_dth) >= trunc(hdr.dsbmt_dt))";

    /* Added by Yousaf Ali - Dated 18-Jan-2022
     * Portfolio Transfer */
    public static String tranfersDataCount = "SELECT count(*)\n" +
        "        FROM mw_clnt clnt \n" +
        "        Join mw_loan_app mla on mla.clnt_seq=clnt.clnt_seq and mla.crnt_rec_flg=1 and mla.loan_app_sts = 703\n" +
        "        JOIN mw_dsbmt_vchr_hdr hdr on hdr.LOAN_APP_SEQ = mla.LOAN_APP_SEQ and hdr.crnt_rec_flg =1\n" +
        "        join mw_prd mp on mp.prd_seq=mla.prd_seq and mp.crnt_rec_flg=1 and mp.prd_typ_key!=1165\n" +
        "        JOIN mw_port port ON port.port_seq = clnt.port_key AND port.crnt_rec_flg = 1\n" +
        "        JOIN mw_acl acl ON acl.port_seq = clnt.port_key AND acl.user_id = :userId\n" +
        "        LEFT OUTER JOIN mw_addr_rel ar ON ar.enty_key = clnt.clnt_seq AND ar.crnt_rec_flg = 1 AND ar.enty_typ = 'Client'\n" +
        "        LEFT OUTER JOIN mw_addr ad ON ad.addr_seq = ar.addr_seq AND ad.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_city_uc_rel rel ON rel.city_uc_rel_seq = ad.city_seq\n" +
        "        LEFT OUTER JOIN mw_city cit ON cit.city_seq = rel.city_seq AND cit.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_uc uc ON uc.uc_seq = rel.uc_seq AND uc.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_thsl thsl ON thsl.thsl_seq = uc.thsl_seq AND thsl.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_dist dist ON dist.dist_seq = thsl.dist_seq AND dist.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_st st ON st.st_seq = dist.st_seq AND st.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_cntry cntry ON cntry.cntry_seq = st.st_seq AND cntry.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_brnch brnch ON brnch.brnch_seq = port.brnch_seq AND brnch.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_area area ON area.area_seq = brnch.area_seq AND area.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_reg reg ON reg.reg_seq = area.reg_seq AND reg.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_cmnty cmnty ON cmnty.cmnty_seq = ad.cmnty_seq AND cmnty.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_port_emp_rel perel ON perel.port_seq = port.port_seq AND perel.crnt_rec_flg = 1\n" +
        "        LEFT OUTER JOIN mw_emp emp ON emp.emp_seq = perel.emp_seq \n" +
        "        WHERE clnt.crnt_rec_flg = 1\n" +
        "        and port.brnch_seq=:brnchSeq AND port.port_seq = (CASE WHEN :portSeq = -1 THEN port.port_seq ELSE :portSeq END)\n" +
        "        and not exists \n" +
        "        (select dth.clnt_seq from mw_dth_rpt dth where dth.clnt_seq = clnt.clnt_seq and dth.crnt_rec_flg=1\n" +
        "        and trunc(dth.dt_of_dth) >= trunc(hdr.dsbmt_dt))";

    public static String loanQuery = "select loan_app_seq from mw_loan_app where crnt_rec_flg=1 and loan_id=";

    public static String clntQuery = "select clnt_seq from mw_clnt where crnt_rec_flg=1 and clnt_id=";

    public static String recoveryClosingQuery = "  SELECT rcvry_trx_seq\r\n" + "             AS txId,\r\n" + "         c.clnt_id\r\n"
        + "             AS clientId,\r\n" + "         CONCAT (c.frst_nm,c.last_nm)\r\n" + "             AS clientName,\r\n"
        + "         LISTAGG(p.prd_cmnt,',')\r\n" + "         WITHIN GROUP (ORDER BY r.rcvry_trx_seq) AS    \r\n"
        + "          product,\r\n" + "         a.PRNT_LOAN_APP_SEQ\r\n" + "             AS loanId,\r\n" + "         r.pymt_mod_key\r\n"
        + "             AS paymentMode,\r\n" + "         r.instr_num\r\n" + "             AS instrument,\r\n"
        + "         r.pymt_amt\r\n" + "             AS amount,\r\n" + "         'Unposted'\r\n" + "             AS status,\r\n"
        + "         to_char(r.pymt_dt,'dd/MM/yyyy')\r\n" + "             AS paymentDate,\r\n" + "         r.rcvry_typ_seq\r\n"
        + "             AS recoveryTypeSeq,\r\n" + "         t.typ_str\r\n" + "             AS recoveryTypeName\r\n"
        + "    FROM MW_rcvry_trx r\r\n" + "         JOIN mw_clnt c ON r.pymt_ref = c.clnt_seq AND r.crnt_rec_flg = 1\r\n"
        + "         JOIN mw_loan_app a ON a.clnt_seq = c.clnt_seq AND a.crnt_rec_flg = 1 "
        + " join mw_ref_cd_val val on val.ref_cd_seq=a.loan_app_sts and val.crnt_rec_flg=1 and  (val.ref_cd = '0005' or val.ref_cd ='1245')  \r\n"
        + "            join mw_ref_cd_grp grp on grp.ref_cd_grp_seq = val.ref_cd_grp_key and grp.crnt_rec_flg=1 and grp.ref_cd_grp = '0106'\r\n"
        + "         JOIN mw_acl acl\r\n" + "             ON acl.port_seq = a.port_seq AND acl.user_id =:currUser\r\n"
        + "         JOIN mw_prd p ON p.prd_seq = a.prd_seq AND p.crnt_rec_flg = 1\r\n"
        + "         JOIN mw_typs t ON t.typ_seq = r.rcvry_typ_seq AND t.crnt_rec_Flg = 1\r\n"
        + "   WHERE r.post_flg = 0 AND TO_DATE (r.pymt_dt) <= TO_DATE (SYSDATE)\r\n"
        + "   group by rcvry_trx_seq, c.clnt_id,c.frst_nm,c.last_nm,r.pymt_mod_key,r.instr_num,r.pymt_amt,r.pymt_dt,r.rcvry_typ_seq,t.typ_str,a.PRNT_LOAN_APP_SEQ\r\n"
        + "ORDER BY c.clnt_id";

    public static String disbursementClosingQuery = "select dsbmt_dtl_key                     as txid, \r\n"
        + "         c.clnt_id                         as clientid, \r\n"
        + "         concat (c.frst_nm, c.last_nm)     as clientname,           p.prd_nm as product, \r\n"
        + "         d.crtd_dt                         as voucherdate, \r\n"
        + "         t.typ_str                         as paymentmode,           amt  as amount, \r\n"
        + "         val.ref_cd_dscr                   as status,           a.loan_app_seq  as loanappseq, \r\n"
        + "         p.PRD_GRP_SEQ                     as productgroup \r\n"
        + "    from mw_dsbmt_vchr_dtl d           \r\n"
        + "    join mw_dsbmt_vchr_hdr h on d.dsbmt_hdr_seq = h.dsbmt_hdr_seq and h.crnt_rec_flg = 1           \r\n"
        + "    join mw_loan_app a on     a.loan_app_seq = h.loan_app_seq and a.crnt_rec_flg = 1\r\n"
        + "    join mw_acl acl on acl.port_seq = a.port_seq and acl.user_id =:activeUser  \r\n"
        + "    join mw_clnt c on a.clnt_seq = c.clnt_seq and c.crnt_rec_flg = 1 \r\n"
        + "    join mw_prd p on a.prd_seq = p.prd_seq and p.crnt_rec_flg = 1\r\n"
        + "    join mw_ref_cd_val val on     val.ref_cd_seq = a.loan_app_sts and val.crnt_rec_flg = 1 and val.ref_cd = '0009'           \r\n"
        + "    join mw_ref_cd_grp grp on     grp.ref_cd_grp_seq = val.ref_cd_grp_key and grp.crnt_rec_flg = 1 and grp.ref_cd_grp = '0106' \r\n"
        + "    join mw_typs t on d.pymt_typs_seq = t.typ_seq and t.crnt_rec_flg = 1 \r\n"
        + "    where h.DSBMT_DT = to_date('01-01-1901','dd-mm-yy') \r\n" + "    and d.crnt_rec_flg = 1 \r\n"
        + "order by val.ref_cd_dscr asc nulls first";

    public static String expenseClosingQuery = "select (case when t.typ_id='0006' and t.BRNCH_SEQ=0 then (select distinct tr.PYMT_REF \r\n"
        + "from  mw_rcvry_trx tr where tr.RCVRY_TRX_SEQ = e.EXP_REF) when t.typ_id='0343' then (select CLNT_SEQ||'' from MW_CLNT_HLTH_INSR_CLM where CLNT_HLTH_CLM_SEQ= e.EXP_REF) else e.EXP_REF   end) clnt_id\r\n"
        + ", e.exp_seq as expenseid, expns_dscr as description, t.typ_str as expensetype, instr_num as instrument#, expns_amt as amount, 'Unposted' as status,t.typ_seq,e.pymt_typ_seq,e.pymt_rct_flg   \r\n"
        + "from  mw_exp e       \r\n" + "join mw_typs t on e.expns_typ_seq=t.typ_seq and t.crnt_rec_flg=1   \r\n"
        + "join mw_brnch b on b.brnch_seq=e.brnch_seq and b.crnt_rec_flg=1     \r\n"
        + "join mw_brnch_emp_rel er on er.brnch_seq=b.brnch_seq and er.crnt_rec_flg=1 and er.del_flg=0   \r\n"
        + "join mw_emp em on em.emp_seq = er.emp_seq        \r\n"
        + "where nvl(e.post_flg,0)!=1 and e.crnt_rec_flg=1 and e.expns_sts_key=200 and to_date(e.crtd_dt)<=to_date(sysdate) and em.emp_lan_id=:user  \r\n"
        + "order by exp_seq desc";

    public static String insuranceClaimClosingQuery = "select i.transaction_id as transactionId, c.clnt_id as clientId, concat(c.frst_Nm, c.last_Nm) as ClientName, clm_amt as ClaimAmount, case when clm_sts_key=1085 then 'Posted' else 'Unposted' end as status from mw_clnt_hlth_insr_clm i join mw_clnt c  on c.clnt_seq=i.clnt_seq and c.crnt_rec_flg=1 and i.crnt_rec_flg=1 and i.post_flg=0 order by clm_sts_key ASC NULLS FIRST";

    public static String glAccount = "select cust_acc_desc,cust_segments from ledger_heads";

}
