package com.example.tatwa10;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class MultipartRequest extends Request<String> {

    private final Response.Listener<String> listener;
    private final Map<String, String> params;
    private final byte[] fileData;
    private final String fileName;

    private final String boundary = "apiclient-" + System.currentTimeMillis();

    public MultipartRequest(String url,
                            Map<String, String> params,
                            byte[] fileData,
                            String fileName,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.listener = listener;
        this.params = params;
        this.fileData = fileData;
        this.fileName = fileName;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(outputStream);

        try {
            // 🔥 TEXT PARAMS
            for (Map.Entry<String, String> entry : params.entrySet()) {
                writer.writeBytes("--" + boundary + "\r\n");
                writer.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
                writer.writeBytes(entry.getValue() + "\r\n");
            }

            // 🔥 FILE
            if (fileData != null) {
                writer.writeBytes("--" + boundary + "\r\n");
                writer.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n");
                writer.writeBytes("Content-Type: application/pdf\r\n\r\n");
                writer.write(fileData);
                writer.writeBytes("\r\n");
            }

            writer.writeBytes("--" + boundary + "--\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success(new String(response.data), HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }
}