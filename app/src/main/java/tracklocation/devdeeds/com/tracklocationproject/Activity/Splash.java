package tracklocation.devdeeds.com.tracklocationproject.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import tracklocation.devdeeds.com.tracklocationproject.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPermission();
                /* Create an Intent that will start the Menu-Activity. */

            }
        }, 2000);
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!checkIfAlreadyhavePermission()) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            } else {
                showCity();
            }
        } else {
            showCity();
        }
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCity();

                } else {
                    finish();
                }
                break;
            }

        }
    }

    private void showCity() {
        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
        Splash.this.startActivity(mainIntent);
        Splash.this.finish();
    }
}
