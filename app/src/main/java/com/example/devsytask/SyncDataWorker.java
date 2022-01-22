package com.example.devsytask;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncDataWorker extends Worker {

    private static final String TAG = SyncDataWorker.class.getSimpleName();

    public SyncDataWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        try {

            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getMarket();
            call.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        if (response.code() == 200) {
                            String remoteResponse = response.body().string();

                            JSONArray dataArray = new JSONArray(remoteResponse);
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);

                                RoomClassPrice task = new RoomClassPrice();
                                task.setSymbol(dataobj.optString("symbol"));
                                task.setName(dataobj.optString("name"));
                                task.setImage(dataobj.optString("image"));
                                task.setCurrentPrice(dataobj.optDouble("current_price"));
                                task.setHigh(dataobj.optDouble("high_24h"));
                                task.setLow(dataobj.optDouble("low_24h"));
                                task.setData_last_update(System.currentTimeMillis());
                                task.setMarket_cap_rank(dataobj.optInt("market_cap_rank"));

                                //adding to database
                                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                                        .Dao()
                                        .insert(task);

                            }
                        }

                    } catch (JSONException | IOException e) {
                        System.out.println(e);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //failed
                    System.out.println("t.getMessage() = " + t.getMessage());
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
            // Technically WorkManager will return Result.failure()
            // but it's best to be explicit about it.
            // Thus if there were errors, we're return FAILURE
            Log.e(TAG, "Error fetching data", e);
            return Result.failure();
        }

        return Result.success();
    }


    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "OnStopped called for this worker");
    }
}
