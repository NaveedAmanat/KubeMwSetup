package com.idev4.setup.repository;

import com.idev4.setup.domain.MwAmlList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwAddrRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAmlListRepository extends JpaRepository<MwAmlList, Long> {

    public List<MwAmlList> findAllByFrstNmIgnoreCaseContainingAndCrntRecFlg(String frstNm, boolean flg);

}
