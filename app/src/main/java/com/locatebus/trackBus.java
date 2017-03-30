package com.locatebus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.style.SubscriptSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;


/**
 * Created by new on 3/30/2017.
 */

public class trackBus extends Activity {

    public class busDetails{
        public String bus_no;
        public String vehicle_no;
        public String latitude;
        public String longitude;

        public busDetails(){
        }

        public busDetails(String bus, String vehicle, String lat, String lon){
            this.bus_no=bus;
            this.vehicle_no=vehicle;
            this.longitude=lat;
            this.longitude=lon;
        }
    }
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    EditText edit_busno;
    Button submit;
    Button add_bus;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackbus);
        submit = (Button) findViewById(R.id.btn_busno_submit);
        edit_busno = (EditText) findViewById(R.id.bus_no);
        add_bus = (Button) findViewById(R.id.add_bus);
        firebaseAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference();
        pd=new ProgressDialog(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Checking database");
                pd.show();
                final String bus=edit_busno.getText().toString().trim();
                mDatabase.child("Bus").child(bus).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> details = new ArrayList<>();
                        for(DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String temp = childSnapshot.getValue(String.class);
                            details.add(temp);
                        }
                        pd.hide();
                        if(details.isEmpty())
                            Toast.makeText(trackBus.this,"Bus not found",Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(trackBus.this, "Bus found", Toast.LENGTH_SHORT).show();
                            System.out.println(details);
                            //Pass on the values to maps activity... Remember everything is String, covert to Double using Double.parse
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        pd.hide();
                        Toast.makeText(trackBus.this,"Database error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        add_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(trackBus.this,addBus.class);
                startActivity(i);
            }
        });
    }
}
