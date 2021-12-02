package com.example.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class TechsoTokenResponseModel {
   String access_token,token_type,userName,userID,role,
           Mobile,EmailId,Name,BrandId,RespId
           ,StaffLevel,EmpCode,RespMessage,CompanyName;

   @SerializedName(".issued")
   private String ISSUED;
   @SerializedName(".expires")
   private String EXPIRES;
   Integer expires_in;

   public String getAccess_token() {
      return access_token;
   }

   public void setAccess_token(String access_token) {
      this.access_token = access_token;
   }

   public String getToken_type() {
      return token_type;
   }

   public void setToken_type(String token_type) {
      this.token_type = token_type;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getUserID() {
      return userID;
   }

   public void setUserID(String userID) {
      this.userID = userID;
   }

   public String getRole() {
      return role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   public String getMobile() {
      return Mobile;
   }

   public void setMobile(String mobile) {
      Mobile = mobile;
   }

   public String getEmailId() {
      return EmailId;
   }

   public void setEmailId(String emailId) {
      EmailId = emailId;
   }

   public String getName() {
      return Name;
   }

   public void setName(String name) {
      Name = name;
   }

   public String getBrandId() {
      return BrandId;
   }

   public void setBrandId(String brandId) {
      BrandId = brandId;
   }

   public String getRespId() {
      return RespId;
   }

   public void setRespId(String respId) {
      RespId = respId;
   }

   public String getStaffLevel() {
      return StaffLevel;
   }

   public void setStaffLevel(String staffLevel) {
      StaffLevel = staffLevel;
   }

   public String getEmpCode() {
      return EmpCode;
   }

   public void setEmpCode(String empCode) {
      EmpCode = empCode;
   }

   public String getRespMessage() {
      return RespMessage;
   }

   public void setRespMessage(String respMessage) {
      RespMessage = respMessage;
   }

   public String getCompanyName() {
      return CompanyName;
   }

   public void setCompanyName(String companyName) {
      CompanyName = companyName;
   }

   public String getISSUED() {
      return ISSUED;
   }

   public void setISSUED(String ISSUED) {
      this.ISSUED = ISSUED;
   }

   public String getEXPIRES() {
      return EXPIRES;
   }

   public void setEXPIRES(String EXPIRES) {
      this.EXPIRES = EXPIRES;
   }

   public Integer getExpires_in() {
      return expires_in;
   }

   public void setExpires_in(Integer expires_in) {
      this.expires_in = expires_in;
   }
}
