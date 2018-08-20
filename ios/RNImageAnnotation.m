#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

#import "RNImageAnnotation.h"

@implementation RNImageAnnotation

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(annotate:(NSString *)image64
                    text:(NSString *)text
                    config:(NSDictionary *)config
                   resolver:(RCTPromiseResolveBlock)resolve
                   rejecter:(RCTPromiseRejectBlock)reject)
{
    
    
    NSData *inData = [[NSData alloc] initWithBase64EncodedString:image64 options:0];
    
    UIImage *inImage = [UIImage imageWithData:inData];
    
    id fsv = [config objectForKey:@"fontSize"];
    int fontSize = (fsv)? [fsv intValue] : 20;
    
    NSDictionary *shadowFontAttributes = [NSDictionary dictionaryWithObjectsAndKeys:[UIFont fontWithName:@"Arial" size:fontSize], NSFontAttributeName,[UIColor colorWithRed:0 green:0 blue:0 alpha:1],NSForegroundColorAttributeName, nil];
    
    NSDictionary *fontAttributes = [NSDictionary dictionaryWithObjectsAndKeys:[UIFont fontWithName:@"Arial" size:fontSize], NSFontAttributeName,[UIColor colorWithRed:255 green:255 blue:255 alpha:1],NSForegroundColorAttributeName, nil];
    
    id mwv = [config objectForKey:@"outputMaxWidth"];
    id mhv = [config objectForKey:@"outputMaxHeight"];
    
    int width = (mwv)?[mwv intValue]:inImage.size.width;
    int height = (mhv)?[mhv intValue]:inImage.size.height;

    if (mwv && mhv){
        // scale
        if (inImage.size.width > inImage.size.height){
            height = (width * inImage.size.height) / inImage.size.width;
        } else {
            width = (height * inImage.size.width) / inImage.size.height;
        }
    }
    
    id qv = [config objectForKey:@"quality"];
    float quality = (qv)?[qv floatValue]:.8;

    
    UIGraphicsBeginImageContextWithOptions(CGSizeMake(width, height), false, inImage.scale);
    [inImage drawInRect:CGRectMake(0, 0, width, height)];
    [text drawAtPoint:CGPointMake(6, 6) withAttributes:shadowFontAttributes];
    [text drawAtPoint:CGPointMake(4, 4) withAttributes:fontAttributes];

    UIImage *annotatedImage = UIGraphicsGetImageFromCurrentImageContext();
    
    NSData *outData = UIImageJPEGRepresentation(annotatedImage, quality);
    
    NSString *outImage64 = [outData base64EncodedStringWithOptions:0];

    resolve(outImage64);

}

@end
  
