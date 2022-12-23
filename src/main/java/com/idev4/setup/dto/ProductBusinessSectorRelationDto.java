package com.idev4.setup.dto;

public class ProductBusinessSectorRelationDto {

    private Long prdBizSectRelSeq;
    private Long bizSectSeq;
    private Long prdSeq;

    public Long getPrdBizSectRelSeq() {
        return prdBizSectRelSeq;
    }

    public void setPrdBizSectRelSeq(Long prdBizSectRelSeq) {
        this.prdBizSectRelSeq = prdBizSectRelSeq;
    }

    public Long getBizSectSeq() {
        return bizSectSeq;
    }

    public void setBizSectSeq(Long bizSectSeq) {
        this.bizSectSeq = bizSectSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

}
