package com.rheredias.colormatch.util;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;


public class ColorName {

    private final HashMap<String, Integer> colorHashMap;


    public ColorName() {
        colorHashMap = initColor();
    }

    //aproxima el pixel central a los colores en el hashmap
    public Result getColor(int pixelColor) {

        Result result = new Result();
        // la mayor diferencia es 255 para cada componente de color
        int currentDifference = 3 * 255;

        // nombre del mejor color aproximado, a principio null
        String colorName;
        Integer colorRGB;


        // se obtiene la cantidad de R G B del pixel
        int pixelColorR = Color.red(pixelColor);
        int pixelColorG = Color.green(pixelColor);
        int pixelColorB = Color.blue(pixelColor);

        //se itera por el map
        for (Map.Entry<String, Integer> colorItem : colorHashMap.entrySet()) {
            colorName = colorItem.getKey();
            colorRGB = colorItem.getValue();

            int colorR = Color.red(colorRGB);
            int colorG = Color.green(colorRGB);
            int colorB = Color.blue(colorRGB);

            //se calcula la diferencia
            int difference = Math.abs(pixelColorR - colorR) + Math.abs(pixelColorG - colorG) + Math.abs(pixelColorB - colorB);

            //nos quedamos con el que la diferencia es mejor
            if (currentDifference > difference) {
                currentDifference = difference;
                result.colorName = colorName;
                result.colorRGB = colorRGB;
            }
        }

        //se devuelve el color
        return result;
    }

    //aproxima el pixel central a los colores en el hashmap
    public String getComplementaryColor(int pixelColor) {

        // se obtiene la cantidad de R G B del pixel
        int pixelColorR = Color.red(pixelColor);
        int pixelColorG = Color.green(pixelColor);
        int pixelColorB = Color.blue(pixelColor);

        //R G B del complementario
        int colorRComplementR = 255 - pixelColorR;
        int colorGComplementG = 255 - pixelColorG;
        int colorBComplementB = 255 - pixelColorB;

        //devuelve el pixel complementario
        int pixelComplementary = Color.rgb(colorRComplementR, colorGComplementG, colorBComplementB);

        //se devuelve el color
        Result colorComplementaryName = getColor(pixelComplementary);

        return colorComplementaryName.getColorName();
    }

    public HashMap<String, Integer> initColor() {
        HashMap<String, Integer> mColors = new HashMap<>();
        //amarillo y complementario
        mColors.put("Amarillo", Color.rgb(255,255,0));
        mColors.put("Azul", Color.rgb(0,0,255));

        mColors.put("Amarillo kaki", Color.rgb(101,132,38));
        mColors.put("Azul oscuro", Color.rgb(69,38,132));

        mColors.put("Amarillo claro", Color.rgb(240,230,140));
        mColors.put("Azul claro", Color.rgb(140,149,240));

        mColors.put("Amarillo oro", Color.rgb(255,215,0));
        mColors.put("Azul eléctrico", Color.rgb(0,40,255));

        //verde y complementario
        mColors.put("Verde", Color.rgb(0,255,0));
        mColors.put("Magenta", Color.rgb(255,0, 255));

        mColors.put("Verde oscuro", Color.rgb(0,128,0));
        mColors.put("Magenta oscuro", Color.rgb(128,0,128));

        mColors.put("Verde amarillo", Color.rgb(173,255,47));
        mColors.put("Lila", Color.rgb(129,47,255));

        mColors.put("Verde claro", Color.rgb(144,238,144));
        mColors.put("Magenta claro", Color.rgb(144,238,144));

        mColors.put("Verde oliva", Color.rgb(107,142,35));
        mColors.put("Azul marino oscuro", Color.rgb(70,35,141));

        //azul y complementario
        mColors.put("Azul marino", Color.rgb(25,25,112));
        mColors.put("Verde kaki oscuro", Color.rgb(112,111,24));

        mColors.put("Cian", Color.rgb(0,255,255));
        mColors.put("Rojo", Color.rgb(255,0,0));

        mColors.put("Agua marina", Color.rgb(68,205,170));
        mColors.put("Rojo rosado", Color.rgb(205,67,103));

        mColors.put("Azul cielo", Color.rgb(135,206,249));
        mColors.put("Tierra", Color.rgb(249,178,135));

        //rojo
        mColors.put("Rojo oscuro", Color.rgb(160,23,23));
        mColors.put("Turquesa oscuro", Color.rgb(23,160,160));

        mColors.put("Rojo anaranjado", Color.rgb(255,99,71));
        mColors.put("Turquesa brillante", Color.rgb(70,226,255));


        //rosa/naranja y complementario
        mColors.put("Coral", Color.rgb(255,127,80));
        mColors.put("Turquesa", Color.rgb(80,207,254));

        mColors.put("Naranja", Color.rgb(255,165,0));
        mColors.put("Azul", Color.rgb(0,89,255));

        mColors.put("Rosa palo", Color.rgb(255,192,203));
        mColors.put("Turquesa claro", Color.rgb(192,255,244));

        mColors.put("Rosa", Color.rgb(255,20,147));
        mColors.put("Verde", Color.rgb(20,254,128));

        mColors.put("Fucsia claro", Color.rgb(238,130,238));
        mColors.put("Verde claro", Color.rgb(130,237,130));

        mColors.put("Mostaza", Color.rgb(218,165,32));
        mColors.put("Azul oscuro", Color.rgb(32,84,218));

        //marron  y comlentario
        mColors.put("Marrón", Color.rgb(148,61,21));
        mColors.put("Turquesa oscuro", Color.rgb(148,61,21));

        //otros
        mColors.put("Crema", Color.rgb(245,245,220));
        mColors.put("Azul muy claro", Color.rgb(245,245,220));

        //negro
        mColors.put("Negro", Color.rgb(0,0,0));
        //blanco
        mColors.put("Blanco", Color.rgb(255,255,255));
        //plata
        mColors.put("Gris", Color.rgb(128,128,128));

        return mColors;
    }
}