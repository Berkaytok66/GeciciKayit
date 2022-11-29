package com.gecici.gecickayit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button GirisYap , HesapOlusturButton;
    public TextInputLayout GmailLayout,SifreLayout;
    public TextInputEditText GmailEditText, SifreEditText;
    public String GmailTutucu, SifreTutucu;
    private LinearLayout linearLayout;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean aBoolean;
    private static final String SHARED_PREF_NAME = "MyPref";
    private static final String KEY_CHECKBOX = "CheckBox";
    private Handler handler = new Handler(Looper.getMainLooper());

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog progressDialog, progressDialog2;
    public void init(){
        GmailLayout = findViewById(R.id.textInputPhoneNumberLayout);
        SifreLayout = findViewById(R.id.textInputLayout);
        GmailEditText = findViewById(R.id.textInputPhoneNumberEditText);
        SifreEditText = findViewById(R.id.textInputEditText);
        GirisYap = findViewById(R.id.giris_yap_button);
        HesapOlusturButton = findViewById(R.id.hesabınız_yokmu_button);
        linearLayout = findViewById(R.id.LinearLayout);
        checkBox = findViewById(R.id.checkBox);

        mAuth= FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Buttonlar();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                aBoolean=b;
                editor.putBoolean(KEY_CHECKBOX,b);
                editor.apply();
            }
        });
        if (aBoolean){
            Intent intent = new Intent(MainActivity.this,UygulamaArayuz.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_out_app,R.anim.slide_in_down);
        }else {
        }
        boolean isChacked = sharedPreferences.getBoolean(KEY_CHECKBOX,false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isChacked){
                   if (mUser != null){
                       Intent intent = new Intent(MainActivity.this,UygulamaArayuz.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                       finish();
                       overridePendingTransition(R.anim.slide_out_app,R.anim.slide_in_down);
                   }else
                       mAuth.signOut();
                }else {
                    mAuth.signOut();
                }
            }
        },1000);





    }
    public void Buttonlar(){
        GirisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        HesapOlusturButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(MainActivity.this,UyeOl.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(x);
                overridePendingTransition(R.anim.slide_out_app,R.anim.slide_in_down);
            }
        });
    }
    public void Login(){
        GmailTutucu = GmailEditText.getText().toString();
        SifreTutucu = SifreEditText.getText().toString();
        if (!TextUtils.isEmpty(GmailTutucu)){
            if (!TextUtils.isEmpty(SifreTutucu)){
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Lütfen Birkaç Saniye Bekleyin...");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(GmailTutucu,SifreTutucu).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Intent intent = new Intent(MainActivity.this,UygulamaArayuz.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_out_app,R.anim.slide_in_down);
                        }else{
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }


                    }
                });
            }else
                SifreLayout.setError("Sifre Boş bırakılamaz");
        }else
            GmailLayout.setError("Gmail Boş Olamaz");
    }
}