package au.edu.utas.auvde.assignment2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static au.edu.utas.auvde.assignment2.R.*;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPETURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        Button locationPageButton =  findViewById(id.btnLogtimeline);
        locationPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,TimeLine.class);
                startActivity(i);
            }


        });

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
      ///  String time = "Current Time;" + format.format(calendar.getTime());



        TextView textView =  findViewById(id.txtdate);
      //  TextView textshow = (TextView) findViewById(id.time);

        TextView textViewDate = findViewById(R.id.txtdate);
        textViewDate.setText(currentDate);

        ImageButton camera =  findViewById(id.imgBCamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open camera
            }
        });

    }

    private void requestToTakeAPicture() {
        ActivityCompat.requestPermissions(
                this,
                new String[] {Manifest.permission.CAMERA},
                REQUEST_IMAGE_CAPETURE);
    }

    public  void onRequestPermissionsResult(int requestCode, String permissionsp[], int[] grantResults){
        switch (requestCode){
            case REQUEST_IMAGE_CAPETURE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission is granted
                    takeAPicture();
                }else {
                    //permission is denied
                }
                break;
        }
    }
    private void takeAPicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) !=null)
        try {
            File photoFIle = createImageFIle();
            Uri photoURI = FileProvider.getUriForFile(this,"au.edu.utas.auvde.assignment2",photoFIle);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPETURE);
        } catch (IOException ex){}
    }

    String mCurrentPhotoPath;
    private File createImageFIle() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MYIMAGE_" + timeStamp+ "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        if (requestCode == REQUEST_IMAGE_CAPETURE && resultCode == RESULT_OK){
            ImageView myImageView  = findViewById(id.myImageView);
            setPic(myImageView,mCurrentPhotoPath);
        }
    }
    private void setPic(ImageView myImageView, String path){

        //Get the dimensions of the View
        int targetW = myImageView.getWidth();
        int targetH = myImageView.getHeight();

        //Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        //Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW,photoH/targetH);

        //Decode the image file into a Bitmap sized to fill the View

        bmOptions.inJustDecodeBounds=false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable= true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        myImageView.setImageBitmap(bitmap);
    }
}