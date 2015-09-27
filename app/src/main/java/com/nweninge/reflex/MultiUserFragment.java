package com.nweninge.reflex;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MultiUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiUserFragment extends Fragment {
    private static final String ARG_DB = "database";

    private RecordDatabase recordDb;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recordDb The record database to store results in.
     * @return A new instance of fragment SingleUserFragment.
     */
    public static MultiUserFragment newInstance(RecordDatabase recordDb) {
        MultiUserFragment fragment = new MultiUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DB, recordDb);
        fragment.setArguments(args);
        return fragment;
    }

    public MultiUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recordDb = (RecordDatabase)getArguments().getSerializable(ARG_DB);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_user, container, false);
    }
}
