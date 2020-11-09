package com.resuadam.androidwebviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    /**
     * Llamado cuando la actividad es creada.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_main );

        ImageButton btBack = (ImageButton) this.findViewById( R.id.btBack );
        WebView wvView = (WebView) this.findViewById( R.id.wvView );

        btBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        } );

        this.configureWebView( wvView, "http://www.google.es", 10 );
    }

    private void configureWebView(WebView wvView, String url, int defaultFontSize)
    {
        WebSettings webSettings = wvView.getSettings();

        webSettings.setBuiltInZoomControls( true );
        webSettings.setDefaultFontSize( defaultFontSize );

        // Enable javascript and make android code available from it as the Android object.
        webSettings.setJavaScriptEnabled( true );
        wvView.addJavascriptInterface( new WebAppInterface( this ), "Android" );

        // URLs are handled by this WebView,instead of launching a browser
        wvView.setWebViewClient( new WebViewClient() );

        // Load from a URL - remember to give the app the internet permission
        // wvView.loadUrl( url );

        // Load a HTML file from the assets subdir
        StringBuilder builder = new StringBuilder();
        InputStream in = null;
        try {
            String line;

            in = this.getAssets().open( "calc.html" );
            BufferedReader inf = new BufferedReader( new InputStreamReader( in ) );

            while( ( line = inf.readLine()) != null ) {
                builder.append( line );
            }
        } catch (IOException e) {
            builder.append( "<html><body><big>ERROR internal: loading asset</big></body></html>");
            Log.e( "main.configureWebView", "error loading asset 'calc.html'" );
        }
        finally {
            try {
                if ( in != null ) {
                    in.close();
                }
            } catch(IOException exc) {
                // ignored
            }
        }

        wvView.loadData( builder.toString(), "text/html", "utf-8" );
    }
}