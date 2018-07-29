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
                   resolver:(RCTPromiseResolveBlock)resolve
                   rejecter:(RCTPromiseRejectBlock)reject)
{
    
    
    NSData *inData = [[NSData alloc] initWithBase64EncodedString:image64 options:0];
    
    UIImage *inImage = [UIImage imageWithData:inData];
    
    NSDictionary *shadowFontAttributes = [NSDictionary dictionaryWithObjectsAndKeys:[UIFont fontWithName:@"Arial" size:40], NSFontAttributeName,[UIColor colorWithRed:0 green:0 blue:0 alpha:1],NSForegroundColorAttributeName, nil];
    
    NSDictionary *fontAttributes = [NSDictionary dictionaryWithObjectsAndKeys:[UIFont fontWithName:@"Arial" size:40], NSFontAttributeName,[UIColor colorWithRed:255 green:255 blue:255 alpha:1],NSForegroundColorAttributeName, nil];
    
    UIGraphicsBeginImageContextWithOptions(inImage.size, false, inImage.scale);
    [inImage drawInRect:CGRectMake(0, 0, inImage.size.width, inImage.size.height)];
    [text drawAtPoint:CGPointMake(6, 6) withAttributes:shadowFontAttributes];
    [text drawAtPoint:CGPointMake(4, 4) withAttributes:fontAttributes];

    UIImage *annotatedImage = UIGraphicsGetImageFromCurrentImageContext();
    
    NSData *outData = UIImageJPEGRepresentation(annotatedImage, 0.8);
    
    NSString *outImage64 = [outData base64EncodedStringWithOptions:0];

    resolve(outImage64);

}

@end
  
