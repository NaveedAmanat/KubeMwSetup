package com.idev4.setup.repository;

import com.idev4.setup.domain.MwAcl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwAddrRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAclRepository extends JpaRepository<MwAcl, Long> {

    public MwAcl findOneByAclSeq(long aclSeq);

    public List<MwAcl> findAllByPortSeq(long portSeq);

    public List<MwAcl> findAllByUserIdAndPortSeqIn(String user, List<Long> ports);

    public List<MwAcl> findAllByPortSeqIn(List<Long> ports);

    public MwAcl findOneByUserIdAndPortSeq(String user, Long portSeq);
}
