package com.idev4.setup.dto;

import java.util.List;

public class ProvinceDto {

    public long provSeq;
    public String provName;
    public String provCode;
    public String provDescription;
    public long countrySeq;

    public List<DistrictDto> districts;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (provSeq ^ (provSeq >>> 32));
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
        ProvinceDto other = (ProvinceDto) obj;
        if (provSeq != other.provSeq)
            return false;
        return true;
    }


}
