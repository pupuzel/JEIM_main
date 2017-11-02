package com.example.jock.jeim_main.Another;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

/**
  앨범에서 가져온 이미지를 최적화 해주는 클래스
 */

public class GalleryBitmap {
    private String imgPath,imgName;
    private ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
    private Bitmap bitmap;

    public GalleryBitmap(Bitmap bitmap){
        this.bitmap = setBitmap(bitmap);
    }
    public GalleryBitmap(Bitmap bitmap,String imgPath){
        int imgorientation = getimgMate(imgPath);
        Bitmap bitmap1 = rotateBitmap(bitmap,imgorientation);
        this.bitmap = setBitmap(bitmap1);
    }

    public byte[] getByteArray() {
        return byteArray.toByteArray();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    // 사진 회전 메타 정보 가져오기
    private int getimgMate(String path){
        ExifInterface exif = null;
        int orientation = 0;
        try {
            exif = new ExifInterface(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
        return orientation;
    }
    // 자동 회전된 비트맵 값 초기화 시켜주기
    private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap setBitmap(Bitmap bitmap){
        if(bitmap.getWidth() > 1600 || bitmap.getHeight() > 1024){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArray);

        }else if(bitmap.getWidth() > 1024 || bitmap.getHeight() > 700){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArray);

        }else{
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);

        }

        return bitmap;
    }

}
