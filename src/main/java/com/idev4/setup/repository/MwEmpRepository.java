package com.idev4.setup.repository;

import com.idev4.setup.domain.MwEmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwEmp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwEmpRepository extends JpaRepository<MwEmp, Long> {

    public List<MwEmp> findAllByEmpNmContaining(String chars);

    public MwEmp findOneByEmpNmContaining(String chars);

    public MwEmp findOneByEmpSeq(Long seq);

    @Query(value = "SELECT emp.*\n" +
        "  FROM HR_MW6_BRANCH_MAPS  mp\n" +
        "       JOIN mw_brnch mb ON mp.mw6_branch_cd = LPAD (mb.brnch_seq, 4, '0')\n" +
        "       JOIN hr.hris e\n" +
        "           ON e.detail_location_id = mp.hr_branch_cd AND e.STATUS = 1\n" +
        "       JOIN mw_emp emp\n" +
        "           ON     REGEXP_REPLACE (emp.emp_lan_id, '[^0-9]', '') =\n" +
        "                  LTRIM (e.employee_id, '0')\n" +
        "              AND emp.emp_lan_id LIKE '%' || LTRIM (e.employee_id, '0')\n" +
        "              and mp.MAP_FLG = 1\n" +
        " WHERE mb.brnch_seq = :brnchSeq AND E.position_id IN :positions", nativeQuery = true)
    public List<MwEmp> findEmpForBranch(@Param("brnchSeq") Long brnchSeq, @Param("positions") List positions);
}
