package com.example.facelandmark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    protected static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            testFaceDetection();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void testFaceDetection() throws IOException {
        FaceDetectorOptions options =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                        .setContourMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
                        .build();

        FaceDetector faceDetector = FaceDetection.getClient(options);

        String assetPath = "emma_watson.jpg";
        AssetManager assetManager = getAssets();
        InputStream is = assetManager.open(assetPath);
        Bitmap bitmap = BitmapFactory.decodeStream(is);

        InputImage image = InputImage.fromBitmap(bitmap, 0);



        faceDetector.process(image).addOnSuccessListener(faces -> {
            Face face = faces.get(0);
            for (FaceLandmark landmark : face.getAllLandmarks()){
                Log.e(TAG, String.format("Landmark Type: %d, position: X=%f, Y=%f",
                        landmark.getLandmarkType(), landmark.getPosition().x, landmark.getPosition().y));
            }
        });
    }
}