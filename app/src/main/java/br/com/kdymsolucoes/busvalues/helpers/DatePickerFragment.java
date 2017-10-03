package br.com.kdymsolucoes.busvalues.helpers;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import br.com.kdymsolucoes.busvalues.R;

/**
 * Created by sKnMetal on 03/10/2017.
 */

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText editText;

    public DatePickerFragment(EditText editText) {
        this.editText = editText;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String editTextValue = this.editText.getText().toString();

        Calendar date = Calendar.getInstance();

        if (!editTextValue.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format));

            try {
                date.setTime(dateFormat.parse(editTextValue));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return new DatePickerDialog(
                getActivity(),
                this,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
        );
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(i, i1, i2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.date_format));

        editText.setText(simpleDateFormat.format(calendar.getTime()));
    }
}