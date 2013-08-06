package pnixx.libs3.core;

import android.os.Message;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * User: P.Nixx
 * Date: 29.12.12
 * Time: 12:40
 */
public class XmlHandler extends JsonHttpResponseHandler {
	private static String TAG = "XmlHandler";
	protected static final int SUCCESS_XML_MESSAGE = 100;

	@Override
	protected void handleMessage(Message msg) {
		switch(msg.what){
			case SUCCESS_XML_MESSAGE:
				try {
					handleSuccessXmlMessage((String) msg.obj);
				} catch( ClassCastException e ) {
					Log.e(e.getMessage(), e);
				}
				break;
			default:
				super.handleMessage(msg);
		}
	}

	protected void handleSuccessXmlMessage(String responseBody) {
		try {
			Object domResponse = parse(responseBody);
			onSuccess((Document) domResponse);
		} catch( SAXException e ) {
			sendFailureMessage(e, responseBody);
		}
	}

	public void onSuccess(Document response) {
		Log.v(TAG + " onSuccess Document: " + response);
	}

	public void onFailure(Throwable e, Document errorResponse) {
		Log.e(TAG + " onFailure *" + e.getCause() + "* String: " + errorResponse);
	}

	//Парсим полученные данные
	private Document parse(String r) throws SAXException {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(r));
			doc = db.parse(is);

		} catch (ParserConfigurationException e) {
			Log.e(e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e(e.getMessage());
			return null;
		}
		// return DOM
		return doc;
	}
}
