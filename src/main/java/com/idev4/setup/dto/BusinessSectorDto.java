package com.idev4.setup.dto;

public class BusinessSectorDto {

    private Long bizSectSeq;
    private String bizSectId;
    private String bizSectNm;
    private Long bizSectStsKey;
    private Long bizSectSortOrdr;

    public Long getBizSectSortOrdr() {
        return bizSectSortOrdr;
    }

    public void setBizSectSortOrdr(Long bizSectSortOrdr) {
        this.bizSectSortOrdr = bizSectSortOrdr;
    }

    public Long getBizSectSeq() {
        return bizSectSeq;
    }

    public void setBizSectSeq(Long bizSectSeq) {
        this.bizSectSeq = bizSectSeq;
    }

    public String getBizSectId() {
        return bizSectId;
    }

    public void setBizSectId(String bizSectId) {
        this.bizSectId = bizSectId;
    }

    public String getBizSectNm() {
        return bizSectNm;
    }

    public void setBizSectNm(String bizSectNm) {
        this.bizSectNm = bizSectNm;
    }

    public Long getBizSectStsKey() {
        return bizSectStsKey;
    }

    public void setBizSectStsKey(Long bizSectStsKey) {
        this.bizSectStsKey = bizSectStsKey;
    }
}
