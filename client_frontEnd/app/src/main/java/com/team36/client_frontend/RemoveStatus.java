package com.team36.client_frontend;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Window;

public class RemoveStatus {
    public Drawable bgGradient;

    RemoveStatus(Activity activity){
        Window window = activity.getWindow();
        bgGradient = activity.getDrawable(R.drawable.gradient);
        window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(bgGradient);
    }

}
