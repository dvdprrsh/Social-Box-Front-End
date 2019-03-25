package com.team36.client_frontend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JourneysFragment extends Fragment {
    public final String TITLE = "Your Journeys";

    private View returnView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_journeys, container, false);

        return returnView;
    }
}
