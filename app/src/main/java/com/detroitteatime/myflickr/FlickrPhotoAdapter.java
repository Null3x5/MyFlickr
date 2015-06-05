package com.detroitteatime.myflickr;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.ImageView;

/**
 * Created by mark on 5/7/15.
 */
public class FlickrPhotoAdapter extends CursorAdapter {
    private ImageView mImageView;
    public FlickrPhotoAdapter(Context c, Cursor cursor){

        super(c, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String wtf = cursor.getString(cursor.getColumnIndexOrThrow(Contract.PhotoEntry.WTF));
        int j = Integer.parseInt(wtf);
        if(j < 900) {
            mImageView = (ImageView) view.findViewById(R.id.imageView1);
            mImageView.setImageResource(R.drawable.sunny);
        }
        if(j < 600) {
            mImageView = (ImageView) view.findViewById(R.id.imageView1);
            mImageView.setImageResource(R.drawable.rain);
        }
        TextView title = (TextView) view.findViewById(R.id.title_text);
        String description = cursor.getString(cursor.getColumnIndexOrThrow(Contract.PhotoEntry.DESCRIPTION));
        title.setText(description);

        //this is where you want to put the immage at...............................................
    }

}
