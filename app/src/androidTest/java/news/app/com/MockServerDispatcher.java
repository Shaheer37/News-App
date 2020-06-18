package news.app.com;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class MockServerDispatcher {

    /**
     * Return ok response from mock server
     */
    public class RequestDispatcher extends Dispatcher {
        @Override
        public MockResponse dispatch(RecordedRequest request) {

            if(request.getPath().equals("/top-headlines?country=US&pageSize=20&page=1")){
              return new MockResponse().setResponseCode(200).setBody(readFromFile("/page1.json"));
            }else if(request.getPath().equals("/top-headlines?country=US&pageSize=20&page=2")){
              return new MockResponse().setResponseCode(200).setBody(readFromFile("/page2.json"));
            }else if(request.getPath().equals("/top-headlines?country=US&pageSize=20&page=3"))
              return new MockResponse().setResponseCode(200).setBody(readFromFile("/page3.json"));

          return new MockResponse().setResponseCode(404);
        }
    }

    /**
     * Return error response from mock server
     */
    public class ErrorDispatcher extends Dispatcher {

        @Override
        public MockResponse dispatch(RecordedRequest request) {

            return new MockResponse().setResponseCode(400);

        }
    }

    public static String readFromFile(String filename){
        try {
            InputStream is = MockServerDispatcher.class.getResourceAsStream(filename);
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            byte[] b = new byte[4096];
            while ((i = is.read(b)) != -1) {
                stringBuilder.append(new String(b, 0, i));
            }
            return stringBuilder.toString();
        }catch (IOException e){
            e.printStackTrace();
            return "{}";
        }
    }
}