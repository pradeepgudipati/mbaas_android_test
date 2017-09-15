/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.axway.mbaas_preprod.SdkClient;
import com.axway.mbaas_preprod.SdkConstants;
import com.axway.mbaas_preprod.SdkException;



public class LoginActivity extends Activity   {

    private Button btnlogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnlogin = (Button) findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Start the actual Login process
                        try {
                            SdkClient.getInstance().setAuthenticationType(SdkConstants.NAME_API_AUTH);
                            SdkClient.getInstance().setApiKey("UNCYIe7DYOJAaZWNET2EwcMCI0828JvP"); // 360 preprod added by pradeep
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } catch (SdkException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==resultCode)
        {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }
}



