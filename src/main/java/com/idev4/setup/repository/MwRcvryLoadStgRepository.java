package com.idev4.setup.repository;

import com.idev4.setup.domain.MwRcvryLoadStg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwRcvryLoadStgRepository extends JpaRepository<MwRcvryLoadStg, Long> {

    public List<MwRcvryLoadStg> findAll();

}



