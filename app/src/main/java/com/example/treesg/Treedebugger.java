package com.example.treesg;

import android.util.Log;

public class Treedebugger {

    public static void log(String m){
        Log.println(Log.ASSERT,"debugger", m);
    }
}
