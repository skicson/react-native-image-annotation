
package com.mjsimageannotate;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

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

      Bitmap newBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(newBitmap);

      Paint paint = new Paint();
      paint.setColor(Color.WHITE); // Text Color
      paint.setTextSize(30); // Text Size
      paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)); // Text Overlapping Pattern
      paint.setShadowLayer(3,0,2, Color.BLACK);

      canvas.drawBitmap(originalBitmap, 0, 0, paint);
      canvas.drawText(text, 10, 30, paint);


      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
      byte[] byteArray = byteArrayOutputStream .toByteArray();
      output = Base64.encodeToString(byteArray, Base64.DEFAULT);

    } catch (Error error){
      Log.e("RNImageAnnotation", "Unable to annotate image, "+error.getLocalizedMessage());
    }

    promise.resolve(output);
  }
}
