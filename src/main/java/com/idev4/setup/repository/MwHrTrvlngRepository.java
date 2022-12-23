package com.idev4.setup.repository;

import com.idev4.setup.domain.MwHrTrvlng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*Authored by Areeba
HR Travelling SCR
Dated - 07-06-2022
*/

@Repository
public interface MwHrTrvlngRepository extends JpaRepository<MwHrTrvlng, Long> {

    List<MwHrTrvlng> findAllByCrntRecFlg(Boolean flg);

    MwHrTrvlng findOneByHrTrvlngSeqAndCrntRecFlg(Long seq, Boolean flg);
}
