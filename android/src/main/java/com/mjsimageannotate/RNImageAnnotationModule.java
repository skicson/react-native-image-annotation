
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
  public void annotate(String image64, String text, final Promise promise) {



    promise.resolve("okeydokey");

  }
}