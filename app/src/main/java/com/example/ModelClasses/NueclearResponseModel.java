package com.example.ModelClasses;


import com.google.gson.annotations.SerializedName;

public class NueclearResponseModel {


    @SerializedName("RespId")
    private String respId;
    @SerializedName("ResponseMessage")
    private String responseMessage;
    @SerializedName("Id")
    private Integer id;
    @SerializedName("UserId")
    private String userId;
    @SerializedName("UserCode")
    private String userCode;
    @SerializedName("Name")
    private String name;
    @SerializedName("Address")
    private Object address;
    @SerializedName("Pincode")
    private Object pincode;
    @SerializedName("Mobile")
    private String mobile;
    @SerializedName("Email")
    private Object email;
    @SerializedName("Role")
    private String role;
    @SerializedName("LoginType")
    private String loginType;
    @SerializedName("Company")
    private String company;
    @SerializedName("API_Key")
    private Object apiKey;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private String expiresIn;
    @SerializedName("_issued")
    private Object issued;
    @SerializedName("_expires")
    private Object expires;
    @SerializedName("error")
    private Object error;
    @SerializedName("error_description")
    private Object errorDescription;
    @SerializedName("Parent")
    private Object parent;

    public String getRespId() {
        return respId;
    }

    public void setRespId(String respId) {
        this.respId = respId;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getPincode() {
        return pincode;
    }

    public void setPincode(Object pincode) {
        this.pincode = pincode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Object getApiKey() {
        return apiKey;
    }

    public void setApiKey(Object apiKey) {
        this.apiKey = apiKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Object getIssued() {
        return issued;
    }

    public void setIssued(Object issued) {
        this.issued = issued;
    }

    public Object getExpires() {
        return expires;
    }

    public void setExpires(Object expires) {
        this.expires = expires;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(Object errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public CenterDTO getCenter() {
        return center;
    }

    public void setCenter(CenterDTO center) {
        this.center = center;
    }

    @SerializedName("Center")
    private CenterDTO center;


    public static class CenterDTO {
        public String getCenterId() {
            return centerId;
        }

        public void setCenterId(String centerId) {
            this.centerId = centerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAddres() {
            return addres;
        }

        public void setAddres(Object addres) {
            this.addres = addres;
        }

        public Object getPincode() {
            return pincode;
        }

        public void setPincode(Object pincode) {
            this.pincode = pincode;
        }

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
            this.mobile = mobile;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(Object contactPerson) {
            this.contactPerson = contactPerson;
        }

        public Object getContactPersonNo() {
            return contactPersonNo;
        }

        public void setContactPersonNo(Object contactPersonNo) {
            this.contactPersonNo = contactPersonNo;
        }

        @SerializedName("CenterId")
        private String centerId;
        @SerializedName("Name")
        private String name;
        @SerializedName("Addres")
        private Object addres;
        @SerializedName("Pincode")
        private Object pincode;
        @SerializedName("Mobile")
        private Object mobile;
        @SerializedName("Email")
        private Object email;
        @SerializedName("Type")
        private String type;
        @SerializedName("ContactPerson")
        private Object contactPerson;
        @SerializedName("ContactPersonNo")
        private Object contactPersonNo;
    }
}
