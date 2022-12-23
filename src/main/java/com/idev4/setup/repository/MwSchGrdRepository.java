package com.idev4.setup.repository;

import com.idev4.setup.domain.MwSchGrd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwSchGrdRepository extends JpaRepository<MwSchGrd, Long> {

    public List<MwSchGrd> findAllByCrntRecFlg(boolean flag);

    public MwSchGrd findOneBySchGrdSeqAndCrntRecFlg(long seq, boolean flag);

    public MwSchGrd findOneBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwSchGrd> findAllBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwSchGrd> findAllBySchGrdSeqAndCrntRecFlg(long seq, boolean flag);
}

