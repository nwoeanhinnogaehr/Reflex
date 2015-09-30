package com.nweninge.reflex;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {
    private static final String ARG_DB = "database";

    private RecordDatabase recordDb;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recordDb The record database to store results in.
     * @return A new instance of fragment SingleUserFragment.
     */
    public static StatsFragment newInstance(RecordDatabase recordDb) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DB, recordDb);
        fragment.setArguments(args);
        return fragment;
    }

    public StatsFragment() {
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
    public void onResume() {
        super.onResume();
        ((TextView)getActivity().findViewById(R.id.min_all)).setText("" + recordDb.minLastN(-1));
        ((TextView)getActivity().findViewById(R.id.min_100)).setText("" + recordDb.minLastN(100));
        ((TextView)getActivity().findViewById(R.id.min_10)).setText("" + recordDb.minLastN(10));
        ((TextView)getActivity().findViewById(R.id.max_all)).setText("" + recordDb.maxLastN(-1));
        ((TextView)getActivity().findViewById(R.id.max_100)).setText("" + recordDb.maxLastN(100));
        ((TextView)getActivity().findViewById(R.id.max_10)).setText("" + recordDb.maxLastN(10));
        ((TextView)getActivity().findViewById(R.id.avg_all)).setText("" + recordDb.avgLastN(-1));
        ((TextView)getActivity().findViewById(R.id.avg_100)).setText("" + recordDb.avgLastN(100));
        ((TextView)getActivity().findViewById(R.id.avg_10)).setText("" + recordDb.avgLastN(10));
        ((TextView)getActivity().findViewById(R.id.med_all)).setText("" + recordDb.medLastN(-1));
        ((TextView)getActivity().findViewById(R.id.med_100)).setText("" + recordDb.medLastN(100));
        ((TextView)getActivity().findViewById(R.id.med_10)).setText("" + recordDb.medLastN(10));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }
}
