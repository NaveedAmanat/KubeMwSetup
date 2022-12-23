package com.idev4.setup.service;

import com.idev4.setup.dto.AppDto;
import com.idev4.setup.web.rest.util.Queries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TransferService {

    private final Logger log = LoggerFactory.getLogger(TransferService.class);

    private final EntityManager em;

    public TransferService(EntityManager em) {
        this.em = em;
    }

//    @Transactional ( readOnly = true )
//    public List< AppDto > findAllByCrntRecFlg( String activeUser ) {
//
//        Query query = em.createNativeQuery( Queries.tranfersData ).setParameter( "userId", activeUser );
//        List< Object[] > resultSet = query.getResultList();
//
//        List< AppDto > appDtos = new ArrayList< AppDto >();
//
//        for ( Object[] obj : resultSet ) {
//            AppDto dto = new AppDto();
//            dto.firstName = ( obj[ 1 ] == null ) ? "" : obj[ 1 ].toString();
//            dto.lastName = ( obj[ 2 ] == null ) ? "" : obj[ 2 ].toString();
//            dto.clntSeq = ( obj[ 3 ] == null ) ? "" : obj[ 3 ].toString();
//            dto.cnicNum = ( obj[ 4 ] == null ) ? "" : obj[ 4 ].toString();
//            dto.gender = ( obj[ 5 ] == null ) ? "" : obj[ 5 ].toString();
//            dto.maritalStatus = ( obj[ 6 ] == null ) ? "" : obj[ 6 ].toString();
//            dto.house_num = ( obj[ 7 ] == null ) ? "" : obj[ 7 ].toString();
//            dto.city = ( obj[ 8 ] == null ) ? "" : obj[ 8 ].toString();
//            dto.uc = ( obj[ 9 ] == null ) ? "" : obj[ 9 ].toString();
//            dto.tehsil = ( obj[ 10 ] == null ) ? "" : obj[ 10 ].toString();
//            dto.dist = ( obj[ 11 ] == null ) ? "" : obj[ 11 ].toString();
//            dto.state = ( obj[ 12 ] == null ) ? "" : obj[ 12 ].toString();
//            dto.country = ( obj[ 13 ] == null ) ? "" : obj[ 13 ].toString();
//            dto.portfolio = ( obj[ 14 ] == null ) ? "" : obj[ 14 ].toString();
//            dto.branch = ( obj[ 15 ] == null ) ? "" : obj[ 15 ].toString();
//            // dto.branchSeq = ( obj[ 17 ] == null ) ? "" : obj[ 17 ].toString();
//            dto.area = ( obj[ 16 ] == null ) ? "" : obj[ 16 ].toString();
//            // dto.areaSeq = ( obj[ 19 ] == null ) ? "" : obj[ 19 ].toString();
//            dto.region = ( obj[ 17 ] == null ) ? "" : obj[ 17 ].toString();
//            dto.cmntyNm = ( obj[ 18 ] == null ) ? "" : obj[ 18 ].toString();
//            dto.otherDetail = ( obj[ 19 ] == null ) ? "" : obj[ 19 ].toString();
//            dto.village = ( obj[ 20 ] == null ) ? "" : obj[ 20 ].toString();
//            dto.clientId = ( obj[ 21 ] == null ) ? "" : obj[ 21 ].toString();
//            dto.branchSeq = ( obj[ 22 ] == null ) ? "" : obj[ 22 ].toString();
//            appDtos.add( dto );
//        }
//        return appDtos;
//    }

    /* Added by Yousaf Ali - Dated 18-Jan-2022
     * Client wise  Portfolio Transfer */
    @Transactional(readOnly = true)
    public Map<String, Object> findAllByCrntRecFlg(String userId, Long brnchSeq, Long portSeq, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {

        log.info("TransferService.findAllByCrntRecFlg=>");
        log.info("userId: " + userId + ", brnchSeq: " + brnchSeq + ", portSeq: " + portSeq);

        String tranfersData = Queries.tranfersData;
        String trafersDataCount = Queries.tranfersDataCount;

        if (filter != null && filter.length() > 0) {
            String search = " and (((clnt.clnt_id) like '%?%') OR (lower(clnt.frst_nm) like '%?%') OR (lower(clnt.last_nm) like '%?%') OR ((clnt.cnic_num) like '%?%') OR ((port.port_seq) like '%?%'))"
                .replace("?", filter.toLowerCase());

            tranfersData += search;
            trafersDataCount += search;
        }
        List<Object[]> resultSet = em.createNativeQuery(tranfersData + "\r\norder by 1 desc")
            .setParameter("userId", userId)
            .setParameter("brnchSeq", brnchSeq)
            .setParameter("portSeq", portSeq)
            .setFirstResult((pageIndex) * pageSize)
            .setMaxResults(pageSize).getResultList();


        List<AppDto> appDtos = new ArrayList<AppDto>();

        for (Object[] obj : resultSet) {
            AppDto dto = new AppDto();
            dto.firstName = (obj[1] == null) ? "" : obj[1].toString();
            dto.lastName = (obj[2] == null) ? "" : obj[2].toString();
            dto.clntSeq = (obj[3] == null) ? "" : obj[3].toString();
            dto.cnicNum = (obj[4] == null) ? "" : obj[4].toString();
            dto.gender = (obj[5] == null) ? "" : obj[5].toString();
            dto.maritalStatus = (obj[6] == null) ? "" : obj[6].toString();
            dto.house_num = (obj[7] == null) ? "" : obj[7].toString();
            dto.city = (obj[8] == null) ? "" : obj[8].toString();
            dto.uc = (obj[9] == null) ? "" : obj[9].toString();
            dto.tehsil = (obj[10] == null) ? "" : obj[10].toString();
            dto.dist = (obj[11] == null) ? "" : obj[11].toString();
            dto.state = (obj[12] == null) ? "" : obj[12].toString();
            dto.country = (obj[13] == null) ? "" : obj[13].toString();
            dto.portfolio = (obj[14] == null) ? "" : obj[14].toString();
            dto.branch = (obj[15] == null) ? "" : obj[15].toString();
            // dto.branchSeq = ( obj[ 17 ] == null ) ? "" : obj[ 17 ].toString();
            dto.area = (obj[16] == null) ? "" : obj[16].toString();
            // dto.areaSeq = ( obj[ 19 ] == null ) ? "" : obj[ 19 ].toString();
            dto.region = (obj[17] == null) ? "" : obj[17].toString();
            dto.cmntyNm = (obj[18] == null) ? "" : obj[18].toString();
            dto.otherDetail = (obj[19] == null) ? "" : obj[19].toString();
            dto.village = (obj[20] == null) ? "" : obj[20].toString();
            dto.clientId = (obj[21] == null) ? "" : obj[21].toString();
            dto.branchSeq = (obj[22] == null) ? "" : obj[22].toString();
            appDtos.add(dto);
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("apps", appDtos);

        Long totalResultCount = 0L;
        if (isCount.booleanValue()) {
            totalResultCount = new BigDecimal(
                em.createNativeQuery(trafersDataCount)
                    .setParameter("userId", userId).setParameter("brnchSeq", brnchSeq)
                    .setParameter("portSeq", portSeq)
                    .getSingleResult().toString()).longValue();
        }
        resp.put("count", totalResultCount);
        return resp;
    }

    /* Added by Yousaf Ali - Dated 18-Jan-2022
     * Client wise  Portfolio Transfer */
    public boolean updatePortfolio(AppDto[] dtos) {
        boolean flag = false;

        for (AppDto dto : dtos) {

            log.info("TransferService.updatePortfolio-> dto: " + dto);

            String loanQuery = Queries.loanQuery + dto.loanAppId.trim();
            log.debug("loanQuery    " + loanQuery);
            Query query = em.createNativeQuery(loanQuery);
            List<BigDecimal> loanAppSeqes = query.getResultList();
            long loanAppSeq = (loanAppSeqes.get(0)).longValueExact();

            String clntQuery = Queries.clntQuery + dto.clientId.trim();
            log.debug("clntQuery    " + clntQuery);
            query = em.createNativeQuery(clntQuery);
            List<BigDecimal> clntSeqes = query.getResultList();
            long clntSeq = (clntSeqes.get(0)).longValueExact();
            log.debug("clntSeq    " + clntSeq);

            String updateQuery = "update mw_clnt set port_key=" + dto.portSeq + " where clnt_seq=" + clntSeq;
            query = em.createNativeQuery(updateQuery);
            query.executeUpdate();

            updateQuery = "update mw_loan_app set port_seq=" + dto.portSeq + " where loan_app_seq=" + loanAppSeq + " and clnt_seq="
                + clntSeq;
            query = em.createNativeQuery(updateQuery);
            query.executeUpdate();

            flag = true;

        }
        return flag;
    }
}
