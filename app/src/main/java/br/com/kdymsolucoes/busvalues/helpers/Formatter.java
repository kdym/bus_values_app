package br.com.kdymsolucoes.busvalues.helpers;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.kdymsolucoes.busvalues.R;

/**
 * Created by sKnMetal on 04/10/2017.
 */

public class Formatter {
    public static String formatDate(Date date, Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(context.getString(R.string.date_format));

        return simpleDateFormat.format(date);
    }

    public static String formatCurrency(double value, Context context) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(context.getString(R.string.decimal_separator).charAt(0));
        symbols.setGroupingSeparator(context.getString(R.string.thousands_separator).charAt(0));
        symbols.setCurrencySymbol(context.getString(R.string.currency_symbol));

        DecimalFormat formatter = new DecimalFormat("$#,###.##", symbols);

        return formatter.format(value);
    }
}
