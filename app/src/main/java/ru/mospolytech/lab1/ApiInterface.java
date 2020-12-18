package ru.mospolytech.lab1;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api/web/v1/jobs?lat=55.751094&long=37.599349") //с выдачей по Москве
    Observable<JobsList> productlist(@Query("key_word") String key_word);

    @GET("api/web/v1/jobs/{hashid}")
    Observable<JobObject> product(@Path("hashid") String hashid);
}
