
# react-native-image-annotation

## Getting started

`$ npm install react-native-image-annotation --save`

### Mostly automatic installation

`$ react-native link react-native-image-annotation`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-image-annotation` and add `RNImageAnnotation.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNImageAnnotation.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNImageAnnotationPackage;` to the imports at the top of the file
  - Add `new RNImageAnnotationPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-image-annotation'
  	project(':react-native-image-annotation').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-image-annotation/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-image-annotation')
  	```


## Usage
Images are represented in the inputs and outputs as <b>base64 encoded strings</b>.
```javascript
import RNImageAnnotation from 'react-native-image-annotation';


RNImageAnnotation.annotate(inputImage,
                                textToAdd,
                                configObject)
.then(outputImage => {
    // do something
})
```
