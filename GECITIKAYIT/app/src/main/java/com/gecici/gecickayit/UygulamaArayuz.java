package com.gecici.gecickayit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.gecici.gecickayit.Fragment.DurumFragment;
import com.gecici.gecickayit.Fragment.HelpFragment;
import com.gecici.gecickayit.Fragment.HomeFragment;
import com.gecici.gecickayit.Fragment.imeiFragment;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class UygulamaArayuz extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    //reklamlar
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;
    public void init(){

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        adRequest = new AdRequest.Builder().build();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uygulama_arayuz);
        init();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new imeiFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.home);
        fragment();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                reklamAds();
            }
        });
       reklamAds();



    }
    public void fragment(){

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.imei:
                        fragment = new imeiFragment();
                        break;

                    case R.id.durum:
                        fragment = new DurumFragment();

                        break;

                    case R.id.help:
                        fragment = new HelpFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
                return true;
            }
        });
    }
    private void reklamAds(){

        InterstitialAd.load(this, "ca-app-pub-4626303575477690/1763101632", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                UygulamaArayuz.this.mInterstitialAd = interstitialAd;
                System.out.println(mInterstitialAd);
                if (mInterstitialAd != null){
                    mInterstitialAd.show(UygulamaArayuz.this);
                }else{
                    System.out.println(mInterstitialAd+" = boş");

                    Toast.makeText(getApplicationContext(),"boş",Toast.LENGTH_SHORT).show();
                }
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Tam ekran içerik kapatıldığında çağrılır.
                        // Referansınızı null olarak ayarladığınızdan emin olun, böylece
                        // ikinci kez göster.
                        UygulamaArayuz.this.mInterstitialAd = null;

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {

                        UygulamaArayuz.this.mInterstitialAd = null;

                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }
                });
            }
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
                Toast.makeText(getApplicationContext(),loadAdError.getMessage(),Toast.LENGTH_SHORT).show();
            }


        });

    }
}