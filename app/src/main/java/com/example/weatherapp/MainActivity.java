package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView country, city, temp, time, lat, lon, hum, press, sunrise, sunset, wind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        temp = findViewById(R.id.textView5);
        time = findViewById(R.id.time);
        lat = findViewById(R.id.Latitude);
        lon = findViewById(R.id.Londitude);
        hum = findViewById(R.id.Humidity);
        press = findViewById(R.id.Pressure);
        sunrise = findViewById(R.id.Sunrise);
        sunset = findViewById(R.id.Sunset);
        wind = findViewById(R.id.Wind);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findWeather();
            }
        });
    }

        public void findWeather() {
        String cName = editText.getText().toString();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cName + "&appid=0a080f8752d7fe351f0a33800b62896e";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        // najde krajinu
                        JSONObject object1 = jsonObject.getJSONObject("sys");
                        String county_find = object1.getString("country");
                        country.setText(county_find);

                        // najde mesto
                        String city_find = jsonObject.getString("name");
                        city.setText(city_find);

                        // zisti teplotu
                        JSONObject object2 = jsonObject.getJSONObject("main");
                        int temp_find = object2.getInt("temp");
                        int temp_celsius = (int) (temp_find-272.15);
                        String temp_final = String.valueOf(temp_celsius);
                        //String temp_find = object2.getString("temp");
                        temp.setText(temp_final + " °C");

                        // datum a cas
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat std = new SimpleDateFormat("dd/MM/yyyy \nHH:mm:ss");
                        String date = std.format(calendar.getTime());
                        time.setText(date);

                        // zemepisna sirka

                        JSONObject object3 = jsonObject.getJSONObject("coord");
                        String latitude_find = object3.getString("lat");
                        lat.setText(latitude_find + " °");

                        // zemepisna dlzka

                        String lon_find = object3.getString("lon");
                        lon.setText(lon_find + " °");

                        // vlhkost

                        String hum_find = object2.getString("humidity");
                        hum.setText(hum_find + " %");

                        // tlak

                        String press_find = object2.getString("pressure");
                        press.setText(press_find + " hPa");

                        // vychod slnka

                        String sunrise_find = object1.getString("sunrise");
                        sunrise.setText(sunrise_find);

                        // zapad slnka

                        String sunset_find = object1.getString("sunset");
                        sunset.setText(sunset_find);

                        // rychlost vetra

                        JSONObject object4 = jsonObject.getJSONObject("wind");
                        String wind_find = object4.getString("speed");
                        wind.setText(wind_find + " m/s");





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }
}