package com.idev4.setup.repository;

import com.idev4.setup.domain.MwRsLoanRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MwRsLoanRuleRepository extends JpaRepository<MwRsLoanRule, Long> {

    MwRsLoanRule findOneByRuleSeqAndDelFlg(Long ruleSeq, Boolean delFlg);

    MwRsLoanRule findOneByRuleSeq(Long ruleSeq);
}
