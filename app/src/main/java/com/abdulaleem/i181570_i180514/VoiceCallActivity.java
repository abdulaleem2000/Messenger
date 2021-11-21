package com.abdulaleem.i181570_i180514;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.abdulaleem.i181570_i180514.databinding.ActivityVoiceCallBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

public class VoiceCallActivity extends AppCompatActivity {
    ActivityVoiceCallBinding binding;
    FirebaseAuth auth;
    SinchClient sinchClient;
    Call call;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoiceCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //String profile = getIntent().getStringExtra("profile");
        //Uri uri = Uri.parse(profile);
        //binding.callerPhoto.setImageURI(uri);
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        User user = (User) getIntent().getSerializableExtra("user");
        System.out.println(user.getUid());



        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(firebaseUser.getUid())
                .applicationKey("2cda8171-bcef-411d-969a-94f7a7349e88")
               
                .environmentHost("clientapi.sinch.com")
                .build();
        sinchClient.setSupportManagedPush(true);
        sinchClient.startListeningOnActiveConnection();

        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener()
        {

        });

        sinchClient.start();
        callUser(user);
        fetchAllUsers();
    }

    private void fetchAllUsers()
    {

    }



    private class SinchCallListener implements CallListener
    {

        @Override
        public void onCallProgressing(Call call) {
            Toast.makeText(getApplicationContext(),"Ringing...",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCallEstablished(Call call) {
            Toast.makeText(getApplicationContext(),"Call Established",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCallEnded(Call endedCall) {
            Toast.makeText(getApplicationContext(),"Call Ended",Toast.LENGTH_LONG).show();
            call = null;
            endedCall.hangup();
        }
    }

    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            //later open dialog for new incoming call
            AlertDialog alertDialog = new AlertDialog.Builder(VoiceCallActivity.this).create();
            alertDialog.setTitle("CALLING");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reject", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();;
                    call.hangup();
                }
            });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Pick", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    call = incomingCall;
                    call.answer();
                    call.addCallListener(new SinchCallListener());
                    Toast.makeText(getApplicationContext(),"Call is Started",Toast.LENGTH_LONG).show();
                }
            });

            alertDialog.show();
        }
    }
    public void callUser(User user){
        if(call == null)
        {
            call = sinchClient.getCallClient().callUser(user.getUid());
            call.addCallListener(new SinchCallListener());

            openCaller();
        }
    }
    public void openCaller(){
        binding.endCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call.hangup();
                Intent intent = new Intent(VoiceCallActivity.this,Home.class);
                startActivity(intent);
            }
        });
    }
}