package com.example.huecoscolombia;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.huecoscolombia.Model.entity.Person;
import com.example.huecoscolombia.Model.entity.Publication;
import com.example.huecoscolombia.app.HuecosColombiaApp;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListAdminFragment extends Fragment {

    private ListAdminAdapter listAdminAdapter;
    private ListView listAdminLv;
    private CheckBox inProcess, toDo, done;

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
        inProcess = view.findViewById(R.id.fr_list_admin_inprocess_cb);
        toDo = view.findViewById(R.id.fr_list_admin_todo_cb);
        done = view.findViewById(R.id.fr_list_admin_done_cb);
        listAdminAdapter.loadPersons(toDo.isChecked(),inProcess.isChecked(),done.isChecked());

        toDo.setOnClickListener(
                view1 -> {
                    inProcess.setChecked(false);
                    done.setChecked(false);
                    listAdminAdapter.loadPersons(toDo.isChecked(),inProcess.isChecked(),done.isChecked());
                    Log.e(">>>>>", "onCreateView: ");
                }
        );
        inProcess.setOnClickListener(
                view1 -> {
                    toDo.setChecked(false);
                    done.setChecked(false);
                    listAdminAdapter.loadPersons(toDo.isChecked(),inProcess.isChecked(),done.isChecked());
                }
        );
        done.setOnClickListener(
                view1 -> {
                    inProcess.setChecked(false);
                    toDo.setChecked(false);
                    listAdminAdapter.loadPersons(toDo.isChecked(),inProcess.isChecked(),done.isChecked());
                }
        );
        listAdminLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person aux = (Person)listAdminAdapter.getItem(i);
                Intent intent = new Intent(HuecosColombiaApp.getContext(),ListPublicationUser.class);
                intent.putExtra("key",aux.getEmail().replace(".","_"));
                if(toDo.isChecked()){
                    intent.putExtra("state", Publication.TO_DO);
                } else if(inProcess.isChecked()){
                    intent.putExtra("state", Publication.IN_PROGRESS);
                } else if(done.isChecked()){
                    intent.putExtra("state", Publication.DONE);
                }
                Log.e("user", "si entra" );
                startActivity(intent);
            }
        });
        return view;
    }

}
