package com.idev4.setup.repository;

import com.idev4.setup.domain.MwSchAtnd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwSchAtndRepository extends JpaRepository<MwSchAtnd, Long> {

    public List<MwSchAtnd> findAllByCrntRecFlg(boolean flag);

    public MwSchAtnd findOneBySchAtndSeqAndCrntRecFlg(long seq, boolean flag);

    public MwSchAtnd findOneBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwSchAtnd> findAllBySchAtndSeq(long seq);
}

