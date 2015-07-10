package pnixx.libs3.cache;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import pnixx.libs3.core.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * User: nixx
 * Date: 10.02.15
 * Time: 18:09
 * Contact: http://vk.com/djnixx
 */
public class BitmapDecodeAssetAsync extends AsyncTask<String, Void, Bitmap> {
	private final WeakReference<ImageCacheView> imageCacheViewReference;
	private final WeakReference<ImageView> imageViewReference;
	private final String key;
	private final int width;
	private final int height;

	public BitmapDecodeAssetAsync(ImageCacheView imageView, int width, int height) {
		key = imageView.url;
		// Use a WeakReference to ensure the ImageView can be garbage collected
		imageCacheViewReference = new WeakReference<ImageCacheView>(imageView);
		imageViewReference = null;
		this.width = width;
		this.height = height;
	}

	// Decode image in background.
	@Override
	protected Bitmap doInBackground(String... params) {
		String url = params[0];

		try {
			//Получаем ресурс
			Bitmap bitmap = Cache.decodeSampledBitmapFromAsset(imageCacheViewReference.get().getContext(), url, width, height);

			//Добавляем в память
			Cache.addBitmap(key, bitmap);

			//Возвращаем
			return bitmap;
		} catch( IOException e ) {
			Log.e(e.getMessage(), e);
		}
		return null;
	}

	// Once complete, see if ImageView is still around and set bitmap.
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if( imageCacheViewReference != null && bitmap != null ) {
			final ImageCacheView imageView = imageCacheViewReference.get();
			if( imageView != null ) {

				//Вставляем изображение
				imageView.setImageBitmap(bitmap);
				imageView.is_loaded = true;
			}
		}

		//Для обычных изображений
		if( imageViewReference != null && bitmap != null ) {
			final ImageView imageView = imageViewReference.get();
			if( imageView != null ) {
				imageView.setImageBitmap(bitmap);
			}
		}
	}
}
