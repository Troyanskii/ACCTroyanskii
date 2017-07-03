package com.troyanskiievgen.acctroyanskii.utils;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Relax on 28.06.2017.
 */

public class Animator {

    public static final float ALPHA_VISIBLE = 1f;
    public static final float ALPHA_INVISIBLE = 0f;

    public void animateVisibility(float alpha, View view, long animationDuration) {
        view.animate()
                .alpha(alpha)
                .setDuration(animationDuration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }
}