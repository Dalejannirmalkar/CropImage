package dalejan.nirmalkar.cropimage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
LoginButton loginButton;
CallbackManager callbackManager;
ProfileTracker profileTracker;
AccessTokenTracker accessTokenTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        loginButton=findViewById(R.id.login_button);
loginButton.setReadPermissions("user_photos");
        callbackManager=CallbackManager.Factory.create();
        accessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

             UserProfile.accessToken=currentAccessToken;

            }
        };
        profileTracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
nextActivity(currentProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Profile profile=Profile.getCurrentProfile();
                nextActivity(profile);
                UserProfile.accessToken=loginResult.getAccessToken();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Cancle Login",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this,"Errror in Login:"+error,Toast.LENGTH_SHORT).show();
            }
        });
        loginButton.setReadPermissions("user_friends");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Profile profile=Profile.getCurrentProfile();
        nextActivity(profile);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
    private void nextActivity(Profile profile){
        if (profile!=null){
        Intent main=new Intent(MainActivity.this,UserProfile.class);
        main.putExtra("name",profile.getFirstName()+" "+profile.getLastName());
        main.putExtra("imageurl",profile.getProfilePictureUri(200,200).toString());
       startActivity(main);
        }
    }
}
