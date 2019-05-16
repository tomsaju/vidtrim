package trial.testy.videocutter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int VIDEO_CAPTURED = 4;
    protected Button capture;
    protected TextView fileName;
    protected Button convert;
    private Uri videoFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.capture) {
            Intent captureVideoIntent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(captureVideoIntent,VIDEO_CAPTURED);

        } else if (view.getId() == R.id.convert) {
            startActivity(new Intent(this,VideoTrimmerActivity.class));
        }
    }

    private void initView() {
        capture = (Button) findViewById(R.id.capture);
        capture.setOnClickListener(MainActivity.this);
        fileName = (TextView) findViewById(R.id.fileName);
        convert = (Button) findViewById(R.id.convert);
        convert.setOnClickListener(MainActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == VIDEO_CAPTURED) {
            videoFileUri = data.getData();
           if(videoFileUri!=null){
              copyFileFromUri(MainActivity.this,videoFileUri);
           }
        }

    }

    /*private void createFile(Uri videoFileUri) {
        //File sourceFile = new File(videoFileUri.getPath());

        File file;
        FileOutputStream outputStream;
        try {
            file = new File(Environment.getExternalStorageDirectory(),Environment.DIRECTORY_DOWNLOADS);
            outputStream = new FileOutputStream(file);
            outputStream.write(videoFileUri);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public boolean copyFileFromUri(Context context, Uri fileUri)
    {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try
        {
            ContentResolver content = context.getContentResolver();
            inputStream = content.openInputStream(fileUri);

            File root = Environment.getExternalStorageDirectory();
            if(root == null){

            }

            // create a directory
            File saveDirectory = new File(Environment.getExternalStorageDirectory()+File.separator+ "directory_name" +File.separator);
            // create direcotory if it doesn't exists
            saveDirectory.mkdirs();

            outputStream = new FileOutputStream( saveDirectory + "testvideo.mp4"); // filename.png, .mp3, .mp4 ...
            if(outputStream != null){

            }

            byte[] buffer = new byte[1000];
            int bytesRead = 0;
            while ( ( bytesRead = inputStream.read( buffer, 0, buffer.length ) ) >= 0 )
            {
                outputStream.write( buffer, 0, buffer.length );
            }
        } catch ( Exception e ){

        } finally{

        }
        return true;
    }
}
