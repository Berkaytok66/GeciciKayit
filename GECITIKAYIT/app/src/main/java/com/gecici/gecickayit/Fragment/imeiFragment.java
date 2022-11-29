package com.gecici.gecickayit.Fragment;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
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
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.gecici.gecickayit.Claslar.Kullanici;
import com.gecici.gecickayit.Claslar.imei;
import com.gecici.gecickayit.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.vungle.mediation.VungleExtrasBuilder;
import com.vungle.mediation.VungleInterstitialAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class imeiFragment extends Fragment{

    private TextInputLayout imeiLayout;
    private TextInputEditText imeiEditText;
    private String imeiTutucu, kanalID,kullaniciEmail,imeiTime;
    private Dialog dialog, dialog2;
    private Window window;
    private Button info_close_button, imei_dialog_succses_button;
    private View root;
    private Button odeMeButton;
    private FirebaseFirestore mFirestor;
    private FirebaseUser mUser;
    private imei imeiClas;
    private Kullanici kullanici;
    private Date zaman;
    List<String> productIdsList = new ArrayList<>();
    private BillingClient billingClient;


    private Animation infoAnim;
    private ImageView infoImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_imei, container, false);

        imeiLayout = root.findViewById(R.id.textInputImeiLayout);
        imeiEditText = root.findViewById(R.id.textInputImeiEditText);
        odeMeButton = root.findViewById(R.id.button);
        infoImage = root.findViewById(R.id.info_id);
        mFirestor = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        kullanici = new Kullanici();
        productIdsList.add("buy_heart1");

        infoAnim = AnimationUtils.loadAnimation(getActivity(),R.anim.info_animation);
        infoImage.setAnimation(infoAnim);

        infoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialogSettings();
            }
        });
        billingClient = BillingClient.newBuilder(getActivity()).enablePendingPurchases().setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null){
                        for(Purchase purchase : list){
                            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()){
                                handlePurchase(purchase);
                            }
                        }
                    }
            }
        }).build();
        connentToGooglePlayBilling();


     /*   odeMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imeiTutucu = imeiEditText.getText().toString();

                AlertDialog.Builder alerdDialog = new AlertDialog.Builder(root.getContext());
                alerdDialog.setTitle("İŞLEME DEVAM ETMEK İSTEDİĞİNİZE EMİNMİSİNİZ");
                alerdDialog.setMessage("İşleme devam etdiğiniz durumda "+imeiTutucu+" Numaralı IMEI 2 ya kullanım süresi eklenecektir DEVAM ETMEK İSTİYORMSUUNUZ");
                alerdDialog.setNegativeButton("HAYIR", null);
                alerdDialog.setPositiveButton("DEVAM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (!TextUtils.isEmpty(imeiTutucu)){
                            if (imeiTutucu.length() ==15){
                               satinAl();
                            }else
                                imeiLayout.setError("İmei Numarası 15 Haneli Olmalıdır");
                        }else
                            imeiLayout.setError("İmei Boş Olamaz");

                    }
                });alerdDialog.show();
            }
        });*/
        return root;
    }
    private void connentToGooglePlayBilling(){//Google Play Faturalandırmaya bağlan
        billingClient.startConnection(
                new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {
                        connentToGooglePlayBilling();
                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                            getProductDetails();
                        }
                    }
                }
        );

    }

    private void getProductDetails(){// ürün detaylarını al

        SkuDetailsParams getProductDetilsQuery = SkuDetailsParams.newBuilder().setSkusList(productIdsList).setType(BillingClient.SkuType.INAPP).build();
        billingClient.querySkuDetailsAsync(getProductDetilsQuery, new SkuDetailsResponseListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null){
                    SkuDetails itemInfo = list.get(0);
                    String s = " SATIN AL";
                    odeMeButton.setText(itemInfo.getPrice()+s);
                    odeMeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            imeiTutucu = imeiEditText.getText().toString();

                            AlertDialog.Builder alerdDialog = new AlertDialog.Builder(root.getContext());
                            alerdDialog.setTitle("İŞLEME DEVAM ETMEK İSTEDİĞİNİZE EMİNMİSİNİZ");
                            alerdDialog.setMessage("İşleme devam etdiğiniz durumda "+imeiTutucu+" Numaralı IMEI 2 ya kullanım süresi eklenecektir DEVAM ETMEK İSTİYORMSUUNUZ");
                            alerdDialog.setNegativeButton("HAYIR", null);
                            alerdDialog.setPositiveButton("DEVAM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (!TextUtils.isEmpty(imeiTutucu)){
                                        if (imeiTutucu.length() ==15){
                                            billingClient.launchBillingFlow(getActivity()
                                                    ,BillingFlowParams.newBuilder().setSkuDetails(itemInfo).build());
                                        }else
                                            imeiLayout.setError("İmei Numarası 15 Haneli Olmalıdır");
                                    }else
                                        imeiLayout.setError("İmei Boş Olamaz");

                                }
                            });alerdDialog.show();

                        }
                    });
                }
            }
        });
    }
    private void handlePurchase(Purchase purchase){

        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                    Log.d(TAG, purchase.getSkus().get(0) + " sku");

                    if (purchase.getSkus().get(0).equals("buy_heart1")) {
                        satinAl();
                    }

                }

            }
        };

        billingClient.consumeAsync(consumeParams, listener);

    }
    public void onResume() {
        super.onResume();


        billingClient.queryPurchasesAsync(
                BillingClient.SkuType.INAPP,
                new PurchasesResponseListener() {
                    @Override
                    public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            for (Purchase purchase : list) {
                                if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                                    handlePurchase(purchase);
                                }
                            }
                        }
                    }
                }
        );


    }




    public void satinAl(){
        imeiTutucu = imeiEditText.getText().toString();
        kanalID = UUID.randomUUID().toString();
        zaman= new Date();
        Date simdikiZaman = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        imeiTime = df.format(simdikiZaman);
        kullaniciEmail = mUser.getEmail();



                imeiClas = new imei(imeiTutucu,imeiTime,kullaniciEmail,"Beklemede",mUser.getUid());
                mFirestor.collection("imei").document(mUser.getUid()).collection("İmeiNumaraları").document(kanalID).set(imeiClas)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                              //  dialog = new Dialog(getActivity(),android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                                dialog2 = new Dialog(getActivity());
                                window = dialog2.getWindow();
                                window.setGravity(Gravity.CENTER);
                                dialog2.setContentView(R.layout.imei_succsesful_dialog);
                                dialog2.show();
                                imei_dialog_succses_button = dialog2.findViewById(R.id.imei_succses_dialog_button);
                                imei_dialog_succses_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog2.dismiss();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




    }
    public void infoDialogSettings(){
        dialog = new Dialog(getActivity(),android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.info_dialog);
        dialog.show();

        info_close_button = dialog.findViewById(R.id.info_button_dialog);
        info_close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


}