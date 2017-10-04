package br.com.kdymsolucoes.busvalues.helpers;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.kdymsolucoes.busvalues.R;

/**
 * Created by sKnMetal on 04/10/2017.
 */

public class Parser {
    public static Date parseDate(String date, Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.date_format));

        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static double parseDecimal(String value, Context context) {
        value = value.replace(context.getString(R.string.currency_symbol), "");
        value = value.replace(context.getString(R.string.thousands_separator), "");
        value = value.replace(context.getString(R.string.decimal_separator), ".");

        return Double.parseDouble(value);
    }
}
