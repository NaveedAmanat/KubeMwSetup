package com.idev4.setup.dto;

public class BusinessActyDto {

    private Long bizActySeq;
    private String bizActyId;
    private String bizActyNm;
    private Long bizActyStsKey;
    private long bizSectSeq;
    private Long bizActySortOrdr;


    public Long getBizActySortOrdr() {
        return bizActySortOrdr;
    }

    public void setBizActySortOrdr(Long bizActySortOrdr) {
        this.bizActySortOrdr = bizActySortOrdr;
    }

    public long getBizSectSeq() {
        return bizSectSeq;
    }

    public void setBizSectSeq(long bizSectSeq) {
        this.bizSectSeq = bizSectSeq;
    }

    public Long getBizActySeq() {
        return bizActySeq;
    }

    public void setBizActySeq(Long bizActySeq) {
        this.bizActySeq = bizActySeq;
    }

    public String getBizActyId() {
        return bizActyId;
    }

    public void setBizActyId(String bizActyId) {
        this.bizActyId = bizActyId;
    }

    public String getBizActyNm() {
        return bizActyNm;
    }

    public void setBizActyNm(String bizActyNm) {
        this.bizActyNm = bizActyNm;
    }

    public Long getBizActyStsKey() {
        return bizActyStsKey;
    }

    public void setBizActyStsKey(Long bizActyStsKey) {
        this.bizActyStsKey = bizActyStsKey;
    }
}
