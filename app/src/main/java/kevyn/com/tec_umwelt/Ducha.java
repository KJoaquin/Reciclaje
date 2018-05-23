package kevyn.com.tec_umwelt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.util.Locale;

public class Ducha extends AppCompatActivity {
    private boolean mTimerRunning;
    private static long START_TIME_IN_MILLIS = 5000;
    private CircularFillableLoaders mcircularFillableLoaders;
    private CountDownTimer mCountDownTimer;
    private TextView mTextViewCountDown;
    private TextView mPause;
    private TextView mTime2;
    private Button mButtonReset;
    private ImageView iLento,iEstandar,iRapido;
    private TextView tN1,tN2,tN3;
    private MediaPlayer player;
    private LottieAnimationView finAlarma;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ducha);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mcircularFillableLoaders = findViewById(R.id.circularFillableLoaders);
        finAlarma = findViewById(R.id.cerrar);
        mButtonReset = findViewById(R.id.button_reset);
        mPause = findViewById(R.id.pause);
        mTime2 = findViewById(R.id.time2);
        LinearLayout mLento = findViewById(R.id.dcLenta);
        LinearLayout mEstandar = findViewById(R.id.dcEstandar);
        LinearLayout mRapido = findViewById(R.id.dcRapida);
        iLento = findViewById(R.id.imLento);
        iEstandar = findViewById(R.id.imEstandar);
        iRapido = findViewById(R.id.imRapido);
        tN1 = findViewById(R.id.n1);
        tN2 = findViewById(R.id.n2);
        tN3 = findViewById(R.id.n3);
        mCountDownTimer = new CountDownTimer(1,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
            }
        }.start();
        mLento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lento();
            }
        });
        mEstandar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estandar();
            }
        });
        mRapido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rapido();
            }
        });
        mcircularFillableLoaders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        updateCountDownText();
        mPause.setVisibility(View.GONE);
        mTextViewCountDown.setText("Start");
        mTime2.setVisibility(View.VISIBLE);
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        estandar();
    }

    private void rapido() {
        iLento.setBackgroundResource(R.drawable.lento_s);
        iEstandar.setBackgroundResource(R.drawable.estandar_s);
        iRapido.setBackgroundResource(R.drawable.rapido);
        tN1.setTextColor(Color.WHITE);
        tN2.setTextColor(Color.WHITE);
        tN3.setTextColor(Color.CYAN);

        START_TIME_IN_MILLIS = 300000;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        pauseTimer();
        resetTimer();
        updateCountDownText();
        mPause.setVisibility(View.GONE);
        mTextViewCountDown.setText("Start");
        mTime2.setVisibility(View.VISIBLE);
    }

    private void estandar() {
        iLento.setBackgroundResource(R.drawable.lento_s);
        iEstandar.setBackgroundResource(R.drawable.estandar);
        iRapido.setBackgroundResource(R.drawable.rapido_s);
        tN1.setTextColor(Color.WHITE);
        tN2.setTextColor(Color.CYAN);
        tN3.setTextColor(Color.WHITE);

        START_TIME_IN_MILLIS = 470000;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        pauseTimer();
        resetTimer();
        updateCountDownText();
        mPause.setVisibility(View.GONE);
        mTextViewCountDown.setText("Start");
        mTime2.setVisibility(View.VISIBLE);
    }

    private void lento() {
        iLento.setBackgroundResource(R.drawable.lento);
        iEstandar.setBackgroundResource(R.drawable.estandar_s);
        iRapido.setBackgroundResource(R.drawable.rapido_s);
        tN1.setTextColor(Color.CYAN);
        tN2.setTextColor(Color.WHITE);
        tN3.setTextColor(Color.WHITE);

        START_TIME_IN_MILLIS = 900000;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        pauseTimer();
        resetTimer();
        updateCountDownText();
        mPause.setVisibility(View.GONE);
        mTextViewCountDown.setText("Start");
        mTime2.setVisibility(View.VISIBLE);
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                finAlarma.setVisibility(View.VISIBLE);
                play();
                mTimerRunning = false;
                mcircularFillableLoaders.setProgress(100);
                mTime2.setVisibility(View.GONE);
                mButtonReset.setVisibility(View.GONE);
                resetTimer();
                mTextViewCountDown.setText("Start");
                mPause.setVisibility(View.GONE);
            }
        }.start();

        mTimerRunning = true;
        mTime2.setVisibility(View.GONE);
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void play() {

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.alerta2);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }

    private void stopPlayer() {
        finAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                player = null;
                finAlarma.setVisibility(View.GONE);
            }
        });
        if (player != null) {
            play();
        }
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mPause.setVisibility(View.GONE);
        mTime2.setVisibility(View.VISIBLE);
        mTextViewCountDown.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mTextViewCountDown.setText("Start");
        mPause.setVisibility(View.GONE);
        mTime2.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        int progres = (int) (START_TIME_IN_MILLIS / 100);
        int progres2 = (int) (mTimeLeftInMillis / progres);

        mPause.setVisibility(View.VISIBLE);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mcircularFillableLoaders.setProgress(100-progres2);
        mTextViewCountDown.setText(timeLeftFormatted);
        mTime2.setText(timeLeftFormatted);
        mTime2.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Ducha.this, Tecno.class);
        resetTimer();
        startActivity(intent);
        finish();
    }
}