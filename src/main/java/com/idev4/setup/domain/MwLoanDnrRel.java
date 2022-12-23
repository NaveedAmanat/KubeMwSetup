package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MW_loan_dnr_rel")
public class MwLoanDnrRel {

    @Id
    @Column(name = "loan_dnr_rel_SEQ")
    private Long loanDnrRelSeq;

    @Column(name = "loan_App_SEQ")
    private Long loanAppSeq;

    @Column(name = "dnr_Key")
    private Long dnrKey;

    public Long getLoanDnrRelSeq() {
        return loanDnrRelSeq;
    }

    public void setLoanDnrRelSeq(Long loanDnrRelSeq) {
        this.loanDnrRelSeq = loanDnrRelSeq;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public Long getDnrKey() {
        return dnrKey;
    }

    public void setDnrKey(Long dnrKey) {
        this.dnrKey = dnrKey;
    }
}
