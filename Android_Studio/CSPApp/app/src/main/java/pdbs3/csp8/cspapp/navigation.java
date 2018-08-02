package pdbs3.csp8.cspapp;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import pdbs3.csp8.cspapp.R;
//navigation activity

public class navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {




    private  RecyclerView UserComplaint;
    private DatabaseReference mdatabase;
    private FirebaseUser user;
    private String muid;
    private FirebaseAuth mauth;
    private Query mquery;
    private ImageView post_uimage;

     private TextView post_uname;
     private TextView post_uemail;
     private static final int RESULT_code=100;
    FirebaseAuth.AuthStateListener mAuthListner;









    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);//call activity_navigation.xml to call ui
        mauth=FirebaseAuth.getInstance();//declaring the aauthuntication
        mdatabase= FirebaseDatabase.getInstance().getReference().child("All_Image_Uploads_Database");
        muid=mauth.getCurrentUser().getUid();//pass the current user id to muid
        mquery=mdatabase.orderByChild("uid").equalTo(muid);//checks no of post uploaded by user in firebase database
        //filter the database using query

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserComplaint=(RecyclerView) findViewById(R.id.users_list);//passing Recycler view id to Recycler view
        UserComplaint.setHasFixedSize(true);
        UserComplaint.setLayoutManager(new LinearLayoutManager(this));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(navigation.this,ClassifierActivity.class));

            }
        });



  FirebaseUser user = mauth.getCurrentUser();
        String uname  = user.getDisplayName();
        String uemail = user.getEmail();
        String uphotoUrl = user.getPhotoUrl().toString();
        View navHeaderView =navigationView.getHeaderView(0);
       post_uimage=navHeaderView.findViewById(R.id.User_photo);
         post_uname=navHeaderView.findViewById(R.id.user_name);
        post_uemail=navHeaderView.findViewById(R.id.User_mailid);



        post_uname.setText(uname);
        post_uemail.setText(uemail);
        Picasso.with(navigation.this).load(uphotoUrl).into(post_uimage);




    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<UserComplints, userComplaintviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserComplints, userComplaintviewHolder>(


                UserComplints.class,
                R.layout.navigation_main_layout,
                userComplaintviewHolder.class,
                mquery//passing filter database to display users complaint
        ) {


            @Override
            protected void populateViewHolder(userComplaintviewHolder viewHolder, UserComplints model, int position) {

                final String mpost_key =getRef(position).getKey();//getting post key(post unique id ) on click
                viewHolder.setImageName(model.getImageName());
                viewHolder.setStrDate(model.getStrDate());
                //to open new activity on click
                //by passing current id of post to display other contents of post.
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent SingleComplaintIntent =new Intent(navigation.this,ComplaintDetails.class);
                        SingleComplaintIntent.putExtra("Complaint_id",mpost_key);
                        startActivity(SingleComplaintIntent);


                    }
                });


            }
        };

        UserComplaint.setAdapter(firebaseRecyclerAdapter);//display no of child in post complaint database
    }


    public static class userComplaintviewHolder extends RecyclerView.ViewHolder{
        View mview;
        public userComplaintviewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }

        public void setImageName(String imageName){
            TextView post_name=(TextView)mview.findViewById(R.id.user_post_name);
            post_name.setText(imageName);//set name of post  in ui
        }


        public void setStrDate(String strDate){
            TextView post_date=(TextView)mview.findViewById(R.id.user_post_date);
            post_date.setText(strDate);//set date of post on ui
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



        if (item.getItemId()==R.id.action_add)
        {
            startActivity(new Intent(navigation.this,UploadForm.class));

            //changing activity from navigation to upload post activity
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager=getFragmentManager();



        if (id == R.id.nav_first_layout) {
            startActivity(new Intent(navigation.this,OtherComplaints.class));


            //changing activity from navigation to display other users complaint

        }
        // Handle the camera action
        else if (id == R.id.nav_second_layout) {
           startActivity(new Intent(navigation.this,ResolveComplaint.class));



        }  else if (id==R.id.nav_manage){

            SharedPreferences hshare = getSharedPreferences("HPREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor;

            editor= hshare.edit();
            editor.putInt("HFLAG", 1);
            editor.apply();

            startActivity(new Intent(navigation.this,Tutorial.class));
            finish();

        }


        else if (id == R.id.nav_share) {

            startActivity(new Intent(navigation.this,gpsLocation.class));

        } else if (id == R.id.nav_send) {

            oninviteClick();
        }else if(id==R.id.nav_logout) {


            Logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Logout() {

        mAuthListner =new FirebaseAuth.AuthStateListener() {
          @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
          if (firebaseAuth.getCurrentUser()==null){


        }
        }
        };
        mauth.signOut();
        startActivity(new Intent(navigation.this,MainActivity.class));
        finish();

    }

    private void oninviteClick() {

        Intent intent =new AppInviteInvitation.IntentBuilder("Invitation")
                .setMessage("Hey there, try this it's good.")
                .setDeepLink(Uri.parse("https://drive.google.com/open?id=1Xbb1AFwUFW7m6SE9ULJK2uaYRtTltwEu"))
                .setCallToActionText("Invitation cta")
                .build();
        startActivityForResult(intent,RESULT_code);
//        finish();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_code && resultCode==RESULT_OK) {

            String[] ids=AppInviteInvitation.getInvitationIds(resultCode,data);



        }
    }





}
