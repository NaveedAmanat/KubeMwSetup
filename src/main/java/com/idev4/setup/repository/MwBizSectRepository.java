package com.idev4.setup.repository;

import com.idev4.setup.domain.MwBizSect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwBizSectRepository extends JpaRepository<MwBizSect, Long> {

    public MwBizSect findOneByBizSectSeqAndCrntRecFlg(Long bizSectSeq, boolean flag);

    public List<MwBizSect> findAllByBizSectSeq(Long bizSectSeq);

    public MwBizSect findOneByBizSectSeq(Long bizSectSeq);

    public List<MwBizSect> findAllByCrntRecFlg(boolean flag);

    @Query(value = "select distinct bsect.* from mw_biz_sect bsect \r\n"
        + "join mw_prd_biz_sect_rel pbsrel on pbsrel.biz_sect_seq=bsect.biz_sect_seq \r\n"
        + "join mw_prd prd on prd.prd_seq=pbsrel.prd_seq \r\n" + "join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq \r\n"
        + "join mw_ref_cd_val val on val.ref_cd_seq=prd.prd_typ_key and val.ref_cd!='1165' and val.crnt_rec_flg=1\r\n"
        + "where bprel.brnch_seq=:brnchSeq \r\n"
        + "and ((prd.last_upd_dt > :syncDate and (prd.crnt_rec_flg=1 or prd.del_flg=1)) or (bprel.last_upd_dt >  :syncDate and (bprel.crnt_rec_flg=1 or bprel.del_flg=1)) or\r\n"
        + "(pbsrel.last_upd_dt > :syncDate and (pbsrel.crnt_rec_flg=1 or pbsrel.del_flg=1))) or (bsect.last_upd_dt >  :syncDate and (bsect.crnt_rec_flg=1 or bsect.del_flg=1))\r\n"
        + "order by bsect.last_upd_dt", nativeQuery = true)
    public List<MwBizSect> findUpdatedMwBizSectForBranch(@Param("brnchSeq") Long branchSeq, @Param("syncDate") Instant syncDate);

    @Query(value = "select sect.* from mw_biz_sect sect "
        + "join mw_prd_biz_sect_rel sectRel on sectRel.biz_sect_seq=sect.biz_sect_seq and sectRel.crnt_rec_flg=1 "
        + "join mw_brnch_prd_rel rel on rel.prd_seq=sectRel.prd_seq and rel.crnt_rec_flg=1 "
        + "join mw_port port on port.brnch_seq=rel.brnch_seq and port.crnt_rec_flg=1 "
        + "where (sect.crnt_rec_flg=1 or sect.del_flg=1) and port.port_seq= :portSeq"
        + "  and sect.last_upd_dt > :syncDate", nativeQuery = true)
    public List<MwBizSect> findUpdatedMwBizSectForPort(@Param("portSeq") Long portSeq, @Param("syncDate") Instant syncDate);
}
