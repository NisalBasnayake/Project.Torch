package com.torch;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleFlashLightOnOff;
    private CameraManager cameraManager;
    private String getCameraID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register the ToggleButton with a specific ID
        toggleFlashLightOnOff = findViewById(R.id.toggle_flashlight);

        // cameraManager to interact with camera devices
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        // Exception is handled, because to check whether
        // the camera resource is being used by another
        // service or not.
        try {
            // 0 means the back camera unit
            getCameraID = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    // RequiresApi is set because devices below API level 10 don't have a flashlight unit.
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void toggleFlashLight(View view) {
        if (toggleFlashLightOnOff.isChecked()) {
            // Exception is handled to check whether the camera resource is being used by
            // another service or not.
            try {
                // true sets the flashlight in ON mode
                cameraManager.setTorchMode(getCameraID, true);

                // Inform the user about the flashlight
                // status using a Toast message
                Toast.makeText(this, "Flashlight is turned ON", Toast.LENGTH_SHORT).show();
            } catch (CameraAccessException e) {
                // Print a stack trace on standard error
                // output error stream
                e.printStackTrace();
            }
        } else {
            // Exception is handled to check whether the camera resource is being used by
            // another service or not.
            try {
                // false sets the flashlight in OFF mode
                cameraManager.setTorchMode(getCameraID, false);

                // Inform the user about the flashlight
                // status using a Toast message
                Toast.makeText(this, "Flashlight is turned OFF", Toast.LENGTH_SHORT).show();
            } catch (CameraAccessException e) {
                // Print a stack trace on standard error
                // output error stream
                e.printStackTrace();
            }
        }
    }

    // When you click on a button and the flashlight opens, and
    // you do not close the flashlight again, this code
    // will turn off the flashlight automatically.
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void finish() {
        super.finish();
        try {
            // false sets the flashlight in OFF mode
            cameraManager.setTorchMode(getCameraID, false);

            // Inform the user about the flashlight
            // status using a Toast message
            Toast.makeText(this, "Flashlight is turned OFF", Toast.LENGTH_SHORT).show();
        } catch (CameraAccessException e) {
            // Print a stack trace on standard error
            // output error stream
            e.printStackTrace();
        }
    }
}
