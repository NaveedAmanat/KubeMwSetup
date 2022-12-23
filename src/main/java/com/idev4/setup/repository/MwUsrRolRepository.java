package com.idev4.setup.repository;

import com.idev4.setup.domain.MwUsrRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwPort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwUsrRolRepository extends JpaRepository<MwUsrRol, Long> {

    public MwUsrRol findOneByUsrRolNmAndCrntRecFlg(String role, boolean flg);

    public List<MwUsrRol> findAllByCrntRecFlg(boolean flg);
}
