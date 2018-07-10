package com.globeandi.toeat.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ImageUtil {

    private ImageUtil() {
    }

    public static void setRegistrationImage(Context context, String imagePath, ImageView imgView) {
        Glide.with(context).load(imagePath).bitmapTransform(new RoundedCornersTransformation(context, 40, 10))
                .override(140, 120)
                .centerCrop()
                .into(imgView);
    }

    /*
    Save image and return the new URI of the image
     */
    public static String imageToInternalStorage(Bitmap bitmap, Context context) {

        //get App context
        ContextWrapper contextWrapper = new ContextWrapper(context.getApplicationContext());
        //path to /data/data/to eat/app_data/imageDir
        File directory = contextWrapper.getDir(AppConstants.INTERNAL_IMAGE_DIRECTORY, Context.MODE_PRIVATE);
        //Create the image in the directory
        File profilePicturePath = new File(directory, "profilePicture.jpg");

        //write bitmap to this path
        try (OutputStream out = new FileOutputStream(profilePicturePath)) {
            //write image to the path specified by the output stream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap getScaledBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        return BitmapFactory.decodeFile(path, options);

    }


}
