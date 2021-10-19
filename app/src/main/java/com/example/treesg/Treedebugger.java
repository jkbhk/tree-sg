package com.example.treesg;

import android.util.Log;

public class Treedebugger {

    public static enum ENVIRONMENT{TESTING,DEPLOYMENT};

    public static void log(String m){
        Log.println(Log.ASSERT,"debugger", m);
    }

    public static void setEnvironment(ENVIRONMENT e){
        switch (e){
            case DEPLOYMENT:

                break;
            case TESTING:

                break;
        }
    };
}
