package tracklocation.devdeeds.com.tracklocationproject.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.Random;

public class RadioManager {

    private static RadioManager instance = null;

    private static LocationMonitoringService service;

    private Context context;

    private boolean serviceBound;

    private RadioManager(Context context) {
        this.context = context;
        serviceBound = false;
    }

    public static RadioManager with(Context context) {

        // if (instance == null)
        instance = new RadioManager(context);

        return instance;
    }

    public static LocationMonitoringService getService() {
        return service;
    }

    public boolean isBind() {
        try {
            return context.bindService(new Intent(context, LocationMonitoringService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            return false;
        }

    }
    public void setEnableTrip(boolean flag) {
        service.setEnableTrip(flag);
    }
    public boolean isTripStatus() {
        return service.isTripStatus();
    }
    public void bind() {
        Intent intent = new Intent(context, LocationMonitoringService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbind() {
        context.unbindService(serviceConnection);

    }
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder binder) {

            service = ((LocationMonitoringService.LocalBinder) binder).getService();
            serviceBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

            serviceBound = false;
        }
    };


}
