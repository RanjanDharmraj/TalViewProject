package android.com.talviewproject.photoutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Ranjan Kumar on 4/25/2016.
 */
public class ImageLoader {

    private String filePath;
    private static ImageLoader instance;
    private int width = 128;
    private int height = 128;

    private ImageLoader() {
    }

    public static ImageLoader init() {
        if(instance == null) {
            Class var0 = ImageLoader.class;
            synchronized(ImageLoader.class) {
                if(instance == null) {
                    instance = new ImageLoader();
                }
            }
        }

        return instance;
    }

    public ImageLoader from(String filePath) {
        this.filePath = filePath;
        return instance;
    }

    public ImageLoader requestSize(int width, int height) {
        this.height = width;
        this.width = height;
        return instance;
    }

    public Bitmap getBitmap() throws FileNotFoundException {
        File file = new File(this.filePath);
        if(!file.exists()) {
            throw new FileNotFoundException();
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(this.filePath, options);
            options.inSampleSize = this.calculateInSampleSize(options, this.width, this.height);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(this.filePath, options);
            return bitmap;
        }
    }

    public Drawable getImageDrawable() throws FileNotFoundException {
        File file = new File(this.filePath);
        if(!file.exists()) {
            throw new FileNotFoundException();
        } else {
            Drawable drawable = Drawable.createFromPath(this.filePath);
            return drawable;
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if(height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;

            for(int halfWidth = width / 2; halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth; inSampleSize *= 2) {
                ;
            }
        }

        return inSampleSize;
    }
}
