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
    public ColorSetting getColor(int pixelColor) {

        ColorSetting result = new ColorSetting();
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
        ColorSetting colorComplementaryName = getColor(pixelComplementary);

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

        mColors.put("Rosa", Color.rgb(255,192,203));
        mColors.put("Rosa claro", Color.rgb(255,182,193));
        mColors.put("Rosa ", Color.rgb(255,105,180));
        mColors.put("Rosa", Color.rgb(255,20,147));
        mColors.put("Rosa", Color.rgb(219,112,147));
        mColors.put("Rosa", Color.rgb(199,21,133));
        mColors.put("Gris oscuro", Color.rgb(105,105,105));
        mColors.put("Gris", Color.rgb(128,128,128));
        mColors.put("Gris", Color.rgb(169,169,169));
        mColors.put("Gris", Color.rgb(192,192,192));
        mColors.put("Azul claro", Color.rgb(176,224,232));
        mColors.put("Azul cielo", Color.rgb(173,216,230));
        mColors.put("Azul cielo", Color.rgb(135,206,250));
        mColors.put("Azul cielo", Color.rgb(125,206,235));
        mColors.put("Azul celeste", Color.rgb(0,191,255));
        mColors.put("Azul claro", Color.rgb(176,196,222));
        mColors.put("Azul", Color.rgb(30,144,255));
        mColors.put("Azul cian", Color.rgb(100,149,237));
        mColors.put("Azul", Color.rgb(70,130,180));
        mColors.put("Azul", Color.rgb(95,158,160));
        mColors.put("Azul pizarra", Color.rgb(123,104,238));
        mColors.put("Azul oscuro", Color.rgb(72,61,139));
        mColors.put("Azul", Color.rgb(65,105,225));
        mColors.put("Azul", Color.rgb(0,0,255));
        mColors.put("Azul", Color.rgb(0,0,205));
        mColors.put("Azul", Color.rgb(0,0,139));
        mColors.put("Azul", Color.rgb(0,0,138));
        mColors.put("Azul", Color.rgb(25,25,112));
        mColors.put("Violeta", Color.rgb(138,43,226));
        mColors.put("Índigo", Color.rgb(75,0,130));
        mColors.put("Marrón", Color.rgb(165,42,42));
        mColors.put("Beig", Color.rgb(255,248,220));
        mColors.put("Beig", Color.rgb(255,235,205));
        mColors.put("Beig", Color.rgb(255,222,173));
        mColors.put("Beig", Color.rgb(245,222,179));
        mColors.put("Beig", Color.rgb(222,184,135));
        mColors.put("Beig oscuro", Color.rgb(210,180,140));
        mColors.put("Marrón rosado", Color.rgb(188,143,143));
        mColors.put("Marrón dorado", Color.rgb(218,165,32));
        mColors.put("Marrón", Color.rgb(205,133,63));
        mColors.put("Marrón chocolate", Color.rgb(210,105,30));
        mColors.put("Marrón", Color.rgb(139,69,19));
        mColors.put("Marrón granate", Color.rgb(128,0,0));
        mColors.put("Cian claro", Color.rgb(224,255,255));
        mColors.put("Cian", Color.rgb(0,255,255));
        mColors.put("Agua", Color.rgb(0,255,255));
        mColors.put("Agua marina", Color.rgb(127,255,212));
        mColors.put("Agua marina", Color.rgb(102,205,170));
        mColors.put("Turquesa pálido", Color.rgb(175,238,238));
        mColors.put("Turquesa", Color.rgb(64,224,208));
        mColors.put("Turquesa", Color.rgb(72,209,204));
        mColors.put("Turquesa", Color.rgb(0,206,209));
        mColors.put("Turquesa verde", Color.rgb(32,178,170));
        mColors.put("Turquesa oscuro", Color.rgb(95,158,160));
        mColors.put("Cian oscuro", Color.rgb(0,139,139));
        mColors.put("Turquesa azulado", Color.rgb(0,128,128));
        mColors.put("Amarillo claro", Color.rgb(250,250,210));
        mColors.put("Amarrillo claro", Color.rgb(238,232,170));
        mColors.put("Caqui claro", Color.rgb(240,230,140));
        mColors.put("Amarillo", Color.rgb(218,165,32));
        mColors.put("Naranja", Color.rgb(255,215,0));
        mColors.put("Naranja", Color.rgb(255,165,0));
        mColors.put("Amarillo dorado", Color.rgb(255,223,0));
        mColors.put("Oro metalizado", Color.rgb(212,175,55));
        mColors.put("Oro oscuro", Color.rgb(207,181,59));
        mColors.put("Oro oscuro", Color.rgb(197,179,88));
        mColors.put("Oro pálido", Color.rgb(230,190,138));
        mColors.put("Marrón dorado", Color.rgb(153,101,21));
        mColors.put("Verd césped", Color.rgb(124,252,0));
        mColors.put("Verde césped", Color.rgb(127,255,0));
        mColors.put("Verde lima", Color.rgb(50,205,50));
        mColors.put("Verde", Color.rgb(0,255,0));
        mColors.put("Verde oscuro", Color.rgb(34,139,34));
        mColors.put("Verde oscuro", Color.rgb(0,128,0));
        mColors.put("Verde oscuro", Color.rgb(0,100,0));
        mColors.put("Verde", Color.rgb(173,255,47));
        mColors.put("Verde amarillo", Color.rgb(154,205,50));
        mColors.put("Verde claro", Color.rgb(0,255,127));
        mColors.put("Verde claro", Color.rgb(0,250,154));
        mColors.put("Verde claro", Color.rgb(144,238,144));
        mColors.put("Verde claro", Color.rgb(152,251,152));
        mColors.put("Verde osucro", Color.rgb(143,188,143));
        mColors.put("Verde oscuro", Color.rgb(60,179,113));
        mColors.put("Verde azulado", Color.rgb(32,178,170));
        mColors.put("Verde", Color.rgb(46,139,87));
        mColors.put("Verde aceituna", Color.rgb(128,128,0));
        mColors.put("Verde oliva", Color.rgb(85,107,47));
        mColors.put("Verde oliva", Color.rgb(107,142,35));
        mColors.put("Coral", Color.rgb(255,127,80));
        mColors.put("Naranja tomate", Color.rgb(255,99,71));
        mColors.put("Naranja rojjizo", Color.rgb(255,69,0));
        mColors.put("Oro", Color.rgb(255,215,0));
        mColors.put("Naranja", Color.rgb(255,165,0));
        mColors.put("Naranja oscuro", Color.rgb(255,140,0));
        mColors.put("Lavanda", Color.rgb(230,230,250));
        mColors.put("Lavanda", Color.rgb(216,191,216));
        mColors.put("Ciruela", Color.rgb(221,160,221));
        mColors.put("Violeta", Color.rgb(238,130,238));
        mColors.put("Violeta", Color.rgb(218,112,214));
        mColors.put("Fucsia", Color.rgb(255,0,255));
        mColors.put("Magenta", Color.rgb(255,0,255));
        mColors.put("Violeta", Color.rgb(186,85,211));
        mColors.put("Morado", Color.rgb(147,112,219));
        mColors.put("Violeta azulado", Color.rgb(138,43,226));
        mColors.put("Violeta oscuro", Color.rgb(148,0,211));
        mColors.put("Violeta", Color.rgb(153,50,204));
        mColors.put("Magenta oscuro", Color.rgb(139,0,139));
        mColors.put("Púrpura", Color.rgb(128,0,128));
        mColors.put("Índigo", Color.rgb(75,0,130));
        mColors.put("Salmón claro", Color.rgb(255,160,122));
        mColors.put("Salmón claro", Color.rgb(250,128,114));
        mColors.put("Salmón oscuro", Color.rgb(233,150,122));
        mColors.put("Coral claro", Color.rgb(240,128,128));
        mColors.put("Indirojo", Color.rgb(205,92,92));
        mColors.put("Carmesí", Color.rgb(220,20,60));
        mColors.put("Rojo", Color.rgb(178,34,34));
        mColors.put("Rojo", Color.rgb(255,0,0));
        mColors.put("Rojo oscuro", Color.rgb(139,0,0));
        mColors.put("Granate", Color.rgb(128,0,0));
        mColors.put("Rojo tomate", Color.rgb(255,99,71));
        mColors.put("Rojo naranja", Color.rgb(255,69,0));
        mColors.put("Rojo violeta pálido", Color.rgb(219,112,147));
        mColors.put("Blanco", Color.rgb(255,255,255));
        mColors.put("Blanco", Color.rgb(255,250,250));
        mColors.put("Blanco", Color.rgb(240,255,240));
        mColors.put("Blanco", Color.rgb(245,255,250));
        mColors.put("Blanco", Color.rgb(240,255,255));
        mColors.put("Blanco", Color.rgb(240,248,255));
        mColors.put("Blanco roto", Color.rgb(248,248,255));
        mColors.put("Blanco roto", Color.rgb(245,245,245));
        mColors.put("Blanco roto", Color.rgb(255,245,238));
        mColors.put("Blanco roto", Color.rgb(245,245,220));
        mColors.put("Blanco roto", Color.rgb(253,245,230));
        mColors.put("Blanco roto", Color.rgb(255,250,240));
        mColors.put("Blanco roto", Color.rgb(255,255,240));
        mColors.put("Blanco roto", Color.rgb(250,235,215));
        mColors.put("Blanco roto", Color.rgb(250,240,230));
        mColors.put("Blanco roto", Color.rgb(255,240,245));
        mColors.put("Blanco roto", Color.rgb(255,228,225));
        mColors.put("Blanco roto", Color.rgb(255,222,173));
        mColors.put("Amarrillo claro", Color.rgb(255,255,204));
        mColors.put("Amarillo claro", Color.rgb(255,255,153));
        mColors.put("Amarillo claro", Color.rgb(255,255,102));
        mColors.put("Amarillo claro", Color.rgb(255,255,51));
        mColors.put("Amarillo", Color.rgb(255,255,0));
        mColors.put("Amarillo oscuro", Color.rgb(204,204,0));
        mColors.put("Amarillo oscuro", Color.rgb(153,153,0));
        mColors.put("Amarillo oscuro", Color.rgb(102,102,0));
        mColors.put("Amarllo oscuro", Color.rgb(51,51,0));
        mColors.put("Verde amarillo oscuro", Color.rgb(240,230,140));
        mColors.put("Caqui", Color.rgb(240,230,140));
        mColors.put("Amarillo", Color.rgb(255,255,0));
        mColors.put("Aceituna", Color.rgb(128,128,0));
        mColors.put("Verde amarillo", Color.rgb(173,255,47));
        mColors.put("Amarillo verde", Color.rgb(154,205,50));
        mColors.put("Marfil", Color.rgb(255,255,240));
        mColors.put("Lavanda claro", Color.rgb(230,230,250));
        mColors.put("Magenta", Color.rgb(255,0,255));
        mColors.put("Fucsia", Color.rgb(255,0,255));
        mColors.put("Magenta osucro", Color.rgb(139,0,139));
        mColors.put("Púrpura", Color.rgb(128,0,128));
        mColors.put("Melocotón", Color.rgb(255,218,185));
        mColors.put("Melocotón", Color.rgb(255,228,181));
        mColors.put("Beig rosado", Color.rgb(255,239,213));
        mColors.put("Rosado", Color.rgb(255,192,203));
        mColors.put("Bronce", Color.rgb(210,180,140));
        mColors.put("Bronce", Color.rgb(222,184,135));
        mColors.put("Verde azulado", Color.rgb(0,128,128));
        mColors.put("Verde azulado", Color.rgb(0,139,139));
        mColors.put("Cian", Color.rgb(0,255,255));
        mColors.put("Turquesa", Color.rgb(175,238,238));
        mColors.put("Turquesa", Color.rgb(64,224,208));
        mColors.put("Turquesa", Color.rgb(72,209,204));
        mColors.put("Turquesa", Color.rgb(0,206,209));

        return mColors;
    }
}