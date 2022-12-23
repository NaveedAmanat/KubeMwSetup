package com.idev4.setup.repository;

import com.idev4.setup.domain.MwBrnch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwBrnch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBrnchRepository extends JpaRepository<MwBrnch, Long> {

    public List<MwBrnch> findAllByAreaSeqAndCrntRecFlgOrderByBrnchSeqDesc(long areaSeq, boolean flag);

    public List<MwBrnch> findAllByCrntRecFlg(boolean flag);

    public MwBrnch findTopByOrderByBrnchSeqDesc();

    public MwBrnch findOneByBrnchSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwBrnch> findAllByBrnchSeq(long seq);

    public List<MwBrnch> findAllByAreaSeqAndCrntRecFlg(Long id, boolean b);

    @Query(value = "select b.* " + " from mw_brnch b "
        + " join mw_brnch_emp_rel ber on ber.brnch_seq = b.brnch_seq and ber.crnt_rec_flg = 1 and ber.del_flg=0"
        + " join mw_emp e on e.emp_seq = ber.emp_seq " + " where b.crnt_rec_flg = 1 and e.emp_lan_id=:user", nativeQuery = true)
    public MwBrnch findOneByEmpSeqAndCrntRecFlg(@Param("user") String user);

    @Query(value = "select b.* " + "from  mw_brnch b " + "join mw_port prt on prt.BRNCH_SEQ=b.BRNCH_SEQ and prt.crnt_rec_flg=1 "
        + "join mw_loan_app ap on ap.PORT_SEQ=prt.PORT_SEQ and ap.crnt_rec_flg=1 and ap.loan_cycl_num=(select max(loan_cycl_num) from mw_loan_app where crnt_rec_flg=1 and clnt_seq=ap.clnt_seq) "
        + " join mw_clnt clnt on clnt.clnt_Seq = ap.clnt_seq and clnt.crnt_rec_flg=1"
        + "where b.CRNT_REC_FLG=1 and clnt.CLNT_id=:clnt_id", nativeQuery = true)
    public MwBrnch findOneByClntId(@Param("clnt_id") String clntId);

    @Query(value = "select b.* " + "from  mw_brnch b " + "join mw_port prt on prt.BRNCH_SEQ=b.BRNCH_SEQ and prt.crnt_rec_flg=1 "
        + "join mw_loan_app ap on ap.PORT_SEQ=prt.PORT_SEQ and ap.crnt_rec_flg=1 and ap.loan_cycl_num=(select max(loan_cycl_num) from mw_loan_app where crnt_rec_flg=1 and clnt_seq=ap.clnt_seq) "
        + " join mw_clnt clnt on clnt.clnt_Seq = ap.clnt_seq and clnt.crnt_rec_flg=1"
        + "where b.CRNT_REC_FLG=1 and clnt.CLNT_seq=:clnt_seq", nativeQuery = true)
    public MwBrnch findOneByClntSeq(@Param("clnt_seq") Long clntSeq);

    @Query(value = "select distinct b.* from mw_brnch b \r\n" + "    join mw_port p on p.brnch_seq=b.brnch_seq and p.crnt_rec_flg=1\r\n"
        + "    join mw_acl a on  a.port_seq=p.port_seq\r\n" + "    where b.crnt_rec_flg=1 and a.user_id=:userId", nativeQuery = true)
    public List<MwBrnch> findAllForUsr(@Param("userId") String userId);

    @Query(value = "SELECT MB.* from MW_REG MG\n" +
        "    JOIN MW_AREA MA ON MA.REG_SEQ = MG.REG_SEQ AND MA.CRNT_REC_FLG = 1\n" +
        "    JOIN MW_BRNCH MB ON MB.AREA_SEQ = MA.AREA_SEQ AND MB.CRNT_REC_FLG = 1\n" +
        "    WHERE MG.CRNT_REC_FLG = 1 AND MG.REG_SEQ = :P_REG_SEQ\n" +
        "    ORDER BY MB.BRNCH_NM", nativeQuery = true)
    public List<MwBrnch> findAllBrnchByRegion(@Param("P_REG_SEQ") long regSeq);


}
