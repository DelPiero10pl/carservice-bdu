package com.cars.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.cars.data.model.Photo;
import com.cars.ui.R;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

import java.io.File;

public class PhotoRecycleViewAdapter  extends RealmBasedRecyclerViewAdapter<Photo, PhotoRecycleViewAdapter.ViewHolder> {
    public PhotoRecycleViewAdapter(Context context, RealmResults<Photo> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public PhotoRecycleViewAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.photo_item, viewGroup, false);
        PhotoRecycleViewAdapter.ViewHolder vh = new PhotoRecycleViewAdapter.ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(PhotoRecycleViewAdapter.ViewHolder viewHolder, final int i) {

        // Get the dimensions of the View
        int targetW = 90;
        int targetH = 90;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(realmResults.get(i).getFilename(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(realmResults.get(i).getFilename(), bmOptions);
        viewHolder.photo.setImageBitmap(bitmap);
        viewHolder.photoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(realmResults.get(i).getId());
            }
        });
    }

    public void click(Integer id) {

    }

    public class ViewHolder extends RealmViewHolder {
        public final ImageView photo;
        private final CardView photoCard;
        public ViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            photoCard = (CardView) itemView.findViewById(R.id.photo_card);
        }
    }
}

