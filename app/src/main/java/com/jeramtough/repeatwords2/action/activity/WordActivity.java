package com.jeramtough.repeatwords2.action.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtandroid.business.BusinessCaller;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ui.TimedCloseTextView;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.bean.word.Word;
import com.jeramtough.repeatwords2.component.youdao.bean.YoudaoQueryResult;
import com.jeramtough.repeatwords2.service.WordService;

import java.io.IOException;

public class WordActivity extends BasicActivity {

    private static final int BUSINESS_CODE_QUEARY_WORD = 0;

    private TimedCloseTextView timedCloseTextView;
    private TextView textViewEn;
    private TextView textViewUkPhonetic;
    private TextView textViewUsPhonetic;
    private TextView textViewWorldPhonetic;
    private TextView textViewWordDetail;

    private MediaPlayer mediaPlayer;

    @InjectService
    private WordService wordService;

    private String ukSpeechUrl, usSpeechUrl, worldSpeechUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        timedCloseTextView = findViewById(R.id.timedCloseTextView);
        textViewEn = findViewById(R.id.textView_en);
        textViewUkPhonetic = findViewById(R.id.textView_uk_phonetic);
        textViewUsPhonetic = findViewById(R.id.textView_us_phonetic);
        textViewWorldPhonetic = findViewById(R.id.textView_world_phonetic);
        textViewWordDetail = findViewById(R.id.textView_word_detail);

        textViewUsPhonetic.setOnClickListener(this);
        textViewUkPhonetic.setOnClickListener(this);
        textViewWorldPhonetic.setOnClickListener(this);

        initResources();
    }

    @Override
    protected void initResources() {
        mediaPlayer = new MediaPlayer();

        Word word = (Word) getIntent().getSerializableExtra("word");
        if (word != null) {
            textViewEn.setText(word.getEn() + "\n" + word.getCh());
            wordService.queryWordDetail(word, new BusinessCaller(getActivityHandler(), BUSINESS_CODE_QUEARY_WORD));
        }
    }

    @Override
    public void onClick(View view, int viewId) {
        switch (viewId) {
            case R.id.textView_uk_phonetic:
                this.mediaPlay(ukSpeechUrl);
                break;
            case R.id.textView_us_phonetic:
                this.mediaPlay(usSpeechUrl);
                break;
            case R.id.textView_world_phonetic:
                this.mediaPlay(worldSpeechUrl);
                break;
        }
    }

    @Override
    public void handleActivityMessage(Message message) {
        switch (message.what) {
            case BUSINESS_CODE_QUEARY_WORD:
                YoudaoQueryResult youdaoQueryResult = (YoudaoQueryResult) message.getData().getSerializable("youdaoQueryResult");
                if (youdaoQueryResult.getBasic() != null) {
                    textViewUkPhonetic.setText(youdaoQueryResult.getBasic().getUkPhonetic());
                    textViewUsPhonetic.setText(youdaoQueryResult.getBasic().getUsPhonetic());

                    ukSpeechUrl = youdaoQueryResult.getBasic().getUkSpeech();
                    usSpeechUrl = youdaoQueryResult.getBasic().getUsSpeech();
                    worldSpeechUrl = youdaoQueryResult.getSpeakurl();

                    youdaoQueryResult.getBasic().setUkPhonetic(null);
                    youdaoQueryResult.getBasic().setUsPhonetic(null);
                    youdaoQueryResult.getBasic().setUkSpeech(null);
                    youdaoQueryResult.getBasic().setUsSpeech(null);
                    youdaoQueryResult.setTspeakurl(null);
                    youdaoQueryResult.setSpeakurl(null);
                }
                String text = JSON.toJSONString(youdaoQueryResult, true);
                textViewWordDetail.setText(text);
                break;
        }
    }

    //*************************
    private void mediaPlay(String url) {
        new Thread() {
            @Override
            public void run() {
                if (!mediaPlayer.isPlaying()) {
                    timedCloseTextView.post(() -> {
                        timedCloseTextView.setPrimaryMessage("preparing");
                        timedCloseTextView.visible();
                    });

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            timedCloseTextView.post(() -> {
                                timedCloseTextView.setNiceMessage("Ready");
                                timedCloseTextView.closeDelayed(1000);
                            });
                        }
                    });
                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            timedCloseTextView.setErrorMessage("...");
                            timedCloseTextView.closeDelayed(1000);
                            return false;
                        }
                    });
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
