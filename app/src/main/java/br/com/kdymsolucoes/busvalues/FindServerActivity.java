package br.com.kdymsolucoes.busvalues;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.kdymsolucoes.busvalues.api.ApiServer;
import br.com.kdymsolucoes.busvalues.api.ConnectionListener;

public class FindServerActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView txtStatus;
    private Button btTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_server);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        btTryAgain = (Button) findViewById(R.id.btTryAgain);

        checkConnectivity();
    }

    private void checkConnectivity() {
        txtStatus.setText(R.string.searching_for_server);

        progressBar.setVisibility(View.VISIBLE);
        btTryAgain.setVisibility(View.GONE);

        new ApiServer(this).isActive(new ConnectionListener() {
            @Override
            public void onConnected() {
                serverFound();
            }

            @Override
            public void onDisconnected() {
                serverNotFound();
            }
        });
    }

    private void serverNotFound() {
        txtStatus.setText(R.string.server_not_found);

        progressBar.setVisibility(View.GONE);
        btTryAgain.setVisibility(View.VISIBLE);
    }

    private void serverFound() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void btTryAgainClick(View view) {
        checkConnectivity();
    }
}
