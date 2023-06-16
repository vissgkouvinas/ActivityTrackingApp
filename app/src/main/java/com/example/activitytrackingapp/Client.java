package com.example.activitytrackingapp;

import android.os.Environment;
import android.util.Log;

import Codebase.Result;

import java.io.*;
import java.net.*;

public class Client extends Thread {
    String fileName;
    File fileToUpload;
    Socket socket;
    Result result;
    String username;

    float[] statistics = new float[4];
    float[] generalStatistics = new float[4];
    private static final String TAG = "Client";


    Client(String path) {
        int index = path.lastIndexOf("/");
        this.fileName = path.substring(index + 1);
        this.fileToUpload =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName);
    }

    public Result getResult(){
        return result;
    }
    public String getUsername(){return username;}
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
            BufferedReader reader = new BufferedReader(new FileReader(fileToUpload));
            String line = "";
            Log.v(TAG,"Started reading the file!");
            /*Read and Send each line*/
            while ((line = reader.readLine())!= null) {
                out.writeUTF(line);
                out.flush();
                // send next line
            }
            Log.v(TAG,"Finished reading the File!");
            reader.close();//Close the File Reader


            /*---------------Create the Input Stream where I will be receiving my results---------------*/

            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {

                /* Read result back from stream */
                result = (Result) in.readObject();
                username = (String) in.readObject();
                statistics = (float[]) in.readObject();
                generalStatistics = (float[]) in.readObject();

                //Thread.sleep(10000);
                /*Print Result*/
                Log.v(TAG,result.toString());

            }catch(IOException ioException){
                ioException.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } /*catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/

            /*Closing Output and Input Streams, also the Socket to the server*/
            in.close();
            out.close();
            socket.close();

        } catch (UnknownHostException unknownHost) {
            Log.v(TAG,"You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}