package com.idev4.setup.repository;

import com.idev4.setup.domain.MwHlthInsrPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwHlthInsrPlanRepository extends JpaRepository<MwHlthInsrPlan, Long> {

    public List<MwHlthInsrPlan> findAllByCrntRecFlg(boolean flag);

    public MwHlthInsrPlan findOneByHlthInsrPlanSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwHlthInsrPlan> findAllByHlthInsrPlanSeq(long seq);

    public List<MwHlthInsrPlan> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg,
                                                                                              Instant ldate, boolean delFlg);
}
