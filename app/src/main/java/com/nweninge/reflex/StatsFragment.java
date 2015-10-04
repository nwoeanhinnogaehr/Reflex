package com.nweninge.reflex;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileOutputStream;


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
        updateData();

        getActivity().findViewById(R.id.clearButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordDb.clear();
                updateData();
                try {
                    FileOutputStream fos = getActivity().openFileOutput(MainActivity.FILENAME, Context.MODE_PRIVATE);
                    recordDb.saveRecords(fos);
                    fos.close();
                } catch (Exception e) {
                }
            }
        });

        getActivity().findViewById(R.id.emailButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "nweninge-reflex statistics");
                StringBuilder sb = new StringBuilder();
                sb.append("Reaction time stats:");
                sb.append("\nMinimum (all): "); sb.append(recordDb.minLastN(-1));
                sb.append("\nMinimum (last 100): "); sb.append(recordDb.minLastN(100));
                sb.append("\nMinimum (last 10): "); sb.append(recordDb.minLastN(10));
                sb.append("\nMaximum (all): "); sb.append(recordDb.maxLastN(-1));
                sb.append("\nMaximum (last 100): "); sb.append(recordDb.maxLastN(100));
                sb.append("\nMaximum (last 10): "); sb.append(recordDb.maxLastN(10));
                sb.append("\nAverage (all): "); sb.append(recordDb.avgLastN(-1));
                sb.append("\nAverage (last 100): "); sb.append(recordDb.avgLastN(100));
                sb.append("\nAverage (last 10): "); sb.append(recordDb.avgLastN(10));
                sb.append("\nMedian (all): "); sb.append(recordDb.medLastN(-1));
                sb.append("\nMedian (last 100): "); sb.append(recordDb.medLastN(100));
                sb.append("\nMedian (last 10): "); sb.append(recordDb.medLastN(10));
                sb.append("\n\nGameshow buzzer stats");
                sb.append("\n2 player mode:");
                    sb.append("\n\tPlayer 1 fastest: "); sb.append(recordDb.getBuzzerPresses(2, 1));
                    sb.append("\n\tPlayer 2 fastest: "); sb.append(recordDb.getBuzzerPresses(2, 2));
                sb.append("\n3 player mode:");
                    sb.append("\n\tPlayer 1 fastest: "); sb.append(recordDb.getBuzzerPresses(3, 1));
                    sb.append("\n\tPlayer 2 fastest: "); sb.append(recordDb.getBuzzerPresses(3, 2));
                    sb.append("\n\tPlayer 3 fastest: "); sb.append(recordDb.getBuzzerPresses(3, 3));
                sb.append("\n4 player mode:");
                    sb.append("\n\tPlayer 1 fastest: "); sb.append(recordDb.getBuzzerPresses(4, 1));
                    sb.append("\n\tPlayer 2 fastest: "); sb.append(recordDb.getBuzzerPresses(4, 2));
                    sb.append("\n\tPlayer 3 fastest: "); sb.append(recordDb.getBuzzerPresses(4, 3));
                    sb.append("\n\tPlayer 4 fastest: "); sb.append(recordDb.getBuzzerPresses(4, 4));
                emailIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                startActivity(Intent.createChooser(emailIntent, "Send email"));
            }
        });
    }

    private void updateData() {
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

        ((TextView)getActivity().findViewById(R.id.players_2_1)).setText("" + recordDb.getBuzzerPresses(2, 1));
        ((TextView)getActivity().findViewById(R.id.players_2_2)).setText("" + recordDb.getBuzzerPresses(2, 2));
        ((TextView)getActivity().findViewById(R.id.players_3_1)).setText("" + recordDb.getBuzzerPresses(3, 1));
        ((TextView)getActivity().findViewById(R.id.players_3_2)).setText("" + recordDb.getBuzzerPresses(3, 2));
        ((TextView)getActivity().findViewById(R.id.players_3_3)).setText("" + recordDb.getBuzzerPresses(3, 3));
        ((TextView)getActivity().findViewById(R.id.players_4_1)).setText("" + recordDb.getBuzzerPresses(4, 1));
        ((TextView)getActivity().findViewById(R.id.players_4_2)).setText("" + recordDb.getBuzzerPresses(4, 2));
        ((TextView)getActivity().findViewById(R.id.players_4_3)).setText("" + recordDb.getBuzzerPresses(4, 3));
        ((TextView)getActivity().findViewById(R.id.players_4_4)).setText("" + recordDb.getBuzzerPresses(4, 4));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }
}
