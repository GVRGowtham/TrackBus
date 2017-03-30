package com.locatebus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

/**
 * Created by new on 3/30/2017.
 */

public class addBus extends Activity {

    @IgnoreExtraProperties
    public class busDetails{
        public String bus_no;
        public String vehicle_no;
        public String latitude;
        public String longitude;

        public busDetails(){
            //Default Constructor
        }

        public busDetails(String bus, String vehicle, String lat, String lon){
            this.bus_no=bus;
            this.vehicle_no=vehicle;
            this.latitude=lat;
            this.longitude=lon;
        }
        public ArrayList<String> convert(){
            ArrayList<String> obj = new ArrayList<>();
            obj.add(this.bus_no);
            obj.add(this.vehicle_no);
            obj.add(this.latitude);
            obj.add(this.longitude);
            return obj;
        }
    }
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    EditText edit_busno;
    EditText edit_vehno;
    EditText edit_lat;
    EditText edit_lon;
    Button add_bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbus);
        edit_busno = (EditText) findViewById(R.id.busno);
        edit_vehno = (EditText) findViewById(R.id.vehno);
        edit_lat = (EditText) findViewById(R.id.latitude);
        edit_lon = (EditText) findViewById(R.id.longitude);
        add_bus = (Button) findViewById(R.id.btn_add_submit);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        add_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bus_no=edit_busno.getText().toString().trim();
                String veh_no=edit_vehno.getText().toString().trim();
                String latitude=edit_lat.getText().toString().trim();
                String longitude=edit_lon.getText().toString().trim();
                busDetails bus=new busDetails(bus_no,veh_no,latitude,longitude);
                mDatabase.child("Bus").child(bus_no).setValue(bus.convert()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(addBus.this,"Bus added",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(addBus.this,"Operation failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
