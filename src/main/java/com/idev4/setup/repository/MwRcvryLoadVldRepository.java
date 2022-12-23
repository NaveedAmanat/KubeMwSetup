package com.idev4.setup.repository;

import com.idev4.setup.domain.MwRcvryLoadVld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwRcvryLoadVldRepository extends JpaRepository<MwRcvryLoadVld, Long> {

    public List<MwRcvryLoadVld> findAll();

}
