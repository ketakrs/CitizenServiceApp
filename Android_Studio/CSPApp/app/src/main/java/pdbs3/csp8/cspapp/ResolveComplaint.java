package pdbs3.csp8.cspapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResolveComplaint extends AppCompatActivity {
    private RecyclerView mreslovelist;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_complaint);
        mreslovelist= findViewById(R.id.Resolve_list);
        mreslovelist.setHasFixedSize(true);
        mreslovelist.setLayoutManager(new LinearLayoutManager(this));
        mdatabase= FirebaseDatabase.getInstance().getReference().child("Resolve_Complaints");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<UserComplints,resolveviewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserComplints,resolveviewholder>(
                UserComplints.class,
                R.layout.navigation_main_layout,
                resolveviewholder.class,
                mdatabase

        ) {
            @Override
            protected void populateViewHolder(resolveviewholder viewHolder, UserComplints model, int position) {
                final String mpost_key =getRef(position).getKey();

                viewHolder.setImageName(model.getImageName());

                viewHolder.setStrDate(model.getStrDate());
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




                        Intent SingleComplaintIntent =new Intent(ResolveComplaint.this,ComplaintDetails.class);
                        SingleComplaintIntent.putExtra("Resolve_id",mpost_key);
                        startActivity(SingleComplaintIntent);
                        finish();

                    }
                });

            }
        };
        mreslovelist.setAdapter(firebaseRecyclerAdapter);

    }

    public static class resolveviewholder extends RecyclerView.ViewHolder{
            View mview;
        public resolveviewholder(View itemView) {
            super(itemView);
            mview=itemView;
        }

        public void setImageName(String imageName){


            TextView post_name= mview.findViewById(R.id.user_post_name);
            post_name.setText(imageName);
        }


        public void setStrDate(String strDate){
            TextView post_date= mview.findViewById(R.id.user_post_date);
            post_date.setText(strDate);
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
