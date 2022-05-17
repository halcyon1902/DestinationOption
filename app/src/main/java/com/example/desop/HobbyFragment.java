package com.example.desop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.desop.RecyclerView.FavoriteAdapter;
import com.example.desop.RecyclerView.activityListLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HobbyFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private FavoriteAdapter favoriteAdapter;
    private FirebaseAuth auth;
    private List<DiaDiem> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hobby, container, false);

        list = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("user").child(auth.getUid()).child("Favorites");
        recyclerView = view.findViewById(R.id.rcv_favorite);
        //phan cach giua cac item
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        favoriteAdapter  = new FavoriteAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        recyclerView.setAdapter(favoriteAdapter);
        getListFavorites();

        return view;
    }

    private void getListFavorites() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    DiaDiem diaDiem = child.getValue(DiaDiem.class);
                    list.add(diaDiem);
                }
                favoriteAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Khong the lay du lieu!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}