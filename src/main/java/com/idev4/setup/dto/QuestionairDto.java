package com.idev4.setup.dto;

public class QuestionairDto {

    private long qstnrSeq;
    private String qstnrNm;
    private String qstnrId;
    private long qstnrStsKey;

    public long getQstnrSeq() {
        return qstnrSeq;
    }

    public void setQstnrSeq(long qstnrSeq) {
        this.qstnrSeq = qstnrSeq;
    }

    public String getQstnrNm() {
        return qstnrNm;
    }

    public void setQstnrNm(String qstnrNm) {
        this.qstnrNm = qstnrNm;
    }

    public String getQstnrId() {
        return qstnrId;
    }

    public void setQstnrId(String qstnrId) {
        this.qstnrId = qstnrId;
    }

    public long getQstnrStsKey() {
        return qstnrStsKey;
    }

    public void setQstnrStsKey(long qstnrStsKey) {
        this.qstnrStsKey = qstnrStsKey;
    }
}
