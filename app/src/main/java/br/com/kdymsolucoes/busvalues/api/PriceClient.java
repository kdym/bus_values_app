package br.com.kdymsolucoes.busvalues.api;

import java.util.Date;
import java.util.List;

import br.com.kdymsolucoes.busvalues.model.Price;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by sKnMetal on 04/10/2017.
 */

public interface PriceClient {
    @FormUrlEncoded
    @POST("dashboard/calculate.json")
    Call<List<Price>> calculatePrices(
            @Field("start_date") String startDate,
            @Field("end_date") String endDate,
            @Field("vehicle") int vehicleId,
            @Field("hours") int hour
    );
}
