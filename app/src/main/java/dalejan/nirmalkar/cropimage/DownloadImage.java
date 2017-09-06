package dalejan.nirmalkar.cropimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
//G1AMc1c0W9aqefhP5B/H65aRfLcA=
/**
 * Created by DALEJAN1 on 23-08-2017.
 */

public class DownloadImage extends AsyncTask<String,Void,Bitmap> {
    ImageView bmimage;
    public DownloadImage(ImageView bmimage){
        this.bmimage=bmimage;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        String userdisplay=strings[0];
        Bitmap micon=null;
        try
        {
            InputStream in=new java.net.URL(userdisplay).openStream();
            micon= BitmapFactory.decodeStream(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        return micon;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        bmimage.setImageBitmap(bitmap);
    }
}
