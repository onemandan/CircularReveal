package uk.co.onemandan.circularreveal.Helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import uk.co.onemandan.circularreveal.R;

public class AnimUtils {
    public interface AnimationListener {
        void onAnimationEnd();
    }

    public static void animFadeOut(final View view, int duration){
        view.animate().alpha(0).setDuration(duration).withEndAction(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        });
    }

    public static void animFadeIn(final View view, int duration){
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(1).setDuration(duration);
    }

    public static void animScaleUp(Context context, View view){
        Animation scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        view.startAnimation(scaleUp);
    }

    public static void animCircularReveal(View rootLayout, View overlay, int x, int y){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            int duration = rootLayout.getContext().getResources().getInteger(android.R.integer.config_longAnimTime);

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(duration);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();

            animFadeOut(overlay, duration);
        }
    }

    public static void animCircularUnreveal(final View rootLayout, View overlay, int x, int y, final AnimationListener animationListener){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            int duration = rootLayout.getContext().getResources().getInteger(android.R.integer.config_longAnimTime);

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, finalRadius, 0);
            circularReveal.setDuration(duration);
            circularReveal.setInterpolator(new DecelerateInterpolator());
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.GONE);
                    animationListener.onAnimationEnd();
                }
            });
            circularReveal.start();

            animFadeIn(overlay, duration);
        }
    }
}
