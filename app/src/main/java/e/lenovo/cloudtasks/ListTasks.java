package e.lenovo.cloudtasks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ListTasks extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private List<String> DiscrTasks;

    ListView ListUserTasks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);
        ListUserTasks = findViewById(R.id.discr_for_task);

        myRef = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getInstance().getCurrentUser();

        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>(){};
                DiscrTasks = dataSnapshot.child("Tasks").getValue(t);
                updateUI();
                }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUI(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,DiscrTasks);
        ListUserTasks.setAdapter(adapter);

    }
}
