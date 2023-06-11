import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CricinfoAPIClientTest {

    @Mock
    private HttpURLConnection connection;

    public CricinfoAPIClientTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCallCricinfoAPI_Success() throws IOException {
        String expectedResponse = "{\"series\":[{\"series_id\":\"12345\",\"series_name\":\"Test Series\",\"start_date\":\"2023-06-01\",\"end_date\":\"2023-06-30\"}]}";

        String uri = "https://api.cricapi.com/v1/series?apikey=8c096f5b-dd98-4ebc-b027-a66bd03e4366&offset=0";
        URL url = new URL(uri);
        when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(connection.getInputStream()).thenReturn(createMockInputStream(expectedResponse));

        String actualResponse = CricinfoAPIClient.callCricinfoAPI(url, connection);

        assertEquals(expectedResponse, actualResponse);

        verify(connection, times(1)).setRequestProperty("User-Agent", "YourApp/1.0");
        verify(connection, times(1)).getResponseCode();
        verify(connection, times(1)).getInputStream();
        verify(connection, times(1)).disconnect();
    }

    @Test
    public void testCallCricinfoAPI_Error() throws IOException {
        String uri = "https://api.cricapi.com/v1/series?apikey=8c096f5b-dd98-4ebc-b027-a66bd03e4366&offset=0";
        URL url = new URL(uri);
        when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_INTERNAL_ERROR);

        String actualResponse = CricinfoAPIClient.callCricinfoAPI(url, connection);

        assertEquals("", actualResponse);

        verify(connection, times(1)).setRequestProperty("User-Agent", "YourApp/1.0");
        verify(connection, times(1)).getResponseCode();
        verify(connection, times(0)).getInputStream();  // No interaction expected
        verify(connection, times(1)).disconnect();
    }

    private InputStream createMockInputStream(String response) {
        return new ByteArrayInputStream(response.getBytes());
    }
}
