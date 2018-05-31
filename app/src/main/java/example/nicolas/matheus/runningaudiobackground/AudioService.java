package example.nicolas.matheus.runningaudiobackground;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by matheusnicolas on 25/05/2018.
 */

public class AudioService extends Service {

    private MediaRecorder mRecorder;
    private String mFileName = null;
    private static final String LOG_TAG = "Record_log";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRecording();
    }

    public void startRecording() {
        Toast.makeText(getApplicationContext(), "Gravando...", Toast.LENGTH_SHORT).show();
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/recorded_audio.3gp";

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording() {
        Toast.makeText(getApplicationContext(), "Gravação parou...", Toast.LENGTH_SHORT).show();
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}
