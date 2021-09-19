package tracklocation.devdeeds.com.tracklocationproject;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import tracklocation.devdeeds.com.tracklocationproject.Database.DatabaseClient;
import tracklocation.devdeeds.com.tracklocationproject.Database.Task;
import tracklocation.devdeeds.com.tracklocationproject.Database.TaskDao;
import tracklocation.devdeeds.com.tracklocationproject.services.RadioManager;

public class AppController extends Application {
    public static RadioManager radioManager;
    private static TaskDao dataBaseManager;
    private static AppController mInstance;
    public List<Task> getList() {
        return list;
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }
    public void setList(List<Task> list) {
        this.list = list;
    }

    public static List<Task>list;

    public static RadioManager getRadioManager() {
        return radioManager;
    }

    public static void setRadioManager(RadioManager radioManager) {
        AppController.radioManager = radioManager;
    }

    public static TaskDao getDataBaseManager() {
        return dataBaseManager;
    }

    public static void setDataBaseManager(TaskDao dataBaseManager) {
        AppController.dataBaseManager = dataBaseManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        radioManager = RadioManager.with(this);
        dataBaseManager = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .taskDao();
        mInstance =this;
    }
}
