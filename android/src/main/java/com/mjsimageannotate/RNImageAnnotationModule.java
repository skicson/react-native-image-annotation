
package com.mjsimageannotate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Base64;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.io.ByteArrayOutputStream;

public class RNImageAnnotationModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNImageAnnotationModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNImageAnnotation";
  }

  @ReactMethod
  public void annotate(String image64, String text, final ReadableMap config, final Promise promise) {
    String output = image64;
    try {
      byte[] decodedString = Base64.decode(image64, Base64.DEFAULT);
      Bitmap originalBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

      int width = originalBitmap.getWidth();
      int height = originalBitmap.getHeight();
      int quality = 80;
      int fontSize = 20;

      boolean rescale = false;

      if (config.hasKey("outputMaxWidth")){
        width = config.getInt("outputMaxWidth");
        rescale = true;
      }
      if (config.hasKey("outputMaxHeight")){
        height = config.getInt("outputMaxHeight");
        rescale = true;
      }
      if (rescale){
        if (originalBitmap.getWidth()> originalBitmap.getHeight()){
          height = (width * originalBitmap.getHeight()) / originalBitmap.getWidth();
        } else {
          width = (height * originalBitmap.getWidth()) / originalBitmap.getHeight();
        }

      }


      Rect src = new Rect(0,0,originalBitmap.getWidth(), originalBitmap.getHeight());
      Rect dest = new Rect(0,0, width, height);



      Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(newBitmap);

      Paint paint = new Paint();
      paint.setColor(Color.WHITE); // Text Color
      paint.setTextSize(fontSize); // Text Size
      paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)); // Text Overlapping Pattern
      paint.setShadowLayer(3,0,2, Color.BLACK);


      canvas.drawBitmap(originalBitmap, src, dest, paint);
      canvas.drawText(text, 10, 30, paint);


      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      newBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
      byte[] byteArray = byteArrayOutputStream .toByteArray();
      output = Base64.encodeToString(byteArray, Base64.DEFAULT);

    } catch (Error error){
      Log.e("RNImageAnnotation", "Unable to annotate image, "+error.getLocalizedMessage());
    }

    promise.resolve(output);
  }
}
