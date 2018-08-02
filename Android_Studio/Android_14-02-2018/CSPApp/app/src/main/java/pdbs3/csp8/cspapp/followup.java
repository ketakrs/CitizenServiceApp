package pdbs3.csp8.cspapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class followup extends AppCompatActivity {

    private String MpostKey=null;


    String Storage_Path = "All_Image_Uploads/";

    // Root Database Name for Firebase Database.
    String Database_Path = "Followup_images";

    // Creating button.
    Button ChooseButton, UploadButton;





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
private String cKey;
     Bitmap fbitmap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followup);

        MpostKey=getIntent().getExtras().getString("mpostkey");
        cKey=getIntent().getExtras().getString("key");

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("fImage");




        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        //Assign ID'S to button.
        ChooseButton = (Button) findViewById(R.id.ButtonChooseImage);
        UploadButton = (Button) findViewById(R.id.ButtonUploadImage);



        // Assign ID'S to image view.
        SelectImage = (ImageView) findViewById(R.id.post_image);

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(followup.this);

        // Adding click listener to Choose image button.
        ChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                // Creating intent.
//                Intent intent = new Intent();
//
//                // Setting intent type as image to select image from phone storage.
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".CSPapp");
                Log.d("Dir path", dir.toString());
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        Toast.makeText(followup.this, "Failed to create storage directory.", Toast.LENGTH_SHORT).show();
                    }
                }

                File[] files = dir.listFiles();
                if(files.length==0) {
                    Toast.makeText(followup.this, "No file in the directory!", Toast.LENGTH_SHORT).show();
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


        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent il =new Intent(followup.this,ClassifierActivity.class);
                String key="yash";
                il.putExtra("followup_id",key);
                startActivity(il);
                finish();

            }
        });
//            fbitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        if(cKey!=null &&cKey.equals("yash")) {
            fbitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }

        if(fbitmap!=null){

            SelectImage.setImageBitmap(fbitmap);
            ChooseButton.setText("Image Selected");
            FilePathUri= getImageUri(getApplicationContext(), fbitmap);
        }



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
//        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            FilePathUri = data.getData();
//
//            try {
//
//                // Getting selected image into Bitmap.
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
//
//                // Setting up bitmap selected image into ImageView.
//                SelectImage.setImageBitmap(bitmap);
//
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



                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(followup.this,OtherComplaints.class));
                            finish();
                            @SuppressWarnings("VisibleForTests")


                            FollowUpInfo imageUploadInfo = new FollowUpInfo( taskSnapshot.getDownloadUrl().toString(),MpostKey);

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
                            Toast.makeText(followup.this, exception.getMessage(), Toast.LENGTH_LONG).show();
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

            Toast.makeText(followup.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

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
        super.onBackPressed();


    }



}
