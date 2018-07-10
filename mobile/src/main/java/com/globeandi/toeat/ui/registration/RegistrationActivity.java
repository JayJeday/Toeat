package com.globeandi.toeat.ui.registration;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.data.models.api.ApiError;
import com.globeandi.toeat.databinding.ActivityRegistrationBinding;
import com.globeandi.toeat.ui.base.BaseActivity;
import com.globeandi.toeat.ui.section.MainActivity;
import com.globeandi.toeat.util.AppConstants;
import com.globeandi.toeat.util.ImageUtil;
import com.globeandi.toeat.util.NetworkUtils;
import com.globeandi.toeat.util.SnackBarFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

/**
 * Created by jay on 3/21/2018.
 */

public class RegistrationActivity extends BaseActivity<ActivityRegistrationBinding, RegistrationViewModel> implements RegistrationNavigation {

    private static final int REQUEST_IMAGE_GET = 1;


    @Inject
    RegistrationViewModel mViewModel;

    @Inject
    Retrofit mRetrofit;

    private ActivityRegistrationBinding mActivityRegistrationBinding;
    private Toolbar mToolbar;

    private static final String TAG = "RegistrationActivity";

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public RegistrationViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public void register() {
        String name = mActivityRegistrationBinding.etName.getText().toString();
        String email = mActivityRegistrationBinding.etEmail.getText().toString();
        String password = mActivityRegistrationBinding.etPassword.getText().toString();

        //return a list of validation failures in the client
        Set<String> validateFailuresFields = mViewModel.registrationFieldsValidation(email, password, name);

        //delay slide animation when visibility is Visible
        ViewGroup transitionsContainer = mActivityRegistrationBinding.clRegistration;
        TransitionManager.beginDelayedTransition(transitionsContainer,new Slide(Gravity.END));
        clearValidationTv();

        //hide groups of views when loading
        Group group = mActivityRegistrationBinding.hideGroup;

        RequestBody emailRequest = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody nameRequest = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody passwordRequest = RequestBody.create(MediaType.parse("text/plain"), password);

        //if is empty all fields were validated proceed with registration
        if (validateFailuresFields.isEmpty()) {
            if(isNetworkConnected()){
                mViewModel.register(nameRequest, emailRequest, passwordRequest,group);
            }else{
                //show toast with retry action
                View.OnClickListener listener = view1 -> register();

                SnackBarFactory.createNetworkRetrySnackBar(this, transitionsContainer, getString(R.string.network_snackbar_message), listener).show();
            }


        } else {
            //display all the validation failure in the UI
            for (String field : validateFailuresFields) {
                switch (field) {
                    case "username":
                        mActivityRegistrationBinding.tvNameValidation.setVisibility(View.VISIBLE);
                        break;
                    case "email":
                        mActivityRegistrationBinding.tvEmailValidation.setVisibility(View.VISIBLE);
                        break;
                    case "password":
                        mActivityRegistrationBinding.tvPasswordValidation.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }

    }


    private void clearValidationTv() {
        mActivityRegistrationBinding.tvEmailValidation.setVisibility(View.GONE);
        mActivityRegistrationBinding.tvNameValidation.setVisibility(View.GONE);
        mActivityRegistrationBinding.tvPasswordValidation.setVisibility(View.GONE);
    }

    @Override
    public void openSectionActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        // destroy activity
        finish();
    }

    @Override
    public void handleError(Throwable throwable) {
        //TODO:: display errors in the UI from the servers
        Log.w(TAG, "error: " + throwable);

        if (throwable instanceof HttpException) {
            ResponseBody response = ((HttpException) throwable).response().errorBody();
            ApiError apiError = NetworkUtils.convertErrors(response, mRetrofit);
            for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
                if (error.getKey().equals("name")) {
                    mActivityRegistrationBinding.tvNameValidation.setText(error.getValue().get(0));
                    mActivityRegistrationBinding.tvNameValidation.setVisibility(View.VISIBLE);
                }
                if (error.getKey().equals("email")) {
                    mActivityRegistrationBinding.tvEmailValidation.setText(error.getValue().get(0));
                    mActivityRegistrationBinding.tvEmailValidation.setVisibility(View.VISIBLE);
                }
                if (error.getKey().equals("password")) {
                    mActivityRegistrationBinding.tvPasswordValidation.setText(error.getValue().get(0));
                    mActivityRegistrationBinding.tvPasswordValidation.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void setProfilePicture() {
        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //App has permission to read files from external storage
            getSelectedImageUri();
        } else {
            //request permission
            requestPermissionsSafely(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.PERM_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void showMessage() {
        Toast.makeText(getApplicationContext(),"function unavailable",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppConstants.PERM_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission granted
                    getSelectedImageUri();
                } else {
                    //permission denied
                    Toast.makeText(this, "Cant access funtionality until permmision is accepted", Toast.LENGTH_SHORT).show();
                }

        }
    }

    /*
        Open Intent to get the picture
         */
    private void getSelectedImageUri() {

        Intent pictureIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pictureIntent.setType("image/*");
        startActivityForResult(pictureIntent, REQUEST_IMAGE_GET);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegistrationBinding = getViewDataBinding();
        mViewModel.setNavigator(this);

        //Todo:: find better solution in phase 3 material design
//        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            //get real uri of bitmap
            String imagePath = getPicturePath(data.getData());
            //get scaled  bitmap
            Bitmap bitmap = ImageUtil.getScaledBitmap(imagePath);
            //set image button with glide
            ImageUtil.setRegistrationImage(this, imagePath, mActivityRegistrationBinding.btnImageProfile);
            String InternalImagePath = ImageUtil.imageToInternalStorage(bitmap, this);
            //save image to shared preference
            mViewModel.setupImageToUpload(InternalImagePath);
        }

    }

    private String getPicturePath(Uri fullPhotoUri) {
        // Read picked image path using content resolver
        String[] filePath = {MediaStore.Images.Media.DATA};
        //TODO::Search for one line solution
        Cursor cursor = getContentResolver().query(fullPhotoUri, filePath, null, null, null);
        cursor.moveToFirst();

        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

        cursor.close();
        return imagePath;
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }
}
