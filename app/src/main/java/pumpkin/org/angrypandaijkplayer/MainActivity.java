package pumpkin.org.angrypandaijkplayer;

import android.media.AudioManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;
import org.videolan.vlc.util.VLCInstance;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    private IjkMediaPlayer mIjkMediaPlayer;
    private Button mBtn;
    private Button mVlcBtn;
    private LibVLC mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mMediaPlayer = VLCInstance.getLibVlcInstance(getApplicationContext());
        } catch (LibVlcException e) {
            e.printStackTrace();
        }

        mVlcBtn = (Button)findViewById(R.id.vlcbutton);
        mVlcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMediaPlayer.setRate(2.0f);
                mMediaPlayer.playMRL("http://od.open.qingting.fm/m4a/5822c8767cb891101952eb6e_6245494_64.m4a?u=786&channelId=193626&programId=5825617");

            }
        });

        mBtn = (Button)findViewById(R.id.button);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });

        initPlayer();

    }

    private void startPlay(){
        mIjkMediaPlayer.setOnPreparedListener(IMediaPlayer::start);
        try {
            String sdDir = Environment.getExternalStorageDirectory().toString();//获取跟目录
            //查找SD卡根路径
            sdDir += "/qilixiang.mp3";
            mIjkMediaPlayer.setDataSource("http://od.open.qingting.fm/m4a/5822c8767cb891101952eb6e_6245494_64.m4a?u=786&channelId=193626&programId=5825617");
            //mIjkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "soundtouch", 0);
            mIjkMediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }

        mIjkMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                mIjkMediaPlayer.reset();
            }
        });
    }

    private void initPlayer(){
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        mIjkMediaPlayer = new IjkMediaPlayer();
        mIjkMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mIjkMediaPlayer.setScreenOnWhilePlaying(true);
        mIjkMediaPlayer.setSpeed(2.0f);
    }

}
