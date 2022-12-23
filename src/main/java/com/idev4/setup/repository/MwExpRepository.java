package com.idev4.setup.repository;

import com.idev4.setup.domain.MwExp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwExpRepository extends JpaRepository<MwExp, Long> {

    public final String findByExpBrnchSeqQuery = "select e.* from mw_exp e join mw_typs t on t.TYP_SEQ=e.EXPNS_TYP_SEQ and t.CRNT_REC_FLG=1\n" +
        "      where TYP_ID NOT IN('0424') AND TYP_CTGRY_KEY =2 AND e.BRNCH_SEQ =:brnchSeq ANd e.CRNT_REC_FLG = 1 ";
    public final String findByExpBrnchSeqCountQuery = "select count(*) from mw_exp e join mw_typs t on t.TYP_SEQ=e.EXPNS_TYP_SEQ and t.CRNT_REC_FLG=1\n" +
        "      where TYP_ID NOT IN('0424') AND TYP_CTGRY_KEY =2 AND e.BRNCH_SEQ =:brnchSeq ANd e.CRNT_REC_FLG = 1 ";

    public MwExp findOneByExpSeqAndCrntRecFlg(Long expSeq, boolean flag);

    public List<MwExp> findAllByExpSeq(Long expSeq);

    public List<MwExp> findAllByCrntRecFlgOrderByExpSeqDesc(boolean flag);

    public List<MwExp> findAllByCrntRecFlgAndDelFlgOrCrntRecFlgAndDelFlgOrderByExpSeqDesc(boolean cflag, boolean dflag, boolean ocflag,
                                                                                          boolean odflag);

    public MwExp findOneByExpSeq(Long expSeq);

    public List<MwExp> findAllByBrnchSeqAndCrntRecFlgOrderByExpSeqDesc(Long brnchSeq, boolean flag);

    public List<MwExp> findAllByBrnchSeqAndCrntRecFlgAndDelFlgOrBrnchSeqAndCrntRecFlgAndDelFlgOrderByExpSeqDesc(Long brnchSeq,
                                                                                                                boolean flag, boolean dlfg, Long obrnchSeq, boolean oflag, boolean odlfg);
    // TYP_ID NOT IN('0006','0343','0424')

    @Query(value = "select sum(EXPNS_AMT) from MW_EXP where brnch_seq=:brnchSeq and CRTD_DT between trunc(sysdate) - (to_number(to_char(sysdate,'DD')) - 1) and add_months(trunc(sysdate) - (to_number(to_char(sysdate,'DD')) - 1), 1) -1", nativeQuery = true)
    public Long findSumByCrntMnthAndCrntYear(@Param("brnchSeq") Long brnchSeq);

    @Query(value = "select e.* \r\n" + "from mw_exp e\r\n" + "join mw_typs t on t.TYP_SEQ=e.EXPNS_TYP_SEQ and t.CRNT_REC_FLG=1\r\n"
        + "where TYP_ID NOT IN('0424') AND TYP_CTGRY_KEY =2 AND e.BRNCH_SEQ =:brnchSeq ANd e.CRNT_REC_FLG = 1\r\n"
        + "order by 1 desc", nativeQuery = true)
    public List<MwExp> findExpByBranchSeq(@Param("brnchSeq") Long brnchSeq);

    @Query(value = "select e.* from mw_exp e \r\n"
        + "                                join mw_typs t on t.TYP_SEQ=e.EXPNS_TYP_SEQ and t.CRNT_REC_FLG = 1 and t.TYP_ID='0343' \r\n"
        + "                                and e.crnt_rec_flg=1 and e.del_flg=0 and e.exp_Ref=:clntSeq", nativeQuery = true)
    public MwExp findInsExpForClient(@Param("clntSeq") Long clntSeq);

    /**
     * @Added, Naveed
     * @Date, 16-06-2022
     * @Description, SCR - Branch Wise CMS Funds Data Loader
     */
    @Query(value = "SELECT EX.*\n" +
        "  FROM MW_EXP EX\n" +
        " WHERE     EX.CRNT_REC_FLG = 1\n" +
        "       AND EX.BRNCH_SEQ = :P_BRNCH_SEQ\n" +
        "       AND TRUNC (EX.CRTD_DT) = TRUNC (SYSDATE)\n" +
        "       AND EX.EXPNS_TYP_SEQ =\n" +
        "           (SELECT TYP_SEQ\n" +
        "              FROM MW_TYPS MT\n" +
        "             WHERE     MT.TYP_ID = '0420'\n" +
        "                   AND MT.BRNCH_SEQ = EX.BRNCH_SEQ\n" +
        "                   AND MT.TYP_CTGRY_KEY = 2\n" +
        "                   AND MT.CRNT_REC_FLG = 1)\n" +
        "       AND EX.PYMT_TYP_SEQ =\n" +
        "           (SELECT TYP_SEQ\n" +
        "              FROM MW_TYPS MT\n" +
        "             WHERE     MT.TYP_ID = '0008'\n" +
        "                   AND MT.BRNCH_SEQ = EX.BRNCH_SEQ\n" +
        "                   AND MT.TYP_CTGRY_KEY = 6\n" +
        "                   AND MT.CRNT_REC_FLG = 1\n" +
        "                   AND MT.DEL_FLG = 0) ", nativeQuery = true)
    public List<MwExp> findAllByBrnchSeqAndExpnsTypSeqAndPymtTypSeqAndCrntRecFlg(@Param("P_BRNCH_SEQ") long branchSeq);
}
