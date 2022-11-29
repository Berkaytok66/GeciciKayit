package com.gecici.gecickayit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gecici.gecickayit.Claslar.Kullanici;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UyeOl extends AppCompatActivity {
    private Button hesapOlustur, hesabımVar;
    private TextInputLayout KAdiLayout, GmailLayout, SifreLayout, SifreTekrarLayout;
    private TextInputEditText KAdiEditText, GmailEditText, SifreEditText, SifreTekrarEditText;
    private String KAtutucu, GmailTutucu, SifreTutucu, SifreTekrarTutucu;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;
    private Kullanici mKullanici;
    private LinearLayout linearLayout;
    private ProgressDialog progressDialog;
    public void init(){
        KAdiLayout = findViewById(R.id.textInputKullaniciAdiLayout);
        GmailLayout = findViewById(R.id.textInputGmailLayout);
        SifreLayout = findViewById(R.id.textInputSifreLayout);
        SifreTekrarLayout = findViewById(R.id.textInputSifreTekrarLayout);
        KAdiEditText = findViewById(R.id.textInputKullaniciAdiEditText);
        GmailEditText = findViewById(R.id.textInputGmailEditText);
        SifreEditText = findViewById(R.id.textInputSifreEditText);
        SifreTekrarEditText = findViewById(R.id.textInputSifreTekrarEditText);
        hesapOlustur = findViewById(R.id.hesap_olustur_button);
        hesabımVar= findViewById(R.id.hesabım_var_button);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        linearLayout = findViewById(R.id.LinearLayout);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_ol);
        init();
        Buttonlar();


    }
    public void Buttonlar(){
        hesabımVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(UyeOl.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(x);
                overridePendingTransition(R.anim.slide_out_app,R.anim.slide_in_down);
            }
        });
        hesapOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Kayit();
            }
        });
    }

    public void Kayit(){
        KAtutucu = KAdiEditText.getText().toString();
        GmailTutucu = GmailEditText.getText().toString();
        SifreTutucu = SifreEditText.getText().toString();
        SifreTekrarTutucu = SifreTekrarEditText.getText().toString();

        if(!TextUtils.isEmpty(KAtutucu)){
            if(!TextUtils.isEmpty(GmailTutucu)){
                if (!TextUtils.isEmpty(SifreTutucu)){
                    if (!TextUtils.isEmpty(SifreTekrarTutucu)){
                        if (SifreTutucu.equals(SifreTekrarTutucu)){
                            progressDialog = new ProgressDialog(this);
                            progressDialog.setTitle("Lütfen Bekleyin...");
                            progressDialog.show();
                            mAuth.createUserWithEmailAndPassword(GmailTutucu,SifreTutucu).addOnCompleteListener(UyeOl.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        mUser = mAuth.getCurrentUser();
                                        if (mUser != null){
                                            mKullanici = new Kullanici(KAtutucu,GmailTutucu,mUser.getUid(),"NEXT UPDATE",SifreTutucu);
                                            mFirestore.collection("Kullanicilar").document(mUser.getUid()).set(mKullanici)
                                                    .addOnCompleteListener(UyeOl.this, new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Snackbar.make(linearLayout,"Kayıt işlemi başarılı giriş yapabilirsiniz",Snackbar.LENGTH_INDEFINITE)
                                                                        .setAction("Giriş Yap", new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                Intent intent = new Intent(UyeOl.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                                startActivity(intent);
                                                                                finish();
                                                                                overridePendingTransition(R.anim.slide_out_app,R.anim.slide_in_down);
                                                                            }
                                                                        }).show();
                                                                progressDialog.dismiss();
                                                            }else{
                                                                Snackbar.make(linearLayout,task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                                                                progressDialog.dismiss();
                                                            }
                                                        }
                                                    });
                                        }
                                    }else {
                                        Snackbar.make(linearLayout,task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                        }else
                            SifreTekrarLayout.setError("Şifreler Eşleşmiyor");
                    }else
                        SifreTekrarLayout.setError("Lütfen Sifrenizi Tekrar Girin");
                }else
                    SifreLayout.setError("Sifre Boş Olamaz");
            }else
                GmailLayout.setError("Gmail Boş Olamaz");
        }else
            KAdiLayout.setError("Kullanici Adi Boş Olamaz");
    }
}