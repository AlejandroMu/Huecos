package com.example.huecoscolombia;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListUsersAdminFragment extends Fragment {

    private ListView listUsersAdminLv;
    private ListUsersAdminAdapter listUsersAdminAdapter;

    public ListUsersAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_users_admin, container, false);
        listUsersAdminLv = view.findViewById(R.id.fr_list_users_admin_users_lv);
        listUsersAdminAdapter = new ListUsersAdminAdapter();
        listUsersAdminLv.setAdapter(listUsersAdminAdapter);
        listUsersAdminAdapter.loadPersons();
        return view;
    }

}
