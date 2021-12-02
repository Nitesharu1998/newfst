package com.example.ModelClasses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Requset_Data_Model {
    @SerializedName("ALL_LEAVES")
    private List<ALLLEAVESDTO> ALL_LEAVES;
    @SerializedName("ECODE")
    private String ECODE;

    public List<ALLLEAVESDTO> getALL_LEAVES() {
        return ALL_LEAVES;
    }

    public void setALL_LEAVES(List<ALLLEAVESDTO> aLL_LEAVES) {
        this.ALL_LEAVES = aLL_LEAVES;
    }

    public String getECODE() {
        return ECODE;
    }

    public void setECODE(String eCODE) {
        this.ECODE = eCODE;
    }

    public static class ALLLEAVESDTO {
        @SerializedName("ACTION")
        private String ACTION;
        @SerializedName("LEAVE_ID")
        private String LEAVE_ID;

        public String getACTION() {
            return ACTION;
        }

        public void setACTION(String ACTION) {
            this.ACTION = ACTION;
        }

        public String getLEAVE_ID() {
            return LEAVE_ID;
        }

        public void setLEAVE_ID(String lEAVE_ID) {
            this.LEAVE_ID = lEAVE_ID;
        }
    }
 /*   String ECODE;
    public List<Requset_Data_Model.ALL_LEAVES> getALL_LEAVES() {
        return ALL_LEAVES;
    }

    public void setALL_LEAVES(List<Requset_Data_Model.ALL_LEAVES> ALL_LEAVES) {
        this.ALL_LEAVES = ALL_LEAVES;
    }

    public String getECODE() {
        return ECODE;
    }

    public void setECODE(String ECODE) {
        this.ECODE = ECODE;
    }




    static List<ALL_LEAVES> ALL_LEAVES;



    public static class ALL_LEAVES {
        String LEAVE_ID;
        String ACTION;

        public String getLEAVE_ID() {
            return LEAVE_ID;
        }

        public void setLEAVE_ID(String LEAVE_ID) {
            this.LEAVE_ID = LEAVE_ID;
        }

        public String getACTION() {
            return ACTION;
        }

        public void setACTION(String ACTION) {
            this.ACTION = ACTION;
        }
    }*/


}
