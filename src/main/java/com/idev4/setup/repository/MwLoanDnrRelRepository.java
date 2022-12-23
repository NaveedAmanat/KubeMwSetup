package com.idev4.setup.repository;

import com.idev4.setup.domain.MwLoanDnrRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface MwLoanDnrRelRepository extends JpaRepository<MwLoanDnrRel, Long> {

}


