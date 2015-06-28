package pl.mslawin.latarka;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private boolean isFLashLightOn = false;
    private Camera camera;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button = (Button) findViewById(R.id.button);
        PackageManager pm = this.getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(getApplicationContext(), "Your device doesn't have camera installed", Toast.LENGTH_SHORT).show();
            return;
        }

        camera = Camera.open();
        final Camera.Parameters p = camera.getParameters();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camera == null) {
                    camera = Camera.open();
                }
                if (isFLashLightOn) {
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    isFLashLightOn = false;
                    button.setText("Włącz");
                } else {
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(p);
                    isFLashLightOn = true;
                    button.setText("Wyłącz");
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
        }
    }

}
