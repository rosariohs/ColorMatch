package com.rheredias.colormatch.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.rheredias.colormatch.R;
import com.rheredias.colormatch.databinding.ActivityCameraBinding;
import com.rheredias.colormatch.databinding.ActivityMainBinding;
import com.rheredias.colormatch.util.ColorName;
import com.rheredias.colormatch.util.ColorSetting;
import com.rheredias.colormatch.util.DialogUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.log.LoggersKt;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.selector.FocusModeSelectorsKt;
import io.fotoapparat.selector.LensPositionSelectorsKt;
import io.fotoapparat.selector.SelectorsKt;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    //objeto para enlazar variables con XML
    private ActivityCameraBinding bindingCamera;
    private ActivityMainBinding bindingMain;
    private Bitmap bitmapPicture;
    //para la cámara: https://androidhiro.com/source/android/example/fotoapparat/5211
    private Fotoapparat fotoapparat = null;
    private boolean flagAttachPicture;
    //AlertDialog se define como la pequeña ventana que muestra un mensaje en
    //particular al usuario cuando el usuario realiza o comete determinada acción
    private AlertDialog loadingAlertDialog;
    private static final int PICK_PHOTO_REQUEST = 1;

    //variable para manejar el resultado de la solicitud de los permisos para acceder a la cámara
    private final ActivityResultLauncher<String> camPermissionsLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted)
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        requestCameraPermissionRationale();
                    } else {
                        requestCameraPermissionToContinueDialog();
                    }
                else
                    bindingMain.buttonShowCamera.performClick();
            });

    private final ActivityResultLauncher<String> pickPicture = registerForActivityResult
    (
            new ActivityResultContracts.GetContent(), uri ->
            {
                if (uri != null)
                {
                    bitmapPicture = getBitmapFromUri(uri);
                    bindingCamera.iwResult.setImageURI(uri);
                    flagAttachPicture = true;
                }else
                    Toast.makeText(getApplicationContext(),"No se seleccionó ninguna foto", Toast.LENGTH_SHORT).show();
                    flagAttachPicture = false;
            }
    );


    //primero
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingMain = ActivityMainBinding.inflate(getLayoutInflater());
        bindingCamera = ActivityCameraBinding.inflate(getLayoutInflater());
        //bindingCamera = ActivityCameraBinding.inflate(getLayoutInflater());
        //se llama al dialogo para aceptar/denegar acceso a la cámara
        loadingAlertDialog = DialogUtil.createLoadingDialog(this);
        setContentView(bindingMain.getRoot());
        bindingMain.buttonShowCamera.setOnClickListener( v -> showCamera() );
        bindingCamera.buttonTakePicture.setOnClickListener( v -> takePicture() );
        bindingMain.buttonGetPicture.setOnClickListener( v -> attachPicture() );
        bindingCamera.buttonBack.setOnClickListener( v -> onBackPressed() );
        bindingCamera.buttonAnalyzePicture.setOnClickListener( v -> analizePicture() );
        //setContentView(bindingCamera.getRoot());

    }

    @Override
    public void onBackPressed(){
        setContentView(bindingMain.getRoot());
        if (fotoapparat != null)
            fotoapparat = null;
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void analizePicture()
    {
        if (flagAttachPicture == false) {
            setContentView(bindingMain.getRoot());
        }else
        {
            setContentView(bindingCamera.getRoot());
            setColorAndComplementary(tratammentBitmap(bitmapPicture));
            bindingCamera.buttonAnalyzePicture.setVisibility(View.GONE);
            bindingCamera.buttonTakePicture.setVisibility(View.GONE);
            bindingCamera.colorName.setVisibility(View.VISIBLE);
            bindingCamera.complementaryColorName.setVisibility(View.VISIBLE);
            bindingCamera.buttonBack.setVisibility(View.VISIBLE);
        }
    }

    private void attachPicture() {
        pickPicture.launch("image/*");
        setContentView(bindingCamera.getRoot());
        bindingCamera.buttonAnalyzePicture.setVisibility(View.VISIBLE);
        bindingCamera.buttonTakePicture.setVisibility(View.GONE);
        bindingCamera.colorName.setVisibility(View.GONE);
        bindingCamera.complementaryColorName.setVisibility(View.GONE);
        bindingCamera.buttonBack.setVisibility(View.VISIBLE);
        loadingAlertDialog.show();
    }

    private void showCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            setContentView(bindingCamera.getRoot());
            if (fotoapparat == null)
                startFotoapparat();
            //setContentView(bindingCamera.getRoot());
            bindingCamera.iwResult.setVisibility(View.GONE);
            bindingCamera.cameraView.setVisibility(View.VISIBLE);
            bindingCamera.buttonTakePicture.setVisibility(View.VISIBLE);
            bindingCamera.buttonAnalyzePicture.setVisibility(View.GONE);
            bindingCamera.colorName.setVisibility(View.GONE);
            bindingCamera.complementaryColorName.setVisibility(View.GONE);
            bindingCamera.focusBox.setVisibility(View.VISIBLE);

        } else{
            requestCameraPermissionRationale();
        }
    }

    private void takePicture()
    {
        //diálogo de espera de la cámara
        loadingAlertDialog.show();
        takePictureFotoapparat();
        //muestra botón motrar camara
        bindingCamera.iwResult.setImageBitmap(bitmapPicture);
        bindingCamera.iwResult.setVisibility(View.VISIBLE);
        bindingCamera.colorName.setVisibility(View.VISIBLE);
        bindingCamera.complementaryColorName.setVisibility(View.VISIBLE);
        bindingCamera.buttonTakePicture.setVisibility(View.GONE);
        bindingCamera.buttonAnalyzePicture.setVisibility(View.GONE);
        bindingCamera.buttonBack.setVisibility(View.VISIBLE);
        //quita el elemento de la camara
        bindingCamera.cameraView.setVisibility(View.GONE);
        bindingCamera.focusBox.setVisibility(View.GONE);
    }

    //tercero
    //se activa la cámara
    private void startFotoapparat() {
        fotoapparat = createFotoapparat();
        fotoapparat.start();
    }

    private void requestCameraPermissionRationale() {
        DialogUtil.showNotCancelableDialog(
                this,
                (dialog, which) -> camPermissionLauncher.launch(Manifest.permission.CAMERA),
                getString(R.string.dialog_ask_camera_permission)
        );
    }

    //si se cancelan los permisos a la cámara
    private void requestCameraPermissionToContinueDialog() {
        DialogUtil.showNotCancelableDialog(
                this,
                (dialog, which) -> {
                    dialog.cancel();
                    navigateToAppSettings();
                },
                getString(R.string.dialog_cant_continue_without_camera_permission)
        );
    }

    //navegación hasta los permisos
    private void navigateToAppSettings() {
        Uri packageUri = Uri.fromParts("package", this.getPackageName(), null);
        Intent appDetailSettings = new Intent();
        appDetailSettings.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        appDetailSettings.setData(packageUri);
        appDetailSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(appDetailSettings);
    }

    //lanza los permisos para acceder a la cámara
    private final ActivityResultLauncher<String> camPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted)
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                        requestCameraPermissionRationale();
                    else
                        requestCameraPermissionToContinueDialog();
                else {
                    bindingMain.buttonShowCamera.performClick();
                    //binding.buttonGetPicture.performClick();
                }
            });

    //tercero
    // Fotoapparat constructor
    private Fotoapparat createFotoapparat() {
        return Fotoapparat
                .with(this)
                .into(bindingCamera.cameraView)
                .focusMode(SelectorsKt.firstAvailable(
                        FocusModeSelectorsKt.continuousFocusPicture(),
                        FocusModeSelectorsKt.autoFocus(),
                        FocusModeSelectorsKt.fixed()
                ))
                .previewScaleType(ScaleType.CenterCrop)
                .lensPosition(LensPositionSelectorsKt.back())
                .logger(LoggersKt.loggers(
                        LoggersKt.logcat(),
                        LoggersKt.fileLogger(this)
                ))
                .build();
    }

    private Bitmap tratammentBitmap(Bitmap bitmap)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return rotatedBitmap;

    }
    //cuarto
    //método que toma la fotografía
    private void takePictureFotoapparat() {
        PhotoResult photoResult = fotoapparat.takePicture();
        //final File file = new File(getExternalFilesDir("photos"), "photo.jpg");
        //photoResult.saveToFile(file);

        //pasa el resultado a Bitmap
        photoResult.toBitmap()
                .whenDone(bitmapPhoto -> {
                    if (bitmapPhoto == null);
                    Bitmap bitmap = bitmapPhoto.bitmap;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);
                    setColorAndComplementary(rotatedBitmap);
                    //se añade la foto a la pantalla
                    bindingCamera.iwResult.setImageBitmap(rotatedBitmap);
                });
    }

    private void setColorAndComplementary(final Bitmap bitmap) {
        bitmapPicture = bitmap;
        //coge el pixel central de la fotografía
        int pixel = bitmap.getPixel(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        ColorName colorName = new ColorName();
        ColorSetting color = colorName.getColor(pixel);
        String colorComplementary = colorName.getComplementaryColor(color.colorRGB);


        bindingCamera.colorName.setText(getString(R.string.result_format, color.colorName));
        bindingCamera.complementaryColorName.setText(getString(R.string.result_format_complementary, colorComplementary));

        loadingAlertDialog.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //si es nullo el objeto para el uso de la cámara, se para
        if (fotoapparat != null) fotoapparat.stop();
        loadingAlertDialog.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fotoapparat != null) fotoapparat.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //si es nullo el objeto para el uso de la cámara, se para
        if (fotoapparat != null) fotoapparat.stop();
        loadingAlertDialog.cancel();
    }
}




























