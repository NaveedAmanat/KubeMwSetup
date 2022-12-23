package com.idev4.setup.dto;

public class ClientHealthInsuranceClaimDto {

    private Long clntHlthClmSeq;
    private Long brnchSeq;
    private Long clntSeq;
    private Double clmAmt;
    private String transactionId;
    private Long clmStsKey;

    public Long getClnHlthClmSeq() {
        return clntHlthClmSeq;
    }

    public void setClntHlthClmSeq(Long clntHlthClmSeq) {
        this.clntHlthClmSeq = clntHlthClmSeq;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getClntSeq() {
        return clntSeq;
    }

    public void setClntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
    }

    public Double getClmAmt() {
        return clmAmt;
    }

    public void setClmAmt(Double clmAmt) {
        this.clmAmt = clmAmt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getClmStsKey() {
        return clmStsKey;
    }

    public void setClmStsKey(Long clmStsKey) {
        this.clmStsKey = clmStsKey;
    }

}
