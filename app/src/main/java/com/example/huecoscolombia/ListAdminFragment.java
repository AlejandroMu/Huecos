package com.example.huecoscolombia;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListAdminFragment extends Fragment {

    private ListAdminAdapter listAdminAdapter;
    private ListView listAdminLv;

    public ListAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_admin, container, false);
        listAdminLv = view.findViewById(R.id.fr_list_admin_list_lv);
        listAdminAdapter = new ListAdminAdapter();
        listAdminLv.setAdapter(listAdminAdapter);
        listAdminAdapter.loadPersons();
        return view;
    }

}
