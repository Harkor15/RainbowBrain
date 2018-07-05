package harkor.rainbowbrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;

import java.sql.Time;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeActivity extends AppCompatActivity {

    private int looper=1;
    private int result=0;
    private int winner=0;
    private int col1=0;
    private int col2=0;
    private int col3=0;
    private int col4=0;
    private int text1;
    private int text2;
    private int text3;
    private int text4;
    private long startTime;
    private long timeDifference;
    private Random random=new Random();
    @BindView(R.id.image_btn1)
    ImageView imageBtn1;
    @BindView(R.id.image_btn2)
    ImageView imageBtn2;
    @BindView(R.id.image_btn3)
    ImageView imageBtn3;
    @BindView(R.id.image_btn4)
    ImageView imageBtn4;
    @BindView(R.id.image_color)
    ImageView imageColor;
    @BindView(R.id.text_op1)
    TextView textOp1;
    @BindView(R.id.text_op2)
    TextView textOp2;
    @BindView(R.id.text_op3)
    TextView textOp3;
    @BindView(R.id.text_op4)
    TextView textOp4;
    @BindView(R.id.text_result)
    TextView textResult;
    @BindView(R.id.progress_time)
    ProgressBar progressBar;
    @BindView(R.id.text_game_over)
    TextView textGameOver;
    @BindView(R.id.text_back)
    TextView textBack;
    @BindView(R.id.image_back)
    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.INVISIBLE);
        setAll();
        randomColor();
        imageBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressed(1);
            }
        });
        imageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressed(2);
            }
        });
        imageBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressed(3);
            }
        });
        imageBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressed(4);
            }
        });
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void buttonPressed(int idButton){
        looper++;
        if(isCorrect(idButton)){
            timeDifference=System.currentTimeMillis()-startTime;
            timeDifference=5000-timeDifference;
            if(timeDifference<0){
                timeDifference=0;
            }
            result+=Long.valueOf(timeDifference).intValue();
            textResult.setText(result+"");
            if(looper>10){
                textGameOver.setText(R.string.finish);
                endGame();
                searchForAchievements();
            }else{
                randomColor();
                setAll();
            }
        }else{
            endGame();
           searchForAchievements();
        }
    }
    private void randomColor(){
        winner=random.nextInt(4)+1;
        switch (winner) {
            case 1: imageColor.setImageResource(R.drawable.stain_blue); break;
            case 2: imageColor.setImageResource(R.drawable.stain_green); break;
            case 3: imageColor.setImageResource(R.drawable.stain_red); break;
            case 4: imageColor.setImageResource(R.drawable.stain_yellow); break;
        }
    }
    private void setAll(){
        col1=random.nextInt(4)+1;
        col2=random.nextInt(4)+1;
        if(col1==col2){
            if(col2==4){
                col1=random.nextInt(3)+1;
            }else{
                col2++;
            }
        }
        col3=random.nextInt(4)+1;
        while(col1==col3||col2==col3){
            if(col3==4){
                col3=1;
            }else{
                col3++;
            }
        }
        col4=1;
        while(col1==col4||col2==col4||col3==col4){
            if(col4==4){
                col4=1;
            }else{
                col4++;
            }
        }
        setViewColor(imageBtn1,col1);
        setViewColor(imageBtn2,col2);
        setViewColor(imageBtn3,col3);
        setViewColor(imageBtn4,col4);
        text1=random.nextInt(4)+1;
        text2=random.nextInt(4)+1;
        if(text1==text2){
            if(text2==4){
                text1=random.nextInt(3)+1;
            }else{
                text2++;
            }
        }
        text3=random.nextInt(4)+1;
        while(text1==text3||text2==text3){
            if(text3==4){
                text3=1;
            }else{
                text3++;
            }
        }
        text4=1;
        while(text1==text4||text2==text4||text3==text4){
            if(text4==4){
                text4=1;
            }else{
                text4++;
            }
        }
        setText(textOp1,text1);
        setText(textOp2,text2);
        setText(textOp3,text3);
        setText(textOp4,text4);
        startTime = System.currentTimeMillis();
    }
    private void setText(TextView textView,int text){
        switch (text){
            case 1:textView.setText(R.string.blue);break;
            case 2:textView.setText(R.string.green);break;
            case 3:textView.setText(R.string.red);break;
            case 4:textView.setText(R.string.yellow);break;
        }

    }
    private void setViewColor(ImageView imageView,int color){
        switch (color) {
            case 1:imageView.setImageResource(R.drawable.button_blue);break;
            case 2:imageView.setImageResource(R.drawable.button_green);break;
            case 3:imageView.setImageResource(R.drawable.button_red);break;
            case 4:imageView.setImageResource(R.drawable.button_yellow);break;
        }
    }
    private boolean isCorrect(int idButton){
        int clickValue=0;
        switch (idButton){
            case 1: clickValue=text1;break;
            case 2: clickValue=text2;break;
            case 3: clickValue=text3;break;
            case 4: clickValue=text4;break;
        }
        if(clickValue==winner){
            return true;
        }else{
            return false;
        }
    }
    private void endGame(){
        imageBtn4.setVisibility(View.INVISIBLE);
        imageBtn3.setVisibility(View.INVISIBLE);
        imageBtn2.setVisibility(View.INVISIBLE);
        imageBtn1.setVisibility(View.INVISIBLE);
        textOp1.setVisibility(View.INVISIBLE);
        textOp2.setVisibility(View.INVISIBLE);
        textOp3.setVisibility(View.INVISIBLE);
        textOp4.setVisibility(View.INVISIBLE);
        imageBack.setVisibility(View.VISIBLE);
        textBack.setVisibility(View.VISIBLE);
        textGameOver.setVisibility(View.VISIBLE);
    }
    private void searchForAchievements(){
        try {
            GamesClient gamesClient = Games.getGamesClient(TimeActivity.this, GoogleSignIn.getLastSignedInAccount(getApplicationContext()));
            gamesClient.setViewForPopups(findViewById(R.id.gps_popup2));
            Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .submitScore(getString(R.string.leaderboard_sprint_mode), result);
            Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .increment(getString(R.string.achievement_sprint__10_games), 1);
            Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .increment(getString(R.string.achievement_100_games), 1);
            Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .unlock(getString(R.string.achievement_first_sprint_game));
            if(result>=25000){
                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                        .unlock(getString(R.string.achievement_sprint__25000));
                if(result>30000){
                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                            .unlock(getString(R.string.achievement_sprint__30000));
                    if(result>35000){
                        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                                .unlock(getString(R.string.achievement_sprint__35000));
                        if(result>36000){
                            Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                                    .unlock(getString(R.string.achievement_sprint__36000));
                            if(result>37000){
                                Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                                        .unlock(getString(R.string.achievement_sprint__37000));
                                if(result>38000){
                                    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                                            .unlock(getString(R.string.achievement_sprint__38000));
                                    if(result>39000){
                                        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                                                .unlock(getString(R.string.achievement_sprint__39000));
                                        if(result>40000){
                                            Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                                                    .unlock(getString(R.string.achievement_sprint__40000));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),R.string.login_to_play,Toast.LENGTH_SHORT).show();
        }

    }
}
