package com.juniair.customview.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.juniair.customview.R;
import com.juniair.customview.adapter.RecodeAdapter;
import com.juniair.customview.app.AppConfig;
import com.juniair.customview.app.AppController;
import com.juniair.customview.model.Recode;
import com.juniair.customview.util.Dlog;
import com.juniair.customview.util.GridSpacingItemDecoration;
import com.juniair.customview.util.OnItemClickListener;
import com.juniair.customview.util.OnMenuItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CompactCalendarView compactCalendarView;
    private ActionBar toolbar;
    private RecodeAdapter adapter;
    private RecyclerView recyclerView;
    private List<Recode> recodeList;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy MMM", Locale.getDefault());
    private Calendar currentCalender = Calendar.getInstance(Locale.KOREA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControl();
        // define a listener to receive callbacks when certain events happen.
    }

    private void initControl() {
        recodeList = new ArrayList<>();
        adapter = new RecodeAdapter(this, recodeList);
        assignUiElements();
        assignRecyclerView();
        assignCalendar();
        loadEvents(compactCalendarView);
    }

    private void assignUiElements() {
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        toolbar = getSupportActionBar();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));



    }

    private void assignRecyclerView() {
        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClicked(TextView textView) {
                Intent intent = new Intent(getApplicationContext(), RecodeActivity.class);
                intent.putExtra("file", textView.getText().toString());
                startActivity(intent);
            }
        });
        adapter.setMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add_favourite:
                        Toast.makeText(getApplicationContext(), "Add to favourite", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_play_next:
                        Toast.makeText(getApplicationContext(), "Play next", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                }
                return false;
            }
        });
    }


    private void assignCalendar() {
        compactCalendarView.setShouldShowMondayAsFirstDay(false);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                AppController.getInstance().setIdx("" + 1);
                AppController.getInstance().setYear(new SimpleDateFormat("yyyy").format(dateClicked));
                AppController.getInstance().setMonth(new SimpleDateFormat("MM").format(dateClicked));
                AppController.getInstance().setDay(new SimpleDateFormat("dd").format(dateClicked));
                loadList();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "onMonthScroll1");
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
                AppController.getInstance().setIdx("" + 1);
                AppController.getInstance().setYear(new SimpleDateFormat("yyyy").format(firstDayOfNewMonth));
                AppController.getInstance().setMonth(new SimpleDateFormat("MM").format(firstDayOfNewMonth));
                Dlog.d(AppController.getInstance().getYear());
                Dlog.d(AppController.getInstance().getMonth());
                compactCalendarView.setShouldShowMondayAsFirstDay(false);
                compactCalendarView.removeAllEvents();
                loadEvents();

                //onDayClick(firstDayOfNewMonth);
                Log.d(TAG, "onMonthScroll2");
            }
        });
        compactCalendarView.invalidate();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void loadEvents() {
        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.SCHEDULE_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Dlog.d(response);
                    JSONObject jsonObject = new JSONObject(response);
                    boolean isError = jsonObject.getBoolean("error");
                    if (!isError) {
                        JSONArray jsonArray = jsonObject.getJSONArray("info");
                        currentCalender.setTime(new Date());
                        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
                        List<Event> events = new ArrayList<>();
                        Dlog.d("length " + jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            currentCalender.set(Calendar.MONTH, Integer.parseInt(AppController.getInstance().getMonth()));
                            int day = object.getInt("day")-1;
                            currentCalender.add(Calendar.DATE, day);
                            Dlog.d(currentCalender.get(Calendar.DATE)+"");
                            // 해당일자에 표시
                            for (int j = 0; j < 31; j++) {
                                if (j == day) {
                                    Dlog.d("find day index" + j);
                                    Event event = new Event(Color.argb(255, 169, 68, 65), currentCalender.getTimeInMillis(), "");
                                    events.add(event);
                                    break;
                                }

                            }   // j-for
                        }   // i-for
                        compactCalendarView.addEvents(events);
                    }   // if
                    else {
                        Dlog.d(jsonObject.getString("error_code"));
                        Dlog.d(jsonObject.getString("error_msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idx", AppController.getInstance().getIdx());
                params.put("year", AppController.getInstance().getYear());
                params.put("month", AppController.getInstance().getMonth());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request, "req_schedule_search");
    }

    private void loadList() {
        // TODO: 이 객체는 해당 년/월/일에 사용자가 촬영한 영상 기록을 출력하는 구문이다.
        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject result = new JSONObject(response);
                    boolean isError = result.getBoolean("error");
                    Dlog.d(isError+"");
                    if(!isError) {
                        JSONArray jsonArray = result.getJSONArray("result");
                        recodeList.clear();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Recode recode = new Recode();
                            recode.setTitle(object.getString("title"));
                            recode.setThumbnailURL(object.getString("thumbnailURL"));
                            Dlog.d(recode.getThumbnailURL());
                            recodeList.add(recode);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Dlog.d(result.getString("error_msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Dlog.d(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idx", AppController.getInstance().getIdx());
                params.put("year", AppController.getInstance().getYear());
                params.put("month", AppController.getInstance().getMonth());
                params.put("day", AppController.getInstance().getDay());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request, "req_search");
    }

    private void loadEvents(CompactCalendarView compactCalendarView) {
        addEvents(compactCalendarView, -1);
        addEvents(compactCalendarView, Calendar.JULY);
        addEvents(compactCalendarView, Calendar.AUGUST);
    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        Log.d(TAG, "Events for current month: " + compactCalendarView.getEventsForMonth(new Date()));
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(Calendar.MONTH, Calendar.JANUARY);
        Log.d(TAG, "Events for Jan month: " + compactCalendarView.getEventsForMonth(currentCalender.getTime()));
    }

    private void addEvents(CompactCalendarView compactCalendarView, int month) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1); // 한주 밑으로
        Date firstDayOfMonth = currentCalender.getTime(); // 일렬 정렬
        for (int i = 10; i < 15; i++) {
            currentCalender.setTime(firstDayOfMonth); // 일렬 정렬
            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month);
                Log.d(TAG, currentCalender.toString());
            }
            currentCalender.add(Calendar.DATE, i);
            Log.d(TAG, "DATE : " + currentCalender.get(Calendar.DATE));
            long timeInMillis = currentCalender.getTimeInMillis();

            List<Event> events = getEvents(timeInMillis, i);
            compactCalendarView.addEvents(events);
            if(i == 0) {
                for (Event e : events) {
                    Log.d(TAG, e.toString());
                }

            }
        }
    }

    private List<Event> getEvents(long timeInMillis, int day) {
        if (day < 2) {
            Log.d(TAG, "SIZE 1, 2 => " + day);
            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event1 at " + new Date(timeInMillis)));
        }
        else if ( day > 2 && day <= 4) {
            Log.d(TAG, "SIZE 3, 4 => " + day);
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event 0 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
        }
        else {
            Log.d(TAG, "SIZE 5, 6 => " + day);
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis) ),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)));
        }

    }
}
