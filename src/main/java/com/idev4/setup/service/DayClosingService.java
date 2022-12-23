package com.idev4.setup.service;

import com.idev4.setup.dto.DisbursementDto;
import com.idev4.setup.dto.ExpenseClosingDto;
import com.idev4.setup.dto.InsuranceClaimClosingDto;
import com.idev4.setup.dto.RecoveryDto;
import com.idev4.setup.web.rest.util.Queries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DayClosingService {

    private final EntityManager em;

    private final Logger log = LoggerFactory.getLogger(DayClosingService.class);

    public DayClosingService(EntityManager em) {
        this.em = em;
    }

    public List<RecoveryDto> getRecoveryClosingData() {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Query query = em.createNativeQuery(Queries.recoveryClosingQuery).setParameter("currUser", currUser);
        List<Object[]> records = query.getResultList();

        log.debug(records.toString());

        List<RecoveryDto> recoveryDtos = new ArrayList<RecoveryDto>();

        records.forEach(r -> {
            RecoveryDto dto = new RecoveryDto();
            dto.txId = r[0] == null ? "" : r[0].toString();
            dto.clientId = r[1] == null ? "" : r[1].toString();
            dto.clientName = r[2] == null ? "" : r[2].toString();
            dto.product = r[3] == null ? "" : r[3].toString();
            dto.loanId = r[4] == null ? "" : r[4].toString();
            dto.paymentMode = r[5] == null ? "" : r[5].toString();
            dto.instrument = r[6] == null ? "" : r[6].toString();
            dto.amount = r[7] == null ? "" : r[7].toString();
            dto.status = r[8] == null ? "" : r[8].toString();
            dto.paymentDate = r[9] == null ? "" : r[9].toString();
            dto.recoveryTypeSeq = r[10] == null ? "" : r[10].toString();
            dto.recoveryTypeName = r[11] == null ? "" : r[11].toString();

            recoveryDtos.add(dto);
        });

        return recoveryDtos;
    }

    public List<DisbursementDto> getDisbursementClosingData(String activeUser) {
        Query query = em.createNativeQuery(Queries.disbursementClosingQuery).setParameter("activeUser", activeUser);

        List<Object[]> records = query.getResultList();

        log.debug(records.toString());

        List<DisbursementDto> disbursementDtos = new ArrayList<DisbursementDto>();

        records.forEach(r -> {
            DisbursementDto dto = new DisbursementDto();
            dto.txId = r[0] == null ? "" : r[0].toString();
            dto.clientId = r[1] == null ? "" : r[1].toString();
            dto.clientName = r[2] == null ? "" : r[2].toString();
            dto.product = r[3] == null ? "" : r[3].toString();
            dto.voucherDate = r[4] == null ? "" : r[4].toString();
            dto.paymentMode = r[5] == null ? "" : r[5].toString();
            dto.amount = r[6] == null ? "" : r[6].toString();
            dto.status = r[7] == null ? "" : r[7].toString();
            dto.loanAppSeq = r[8] == null ? "" : r[8].toString();
            //Added by Areeba
            dto.productGroup = r[9] == null ? "" : r[9].toString();
            //Ended by Areeba

            disbursementDtos.add(dto);
        });

        return disbursementDtos;
    }

    public List<ExpenseClosingDto> getExpenseClosingData(String user) {
        Query query = em.createNativeQuery(Queries.expenseClosingQuery).setParameter("user", user);

        List<Object[]> records = query.getResultList();

        log.debug(records.toString());

        List<ExpenseClosingDto> expenseClosingDtos = new ArrayList<ExpenseClosingDto>();

        records.forEach(r -> {
            ExpenseClosingDto dto = new ExpenseClosingDto();
            dto.txId = r[0] == null ? "" : r[0].toString();
            dto.expenseId = r[1] == null ? "" : r[1].toString();
            dto.description = r[2] == null ? "" : r[2].toString();
            dto.expenseType = r[3] == null ? "" : r[3].toString();
            dto.instrumentNum = r[4] == null ? "" : r[4].toString();
            dto.amount = r[5] == null ? "" : r[5].toString();
            dto.status = r[6] == null ? "" : r[6].toString();
            dto.catogory = r[7] == null ? "" : r[7].toString();
            dto.mode = r[8] == null ? "" : r[8].toString();
            dto.flg = r[9] == null ? "" : r[9].toString();
            expenseClosingDtos.add(dto);
        });

        return expenseClosingDtos;
    }

    public List<InsuranceClaimClosingDto> getInsuranceClaimClosingData() {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Query query = em.createNativeQuery(Queries.insuranceClaimClosingQuery.replace("CURRENTUSER", currUser.toLowerCase()));
        List<Object[]> records = query.getResultList();

        List<InsuranceClaimClosingDto> insuranceClaimClosingDtos = new ArrayList<InsuranceClaimClosingDto>();

        records.forEach(r -> {
            InsuranceClaimClosingDto dto = new InsuranceClaimClosingDto();
            dto.txId = r[0] == null ? "" : r[0].toString();
            dto.clientId = r[1] == null ? "" : r[1].toString();
            dto.clientName = r[2] == null ? "" : r[2].toString();
            dto.claimAmount = r[3] == null ? "" : r[3].toString();
            dto.status = r[4] == null ? "" : r[4].toString();

            insuranceClaimClosingDtos.add(dto);
        });

        return insuranceClaimClosingDtos;
    }

}
