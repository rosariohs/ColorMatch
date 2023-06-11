package com.rheredias.colormatch;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.rheredias.colormatch.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    private Bitmap dummyBitmap;

    @Before
    public void setup() {
        activityRule = new ActivityTestRule<>(MainActivity.class);
        //obtener la actividad de prueba
        MainActivity activity = activityRule.getActivity();
        dummyBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    }

    @Test
    public void testAttachPicture() {
        // Iniciar la actividad
        ActivityScenario.launch(MainActivity.class);

        // Hacer clic en el botón "Obtener foto"
        onView(withId(R.id.buttonGetPicture)).perform(click());

        // Verificar que la imagen se muestra en la vista
        onView(withId(R.id.iw_result)).check(matches(isDisplayed()));
    }

    @Test
    public void testTakePicture() {
        // Iniciar la actividad
        ActivityScenario.launch(MainActivity.class);

        // Hacer clic en el botón "Mostrar cámara"
        onView(withId(R.id.buttonShowCamera)).perform(click());

        // Verificar que la vista de la cámara está presente en la pantalla
        onView(withId(R.id.camera_view)).check(matches(isDisplayed()));

        // Hacer clic en el botón "Tomar foto"
        onView(withId(R.id.buttonTakePicture)).perform(click());

        // Esperar un segundo para que se capture la imagen
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar que la imagen capturada está presente en la pantalla
        onView(withId(R.id.iw_result)).check(matches(isDisplayed()));

        // Verificar que se muestran el nombre del color, el complementario y la sugerencia
        onView(withId(R.id.color_name)).check(matches(isDisplayed()));
        onView(withId(R.id.color_suggestion)).check(matches(isDisplayed()));
        onView(withId(R.id.complementary_colorName)).check(matches(isDisplayed()));
    }

    private Uri dummyUri() {
        // Retorna un URI de prueba para simular una imagen seleccionada
        return Uri.parse("android.resource://com.rheredias.colormatch/drawable/rojo");
    }

    @Test
    public void testAnalizarFoto() {
        // Crear un intento con el URI de imagen como extra
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        //intent.putExtra("dummyUri", dummyUri());
        // Iniciar la actividad con el intento y cambiar el contexto a activity_camera.xml
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            activity.setContentView(R.layout.activity_camera);
            ImageView imageView = activity.findViewById(R.id.iw_result);
            imageView.setImageURI(dummyUri());

        });
        boolean flagAttachPicture = true;

        // Verificar que el botón "Analizar" está presente en la pantalla
        onView(withId(R.id.buttonAnalyzePicture)).check(matches(isDisplayed()));

        // Hacer clic en el botón "Analizar"
        onView(withId(R.id.buttonAnalyzePicture)).perform(click());

        // Esperar un segundo para que se capture la imagen
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar que la imagen capturada está presente en la pantalla
        onView(withId(R.id.iw_result)).check(matches(isDisplayed()));

        // Verificar que se muestran el nombre del color, el complementario y la sugerencia
        onView(withId(R.id.color_name)).check(matches(isDisplayed()));
        onView(withId(R.id.color_suggestion)).check(matches(isDisplayed()));
        onView(withId(R.id.complementary_colorName)).check(matches(isDisplayed()));

        // Hacer clic en el botón "Atrás"
        onView(withId(R.id.buttonBack)).check(matches(isDisplayed()));
    }

    @Test
    public void testMostrarCamara() {
        // Verificar que el botón "Show Camera" está presente en la pantalla
        onView(withId(R.id.buttonShowCamera)).check(matches(isDisplayed()));

        // Hacer clic en el botón "Show Camera"
        onView(withId(R.id.buttonShowCamera)).perform(click());

        // Verificar que la vista de la cámara está presente en la pantalla
        onView(withId(R.id.camera_view)).check(matches(isDisplayed()));

        // Hacer clic en el botón "Take Picture"
        onView(withId(R.id.buttonTakePicture)).perform(click());

        // Esperar un segundo para que se capture la imagen
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar que la imagen capturada está presente en la pantalla
        onView(withId(R.id.iw_result)).check(matches(isDisplayed()));

        // Verificar que se muestran el nombre del color, el complementario y la sugerencia
        onView(withId(R.id.color_name)).check(matches(isDisplayed()));
        onView(withId(R.id.color_suggestion)).check(matches(isDisplayed()));
        onView(withId(R.id.complementary_colorName)).check(matches(isDisplayed()));

        // Hacer clic en el botón "Atrás"
        onView(withId(R.id.buttonBack)).check(matches(isDisplayed()));
    }

}

