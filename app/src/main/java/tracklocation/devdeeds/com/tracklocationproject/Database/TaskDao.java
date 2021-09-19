package tracklocation.devdeeds.com.tracklocationproject.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT tripid FROM task GROUP BY tripid")
    List<String> getAll();

    @Query("SELECT * FROM task where tripid like :value")
    List<Task> getTrip(String value);

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

}
