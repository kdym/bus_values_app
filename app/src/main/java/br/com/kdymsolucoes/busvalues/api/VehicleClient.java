package br.com.kdymsolucoes.busvalues.api;

import java.util.List;

import br.com.kdymsolucoes.busvalues.model.Vehicle;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sKnMetal on 03/10/2017.
 */

public interface VehicleClient {
    @GET("vehicles.json")
    Call<List<Vehicle>> findVehicles();
}
