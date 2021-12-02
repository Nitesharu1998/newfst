package com.example.ModelClasses;

public class
NueclearGetResponseModel {
    String ServiceId,ServiceName,Category;
    Integer Amount,Min_Amount,HVC_Rate,DSA_Rate;
    Object NHF_Rate;

    public NueclearGetResponseModel() {

    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Integer getMin_Amount() {
        return Min_Amount;
    }

    public void setMin_Amount(Integer min_Amount) {
        Min_Amount = min_Amount;
    }

    public Integer getHVC_Rate() {
        return HVC_Rate;
    }

    public void setHVC_Rate(Integer HVC_Rate) {
        this.HVC_Rate = HVC_Rate;
    }

    public Integer getDSA_Rate() {
        return DSA_Rate;
    }

    public void setDSA_Rate(Integer DSA_Rate) {
        this.DSA_Rate = DSA_Rate;
    }

    public Object getNHF_Rate() {
        return NHF_Rate;
    }

    public void setNHF_Rate(Object NHF_Rate) {
        this.NHF_Rate = NHF_Rate;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
