package tracklocation.devdeeds.com.tracklocationproject.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class Task implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getTripid() {
        return tripid;
    }

    public void setTripid(String tripid) {
        this.tripid = tripid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    @ColumnInfo(name = "tripid")
    private String tripid;

    @ColumnInfo(name = "lat")
    private String lat;

    @ColumnInfo(name = "longi")
    private String longi;

    @ColumnInfo(name = "accuracy")
    private String accuracy;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @ColumnInfo(name = "time")
    private String time;





}

