package com.idev4.setup.dto;

import java.util.List;

public class CountryDto {

    public long countrySeq;
    public String countryCode;
    public String countryName;
    public String countryDescription;

    public List<ProvinceDto> states;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (countrySeq ^ (countrySeq >>> 32));
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
        CountryDto other = (CountryDto) obj;
        if (countrySeq != other.countrySeq)
            return false;
        return true;
    }


}
