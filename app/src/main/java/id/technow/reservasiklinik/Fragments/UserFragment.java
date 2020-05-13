package id.technow.reservasiklinik.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import id.technow.reservasiklinik.LoginActivity;
import id.technow.reservasiklinik.R;
import id.technow.reservasiklinik.Storage.SharedPrefManager;

import static android.app.Activity.RESULT_OK;

public class UserFragment extends Fragment implements View.OnClickListener {
    public UserFragment() {
    }

    ImageView btnImage;
    private static final int REQUEST_CHOOSE_IMAGE = 3;
    File imgValue;
    CircleImageView avatar;
    Button btnLogout;
    LinearLayout btnTermCondition, btnProfile, btnChangePass;


    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_user, container, false);
        // btnImage = fragmentView.findViewById(R.id.btnImage);
        avatar = fragmentView.findViewById(R.id.avatar);
        btnLogout = fragmentView.findViewById(R.id.btnLogout);
        btnTermCondition = fragmentView.findViewById(R.id.btnTermCondition);
        btnProfile = fragmentView.findViewById(R.id.btnProfile);
        btnChangePass = fragmentView.findViewById(R.id.btnChangePass);

        btnProfile.setOnClickListener(this);
        btnChangePass.setOnClickListener(this);
        btnTermCondition.setOnClickListener(this);
        avatar.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        String link = "https://psikologi.ridwan.info/images/";
        //String foto = SharedPrefManager.getInstance(getActivity()).getDetails().getFoto();
        //Picasso.get().load(link + foto).into(avatar);
        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
              /*  EasyImage.openChooserWithGallery(getActivity(), "Choose Picture",
                        REQUEST_CHOOSE_IMAGE);*/
                break;
            case R.id.btnLogout:
                SharedPrefManager.getInstance(getActivity()).clear();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.btnTermCondition:
                /*Intent toc = new Intent(getActivity(), WebViewActivity.class);
                toc.putExtra("BannerLink", "http://k-jur.technow.id/privacy");
                startActivity(toc);*/
                break;
            case R.id.btnProfile:
               /* Intent profile = new Intent(getActivity(), SettingsProfileActivity.class);
                startActivity(profile);*/
                break;
            case R.id.btnChangePass:
                /*Intent pass = new Intent(getActivity(), SettingsChangePassActivity.class);
                startActivity(pass);*/
                break;

        }
    }

    /*   @Override
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
           EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
               @Override
               public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                   CropImage.activity(Uri.fromFile(imageFile))
                           .setGuidelines(CropImageView.Guidelines.ON)
                           .setCropShape(CropImageView.CropShape.OVAL)
                           .setFixAspectRatio(true)
                           .start(getActivity());
               }

               @Override
               public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                   super.onImagePickerError(e, source, type);
                   Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onCanceled(EasyImage.ImageSource source, int type) {
                   super.onCanceled(source, type);
               }
           });

           if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
               CropImage.ActivityResult result = CropImage.getActivityResult(data);
               if (resultCode == RESULT_OK) {
                   Uri resultUri = result.getUri();
                   imgValue = new File(resultUri.getPath());

                   Picasso.get()
                           .load(new File(resultUri.getPath()))
                           //   .error(R.drawable.ic_close)
                           .resize(500, 500)
                           .centerInside()
                           .noFade()
                           .into(avatar);
                   //avatar = 0;
                   //uploadFoto();
               } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                   Exception error = result.getError();

                   Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
               }
           }

       }

   */
    public String imgToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String filePath = imgValue.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        //    bt_uploadFoto.setEnabled(true);
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
}
