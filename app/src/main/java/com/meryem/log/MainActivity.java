package com.meryem.log;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;
    private Button buttonSendRequest;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.EditText);
        buttonSendRequest = findViewById(R.id.button);
        webView = findViewById(R.id.webview);


        webView.getSettings().setJavaScriptEnabled(true);


        buttonSendRequest.setOnClickListener(v -> {

            String userInput = editTextInput.getText().toString();
            makeGetRequest(userInput);

        });
    }


    private void makeGetRequest(String userInput) {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder().url(userInput).build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String jsonResponse = response.body().string();

                    String htmlResponse =  "<html><body><pre>" + jsonResponse + "</pre></body></html>";

                    System.out.println("jsonResponse   "  + jsonResponse );
                    runOnUiThread(() -> webView.loadData(htmlResponse, "text/html", "UTF-8"));
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Request failed: " + e.getMessage(), Toast.LENGTH_LONG).show());

            }
        });
    }
}
