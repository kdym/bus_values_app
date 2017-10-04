package br.com.kdymsolucoes.busvalues.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sKnMetal on 03/08/2017.
 */

public class ApiServer {
    private final String BASE_URL = "192.168.25.17";
    private final int PORT = 3000;

    private Context context;

    public ApiServer(Context context) {
        this.context = context;
    }

    public void isActive(final ConnectionListener listener) {
        new IsApiActive(this.BASE_URL, this.PORT) {
            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    listener.onConnected();
                } else {
                    listener.onDisconnected();
                }
            }
        }.execute();
    }

    public Retrofit buildApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .baseUrl(String.format("http://%s:%s", this.BASE_URL, this.PORT))
                .build();
    }
}

class IsApiActive extends AsyncTask<Void, Void, Boolean> {

    private String hostName;
    private int port;

    public IsApiActive(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (this.hostName == null) {
            return false;
        } else {
            SocketAddress address = new InetSocketAddress(this.hostName, this.port);
            Socket socket = new Socket();

            try {
                socket.connect(address, 5000);

                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}