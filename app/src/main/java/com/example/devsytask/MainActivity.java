package com.example.devsytask;

import android.os.Bundle;
import android.widget.TextView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    private static final String SYNC_DATA_WORK_NAME = "fetch_data";
    RecyclerView recyclerView;
    List<RoomClassPrice> listRoomClassPrice;
    TextView tv_dataUpdated, tv_number_of_records;
    WorkManager mWorkManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWorkManager = WorkManager.getInstance(getApplication());
        tv_dataUpdated = findViewById(R.id.tv_dataUpdated);
        tv_number_of_records = findViewById(R.id.tv_number_of_records);
        recyclerView = findViewById(R.id.recycler_view);

        fetchData();

        DatabaseClient
                .getInstance(getApplicationContext())
                .getAppDatabase()
                .Dao()
                .getAll().observe(this, new Observer<List<RoomClassPrice>>() {
            @Override
            public void onChanged(List<RoomClassPrice> roomClassPrices) {
                TasksAdapter adapter = new TasksAdapter(MainActivity.this, MainActivity.this, roomClassPrices);
                adapter.updateList(roomClassPrices);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(adapter);
                listRoomClassPrice = roomClassPrices;
                setDateUpdatedAndNumberOfRecords(roomClassPrices);

            }
        });
    }

    private void setDateUpdatedAndNumberOfRecords(List<RoomClassPrice> roomClassPrices) {
        if (roomClassPrices != null && roomClassPrices.size() > 0) {
            LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(roomClassPrices.get(roomClassPrices.size() - 1).getData_last_update()), TimeZone.getDefault().toZoneId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            tv_dataUpdated.setText("Data last updated on: " + formatter.format(date));
            tv_number_of_records.setText("Number of records: " + roomClassPrices.size());
        }
    }

    private void fetchData() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(SyncDataWorker.class, 30, TimeUnit.MINUTES)
                        .addTag("TAG_SYNC_DATA")
                        .setConstraints(constraints)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        mWorkManager.enqueueUniquePeriodicWork(
                SYNC_DATA_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
                periodicSyncDataWork //work request
        );

    }
}


