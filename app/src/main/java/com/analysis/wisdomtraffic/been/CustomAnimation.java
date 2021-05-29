package com.analysis.wisdomtraffic.been;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.chad.library.adapter.base.animation.BaseAnimation;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hejunfeng
 * @Date 15:53 2021/3/15 0015
 * @Description com.analysis.wisdomtraffic.been
 **/
public class CustomAnimation implements BaseAnimation {
    @NotNull
    @Override
    public Animator[] animators(@NotNull View view) {
        Animator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.5f, 1);
        Animator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1);
        Animator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);

        scaleY.setDuration(400);
        scaleX.setDuration(400);
        alpha.setDuration(400);

        scaleY.setInterpolator(new DecelerateInterpolator());
        scaleX.setInterpolator(new DecelerateInterpolator());

        return new Animator[]{scaleY, scaleX, alpha};
    }
}
