package com.example.macbookair.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.macbookair.weather.model.WeatherModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ApiInterface apiInterface;
    private TextView txtCityName, txtTemperature, txtCondition;
    private WeatherModel weather;
    private String KEY = "2dfa67e4d09c4dc180e171318180502";
    private EditText editText;
    private ImageButton search;
    private ImageView imgWeather, imgCelsius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCelsius = findViewById(R.id.celsius);
        txtCondition = findViewById(R.id.txtCondition);
        txtCityName = findViewById(R.id.txtCityName);
        txtTemperature = findViewById(R.id.txtTemperature);
        search = findViewById(R.id.searchBtn);
        editText = findViewById(R.id.edittext);
        imgWeather = findViewById(R.id.imgWeather);

        apiInterface = WeatherApi.getWeatherApi().create(ApiInterface.class);

        imgCelsius.setVisibility(View.INVISIBLE);

        search.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Call<WeatherModel> currentCall = apiInterface.getCurrent(KEY, editText.getText().toString());

        currentCall.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {

                if (response.isSuccessful()) {
                    weather = response.body();
                    txtCityName.setText(weather.getLocation().getName());
                    txtTemperature.setText(String.valueOf(weather.getCurrent().getTempC()));
                    txtCondition.setText(weather.getCurrent().getCondition().getText());
                    String urlImage = weather.getCurrent().getCondition().getIcon();
                    urlImage = "https:" + urlImage;

                    imgCelsius.setVisibility(View.VISIBLE);

                    Glide.with(MainActivity.this)
                            .load(urlImage)
                            .apply(new RequestOptions().error(null).circleCrop())
                            .into(imgWeather);
                } else
                    Toast.makeText(getApplicationContext(), "Please enter valid city", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "Please check your Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
