package com.rheredias.colormatch.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.rheredias.colormatch.R;
import com.rheredias.colormatch.databinding.ActivityMainBinding;
import com.rheredias.colormatch.util.ColorName;
import com.rheredias.colormatch.util.DialogUtil;
import com.rheredias.colormatch.util.Result;

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
    private ActivityMainBinding binding;
    //para la cámara: https://androidhiro.com/source/android/example/fotoapparat/5211
    private Fotoapparat fotoapparat;

    //AlertDialog se define como la pequeña ventana que muestra un mensaje en
    //particular al usuario cuando el usuario realiza o comete determinada acción
    private AlertDialog loadingAlertDialog;

    //lanza los permisos para acceder a la cámara
    private final ActivityResultLauncher<String> camPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted)
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                        requestCameraPermissionRationale();
                    else
                        requestCameraPermissionToContinueDialog();
                else
                    binding.buttonShowCamera.performClick();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //se llama al dialogo para aceptar/denegar acceso a la cámara
        loadingAlertDialog = DialogUtil.createLoadingDialog(this);
        setContentView(binding.getRoot());
        setupButtonsOnClickListener();
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
    protected void onPause () {
        super.onPause();
        //si es nullo el objeto para el uso de la cámara, se para
        if (fotoapparat != null) fotoapparat.stop();
        loadingAlertDialog.cancel();
    }
    //método para chequear los permisos de la cámara
    private void setupButtonsOnClickListener() {
        binding.buttonShowCamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                if (fotoapparat == null)
                    startFotoapparat();

                //se visibiliza la cámara
                v.setVisibility(View.GONE);
                try {
                    Thread.sleep(1000); // Pausa de 3 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                binding.focusBox.setVisibility(View.VISIBLE);
                binding.buttonTakePicture.setVisibility(View.VISIBLE);
                binding.cameraView.setVisibility(View.VISIBLE);
                binding.etColorName.setVisibility(View.GONE);
                binding.etComplementaryColorName.setVisibility(View.GONE);

            } else
                requestCameraPermissionRationale();
        });

        //botón evento tomar foto
        binding.buttonTakePicture.setOnClickListener(v -> {
            //diálogo de espera de la cámara
            loadingAlertDialog.show();
            takePictureFotoapparat();

            //quitar botón visi
            v.setVisibility(View.GONE);
            //muestra botón motrar camara
            binding.buttonShowCamera.setVisibility(View.VISIBLE);
            binding.etColorName.setVisibility(View.VISIBLE);
            binding.etComplementaryColorName.setVisibility(View.VISIBLE);
            //quita el elemento de la camara
            binding.cameraView.setVisibility(View.GONE);
            binding.focusBox.setVisibility(View.GONE);
        });
    }

    private void requestCameraPermissionRationale() {
        DialogUtil.showNotCancelableDialog(
                this,
                (dialog, which) -> camPermissionLauncher.launch(Manifest.permission.CAMERA),
                getString(R.string.dialog_ask_camera_permission)
        );
    }

    //permisos para la cámara
    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                camPermissionLauncher.launch(Manifest.permission.CAMERA);

            } else requestCameraPermissionToContinueDialog();
        }
    }

    //write external storage
    // Fotoapparat constructor
    private Fotoapparat createFotoapparat() {
        return Fotoapparat
                .with(this)
                .into(binding.cameraView)
                .focusMode(SelectorsKt.firstAvailable(  // (optional) use the first focus mode which is supported by device
                        FocusModeSelectorsKt.continuousFocusPicture(),
                        FocusModeSelectorsKt.autoFocus(),        // in case if continuous focus is not available on device, auto focus will be used
                        FocusModeSelectorsKt.fixed()             // if even auto focus is not available - fixed focus mode will be used
                ))
                .previewScaleType(ScaleType.CenterCrop)  // we want the preview to fill the view
                .lensPosition(LensPositionSelectorsKt.back())       // we want back camera
                .logger(LoggersKt.loggers(            // (optional) we want to log camera events in 2 places at once
                        LoggersKt.logcat(),           // ... in logcat
                        LoggersKt.fileLogger(this)    // ... and to file
                ))
                .build();



    }
    //se activa la cámara
    private void startFotoapparat() {
        fotoapparat = createFotoapparat();
        fotoapparat.start();
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

    //método que toma la fotografía
    private void takePictureFotoapparat() {
        PhotoResult photoResult = fotoapparat.takePicture();

        //final File file = new File(getExternalFilesDir("photos"), "photo.jpg");
        //photoResult.saveToFile(file);

        //pasa el resultado a Bitmap
        photoResult.toBitmap()
                .whenDone(bitmapPhoto -> {
                    if (bitmapPhoto == null) return;
                    Bitmap bitmap = bitmapPhoto.bitmap;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);
                    setColorAndComplementary(rotatedBitmap);
                });
    }

    private void setColorAndComplementary(final Bitmap bitmap) {
        binding.iwResult.setImageBitmap(bitmap);
        //coge el pixel central de la fotografía
        int pixel = bitmap.getPixel(bitmap.getWidth()/2, bitmap.getHeight()/2);
        ColorName colorName = new ColorName();
        Result color = colorName.getColor(pixel);
        String colorComplementary = colorName.getComplementaryColor(color.colorRGB);


        binding.etColorName.setText(getString(R.string.tw_result_format, color.colorName));
        binding.etComplementaryColorName.setText(getString(R.string.tw_result_format_complementary,
                colorComplementary));

        loadingAlertDialog.cancel();
    }
}






























