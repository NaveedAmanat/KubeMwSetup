package com.idev4.setup.dto;

import java.util.List;

public class TehsilDto {

    public long thslSeq;
    public String thslCode;
    public String thslName;
    public String thslDescription;
    public long districtSeq;
    public long thslStatus;

    public List<UcDto> ucs;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (thslSeq ^ (thslSeq >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TehsilDto other = (TehsilDto) obj;
        if (thslSeq != other.thslSeq)
            return false;
        return true;
    }


}
