package com.paysez.library.Responses;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ExpiredLinksResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;


    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
