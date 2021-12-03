package com.example.ModelClasses;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class TestMasterResponseModel {

    ArrayList<TestMaster> tstratemaster;




    public ArrayList<TestMaster> getTstratemaster() {
        return tstratemaster;
    }

    public void setTstratemaster(ArrayList<TestMaster> tstratemaster) {
        this.tstratemaster = tstratemaster;
    }

    /*public ArrayList<SampleType> sampltype;


    public ArrayList<SampleType> getsampltype() {
        return sampltype;
    }

    public void setSamplType(ArrayList<SampleType> sampltype) {
        this.sampltype = sampltype;
    }*/


    public static class TestMaster {
        //String srno;
        ArrayList<SampleType> sampltype;

        String TestType;
        String Fasting;
        String Description;
        String Rate;
        String TestCode;


        public String getTestCode() {
            return TestCode;
        }

        public void setTestCode(String testCode) {
            TestCode = testCode;
        }




        public String getTesttype() {
            return TestType;
        }

        public void setTesttype(String TestType) {
            this.TestType = TestType;
        }

        public String getFasting() {
            return Fasting;
        }

        public void setFasting(String Fasting) {
            this.Fasting = Fasting;
        }

        public String getDesc() {
            return Description;
        }

        public void setDesc(String Description) {
            this.Description = Description;
        }

        public String getRate() {
            return Rate;
        }

        public void setRate(String Rate) {
            this.Rate = Rate;
        }


        public ArrayList<SampleType> getSampltype() {
            return sampltype;
        }


        public void setSampltype(ArrayList<SampleType> sampltype) {
            this.sampltype = sampltype;
        }



       /* public List<String> getSamplType() {
            return samplType;
        }

        public void setSamplType(List<String> samplType) {
            this.samplType = samplType;
        }*/
    }

    public static class SampleType {
        String sampleType;

        public String getsampleType() {
            return sampleType;
        }

        public void setSampleType(String sampleType) {
            this.sampleType = sampleType;
        }

    }


}
