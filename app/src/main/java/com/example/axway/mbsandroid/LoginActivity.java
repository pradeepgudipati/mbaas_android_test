package com.example.axway.mbsandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkConstants;
import com.axway.mbaas_preprod.SdkException;
import com.example.axway.mbaas.R;


public class LoginActivity extends Activity{


private final String apikey_production = "UNCYIe7DYOJAaZWNET2EwcMCI0828JvP";
private Button btnlogin;

@Override
protected void onCreate(@Nullable Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);


    btnlogin = (Button) findViewById(R.id.btn_login);
    btnlogin.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            new Thread(new Runnable(){
                @Override
                public void run(){
                    try{
                        SdkClient.getInstance().setAuthenticationType(SdkConstants.NAME_API_AUTH);
                        SdkClient.getInstance().setApiKey(apikey_production);
                    }catch(SdkException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    });
}
}



