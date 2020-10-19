package com.gc.nfc.utils;

import android.view.animation.LinearInterpolator;

public class JellyInterpolator extends LinearInterpolator {
    private float factor = 0.15F;

    public float getInterpolation(float paramFloat) {
        return (float)(Math.pow(2.0D, (-10.0F * paramFloat)) * Math.sin((paramFloat - this.factor / 4.0F) * 6.283185307179586D / this.factor) + 1.0D);
    }
}