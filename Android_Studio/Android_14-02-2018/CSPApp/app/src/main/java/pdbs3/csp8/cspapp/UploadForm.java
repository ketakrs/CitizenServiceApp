package pdbs3.csp8.cspapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadForm extends AppCompatActivity {

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";
    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";
    // Creating button.
    Button ChooseButton, UploadButton;
    // Creating EditText.
    TextView ImageName;
    EditText ImageDesc;
    TextView ImageLat;
    TextView Imagelon;
    TextView ImageCity;
    EditText ImageArea;
    // Creating ImageView.
    ImageView SelectImage;
    // Creating URI.
    Uri FilePathUri;
    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;
    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    private Context activity;
    private GpsTraker gpsTraker;
    private Location location;
    private Bitmap mbitmap=null;
    private String uKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_form);


        uKey=getIntent().getExtras().getString("key");

        // Assign FirebaseStif (file.exists()) {
//                      file.delete();
//                    }orage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        //Assign ID'S to button.
        ChooseButton = (Button) findViewById(R.id.ButtonChooseImage);
        UploadButton = (Button) findViewById(R.id.ButtonUploadImage);

        // Assign ID's to EditText.
        ImageName =  findViewById(R.id.ImageNameEditText);
        ImageLat=(TextView)findViewById(R.id.lat);
        Imagelon=(TextView)findViewById(R.id.lon) ;
        ImageDesc = (EditText) findViewById(R.id.descriptiofield);
        ImageCity = (TextView) findViewById(R.id.Imagecity);
        ImageArea = (EditText) findViewById(R.id.Imagearea);

        // Assign ID'S to image view.
        SelectImage = (ImageView) findViewById(R.id.post_image);

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(UploadForm.this);


//        gpsTraker=new GpsTraker(getApplicationContext());
//        location=gpsTraker.getLocation();


        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("Image");
        float lati = extras.getFloat("Latitude");
        float longi = extras.getFloat("Longitude");
        String lat = "Latitude: "+lati;
        String lon = "Longitude: "+longi;
        String key="yash";
        ImageLat.setText(lat);
        Imagelon.setText(lon);
        String Title = extras.getString("Title");
        ImageName.setText(Title);
        if(uKey!=null&&uKey.equals(key))
        {
            mbitmap= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        }


        if(mbitmap!=null){

            SelectImage.setImageBitmap(mbitmap);
            ChooseButton.setText("Image Selected");
            FilePathUri= getImageUri(getApplicationContext(), mbitmap);
        }





    // Adding click listener to Choose image button.
    ChooseButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

//            ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
//            File dir = getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE);

            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".CSPimg");
            Log.d("Dir path", dir.toString());
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Toast.makeText(UploadForm.this, "Failed to create storage directory.", Toast.LENGTH_SHORT).show();
                }
            }

            File[] files = dir.listFiles();
            if(files.length==0) {
                Toast.makeText(UploadForm.this, "No file in the directory!", Toast.LENGTH_SHORT).show();
        }else {
                File newestFile = files[files.length - 1];
                File imgfile = new File(dir.getAbsolutePath() + File.separator + newestFile.getName());
//            }else{
//                return;
//            }
                Uri uri = Uri.fromFile(imgfile);
//                Intent imgintent = new Intent();
//                imgintent.setData(uri);
////                Intent imgintent = new Intent();
//                imgintent.setDataAndType(uri, "image/jpg");


                FilePathUri = uri;

                try {

                    // Getting selected image into Bitmap.
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    // Setting up bitmap selected image into ImageView.

                    SelectImage.setImageBitmap(bitmap);
                    // After selecting image change choose button above text.
                    ChooseButton.setText("Image Selected");

                }
                catch (IOException e) {

                    e.printStackTrace();
                }


//                startActivityForResult(Intent.createChooser(imgintent, "Please Select Image"), Image_Request_Code);
            }
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
//                    + "/CspApp/");
//            Uri uri1=Uri.parse( Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CspApp/");
//            Log.e("Dir path",uri.toString());
//            Log.e("Dir path",uri1.toString());
//
//            intent.setDataAndType(uri, "image/jpg");
//            startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

        }
    });







        // Adding click listener to Upload image button.
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method to upload selected image on Firebase storage.
                UploadImageFileToFirebaseStorage();

            }
        });



    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//       if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            FilePathUri = data.getData();
//
//            try {
//
//                // Getting selected image into Bitmap.
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
//
//                // Setting up bitmap selected image into ImageView.
//
//                SelectImage.setImageBitmap(bitmap);
//                // After selecting image change choose button above text.
//                ChooseButton.setText("Image Selected");
//
//            }
//            catch (IOException e) {
//
//                e.printStackTrace();
//            }
//        }
//    }



    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.
                            String TempImageName = ImageName.getText().toString().trim();
                            String TempImageDesc = ImageDesc.getText().toString().trim();

                            String TempImagecity =ImageCity.getText().toString().trim();
                            String TempImagearea =ImageArea.getText().toString().trim();
                            String TempLatitude =ImageLat.getText().toString().trim();
                            String TempLongitude =Imagelon.getText().toString().trim();


                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(UploadForm.this,navigation.class));

                            finish();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // Name, email address, and profile photo Url
                            String uname  = user.getDisplayName();
                            String uemail = user.getEmail();
                            String uphotoUrl = user.getPhotoUrl().toString().trim();
                            String uid = user.getUid().toString().trim();
                            @SuppressWarnings("VisibleForTests")

                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();
                            String strDate = dateFormat.format(date).toString();



                            UploadFormInfo imageUploadInfo = new UploadFormInfo(TempImageName,TempImageDesc, taskSnapshot.getDownloadUrl().toString(),TempImagearea,uname,uemail,uphotoUrl,uid,strDate,location,TempLatitude,TempLongitude,TempImagecity);
                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();




                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(UploadForm.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        }
        else {

            Toast.makeText(UploadForm.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }

        return false;
    }

    @Override
    public void onBackPressed() {
       finish();
//        super.onBackPressed();

    }


}
