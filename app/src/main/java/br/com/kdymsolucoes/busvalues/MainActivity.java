package br.com.kdymsolucoes.busvalues;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import br.com.kdymsolucoes.busvalues.api.ApiServer;
import br.com.kdymsolucoes.busvalues.api.PriceClient;
import br.com.kdymsolucoes.busvalues.api.VehicleClient;
import br.com.kdymsolucoes.busvalues.helpers.DatePickerFragment;
import br.com.kdymsolucoes.busvalues.helpers.Formatter;
import br.com.kdymsolucoes.busvalues.helpers.Parser;
import br.com.kdymsolucoes.busvalues.model.Price;
import br.com.kdymsolucoes.busvalues.model.Vehicle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText edStartDate;
    private EditText edEndDate;
    private EditText edHours;
    private Spinner spVehicle;
    private TextView txtTotalPrice;
    private LinearLayout lytLoading;
    private LinearLayout lytLoading2;
    private ListView lstPrices;

    private Retrofit retrofit;
    private List<Price> prices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiServer apiServer = new ApiServer(this);
        retrofit = apiServer.buildApi();

        edStartDate = (EditText) findViewById(R.id.edStartDate);
        edEndDate = (EditText) findViewById(R.id.edEndDate);
        edHours = (EditText) findViewById(R.id.edHours);
        spVehicle = (Spinner) findViewById(R.id.spVehicle);
        txtTotalPrice = (TextView) findViewById(R.id.txtTotalPrice);
        lytLoading = (LinearLayout) findViewById(R.id.lytLoading);
        lytLoading2 = (LinearLayout) findViewById(R.id.lytLoading2);
        lstPrices = (ListView) findViewById(R.id.lstPrices);

        edStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    onStartDateFocus();
                }
            }
        });

        edEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    onEndDateFocus();
                }
            }
        });

        retrofit.create(VehicleClient.class).findVehicles().enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if (response.isSuccessful()) {
                    spVehicle.setAdapter(new ArrayAdapter<Vehicle>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {

            }
        });

        txtTotalPrice.setText(Formatter.formatCurrency(0, this));
    }

    private void onStartDateFocus() {
        new DatePickerFragment(edStartDate).show(getSupportFragmentManager(), "startDatePicker");
    }

    public void onEndDateFocus() {
        new DatePickerFragment(edEndDate).show(getSupportFragmentManager(), "endDatePicker");
    }

    public void btCalculateClick(View view) {
        boolean error = false;

        edStartDate.setError(null);
        edEndDate.setError(null);
        edHours.setError(null);

        if (edStartDate.getText().toString().isEmpty()) {
            edStartDate.setError(getString(R.string.required_field));
            error = true;
        } else if (Parser.parseDate(edStartDate.getText().toString(), this) == null) {
            edStartDate.setError(getString(R.string.invalid_date));
            error = true;
        }

        if (edEndDate.getText().toString().isEmpty()) {
            edEndDate.setError(getString(R.string.required_field));
            error = true;
        } else if (Parser.parseDate(edEndDate.getText().toString(), this) == null) {
            edEndDate.setError(getString(R.string.invalid_date));
            error = true;
        }

        if (edHours.getText().toString().isEmpty()) {
            edHours.setError(getString(R.string.required_field));
            error = true;
        }

        if (!error) {
            lytLoading.setVisibility(View.VISIBLE);
            lytLoading2.setVisibility(View.VISIBLE);

            Vehicle vehicle = (Vehicle) spVehicle.getSelectedItem();

            retrofit.create(PriceClient.class).calculatePrices(
                    edStartDate.getText().toString(),
                    edEndDate.getText().toString(),
                    vehicle.getId(),
                    Integer.parseInt(edHours.getText().toString())
            ).enqueue(new Callback<List<Price>>() {
                @Override
                public void onResponse(Call<List<Price>> call, Response<List<Price>> response) {
                    if (response.isSuccessful()) {
                        prices = response.body();

                        lstPrices.setAdapter(new PricesListAdapter());

                        double sum = 0;
                        for (Price p : prices) {
                            sum += p.getPrice();
                        }

                        txtTotalPrice.setText(Formatter.formatCurrency(sum, MainActivity.this));

                        lytLoading.setVisibility(View.GONE);
                        lytLoading2.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<Price>> call, Throwable t) {

                }
            });
        }
    }

    public class PricesListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return prices.size();
        }

        @Override
        public Object getItem(int i) {
            return prices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.prices_list, null);

            TextView txtDate = view.findViewById(R.id.txtDate);
            TextView txtCategory = view.findViewById(R.id.txtCategory);
            TextView txPrice = view.findViewById(R.id.txPrice);

            SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.month_day_format));

            txtDate.setText(dateFormat.format(prices.get(i).getDate()));
            txtCategory.setText(prices.get(i).getCategory());
            txPrice.setText(Formatter.formatCurrency(prices.get(i).getPrice(), MainActivity.this));

            return view;
        }
    }
}
