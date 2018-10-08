package me.jordancarlson.dabeetsdeets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jordancarlson.dabeetsdeets.model.HttpUtil;
import me.jordancarlson.dabeetsdeets.model.Login;
import me.jordancarlson.dabeetsdeets.model.Reading;
import me.jordancarlson.dabeetsdeets.model.Subscription;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
//    private static final String ACCOUNT_ID = "a97afcc3-1348-4a33-84a8-39e0b86a1bcb";
//    private static final String APPLICATION_ID = "D89443D2-327C-4A6F-89E5-496BBB0317DB";
//    private static final String PASSWORD = "d12dd17081854955a7efb52dd46b9da8";

    private static final String ACCOUNT_ID = "5cc8c7af-b374-47da-a539-39e2723c606c";
    private static final String APPLICATION_ID = "D89443D2-327C-4A6F-89E5-496BBB0317DB";
    private static final String PASSWORD = "100b5265dc7a44a7b0ab9be9c3ff9d8f";


    @BindView(R.id.avg_1) TextView mAverage1;
    @BindView(R.id.avg_3) TextView mAverage3;
    @BindView(R.id.avg_6) TextView mAverage6;
    @BindView(R.id.avg_12) TextView mAverage12;
    @BindView(R.id.avg_24) TextView mAverage24;

    @BindView(R.id.chart) ScatterChart mChart;

    private String mSessionToken;
    private DexcomClient mClient;
    private float mReferenceTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://share2.dexcom.com/ShareWebServices/Services/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpUtil.getUnsafeOkHttpClient(this))
                .build();

        mClient = retrofit.create(DexcomClient.class);


        Call<String> sessionCall =  mClient.getSessionId(new Login(ACCOUNT_ID, APPLICATION_ID, PASSWORD));

        sessionCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                mSessionToken = response.body();

                getSubscriptions(mSessionToken);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "]", t);
            }
        });


    }

    private void getSubscriptions(final String sessionToken) {
        Call<List<Subscription>> subs =  mClient.getSubscriptions(sessionToken);

        subs.enqueue(new Callback<List<Subscription>>() {
            @Override
            public void onResponse(Call<List<Subscription>> call, Response<List<Subscription>> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                Subscription firstSub = response.body().get(0);

                getReadings(sessionToken, firstSub.getSubscriptionId(), 60, 7200, mAverage1);
                getReadings(sessionToken, firstSub.getSubscriptionId(), 180, 7200, mAverage3);
                getReadings(sessionToken, firstSub.getSubscriptionId(), 360, 7200, mAverage6);
                getReadings(sessionToken, firstSub.getSubscriptionId(), 720, 7200, mAverage12);
                getReadings(sessionToken, firstSub.getSubscriptionId(), 1440, 7200, mAverage24);
            }

            @Override
            public void onFailure(Call<List<Subscription>> call, Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "]", t);
            }
        });
    }

    private void getReadings(String sessionToken, String subscriptionId, int minutes, int maxCount, final TextView averageText) {
        Call<List<Reading>> readings = mClient.getReadings(sessionToken, subscriptionId, minutes, maxCount);

        readings.enqueue(new Callback<List<Reading>>() {
            @Override
            public void onResponse(Call<List<Reading>> call, Response<List<Reading>> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                List<Reading> readingList = response.body();

                Double sumValue = 0.0;
                Double sumTrend = 0.0;

                // create a dataset and give it a type
                final List<Entry> values = new ArrayList<>();

                boolean first = true;

                for (Reading reading : readingList) {
                    if (first) {
                        mReferenceTimestamp = getFloatFromReading(reading);
                        first = false;
                    }

                    sumValue += reading.getValue();
                    sumTrend += reading.getTrend();
                    values.add(new Entry(calcOffsetFromReference(mReferenceTimestamp, getFloatFromReading(reading)), reading.getValue()));
                }


                Collections.sort(values, new Comparator<Entry>() {
                    @Override
                    public int compare(Entry o1, Entry o2) {
                        return Float.compare(o1.getX(), o2.getX());
                    }
                });

                final Double avgValue = sumValue / readingList.size();
                final Double avgTrend = sumTrend / readingList.size();

                final Double roundValue = Math.round(avgValue * 100.0) / 100.0;
                final Double roundTrend = Math.round(avgTrend * 10.0) / 10.0;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        averageText.setText("Average: " + roundValue + " / " + roundTrend);
//
//                        updateChart(values);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Reading>> call, Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "]", t);
            }
        });
    }

    private float calcOffsetFromReference(float mReferenceTimestamp, float floatFromReading) {
        return floatFromReading - mReferenceTimestamp;
    }

    private float getFloatFromReading(Reading reading) {
        String str = reading.getST();
        str = str.replaceAll("[^\\d.]", "");

        return Float.valueOf(str);
    }

    private float getMillisFromJson(String str) {
        str = str.replaceAll("[^\\d.]", "");
        return Float.valueOf(str);
    }

    private void updateChart(List<Entry> values) {
        mChart.getDescription().setEnabled(false);
//        mChart.setOnChartValueSelectedListener(MainActivity.this);

        mChart.setDrawGridBackground(true);
        mChart.setTouchEnabled(true);
        mChart.setMaxHighlightDistance(50f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.setMaxVisibleValueCount(2000);
        mChart.setPinchZoom(true);


        IAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter((long) mReferenceTimestamp);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(xAxisFormatter);





        ScatterDataSet set1 = new ScatterDataSet(values, "DS 1");
        set1.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set1.setColor(ColorTemplate.COLORFUL_COLORS[0]);

        set1.setScatterShapeSize(8f);

        ArrayList<IScatterDataSet> dataSets = new ArrayList<IScatterDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        ScatterData data = new ScatterData(dataSets);

        mChart.setData(data);
        mChart.invalidate();
    }
}
