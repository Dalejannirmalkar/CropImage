package dalejan.nirmalkar.cropimage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    static AccessToken accessToken;
    TextView textView;
    Button logout,getphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_user_profile);

        Bundle bundle=getIntent().getExtras();
        String name=bundle.get("name").toString();
        String imageuri=bundle.get("imageurl").toString();
        textView=findViewById(R.id.profilename);
        logout=findViewById(R.id.logout);
        getphoto=findViewById(R.id.getphoto);
        textView.setText(name);
        getphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserProfile.this,PhotoGrid.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        new DownloadImage((ImageView)findViewById(R.id.imageface)).execute(imageuri);

        loadimageurl();

    }
    public void loadimageurl(){
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/me",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {

                        try {
                            JSONObject jsonObject = response.getJSONObject();

                            JSONObject jsonObject1=jsonObject.getJSONObject("photos");
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");

                            for (int i = 0; i < 25; i++) {

                                JSONObject onealbum = jsonArray.getJSONObject(i);
                                PhotoGrid.mURLs.add(onealbum.get("picture").toString());

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "photos{picture}");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
