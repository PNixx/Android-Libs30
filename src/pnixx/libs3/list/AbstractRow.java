package pnixx.libs3.list;

import org.json.JSONException;
import org.json.JSONObject;
import pnixx.libs3.core.Log;
import pnixx.libs3.annotation.Json;

import java.lang.reflect.Field;

/**
 * User: nixx
 * Date: 15.06.15
 * Time: 16:15
 * Contact: http://vk.com/djnixx
 */
public abstract class AbstractRow {

	//Пустой конструктор
	public AbstractRow() {

	}

	//Constructor
	public AbstractRow(JSONObject r) throws JSONException {
		parse(this.getClass(), r);
	}

	//Парсинг
	private void parse(Class<?> c, JSONObject r) throws JSONException {

		//Получаем список полей
		Field[] fields = c.getFields();

		//Проходим по списку
		for( Field field : fields ) {
			if( field.isAnnotationPresent(Json.class) ) {
				Json json = field.getAnnotation(Json.class);
				String key = json.value().equals("") ? field.getName() : json.value();
				Object value = r.isNull(key) ? null : r.get(key);
				try {
					if( value != null ) {
						field.set(this, value);
					}
				} catch( IllegalArgumentException e ) {
					Log.e(e.getMessage(), e);
				} catch( IllegalAccessException e ) {
					Log.e(e.getMessage(), e);
				}
			}
		}
	}
}
