package pdbs3.csp8.cspapp;


import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ComplaintDetails extends AppCompatActivity {


    private String mpostkey = null;
    private String mResolve = null;
    private DatabaseReference mdatabase;

    private ImageView mCompaintimage;
    private TextView mComplaintname;

    private TextView mComplaintDesc;
    private TextView mComplaintDate;
    private TextView mComplaintState;
    private TextView mComplaintArea;
    private TextView mComplaintCity;
    private Button mfollowup;
    private Button mSingleRemove;
    private FirebaseAuth mauth;
    private DatabaseReference databaseReference;
    private String Database_Path = "Resolve_Complaints";
    private DatabaseReference mresolvedatabse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        mresolvedatabse = FirebaseDatabase.getInstance().getReference().child("Resolve_Complaints");
        mdatabase = FirebaseDatabase.getInstance().getReference().child("All_Image_Uploads_Database");
        mauth = FirebaseAuth.getInstance();

        mpostkey = getIntent().getExtras().getString("Complaint_id");
        mResolve = getIntent().getExtras().getString("Resolve_id");


        mCompaintimage = (ImageView) findViewById(R.id.Single_post_image);

        mComplaintname = (TextView) findViewById(R.id.single_post_title);
        mComplaintDesc = (TextView) findViewById(R.id.single_post_desc);
        mComplaintDate = (TextView) findViewById(R.id.single_post_date);
        mComplaintState = (TextView) findViewById(R.id.single_post_state);
        mComplaintArea = (TextView) findViewById(R.id.single_post_area);
        mComplaintCity = (TextView) findViewById(R.id.single_post_city);
        mSingleRemove = (Button) findViewById(R.id.single_remove);
        mfollowup = (Button) findViewById(R.id.single_followup);
        mSingleRemove.setVisibility(View.INVISIBLE);
        mfollowup.setVisibility(View.INVISIBLE);


        if (mpostkey == null) {

            mresolvedatabse.child(mResolve).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String post_title = (String) dataSnapshot.child("imageName").getValue();
                    String post_desc = (String) dataSnapshot.child("imageDesc").getValue();
                    String post_image = (String) dataSnapshot.child("imageURL").getValue();
                    String post_uid = (String) dataSnapshot.child("uid").getValue();
                    String post_Date = (String) dataSnapshot.child("strDate").getValue();
                    String post_area = (String) dataSnapshot.child("imsgeArea").getValue();
//                    String post_state = (String) dataSnapshot.child("imageState").getValue();
//                    String post_city = (String) dataSnapshot.child("imageCity").getValue();

//        String post_uid=(String) dataSnapshot.child("uid").getValue();
                    mComplaintname.setText(post_title);
                    mComplaintDesc.setText(post_desc);
                    mComplaintDate.setText(post_Date);
//                    mComplaintState.setText(post_state);
//                    mComplaintCity.setText(post_city);
                    mComplaintArea.setText(post_area);
                    Picasso.with(ComplaintDetails.this).load(post_image).into(mCompaintimage);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {

            mdatabase.child(mpostkey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String post_title = (String) dataSnapshot.child("imageName").getValue();
                    String post_desc = (String) dataSnapshot.child("imageDesc").getValue();
                    String post_image = (String) dataSnapshot.child("imageURL").getValue();
                    String post_uid = (String) dataSnapshot.child("uid").getValue();
                    String post_Date = (String) dataSnapshot.child("strDate").getValue();
                    String post_area = (String) dataSnapshot.child("imsgeArea").getValue();
//                    String post_state = (String) dataSnapshot.child("imageState").getValue();
 String post_city = (String) dataSnapshot.child("imageCity").getValue();

//        String post_uid=(String) dataSnapshot.child("uid").getValue();
                    mComplaintname.setText(post_title);
                    mComplaintDesc.setText(post_desc);
                    mComplaintDate.setText(post_Date);
//                    mComplaintState.setText(post_state);
                    mComplaintCity.setText(post_city);
                    mComplaintArea.setText(post_area);
                    Picasso.with(ComplaintDetails.this).load(post_image).into(mCompaintimage);
//        Toast.makeText(ComplaintSingleActivity.this,mauth.getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();

                    if (mauth.getCurrentUser().getUid().equals(post_uid)) {
//            Toast.makeText(ComplaintSingleActivity.this,post_uid,Toast.LENGTH_SHORT).show();
                        mSingleRemove.setVisibility(View.VISIBLE);
                        mfollowup.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        mfollowup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent SingleComplaintIntent = new Intent(ComplaintDetails.this, followup.class);
                SingleComplaintIntent.putExtra("mpostkey", mpostkey);
                startActivity(SingleComplaintIntent);
                finish();

            }
        });

        mSingleRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mdatabase.child(mpostkey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String post_title = (String) dataSnapshot.child("imageName").getValue();
                        String post_desc = (String) dataSnapshot.child("imageDesc").getValue();
                        String post_image = (String) dataSnapshot.child("imageURL").getValue();
                        String post_uid = (String) dataSnapshot.child("uid").getValue();
                        String post_Date = (String) dataSnapshot.child("strDate").getValue();
                        String post_area = (String) dataSnapshot.child("imsgeArea").getValue();
//                        String post_state = (String) dataSnapshot.child("imageState").getValue();
//                        String post_city = (String) dataSnapshot.child("imageCity").getValue();
                        String post_uname = (String) dataSnapshot.child("uname").getValue();
                        String post_uphoto = (String) dataSnapshot.child("uphotoUrl").getValue();
                        String post_uemail = (String) dataSnapshot.child("uemail").getValue();
                        HashMap mhasmap = (HashMap) dataSnapshot.child("location").getValue();
                        Location mlocation = (Location) dataSnapshot.child("mhasmap").getValue();
                       String Latitude =(String)dataSnapshot.child("Latitude").getValue();
                       String Longitude =(String)dataSnapshot.child("Longitude").getValue();
                        String post_city = (String) dataSnapshot.child("imageCity").getValue();


                        UploadFormInfo imageUploadInfo = new UploadFormInfo(post_title, post_desc,  post_image, post_area, post_uname, post_uemail, post_uphoto, post_uid, post_Date, mlocation,Latitude,Longitude,post_city);

                        startActivity(new Intent(ComplaintDetails.this, navigation.class));
                        String ImageUploadId = databaseReference.push().getKey();

                        databaseReference.child(ImageUploadId).setValue(imageUploadInfo);

                        mdatabase.child(mpostkey).removeValue();

                        String mResolve = ImageUploadId;

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
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




