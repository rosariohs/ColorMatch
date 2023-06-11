package com.rheredias.colormatch.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.graphics.Color;

import org.junit.Test;

import java.util.HashMap;

public class ColorNameTest extends ColorName{

    @Test
    public void testIdentifyColorPixel() {
        int pixel = Color.rgb(192, 0, 23);

        HashMap<String, Integer> colorHashMap =  this.initColor();
        boolean complementary = false;
        String expectedColorName = "Verde oliva";

        ColorName color = new ColorName();
        String actualColorName = color.identifyColorPixel(pixel, colorHashMap, complementary);
        assertEquals(expectedColorName, actualColorName);
    }

    @Test
    public void testProcessColor() {
        int pixel = -65536;

        this.processColor(pixel);

        ColorName.ColorSetting colorSetting = this.colorSetting;
        this.colorSetting.colorNameComplementary = "Verde oliva";
        assertNotNull(colorSetting.colorName);
    }

    @Test
    public void testProcessComplementaryColor() {
        int pixel = 4194281;

        ColorName.ColorSetting colorSetting = this.colorSetting;
        this.processComplementaryColor(pixel);
        this.colorSetting.colorNameComplementary = "Rojo";
        assertNotNull(colorSetting.colorNameComplementary);
    }

    @Test
    public void testProcessSuggestion() {
        int pixel = 4194281;

        this.processSuggestion(pixel);

        ColorName.ColorSetting colorSetting = this.colorSetting;
        assertNotNull(colorSetting.colorNameSuggestion);
    }
}