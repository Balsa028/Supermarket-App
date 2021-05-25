package com.balsa.onlinesupermarket;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class TrackTimeService extends Service {

    private int seconds = 0;
    private Item item;
    private boolean isActive = true;
    private IBinder binder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        TrackUserTime();
        return binder;
    }

    public class LocalBinder extends Binder {
        public TrackTimeService getService() {
            return TrackTimeService.this;
        }

    }


    private void TrackUserTime() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isActive) {
                    try {
                        Thread.sleep(1000);
                        seconds++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        thread.start();
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isActive = false;
        int minutes = seconds / 60;
        //asking if minutes is bigger then 0 cause of updating out suggestion items 1 point per minute
        if (minutes > 0) {
            if (item != null) {
                Utils.updateUserSuggestion(this, item, minutes);
            }
        }
    }
}


