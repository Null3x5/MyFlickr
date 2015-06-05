package com.detroitteatime.myflickr;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.crypto.spec.DESedeKeySpec;

/**
 * Created by mark on 5/1/15.
 */
public class PhotoFragment extends Fragment{

    private ImageView mImageView;
    Cursor cursor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_fragment, container, false);
        mImageView = (ImageView) view.findViewById(R.id.imageView);

        String description = cursor.getString(cursor.getColumnIndexOrThrow(Contract.PhotoEntry.MIN));
        Log.d("TAG", "status " +description);
        TextView title = (TextView) view.findViewById(R.id.textView);
        title.setText(description);

        description = cursor.getString(cursor.getColumnIndexOrThrow(Contract.PhotoEntry.MAX));
        title = (TextView) view.findViewById(R.id.textView2);
        title.setText(description);

        return view;
    }

}
