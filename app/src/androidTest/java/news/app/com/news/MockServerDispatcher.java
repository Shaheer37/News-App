package news.app.com.news;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

class MockServerDispatcher {

    /**
     * Return ok response from mock server
     */
    class RequestDispatcher extends Dispatcher {
        @Override
        public MockResponse dispatch(RecordedRequest request) {

            if(request.getPath().equals("/top-headlines?country=us&page=1&pageSize=20")){
              return new MockResponse().setResponseCode(200).setBody(readFromFile("/page1.json"));
            }else if(request.getPath().equals("/top-headlines?country=us&page=2&pageSize=20")){
              return new MockResponse().setResponseCode(200).setBody(readFromFile("/page2.json"));
            }else if(request.getPath().equals("/top-headlines?country=us&page=3&pageSize=20"))
              return new MockResponse().setResponseCode(200).setBody(readFromFile("/page3.json"));

          return new MockResponse().setResponseCode(404);
        }
    }

    /**
     * Return error response from mock server
     */
    class ErrorDispatcher extends Dispatcher {

        @Override
        public MockResponse dispatch(RecordedRequest request) {

            return new MockResponse().setResponseCode(400);

        }
    }

    public String readFromFile(String filename){
        try {
            InputStream is = getClass().getResourceAsStream(filename);
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