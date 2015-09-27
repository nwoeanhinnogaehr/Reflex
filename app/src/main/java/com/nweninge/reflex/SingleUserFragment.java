package com.nweninge.reflex;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleUserFragment extends Fragment {
    private static final String ARG_DB = "database";

    private RecordDatabase recordDb;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recordDb The record database to store results in.
     * @return A new instance of fragment SingleUserFragment.
     */
    public static SingleUserFragment newInstance(RecordDatabase recordDb) {
        SingleUserFragment fragment = new SingleUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DB, recordDb);
        fragment.setArguments(args);
        return fragment;
    }

    public SingleUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recordDb = (RecordDatabase)getArguments().getSerializable(ARG_DB);
        }

        new AlertDialog.Builder(getActivity())
                .setTitle("Help")
                .setMessage("Tap when the screen turns green. If you tap too soon, it will turn red.")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_user, container, false);
    }


}
