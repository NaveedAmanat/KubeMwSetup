package com.idev4.setup.repository;

import com.idev4.setup.domain.MwTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwTags entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwTagsRepository extends JpaRepository<MwTags, Long> {

    public List<MwTags> findAllByCrntRecFlg(boolean flag);

    public MwTags findOneByTagsSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwTags> findAllByTagsSeq(long seq);

    public MwTags findOneByTagIdAndCrntRecFlg(String tagId, boolean flag);

}
