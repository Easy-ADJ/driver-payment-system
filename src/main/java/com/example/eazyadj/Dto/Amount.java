package com.example.eazyadj.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Amount {

    private Integer total;

    @JsonProperty("tax_free")
    private Integer taxFree;

    private Integer vat;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTaxFree() {
        return taxFree;
    }

    public void setTaxFree(Integer taxFree) {
        this.taxFree = taxFree;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }
}