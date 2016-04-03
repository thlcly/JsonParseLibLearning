package com.aaront.gson.pojo;

import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;

/**
 * @author tonyhui
 * @since 16/4/3
 */
public class SinceUntilSample {
    @Since(4)
    public String since;
    @Until(5)
    public String until;
}
