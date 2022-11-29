package com.gecici.gecickayit.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gecici.gecickayit.Claslar.Kullanici;
import com.gecici.gecickayit.MainActivity;
import com.gecici.gecickayit.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomeFragment extends Fragment {
    private AdView adView,mAdView;
    private AdRequest adRequest;

    private View root;
    private EditText KullaniciAdiEditText,KullaniciGmailEditText,KullaniciPaswordEditText, KullaniciKredi;
    private TextView KullaniciID;
    private FirebaseFirestore mFireStor;
    private DocumentReference mRef;
    private FirebaseUser mUser, firebaseUser;
    private FirebaseAuth firebaseAuth;
    private Kullanici user;
    private Button Logoutbutton;

    private static final String SHARED_PREF_NAME = "MyPref";
    private static final String KEY_CHECKBOX = "CheckBox";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        KullaniciAdiEditText = root.findViewById(R.id.name);
        KullaniciGmailEditText = root.findViewById(R.id.gmail);
        KullaniciPaswordEditText = root.findViewById(R.id.pasword);
        KullaniciKredi = root.findViewById(R.id.kredi);
        KullaniciID = root.findViewById(R.id.kullaniciId);
        Logoutbutton = root.findViewById(R.id.exit_button);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFireStor = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        adRequest = new AdRequest.Builder().build();
        mAdView = root.findViewById(R.id.adView33);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = new AdView(getActivity());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-4626303575477690/4963269600");

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Kullanıcı bir reklamı tıkladığında yürütülecek kod.
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                // Kullanıcı geri dönmek üzereyken çalıştırılacak kod
                // bir reklama dokunduktan sonra uygulamaya.

                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Bir reklam isteği başarısız olduğunda yürütülecek kod.
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLoaded() {
                //Bir reklamın yüklenmesi tamamlandığında yürütülecek kod.
                super.onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                // Bir reklam bir yer paylaşımı açtığında yürütülecek kod
                // ekranı kaplar.
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest);


        mRef = mFireStor.collection("Kullanicilar").document(mUser.getUid());
        mRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null && value.exists()){
                    user = value.toObject(Kullanici.class);
                    if (user !=null){
                        KullaniciID.setText(user.getKullaniciId());
                        KullaniciAdiEditText.setText(user.getKullaniciIsmi());
                        KullaniciGmailEditText.setText(user.getKullaniciEmail());
                        KullaniciPaswordEditText.setText(user.getKullaniciPassword());
                        KullaniciKredi.setText(user.getKrediMiktarı());

                    }
                }

            }
        });
        boolean isChacked = sharedPreferences.getBoolean(KEY_CHECKBOX,true);

        Logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isChacked){
                            firebaseAuth.signOut();
                            firebaseUser = firebaseAuth.getCurrentUser();

                            if (firebaseUser == null){
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }else{

                        }
                    }
                },100);


            }
        });
        return root;
    }
}