package harkor.rainbowbrain;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;

import org.w3c.dom.Text;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassicActivity extends AppCompatActivity {
    private int col1;
    private int col2;
    private int col3;
    private int col4;
    private int text1;
    private int text2;
    private int text3;
    private int text4;
    private int winColor;
    private int result;
    private int time;
    Random random=new Random();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_classic);
        ButterKnife.bind(this);

        randomize();
        randomColor();
        randomText();
        textResult.setText("0");
        result=0;
        time=5000;
       setTimer(time);
    }
    //@OnClick(R.id.image_color)
    void percentTest(){
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .unlock(getString(R.string.achievement_first_game));
    }
    private void randomize(){
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
    }
    private void setViewColor(ImageView imageView,int color){
        switch (color) {
            case 1:imageView.setImageResource(R.drawable.button_blue);break;
            case 2:imageView.setImageResource(R.drawable.button_green);break;
            case 3:imageView.setImageResource(R.drawable.button_red);break;
            case 4:imageView.setImageResource(R.drawable.button_yellow);break;
        }
    }
    private void randomColor(){
        winColor=random.nextInt(4)+1;
        switch (winColor) {
            case 1: imageColor.setImageResource(R.drawable.stain_blue); break;
            case 2: imageColor.setImageResource(R.drawable.stain_green); break;
            case 3: imageColor.setImageResource(R.drawable.stain_red); break;
            case 4: imageColor.setImageResource(R.drawable.stain_yellow); break;
        }
    }
    private void randomText(){
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
    }
    private void setText(TextView textView,int text){
        switch (text){
            case 1:textView.setText(R.string.blue);break;
            case 2:textView.setText(R.string.green);break;
            case 3:textView.setText(R.string.red);break;
            case 4:textView.setText(R.string.yellow);break;
        }

    }
    private void setTimer(int finishTime){
        progressBar.setMax(finishTime);
        progressBar.setProgress(finishTime);
        CountDownTimer cdTimer=new CountDownTimer(finishTime,100) {
            @Override
            public void onTick(long l) {
                progressBar.setProgress(progressBar.getProgress()-100);
            }
            @Override
            public void onFinish() {
                progressBar.setProgress(0);
                lose();
            }
        };
        cdTimer.start();
    }
    private void lose(){
        //TODO: WHEN LOSE!
    }
    @OnClick(R.id.image_btn1)
    void clickBtn1(){
        clickButton(1);
    }
    @OnClick(R.id.image_btn2)
    void clickBtn2(){
        clickButton(2);
    }
    @OnClick(R.id.image_btn3)
    void clickBtn3(){
        clickButton(3);
    }
    @OnClick(R.id.image_btn4)
    void clickBtn4(){
        clickButton(4);
    }
    private void clickButton(int idButton){

    }
}
