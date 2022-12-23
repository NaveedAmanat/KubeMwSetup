package com.idev4.setup.repository;

import com.idev4.setup.domain.MwSchQltyChk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwSchQltyChkRepository extends JpaRepository<MwSchQltyChk, Long> {

    public List<MwSchQltyChk> findAllByCrntRecFlg(boolean flag);

    public MwSchQltyChk findOneBySchQltyChkSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwSchQltyChk> findAllBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public MwSchQltyChk findOneBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwSchQltyChk> findAllBySchQltyChkSeqAndCrntRecFlg(long seq, boolean flag);
}


