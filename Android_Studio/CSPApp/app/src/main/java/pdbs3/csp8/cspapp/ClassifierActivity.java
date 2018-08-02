package pdbs3.csp8.cspapp;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Size;
import android.util.TypedValue;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import pdbs3.csp8.cspapp.Classifier.Recognition;
import pdbs3.csp8.cspapp.RecognitionScoreView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import pdbs3.csp8.cspapp.OverlayView.DrawCallback;
import pdbs3.csp8.cspapp.env.BorderedText;
import pdbs3.csp8.cspapp.env.ImageUtils;
import pdbs3.csp8.cspapp.env.Logger;
import pdbs3.csp8.cspapp.R; // Explicit import needed for internal Google builds.

public class ClassifierActivity extends CameraActivity implements OnImageAvailableListener {

  private final int requestCode = 20;
  private static final Logger LOGGER = new Logger();

  protected static final boolean SAVE_PREVIEW_BITMAP = false;

  private ResultsView resultsView;

  private Bitmap rgbFrameBitmap = null;
  private Bitmap croppedBitmap = null;
  private Bitmap cropCopyBitmap = null;
  private LocationManager locationManager;
  private LocationListener listener;

  private long lastProcessingTimeMs;

  // These are the settings for the original v1 Inception model. If you want to
  // use a model that's been produced from the TensorFlow for Poets codelab,
  // you'll need to set IMAGE_SIZE = 299, IMAGE_MEAN = 128, IMAGE_STD = 128,
  // INPUT_NAME = "Mul", and OUTPUT_NAME = "final_result".
  // You'll also need to update the MODEL_FILE and LABEL_FILE paths to point to
  // the ones you produced.
  //
  // To use v3 Inception model, strip the DecodeJpeg Op from your retrained
  // model first:
  //
  // python strip_unused.py \
  // --input_graph=<retrained-pb-file> \
  // --output_graph=<your-stripped-pb-file> \
  // --input_node_names="Mul" \
  // --output_node_names="final_result" \
  // --input_binary=true
  private static final int INPUT_SIZE = 128;
  private static final int IMAGE_MEAN = 128;
  private static final float IMAGE_STD = 128;
  private static final String INPUT_NAME = "input";
  private static final String OUTPUT_NAME = "final_result";


  private static final String MODEL_FILE = "file:///android_asset/output_graph.pb";
  private static final String LABEL_FILE =
      "file:///android_asset/output_labels.txt";


  private static final boolean MAINTAIN_ASPECT = true;

  private static final Size DESIRED_PREVIEW_SIZE = new Size(300, 300);


  private Integer sensorOrientation;
  private Classifier classifier;
  private Matrix frameToCropTransform;
  private Matrix cropToFrameTransform;
  public float lalt;
  public float llat;
  public float llong;


  private BorderedText borderedText;
  private String key;
  private ImageButton saveButton;
  private Recognition results;


    @Override
  protected int getLayoutId() {
    return R.layout.camera_connection_fragment;
  }

  @Override
  protected Size getDesiredPreviewFrameSize() {
    return DESIRED_PREVIEW_SIZE;
  }

  private static final float TEXT_SIZE_DIP = 10;

  @Override
  public void onPreviewSizeChosen(final Size size, final int rotation) {
    final float textSizePx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, getResources().getDisplayMetrics());
    borderedText = new BorderedText(textSizePx);
    borderedText.setTypeface(Typeface.MONOSPACE);

    classifier =
        TensorFlowImageClassifier.create(
            getAssets(),
            MODEL_FILE,
            LABEL_FILE,
            INPUT_SIZE,
            IMAGE_MEAN,
            IMAGE_STD,
            INPUT_NAME,
            OUTPUT_NAME);

    previewWidth = size.getWidth();
    previewHeight = size.getHeight();

    sensorOrientation = rotation - getScreenOrientation();
    LOGGER.i("Camera orientation relative to screen canvas: %d", sensorOrientation);

