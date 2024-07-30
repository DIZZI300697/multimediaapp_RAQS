package com.example.multimediaapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class VideoSurfaceViewActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton stopButton;
    private ImageButton rewindButton;
    private ImageButton forwardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_surface_view);

        surfaceView = findViewById(R.id.surfaceView);
        playButton = findViewById(R.id.btnPlay);
        pauseButton = findViewById(R.id.btnPause);
        stopButton = findViewById(R.id.btnStop);
        rewindButton = findViewById(R.id.btnRewind);
        forwardButton = findViewById(R.id.btnForward);

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer = new MediaPlayer();
                String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video2;
                Uri uri = Uri.parse(videoPath);
                try {
                    mediaPlayer.setDataSource(VideoSurfaceViewActivity.this, uri);
                    mediaPlayer.setDisplay(holder);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                playButton.setOnClickListener(v -> mediaPlayer.start());
                pauseButton.setOnClickListener(v -> mediaPlayer.pause());
                stopButton.setOnClickListener(v -> {
                    mediaPlayer.stop();
                    mediaPlayer.prepareAsync();
                });
                rewindButton.setOnClickListener(v -> {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.seekTo(Math.max(currentPosition - 15000, 0));
                });
                forwardButton.setOnClickListener(v -> {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.seekTo(Math.min(currentPosition + 15000, mediaPlayer.getDuration()));
                });
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
