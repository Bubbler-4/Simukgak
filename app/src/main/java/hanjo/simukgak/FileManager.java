package hanjo.simukgak;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Kwon Ohhyun on 2016-11-29.
 */

public class FileManager {

    final private static String TAG = "FileManager";
    private  String fileName;
    private File dataFile;
    private Context context;

    public FileManager(Context _context, String _fileName){ //생성자
        context = _context;
        fileName = _fileName;
        setFile();
    }

    private void setFile(){ //파일 세팅
        String savePath = context.getFilesDir() +"/datafiles/";
        File dir = makeDirectory(savePath);
        dataFile = makeFile(dir, (savePath+fileName));
    }

    private File makeDirectory(String dir_path){
        File dir = new File(dir_path);
        if (!dir.exists())
        {
            dir.mkdirs();
            Log.i( TAG , "!dir.exists" );
        }else{

            Log.i( TAG , "dir.exists" );
        }

        return dir;
    }

    private File makeFile(File dir , String file_path){
        File file = null;
        boolean isSuccess = false;
        if(dir.isDirectory()){
            file = new File(file_path);
            if(!file.exists()){
                Log.i( TAG , "!file.exists" );
                try {
                    isSuccess = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    Log.i(TAG, "파일생성 여부 = " + isSuccess);
                }
            }else{
                Log.i( TAG , "file.exists" );
            }
        }
        return file;
    }

    public void resetData() //덮어쓰기로 데이터 초기화
    {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(dataFile);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String content){

        FileOutputStream fos;
        File file = dataFile;
        try {
            fos = new FileOutputStream(file, true);
            PrintWriter out = new PrintWriter(fos);
            try {
                out.println(content);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readFile(){
        File file = dataFile;
        ArrayList<String> strList = new ArrayList<String>();
        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            int i = 0;
            //String[] values;
            FileInputStream fis = new FileInputStream(file);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            while (str != null) {
                strList.add(str);
                str = buffer.readLine();
                i++;
            }
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strList;
    }


}
