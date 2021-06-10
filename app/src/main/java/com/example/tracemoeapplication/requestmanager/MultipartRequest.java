package com.example.tracemoeapplication.requestmanager;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.tracemoeapplication.R;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class MultipartRequest extends Request<NetworkResponse> {
    private static Context context;

    private final String twoHyphens = "--";
    private final String lineEnd = "\r\n";
    private final String boundary = "trace-moe-app-client" + System.currentTimeMillis();

    private final Response.Listener<NetworkResponse> responseListener;
    private final Response.ErrorListener responseErrorListener;
    private Map<String, String> headers;


    public MultipartRequest(Context _context, int _method, String _url, Response.Listener<NetworkResponse> _responseListener, Response.ErrorListener _responseErrorListener) {
        super(_method, _url, _responseErrorListener);
        context = _context;
        responseListener = _responseListener;
        responseErrorListener = _responseErrorListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return (headers != null) ? headers : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    // Populate request body with data parts
    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteOutputStream);

        try {
            Map<String, DataPart> dataMap = getByteData();
            if (dataMap != null && dataMap.size() > 0) {
                dataMapIntoDataOutputStream(dataOutputStream, dataMap);
            }

            // Close multipart form data
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            return byteOutputStream.toByteArray();
        } catch (IOException e) {
            Toast.makeText(context, R.string.image_to_body_request_error_text, Toast.LENGTH_LONG).show();
        }

        return null;
    }

    // Method to override and pass file
    protected Map<String, DataPart> getByteData() throws AuthFailureError {
        return null;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            Toast.makeText(context, R.string.parse_request_response_error_text, Toast.LENGTH_LONG).show();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        responseListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        responseErrorListener.onErrorResponse(error);
    }

    // Add data map to data output stream
    private void dataMapIntoDataOutputStream(DataOutputStream dataOutputStream, Map<String, DataPart> dataMap) throws IOException {
        for (Map.Entry<String, DataPart> entry : dataMap.entrySet()) {
            dataPartIntoDataOutputStream(dataOutputStream, entry.getValue(), entry.getKey());
        }
    }

    // Add data part to data output stream
    private void dataPartIntoDataOutputStream(DataOutputStream dataOutputStream, DataPart file, String name) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + file.getFileName() + "\"" + lineEnd);
        if (file.getType() != null && !file.getType().trim().isEmpty()) {
            dataOutputStream.writeBytes("Content-Type: " + file.getType() + lineEnd);
        }
        dataOutputStream.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(file.getContent());
        int maxBufferSize = 1024 * 1024;

        int bytesToRead = fileInputStream.available();
        int bufferSize = Math.min(bytesToRead, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int remainingBytes = fileInputStream.read(buffer, 0, bufferSize);

        while (remainingBytes > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesToRead = fileInputStream.available();
            bufferSize = Math.min(bytesToRead, maxBufferSize);
            remainingBytes = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(lineEnd);
    }
}
