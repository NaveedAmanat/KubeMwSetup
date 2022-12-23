package com.idev4.setup.dto;

public class CityDto {

    private Long citySeq;
    private String cityCd;
    private String cityNm;
    private String cityCmnt;
    private Long ucSeq;
    private Long cityStsKey;

    public Long getCitySeq() {
        return citySeq;
    }

    public void setCitySeq(Long citySeq) {
        this.citySeq = citySeq;
    }

    public String getCityCd() {
        return cityCd;
    }

    public void setCityCd(String cityCd) {
        this.cityCd = cityCd;
    }

    public String getCityNm() {
        return cityNm;
    }

    public void setCityNm(String cityNm) {
        this.cityNm = cityNm;
    }

    public String getCityCmnt() {
        return cityCmnt;
    }

    public void setCityCmnt(String cityCmnt) {
        this.cityCmnt = cityCmnt;
    }

    public Long getUcSeq() {
        return ucSeq;
    }

    public void setUcSeq(Long ucSeq) {
        this.ucSeq = ucSeq;
    }

    public Long getCityStsKey() {
        return cityStsKey;
    }

    public void setCityStsKey(Long cityStsKey) {
        this.cityStsKey = cityStsKey;
    }
}
