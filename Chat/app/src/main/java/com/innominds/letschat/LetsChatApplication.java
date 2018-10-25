package com.innominds.letschat;

import android.app.Application;

import com.innominds.letschat.helper.AppDatabase;
import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;

public class LetsChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //initializing  database
        DatabaseConfig appDatabase = new DatabaseConfig.Builder(AppDatabase.class)
                .build();

        ReActiveAndroid.init(new ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabase)
                .build());
    }
}
