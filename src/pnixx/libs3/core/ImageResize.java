package pnixx.libs3.core;

import android.graphics.Bitmap;
import pnixx.libs3.cache.Cache;

/**
 * User: P.Nixx
 * Date: 25.10.12
 * Time: 10:47
 */
public class ImageResize {

	/**
	 * Изменение размера пропорционально
	 *
	 * @param image Bitmap
	 * @param max_w int
	 * @param max_h int
	 * @return Bitmap
	 */
	public static Bitmap scale(Bitmap image, int max_w, int max_h) {
		float width = image.getWidth();
		float height = image.getHeight();
		float scale = 1f;
		if( width > height ) {
			if( width > max_w ) {
				scale = max_w / width;
			}
		} else {
			if( height > max_h ) {
				scale = max_h / height;
			}
		}
		if( scale != 1f ) {
			int w = (int) (width * scale);
			int h = (int) (height * scale);
			return Bitmap.createScaledBitmap(image, w, h, false);
		} else {
			return image;
		}
	}

	/**
	 * Создание квадрата
	 *
	 * @param bitmap Bitmap
	 * @return Bitmap
	 */
	public synchronized static Bitmap crop(Bitmap bitmap, int size) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		//Делаем квадрат
		try {
			if( w < h ) {
				int y = (int) ((float) h / 2f - (float) w / 2f);
				bitmap = Bitmap.createBitmap(bitmap, 0, y, w, w, null, false);
			} else {
				int x = (int) ((float) w / 2f - (float) h / 2f);
				bitmap = Bitmap.createBitmap(bitmap, x, 0, h, h, null, false);
			}
		} catch( OutOfMemoryError e ) {
			e.printStackTrace();
			Log.e(e.getMessage());

			//Очищаем кэш
			Cache.getInstance().clear();
			return null;
		}

		//Если ширина квадрата больше необходимого размера, уменьшаем
		if( bitmap.getWidth() > size ) {
			bitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);
		}
		return bitmap;
	}
}
