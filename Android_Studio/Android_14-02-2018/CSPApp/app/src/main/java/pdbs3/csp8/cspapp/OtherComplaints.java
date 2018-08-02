package pdbs3.csp8.cspapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OtherComplaints extends AppCompatActivity {



        private RecyclerView mComplaint;
        private DatabaseReference mdatabase;
        private boolean mProcesslike=false;
        private DatabaseReference mLikeDatabase;
        private FirebaseAuth mauth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_other_complaints);


            mdatabase= FirebaseDatabase.getInstance().getReference().child("All_Image_Uploads_Database");
            mComplaint= findViewById(R.id.complaint_list);
            mComplaint.setHasFixedSize(true);
            mComplaint.setLayoutManager(new LinearLayoutManager(this));
            mLikeDatabase=FirebaseDatabase.getInstance().getReference().child("Likes");
            mauth=FirebaseAuth.getInstance();
            mLikeDatabase.keepSynced(true);


        }


        @Override
        protected void onStart() {
            super.onStart();
            FirebaseRecyclerAdapter<Complaint,ComplaintViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Complaint, ComplaintViewHolder>(
                    Complaint.class,
                    R.layout.other_complaint_layout,
                    ComplaintViewHolder.class,
                    mdatabase

            ) {

                @Override
                protected void populateViewHolder(ComplaintViewHolder viewHolder, Complaint model, int position) {
                    viewHolder.setImageName(model.getImageName());

                    viewHolder.setUname(model.getUname());
                    final String post_key=getRef(position).getKey();
                    viewHolder.setmLikeBtn(post_key);
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent SingleComplaintIntent =new Intent(OtherComplaints.this,ComplaintDetails.class);
                            SingleComplaintIntent.putExtra("Complaint_id",post_key);
                            startActivity(SingleComplaintIntent);
                            finish();

                        }
                    });


                    viewHolder.mLikeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            mProcesslike=true;




                            mLikeDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (mProcesslike) {

                                        if (dataSnapshot.child(post_key).hasChild(mauth.getCurrentUser().getUid())) {

                                            mLikeDatabase.child(post_key).child(mauth.getCurrentUser().getUid()).removeValue();
                                            mProcesslike = false;


                                        } else {
                                            mLikeDatabase.child(post_key).child(mauth.getCurrentUser().getUid()).setValue("Randomvalue");
                                            mProcesslike = false;

                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });





                        }
                    });
                }
            };
            mComplaint.setAdapter(firebaseRecyclerAdapter);

        }

        public static class ComplaintViewHolder extends RecyclerView.ViewHolder{

            View mView;
            ImageButton mLikeBtn;
            DatabaseReference mLikeDatabase;
            FirebaseAuth mAuth;
            public ComplaintViewHolder(View itemView) {
                super(itemView);

                mView=itemView;
                mLikeBtn= mView.findViewById(R.id.likeBtn);
                mLikeDatabase=FirebaseDatabase.getInstance().getReference().child("Likes");
                mAuth=FirebaseAuth.getInstance();
                mLikeDatabase.keepSynced(true);

            }


            public  void setmLikeBtn (final String post_key){

                mLikeDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())){
                            mLikeBtn.setImageResource(R.mipmap.ic_thumb_up_red_24dp);


                        }
                        else {
                            mLikeBtn.setImageResource(R.mipmap.ic_thumb_up_black_24dp);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            public void setImageName(String imageName){
                TextView post_title= mView.findViewById(R.id.All_users_post_name);
                post_title.setText(imageName);
            }

            public void setUname(String uname){
                TextView post_name=mView.findViewById(R.id.All_users_name);
                post_name.setText(uname);
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


