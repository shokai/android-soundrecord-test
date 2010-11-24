package org.shokai.soundrecord;

import java.io.File;

import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener, OnCompletionListener{
    
    private Button buttonRecord, buttonPlay;
    private MediaRecorder recorder;
    private String fileName = "test.3gp";
    private File dataDir;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        trace("start");

        this.dataDir = new File(Environment.getExternalStorageDirectory(), this.getPackageName());
        dataDir.mkdirs();
        
        buttonRecord = (Button)findViewById(R.id.ButtonRecord);
        buttonRecord.setOnClickListener(this);
        buttonPlay = (Button)findViewById(R.id.ButtonPlay);
        buttonPlay.setOnClickListener(this);
    }

    public void onClick(View arg0) {
        switch(arg0.getId()){
        case R.id.ButtonRecord:
            if(recorder == null){
                trace("start record");
                try{
                    this.recorder = new MediaRecorder();
                    this.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    this.recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    
                    this.recorder.setOutputFile(new File(dataDir, fileName).getAbsolutePath());
                    this.recorder.prepare();
                    this.recorder.start();
                    this.buttonRecord.setText(R.string.button_record_stop);
                }
                catch(Exception e){
                    error(e.toString());
                }
            }
            else{
                trace("stop record");
                try{
                    this.recorder.stop();
                    this.recorder.release();
                    this.recorder = null;
                    this.buttonRecord.setText(R.string.button_record);
                }
                catch(Exception e){
                    error(e.toString());
                }
            }
            break;
        case R.id.ButtonPlay:
            trace("play");
            try{
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(new File(dataDir, fileName).getAbsolutePath());
                player.prepare();
                player.seekTo(0);
                player.start();
                player.setOnCompletionListener(this);
            }
            catch(Exception e){
                error(e.toString());
            }
            break;
        }
    }

    public void trace(String message){
        Resources res = getResources();
        Log.v(res.getString(R.string.app_name), message);
    }
    
    public void error(String message){
        Resources res = getResources();
        Log.e(res.getString(R.string.app_name), message);
    }

    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        mp.setOnCompletionListener(null);
        mp.release();
        mp = null;
    }

}
