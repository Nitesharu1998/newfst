package com.example.ModelClasses;

import com.google.gson.annotations.SerializedName;


public class testModel {


    @SerializedName("ServiceId")
    private String serviceId;
    @SerializedName("ServiceName")
    private String serviceName;
    @SerializedName("Amount")
    private Integer amount;
    @SerializedName("Min_Amount")
    private Integer minAmount;
    @SerializedName("Category")
    private String category;
    @SerializedName("HVC_Rate")
    private Integer hvcRate;
    @SerializedName("Old_Rate")
    private Integer oldRate;
    @SerializedName("NHF_Rate")
    private Object nhfRate;
    @SerializedName("DSA_Rate")
    private Integer dsaRate;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getHvcRate() {
        return hvcRate;
    }

    public void setHvcRate(Integer hvcRate) {
        this.hvcRate = hvcRate;
    }

    public Integer getOldRate() {
        return oldRate;
    }

    public void setOldRate(Integer oldRate) {
        this.oldRate = oldRate;
    }

    public Object getNhfRate() {
        return nhfRate;
    }

    public void setNhfRate(Object nhfRate) {
        this.nhfRate = nhfRate;
    }

    public Integer getDsaRate() {
        return dsaRate;
    }

    public void setDsaRate(Integer dsaRate) {
        this.dsaRate = dsaRate;
    }
}