    LOGGER.i("Initializing at size %dx%d", previewWidth, previewHeight);
    rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Config.ARGB_8888);
    croppedBitmap = Bitmap.createBitmap(INPUT_SIZE, INPUT_SIZE, Config.ARGB_8888);

    frameToCropTransform = ImageUtils.getTransformationMatrix(
        previewWidth, previewHeight,
        INPUT_SIZE, INPUT_SIZE,
        sensorOrientation, MAINTAIN_ASPECT);

    cropToFrameTransform = new Matrix();
    frameToCropTransform.invert(cropToFrameTransform);

    addCallback(
        new DrawCallback() {
          @Override
          public void drawCallback(final Canvas canvas) {
            renderDebug(canvas);
          }
        });
  }

  @Override
  protected void processImage() throws IOException{
    getLocInfo();
    rgbFrameBitmap.setPixels(getRgbBytes(), 0, previewWidth, 0, 0, previewWidth, previewHeight);
    final Canvas canvas = new Canvas(croppedBitmap);
    canvas.drawBitmap(rgbFrameBitmap, frameToCropTransform, null);

    // For examining the actual TF input.
//    if (SAVE_PREVIEW_BITMAP) {
//      ImageUtils.saveBitmap(croppedBitmap);
//    }

    saveButton=(ImageButton)findViewById(R.id.SaveImage);



    runInBackground(
        new Runnable() {
          @Override
          public void run() {
            //getLocInfo();
            final long startTime = SystemClock.uptimeMillis();
            final List<Classifier.Recognition> results = classifier.recognizeImage(croppedBitmap);
            SystemClock.sleep(1400);
            lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime;
            LOGGER.i("Detect: %s", results);
            cropCopyBitmap = Bitmap.createBitmap(croppedBitmap);
            if (resultsView == null) {
              resultsView = (ResultsView) findViewById(R.id.results);
            }
            resultsView.setResults(results);
            requestRender();
            readyForNextImage();
          }
        });



    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        String fname = "CSP_";
        key = getIntent().getExtras().getString("followup_id");

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        //recogp.getConfidence()!=null &&recogp.getTitle()!=null && recogp.getConfidence() >=90.0 && !recogp.getTitle().equals("nonpg")

       //Method call for getting location.
        //getLocInfo();




        final List<Classifier.Recognition> resuls = classifier.recognizeImage(croppedBitmap);
        for(final Recognition recog:resuls) {
          if (recog != null) {
            if(llat>0.0&&llong>0.0) {
//              if (true){
//            if(true){
              if ((recog.getTitle().equals("Pothole") || recog.getTitle().equals("Garbage")) && recog.getConfidence() >= 0.9) {

              String Title=recog.getTitle();

                      ImageUtils smb = new ImageUtils();
                try {
                  smb.saveBitmap(rgbFrameBitmap, fname + timestamp + ".jpg",llat,llong);
                }catch (Exception e ){}


//                Toast.makeText(
//                        ClassifierActivity.this,
//                        "Latitude: " + llat + "Longitude: " + llong,
//                        Toast.LENGTH_LONG)
//                        .show();


//        Intent i =new Intent(ClassifierActivity.this,UploadForm.class);
//        i.putExtra("Bitmap",rgbFrameBitmap);
//      startActivity(i);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                rgbFrameBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                String mkey = "yash";
                if (key != null && key.equals(mkey)) {

                  Intent in1 = new Intent(ClassifierActivity.this, followup.class);
                  in1.putExtra("fImage", byteArray);
                  in1.putExtra("key", mkey);
                  startActivity(in1);

                }
//              else if(){
//
//
//
//              }
                else {

                  try {
                    final String txtDir =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".CSPloc";

//                    final File file = new File(txtDir);


                    String tfile=fname + timestamp + ".txt";

                    String tbody = "Latitude: " + llat + "  Longitude: " + llong;

                    File gpxfile = new File(txtDir, tfile);
                    if (gpxfile.exists()) {
                      gpxfile.delete();
                    }
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append(tbody);

                    writer.close();
                    Toast.makeText(ClassifierActivity.this, "Text file created!", Toast.LENGTH_SHORT).show();
                  }catch(Exception e){
                    LOGGER.e(e, "Exception!");
                  }

                  Intent in1 = new Intent(ClassifierActivity.this, UploadForm.class);
                  in1.putExtra("Image", byteArray);
                  in1.putExtra("key", mkey);
                  in1.putExtra("Latitude",llat);
                  in1.putExtra("Longitude",llong);
                  in1.putExtra("Title",Title);
                  startActivity(in1);

                }

              } else {
                Toast.makeText(
                        ClassifierActivity.this,
                        "Latitude: " + llat%.4f + "Longitude: " + llong,
                        Toast.LENGTH_SHORT)
                        .show();
              }
            }else{
              Toast.makeText(
                      ClassifierActivity.this,
                      "Please wait till location updates! " ,
                      Toast.LENGTH_SHORT)
                      .show();
            }
          } else {
            Toast.makeText(
                    ClassifierActivity.this,
                    "Result null!",
                    Toast.LENGTH_SHORT)
                    .show();
          }
        }


      }
    });



  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case 10:
        conf_butn();
        break;
      default:
        break;
    }
  }

  void conf_butn() {
    // first check for permissions
    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                , 10);
      }
      return;
    }
    // this code won't execute IF permissions are not allowed, because in the line above there is return statement.

        //noinspection MissingPermission
        if ((ActivityCompat.checkSelfPermission(ClassifierActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(ClassifierActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
          // TODO: Consider calling
          //    ActivityCompat#requestPermissions
          // here to request the missing permissions, and then overriding
          //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
          //                                          int[] grantResults)
          // to handle the case where the user grants the permission. See the documentation
          // for ActivityCompat#requestPermissions for more details.
          return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);

  }

  private void getLocInfo() {

    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


    listener = new LocationListener() {
      @Override
      public void onLocationChanged(Location location) {
        llat = ((float) location.getLatitude());
         llong = ((float) location.getLongitude());
         lalt = ((float) location.getAltitude()%4f);
      }

      @Override
      public void onStatusChanged(String s, int i, Bundle bundle) {

      }

      @Override
      public void onProviderEnabled(String s) {

      }

      @Override
      public void onProviderDisabled(String s) {

        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(i);
      }
    };
    conf_butn();
  }


  @Override
  public void onSetDebug(boolean debug) {
    classifier.enableStatLogging(debug);
  }

  private void renderDebug(final Canvas canvas) {
    if (!isDebug()) {
      return;
    }
    final Bitmap copy = cropCopyBitmap;
    if (copy != null) {
      final Matrix matrix = new Matrix();
      final float scaleFactor = 2;
      matrix.postScale(scaleFactor, scaleFactor);
      matrix.postTranslate(
              canvas.getWidth() - copy.getWidth() * scaleFactor,
              canvas.getHeight() - copy.getHeight() * scaleFactor);
      canvas.drawBitmap(copy, matrix, new Paint());

      final Vector<String> lines = new Vector<String>();
      if (classifier != null) {
        String statString = classifier.getStatString();
        String[] statLines = statString.split("\n");
        for (String line : statLines) {
          lines.add(line);
        }
      }

      lines.add("Frame: " + previewWidth + "x" + previewHeight);
      lines.add("Crop: " + copy.getWidth() + "x" + copy.getHeight());
      lines.add("View: " + canvas.getWidth() + "x" + canvas.getHeight());
      lines.add("Rotation: " + sensorOrientation);
      lines.add("Inference time: " + lastProcessingTimeMs + "ms");


      borderedText.drawLines(canvas, 10, canvas.getHeight() - 10, lines);

    }
  }
}
