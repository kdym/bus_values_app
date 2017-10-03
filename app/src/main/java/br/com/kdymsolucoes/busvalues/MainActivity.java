package br.com.kdymsolucoes.busvalues;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;

import br.com.kdymsolucoes.busvalues.helpers.DatePickerFragment;

public class MainActivity extends AppCompatActivity {

    private EditText edStartDate;
    private EditText edEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edStartDate = (EditText) findViewById(R.id.edStartDate);
        edEndDate = (EditText) findViewById(R.id.edEndDate);

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
    }

    private void onStartDateFocus() {
        new DatePickerFragment(edStartDate).show(getSupportFragmentManager(), "startDatePicker");
    }

    public void onEndDateFocus() {
        new DatePickerFragment(edEndDate).show(getSupportFragmentManager(), "endDatePicker");
    }
}
