package com.idev4.setup.repository;

import com.idev4.setup.domain.MwClntTagList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface MwClntTagListRepository extends JpaRepository<MwClntTagList, Long> {

    MwClntTagList findOneByCnicNumAndDelFlg(long cnic, boolean del);

    MwClntTagList findOneByCnicNumAndTagsSeqAndDelFlg(long cnic, Long tagSeq, boolean del);

    MwClntTagList findOneByLoanAppSeqAndDelFlg(long loanSeq, boolean del);

    MwClntTagList findOneByLoanAppSeqAndTagsSeqAndDelFlg(long loanSeq, Long tagSeq, boolean del);
}
