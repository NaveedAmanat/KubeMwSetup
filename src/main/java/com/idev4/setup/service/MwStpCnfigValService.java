package com.idev4.setup.service;


import com.idev4.setup.domain.MwStpCnfigVal;
import com.idev4.setup.repository.MwStpCnfigGrpRepository;
import com.idev4.setup.repository.MwStpCnfigValRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Added By Naveed Date - 10-05-2022
 */

@Service
public class MwStpCnfigValService {

    private final Logger log = LoggerFactory.getLogger(MwStpCnfigValService.class);
    @Autowired
    private MwStpCnfigGrpRepository mwStpCnfigGrpRepository;
    @Autowired
    private MwStpCnfigValRepository mwStpCnfigValRepository;

    public List<MwStpCnfigVal> findByStpGrpCd(String grpCd) {
        return mwStpCnfigValRepository.findAllByStpGrpCdAndCrntRecFlgOrderBySortOrderAsc(grpCd, true);
    }
}
