package com.gecici.gecickayit.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gecici.gecickayit.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class HelpFragment extends Fragment {
    private TextView bilgiText;
    private Button destekButton,rulesButton;
    private View root;
    private AdView adView,mAdView;
    private AdRequest adRequest;
    private ImageView rules;
    private Animation infoAnim,destekAnim;
    private Dialog dialog;
    private Window window;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_help, container, false);
        adRequest = new AdRequest.Builder().build();
        destekButton = root.findViewById(R.id.destekbutton);
        mAdView = root.findViewById(R.id.adView);
        rules = root.findViewById(R.id.rules);
        infoAnim = AnimationUtils.loadAnimation(getActivity(),R.anim.info_animation);
        destekAnim = AnimationUtils.loadAnimation(getActivity(),R.anim.destek_translate_button);
        rules.setAnimation(infoAnim);
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = new AdView(getActivity());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-4626303575477690/9547998987");

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

        destekButton.setAnimation(destekAnim);
        destekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerdDialog = new AlertDialog.Builder(root.getContext());
                alerdDialog.setMessage("Telefonunuzda telegram varmı?\n Yoksa indin ve Tekrar deneyin");
                alerdDialog.setNegativeButton("TELEGRAM YOK",null);
                alerdDialog.setPositiveButton("DEVAM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/MiFix_00"));
                        startActivity(intent);
                    }
                });
                alerdDialog.show();



            }
        });
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity(),android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);
                dialog.setContentView(R.layout.rules_dialog);
                dialog.show();
                rulesButton = dialog.findViewById(R.id.info_button_dialog);
                rulesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return root;
    }
}