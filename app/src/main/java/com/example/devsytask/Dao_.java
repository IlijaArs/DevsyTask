package com.example.devsytask;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface Dao_ {

    @Query("SELECT * FROM bit_coin_prices ORDER BY market_cap_rank LIMIT 50")
    LiveData<List<RoomClassPrice>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomClassPrice task);

    @Delete
    void delete(RoomClassPrice task);

    @Update
    void update(RoomClassPrice task);
}
