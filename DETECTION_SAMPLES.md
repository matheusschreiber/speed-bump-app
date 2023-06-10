# Storing detections

The app generates an output of the detection over the uploaded images. It is stored on the filesystem of the phone, under the permitted directory. 

## General

The general saved detection goes like this

`file name:`
```
output-<IMAGE_FILE_NAME>.txt
```
`content:` 
```
[DetectionResult(boundingBox=RectF((<coordinates>), text=SpeedBumpSign, <SCORE>)]
```

## Example

`file name:`
```
output-IMG_20230318_161445159_HDR.txt
```
`content:`
```
[DetectionResult(boundingBox=RectF(850.0, 1627.0, 1209.0, 2186.0), text=SpeedBumpSign, 99%)]
```
