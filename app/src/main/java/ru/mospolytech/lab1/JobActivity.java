package ru.mospolytech.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class JobActivity extends AppCompatActivity {
    TextView jobsHeader;
    TextView jobsText;
    TextView jobsBody;
    TextView jobsCity;
    TextView contactPhone;
    TextView address;
    TextView metroPic;
    ApiInterface api;
    private CompositeDisposable disposables;

    List<Metro> metroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        jobsHeader = findViewById(R.id.jobHeader);
        jobsText = findViewById(R.id.jobText);
        jobsBody = findViewById(R.id.productBody);
        jobsCity = findViewById(R.id.jobCity);
        contactPhone = findViewById(R.id.contactPhone);
        address = findViewById(R.id.nameStation);
        metroPic = findViewById(R.id.metroPin);

        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();

        if (getIntent().getExtras() != null){
            disposables.add(
                    api.product(getIntent().getStringExtra("hashid"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    (job) -> {
                                        jobsHeader.setText(job.all.title);
                                        if (job.all.salary_to == 0) {
                                            jobsText.setText("Зарплата: " + job.all.salary_from  + " ₽");
                                        } else {
                                            jobsText.setText("от " + job.all.salary_from + " до " + job.all.salary_to + " ₽");
                                        }
                                        jobsBody.setText(job.all.description);
                                        metroList = new ArrayList<>();
                                        metroList.clear();
                                        jobsCity.setText("Адрес: " + job.all.address);
                                        Log.d(TAG, "Список ближайших станций метро: " + metroList.addAll(job.all.metro));
                                        if (!metroList.isEmpty()) {
                                            if (metroList.get(0).color == "" || metroList.get(0).color == null || metroList.get(0).color.isEmpty()) {
                                                metroPic.setTextColor(Color.parseColor("#ffc0cb"));
                                                address.setText("Метро: " + metroList.get(0).name);
                                            } else {
                                                Log.d(TAG, "Цвет: " + metroList.get(0).color);
                                                metroPic.setTextColor(Color.parseColor(metroList.get(0).color));
                                                address.setText("Метро: " + metroList.get(0).name);
                                            }
                                        }
                                        if(job.all.contact_phone != null) contactPhone.setText("Контакты: +" + job.all.contact_phone);
                                    },
                                    (error) -> {
                                        error.printStackTrace();
                                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    }));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
