package com.team36.client_frontend;
// David Parrish - 201232252

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

class OpenFriendJourneyFragment {

    OpenFriendJourneyFragment(@Nullable FriendFragment friendFragment, @Nullable JourneyFragment journeyFragment, FragmentManager fragmentManager, String dateName, Float ratingOverall, String ratingAcceleration, String ratingBraking, String ratingSpeed, String ratingTime){
        Bundle arguments = new Bundle(); // For passing data across to the 'journeyFragment' fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adds values to the 'arguments' bundle so that the data stored in it can be used
        arguments.putString("dateName", dateName);
        arguments.putFloat("ratingOverall", ratingOverall);
        arguments.putString("ratingAcceleration", ratingAcceleration);
        arguments.putString("ratingBraking", ratingBraking);
        arguments.putString("ratingSpeed", ratingSpeed);
        arguments.putString("ratingTime", ratingTime);

        if (friendFragment != null){
            friendFragment.setArguments(arguments);
            // Assigns values to the fragment Transaction
            fragmentTransaction.replace(R.id.fragment_layout, friendFragment);
        }else if (journeyFragment != null){
            journeyFragment.setArguments(arguments);
            // Assigns values to the fragment Transaction
            fragmentTransaction.replace(R.id.fragment_layout, journeyFragment);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

}
