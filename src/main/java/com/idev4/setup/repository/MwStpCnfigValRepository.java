package com.idev4.setup.repository;

import com.idev4.setup.domain.MwStpCnfigVal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Added By Naveed Date - 10-05-2022
 */
@Repository
public interface MwStpCnfigValRepository extends JpaRepository<MwStpCnfigVal, Long> {

    List<MwStpCnfigVal> findAllByStpGrpCdAndCrntRecFlgOrderBySortOrderAsc(String grpCd, boolean flag);
}
