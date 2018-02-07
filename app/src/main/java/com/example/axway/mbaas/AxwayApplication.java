package com.example.axway.mbaas;

import android.app.Application;

/**
 * Created by tmarthy on 06/02/18.
 */

public class AxwayApplication extends Application {

//    private AxwayApplication axwayApplication;

    public String logUserId = "";
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
//        axwayApplication = this;

    }

    public void setLogUserId(String logUserId){
        this.logUserId = logUserId;
    }


    public String getUserId(){
        return logUserId;
    }


}
