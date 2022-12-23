package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrdRul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the MwPrd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPrdRulRepository extends JpaRepository<MwPrdRul, Long> {

    MwPrdRul findOneByRulSeqAndCrntRecFlg(long seq, boolean flag);

    List<MwPrdRul> findAllByRulSeqAndCrntRecFlg(long seq, boolean flag);
}
