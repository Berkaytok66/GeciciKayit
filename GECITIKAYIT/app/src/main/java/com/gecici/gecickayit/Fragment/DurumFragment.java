package com.gecici.gecickayit.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gecici.gecickayit.Adapter.DurumAdapter;
import com.gecici.gecickayit.Claslar.imei;
import com.gecici.gecickayit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class DurumFragment extends Fragment {
    private View root;
    private RecyclerView recyclerView;
    private DurumAdapter MyDurumAdapter;
    private FirebaseUser mUser;
    private FirebaseFirestore mFireStor;
    private TextView textView7;
    private ArrayList<imei> imeiDurumList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_durum, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        mFireStor = FirebaseFirestore.getInstance();
        mUser  = FirebaseAuth.getInstance().getCurrentUser();
        imeiDurumList =new ArrayList<imei>();
        textView7=root.findViewById(R.id.textView7);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        MyDurumAdapter = new DurumAdapter(imeiDurumList,root.getContext());
        recyclerView.setAdapter(MyDurumAdapter);


        EventChargeLisiner();
        return root;
    }
    private void EventChargeLisiner() {
        mFireStor.collection("imei").document(mUser.getUid()).collection("İmeiNumaraları")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            if (documentChange.getType() == DocumentChange.Type.ADDED){
                                imeiDurumList.add(documentChange.getDocument().toObject(imei.class));

                                if (imeiDurumList.size() ==0){
                                    textView7.setVisibility(View.VISIBLE);
                                }
                            }
                            MyDurumAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}