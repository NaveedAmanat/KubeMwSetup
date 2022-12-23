package com.idev4.setup.repository;


import com.idev4.setup.domain.MwFundsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Added, Naveed
 * @Date, 14-06-2022
 * @Description, SCR - systemization Funds Request
 */

@Repository
public interface MwFundsRequestRepository extends JpaRepository<MwFundsRequest, Long> {
}
