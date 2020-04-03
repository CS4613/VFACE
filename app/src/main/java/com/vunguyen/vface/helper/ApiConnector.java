/*
 * ApiConnector.java
 */
package com.vunguyen.vface.helper;

import android.app.Application;
import android.content.Context;

//import com.microsoft.projectoxford.face.FaceServiceClient;
//import com.microsoft.projectoxford.face.FaceServiceRestClient;

import edmt.dev.edmtdevcognitiveface.FaceServiceClient;
import edmt.dev.edmtdevcognitiveface.FaceServiceRestClient;

/**
 * This class is to connect the app with Microsoft Face API service via the endpoint and subscription key
 */
public class ApiConnector extends Application
{
    private static FaceServiceClient sFaceServiceClient;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        // Connect to Face Service Api
        sFaceServiceClient = new FaceServiceRestClient("https://vface-4.cognitiveservices.azure.com/face/v1.0", "81d5f2e93eec4ef89faec013ceef83a4");
    }

    public static FaceServiceClient getFaceServiceClient()
    {
        return sFaceServiceClient;
    }
}
