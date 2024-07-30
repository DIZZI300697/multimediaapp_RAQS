package com.example.multimediaapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton stopButton;
    private ImageButton rewindButton;
    private ImageButton forwardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        playButton = findViewById(R.id.btnPlay);
        pauseButton = findViewById(R.id.btnPause);
        stopButton = findViewById(R.id.btnStop);
        rewindButton = findViewById(R.id.btnRewind);
        forwardButton = findViewById(R.id.btnForward);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.your_video_file;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        playButton.setOnClickListener(v -> videoView.start());

        pauseButton.setOnClickListener(v -> videoView.pause());

        stopButton.setOnClickListener(v -> {
            videoView.stopPlayback();
            videoView.setVideoURI(uri);
        });

        rewindButton.setOnClickListener(v -> {
            int currentPosition = videoView.getCurrentPosition();
            if (currentPosition - 15000 > 0) {
                videoView.seekTo(currentPosition - 15000);
            } else {
                videoView.seekTo(0);
            }
        });

        forwardButton.setOnClickListener(v -> {
            int currentPosition = videoView.getCurrentPosition();
            if (currentPosition + 15000 < videoView.getDuration()) {
                videoView.seekTo(currentPosition + 15000);
            } else {
                videoView.seekTo(videoView.getDuration());
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
