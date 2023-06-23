package com.example.activitytrackingapp;


import android.os.Environment;
import android.util.Log;

import Codebase.Result;

import java.io.*;
import java.net.*;
import java.util.Objects;

public class Client extends Thread {
    String fileName;
    InputStream inputStream;
    File fileToUpload;
    Socket socket;
    Result result;
    float[] statistics = new float[4];
    float[] generalStatistics = new float[4];
    private static final String TAG = "Client";


    Client(String path) {
        int index = path.lastIndexOf("/");
        this.fileName = path.substring(index + 1);
        this.fileToUpload =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName);
    }

    Client(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Result getResult(){
        return result;
    }
    public float[] getStatistics(){return statistics;}
    public float[] getGeneralStatistics(){return generalStatistics;}

    public void run() {

        try {
            /* Create socket for contacting the server on port 8000*/
            socket = new Socket("192.168.2.5",8000);
            Log.v(TAG,"Connection has been approved!");

            /* Create the stream to send data to the server */
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());


            /*---------------Reading and sending my gpx---------------*/

            try (
                 BufferedReader reader = new BufferedReader(
                         new InputStreamReader(Objects.requireNonNull(inputStream)))) {
                Log.v(TAG,"Started reading the file!");
                String line ="";
                while ((line = reader.readLine()) != null) {
                    out.writeUTF(line);
                    out.flush();
                    // send next line
                }
                Log.v(TAG,"Finished reading the File!");
            }


            /*---------------Create the Input Stream where I will be receiving my results---------------*/

            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {

                /* Read result back from stream */
                result = (Result) in.readObject();
                statistics = (float[]) in.readObject();
                generalStatistics = (float[]) in.readObject();

                //Thread.sleep(10000);

                /*Print Result*/
                Log.v(TAG,result.toString());

            }catch(IOException ioException){
                ioException.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            /*Closing Output and Input Streams, also the Socket to the server*/
            in.close();
            out.close();
            inputStream.close();
            socket.close();

        } catch (UnknownHostException unknownHost) {
            Log.v(TAG,"You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}