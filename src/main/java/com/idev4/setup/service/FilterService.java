package com.idev4.setup.service;

import com.idev4.setup.dto.*;
import com.idev4.setup.web.rest.util.Queries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FilterService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EntityManager em;

    public FilterService(EntityManager em) {
        this.em = em;
    }

    public Map getQueryFiltersForOrigination(long portSeq) {
        Query q = em.createNativeQuery(Queries.orgnizationQuery + portSeq);

        List<Object[]> organizations = q.getResultList();
        log.debug("List Size  Length is: {}", organizations.size());

        List<RegionDto> regs = new ArrayList<>();

        for (Object[] comb : organizations) {
            RegionDto reg = new RegionDto();
            reg.regionSeq = Long.valueOf(comb[0].toString());
            reg.regionName = comb[1].toString();

            if (regs.contains(reg)) {
                reg = regs.get(regs.indexOf(reg));
                if (reg.areas == null)
                    reg.areas = new ArrayList<>();

                AreaDto area = new AreaDto();
                area.areaSeq = Long.valueOf(comb[2].toString());
                area.areaName = comb[3].toString();

                if (reg.areas.contains(area)) {
                    area = reg.areas.get(reg.areas.indexOf(area));
                    if (area.branches == null)
                        area.branches = new ArrayList<>();

                    BranchDto branch = new BranchDto();
                    branch.branchSeq = Long.valueOf(comb[4].toString());
                    branch.branchName = comb[5].toString();

                    if (area.branches.contains(branch)) {
                        branch = area.branches.get(area.branches.indexOf(branch));
                        if (branch.portfolio == null)
                            branch.portfolio = new ArrayList<>();

                        PortDto port = new PortDto();
                        port.portfolioSeq = Long.valueOf(comb[6].toString());
                        port.portfolioName = comb[7].toString();

                        if (branch.portfolio.contains(port)) {
                            port = branch.portfolio.get(branch.portfolio.indexOf(port));
                            if (port.communities == null)
                                port.communities = new ArrayList<>();

                            CommunityDto com = new CommunityDto();
                            com.comSeq = Long.valueOf(comb[8].toString());
                            com.cmntyName = comb[9].toString();
                            port.communities.add(com);
                        } else
                            branch.portfolio.add(port);
                    } else
                        area.branches.add(branch);
                } else
                    reg.areas.add(area);
            } else {
                reg.areas = new ArrayList<>();

                AreaDto area = new AreaDto();
                area.areaSeq = Long.valueOf(comb[2].toString());
                area.areaName = comb[3].toString();
                area.branches = new ArrayList<>();

                BranchDto branch = new BranchDto();
                branch.branchSeq = Long.valueOf(comb[4].toString());
                branch.branchName = comb[5].toString();
                branch.portfolio = new ArrayList<>();

                PortDto port = new PortDto();
                port.portfolioSeq = Long.valueOf(comb[6].toString());
                port.portfolioName = comb[7].toString();
                port.communities = new ArrayList<>();

                CommunityDto com = new CommunityDto();
                com.comSeq = Long.valueOf(comb[8].toString());
                com.cmntyName = comb[9].toString();
                port.communities.add(com);

                branch.portfolio.add(port);
                area.branches.add(branch);
                reg.areas.add(area);
                regs.add(reg);
            }
        }

        //return regs;

        q = null;
        q = em.createNativeQuery(Queries.geographyQuery);
        List<Object[]> geography = q.getResultList();
        log.debug("List Size  Length is: {}", geography.size());
        List<CountryDto> countries = new ArrayList<>();
        for (Object[] comb : geography) {
            CountryDto cntry = new CountryDto();
            cntry.countrySeq = Long.valueOf(comb[0].toString());
            cntry.countryName = comb[1].toString();

            if (countries.contains(cntry)) {
                cntry = countries.get(countries.indexOf(cntry));
                if (cntry.states == null)
                    cntry.states = new ArrayList<>();

                ProvinceDto prov = new ProvinceDto();
                prov.provSeq = Long.valueOf(comb[2].toString());
                prov.provName = comb[3].toString();

                if (cntry.states.contains(prov)) {
                    prov = cntry.states.get(cntry.states.indexOf(prov));
                    if (prov.districts == null)
                        prov.districts = new ArrayList<>();

                    DistrictDto distric = new DistrictDto();
                    distric.districtSeq = Long.valueOf(comb[4].toString());
                    distric.districtName = comb[5].toString();

                    if (prov.districts.contains(distric)) {
                        distric = prov.districts.get(prov.districts.indexOf(distric));
                        if (distric.tehsils == null)
                            distric.tehsils = new ArrayList<>();

                        TehsilDto tehsil = new TehsilDto();
                        tehsil.thslSeq = Long.valueOf(comb[6].toString());
                        tehsil.thslName = comb[7].toString();

                        if (distric.tehsils.contains(tehsil)) {
                            tehsil = distric.tehsils.get(distric.tehsils.indexOf(tehsil));
                            if (tehsil.ucs == null)
                                tehsil.ucs = new ArrayList<>();

                            UcDto uc = new UcDto();
                            uc.ucSeq = Long.valueOf(comb[8].toString());
                            uc.ucCode = comb[9].toString();
                            uc.ucName = comb[10].toString();
                            uc.ucDescription = comb[11].toString();
                            tehsil.ucs.add(uc);
                        } else
                            distric.tehsils.add(tehsil);
                    } else
                        prov.districts.add(distric);
                } else
                    cntry.states.add(prov);
            } else {
                cntry.states = new ArrayList<>();

                ProvinceDto prov = new ProvinceDto();
                prov.provSeq = Long.valueOf(comb[2].toString());
                prov.provName = comb[3].toString();
                prov.districts = new ArrayList<>();

                DistrictDto distric = new DistrictDto();
                distric.districtSeq = Long.valueOf(comb[4].toString());
                distric.districtName = comb[5].toString();
                distric.tehsils = new ArrayList<>();

                TehsilDto tehsil = new TehsilDto();
                tehsil.thslSeq = Long.valueOf(comb[6].toString());
                tehsil.thslName = comb[7].toString();
                tehsil.ucs = new ArrayList<>();

                UcDto uc = new UcDto();
                uc.ucSeq = Long.valueOf(comb[8].toString());
                uc.ucCode = comb[9].toString();
                uc.ucName = comb[10].toString();
                uc.ucDescription = comb[11].toString();

                tehsil.ucs.add(uc);
                distric.tehsils.add(tehsil);
                prov.districts.add(distric);
                cntry.states.add(prov);
                countries.add(cntry);
            }

        }

        Map<String, List> data = new HashMap<>();
        data.put("organization", regs);
        data.put("geography", countries);
        return data;
    }
}
