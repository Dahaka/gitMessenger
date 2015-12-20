package dahakashow.com.messangerapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    EditText username;
    Context con;
    public static String username123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Boolean bl = checkGooglePlayServiceAvailability(this, -1);
        Log.v("CheckGooPlaSer", String.valueOf(bl));
        Log.v("internetAvaliable ?", String.valueOf(isInternetAvailable()));
        con = getApplicationContext();


        username = (EditText) findViewById(R.id.username);
        Button btn = (Button) findViewById(R.id.login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("button clicked,", "clicked");
                //username.getText().toString();
                Intent i = new Intent(con, RegistrationIntentService.class);
                i.putExtra("username", username.getText().toString());
                username123=username.getText().toString();
                //i.putExtra("password", passwordText.getText().toString());

                startService(i);
               // Toast.makeText(con, "Service Started", Toast.LENGTH_SHORT).show();

                Intent ii = new Intent(MainActivity.this,ChatActivity.class);
                startActivity(ii);


            }
        });

    }




    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }

    public static boolean checkGooglePlayServiceAvailability(Context context, int versionCode) {

        // Query for the status of Google Play services on the device
        int statusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

        if ((statusCode == ConnectionResult.SUCCESS)
                && (GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE >= versionCode)) {
            return true;
        } else {
            return false;
        }
    }

}
