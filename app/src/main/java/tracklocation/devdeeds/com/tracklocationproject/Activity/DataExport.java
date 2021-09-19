package tracklocation.devdeeds.com.tracklocationproject.Activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import tracklocation.devdeeds.com.tracklocationproject.AppController;
import tracklocation.devdeeds.com.tracklocationproject.Database.Task;
import tracklocation.devdeeds.com.tracklocationproject.R;

public class DataExport extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);
        TextView textView = findViewById(R.id.textView);
        setUpJson(AppController.getInstance().getList(), textView);
    }

    private void setUpJson(List<Task> list, TextView textView) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("trip_id", list.get(0).getTripid());
            jsonObject.put("start_time", getDate(Long.parseLong(list.get(0).getTime()),"yyyy-MM-dd'T'HH:mm:ss'Z'"));
            jsonObject.put("end_time", getDate(Long.parseLong(list.get(list.size() - 1).getTime()),"yyyy-MM-dd'T'HH:mm:ss'Z'"));
            JSONArray array = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                JSONObject json = new JSONObject();
                json.put("latitude", list.get(i).getLat());
                json.put("longitide", list.get(i).getLongi());
                json.put("timestamp", getDate(Long.parseLong(list.get(i).getTime()),"yyyy-MM-dd'T'HH:mm:ss'Z'"));
                json.put("accuracy", list.get(i).getAccuracy());
                array.put(i, json);
            }
            jsonObject.put("locations", array);
            textView.setText(jsonObject.toString());
            textView.setMovementMethod(new ScrollingMovementMethod());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static String getDate(long milliSeconds, String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
