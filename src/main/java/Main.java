import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HTTPRequest httpRequest = new HTTPRequest();
        String string = null;
        String string1 = null;

        httpRequest.getRequest();
        string = httpRequest.getToken();
        string1 = httpRequest.getYandexId();
        httpRequest.getRequestTwo(string, string1);
        httpRequest.gerCoordinates();

    }

}
