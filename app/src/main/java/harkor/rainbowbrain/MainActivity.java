package harkor.rainbowbrain;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9876;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final int RC_LEADERBOARD_UI = 9004;

    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount signedInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGoogleSignInClient = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());
    }

    @OnClick(R.id.button_logout)
    void signOut() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // at this point, the user is signed out.
                    }
                });
    }

    @OnClick(R.id.image_classic)
    void classicClick(){
        Intent intent=new Intent(this,ClassicActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.button_percentage)
    void addPercent(){
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .increment(getString(R.string.achievement_100), 1);
    }
    @OnClick(R.id.image_seconds)
    void imageSeconds(){
        if(isSignedIn()){
            String name=signedInAccount.getDisplayName();
            Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"FALSE",Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.button_login)
    void onSignInButtonClicked() {
        startSignInIntent();
    }
    @OnClick(R.id.button_achevements)
     void showAchievements() {
        if (isSignedIn()){
            Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .getAchievementsIntent()
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                        }
                    });
        }
    }
    @OnClick(R.id.button_firstgame)
    void firstGame(){
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .unlock(getString(R.string.achievement_first_game));
    }
    @OnClick(R.id.button_addresult)
    void addresult(){
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .submitScore(getString(R.string.leaderboard_classic_mode), 1);
    }
    @OnClick(R.id.button_clasictable)
    void showLeaderboard() {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getLeaderboardIntent(getString(R.string.leaderboard_classic_mode))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });
    }

    private void startSignInIntent() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
        if(isSignedIn()){
            GamesClient gamesClient = Games.getGamesClient(MainActivity.this, GoogleSignIn.getLastSignedInAccount(getApplicationContext()));
            gamesClient.setViewForPopups(findViewById(R.id.gps_popup));
        }
    }
    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }
    private void signInSilently() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            // The signed in account is stored in the task's result.
                             signedInAccount = task.getResult();
                            GamesClient gamesClient = Games.getGamesClient(MainActivity.this, GoogleSignIn.getLastSignedInAccount(getApplicationContext()));
                            gamesClient.setViewForPopups(findViewById(R.id.gps_popup));
                        } else {
                            // Player will need to sign-in explicitly using via UI
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                signedInAccount = result.getSignInAccount();
                GamesClient gamesClient = Games.getGamesClient(MainActivity.this, GoogleSignIn.getLastSignedInAccount(getApplicationContext()));
                gamesClient.setViewForPopups(findViewById(R.id.gps_popup));
            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        signInSilently();
    }
}
