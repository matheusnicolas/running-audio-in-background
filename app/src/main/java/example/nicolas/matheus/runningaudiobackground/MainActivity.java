package example.nicolas.matheus.runningaudiobackground;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    final int REQUEST_PERMISSION_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!checkedPermissionFromDevice())
            requestPermission();

    }

    public void redirectToSecondScreen(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void startRecording(View view) {
        startService(new Intent(getBaseContext(), AudioService.class));
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permissão Concedida", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permissão Negada", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private boolean checkedPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED;
    }
}
