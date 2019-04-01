package com.team36.client_frontend;
// David Parrish - 201232252

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FriendFragment extends Fragment {
    private final String WELCOME_MESSAGE = "%s's Dashboard";

    private View returnView;
    private Bundle arguments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        returnView = inflater.inflate(R.layout.fragment_friend, container, false);

        arguments = getArguments();
        ReturnView setView = new ReturnView(returnView, WELCOME_MESSAGE, arguments);

        return setView.returnView;
    }
}
