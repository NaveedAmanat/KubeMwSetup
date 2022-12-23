package com.idev4.setup.dto;

import java.util.List;

public class DistrictDto {

    public long districtSeq;
    public String districtName;
    public String districtCode;
    public String districtDescription;
    public long provinceSeq;
    public long districtStatus;

    public List<TehsilDto> tehsils;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (districtSeq ^ (districtSeq >>> 32));
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
        DistrictDto other = (DistrictDto) obj;
        if (districtSeq != other.districtSeq)
            return false;
        return true;
    }


}
