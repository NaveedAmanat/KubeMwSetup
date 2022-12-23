package com.idev4.setup.repository;

import com.idev4.setup.domain.MwAppAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwPort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAppAuthRepository extends JpaRepository<MwAppAuth, Long> {

    public MwAppAuth findOneByUsrRolSeqAndSbModSeqAndCrntRecFlg(Long role, Long sbModSeq, boolean flg);

    public List<MwAppAuth> findAllByUsrRolSeqAndCrntRecFlg(Long role, boolean flg);
}
