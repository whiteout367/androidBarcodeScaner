package example.zxing;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.mikepenz.aboutlibraries.LibsBuilder;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public String barcodeResult;
    public String result;
    public List<Part> userList ;
    public DBHelper helper;
    public SQLiteDatabase db;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Log.d("MainActivity", "Cancelled scan");
                        Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                    } else if(originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        Log.d("MainActivity", "Cancelled scan due to missing camera permission");
                        Toast.makeText(MainActivity.this, "Cancelled due to missing camera permission", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("MainActivity", "Scanned");
                    Intent intent = new Intent(getApplicationContext(), ScanResult.class);
                    startActivity(intent);
                    barcodeResult = result.getContents();
                    //Toast.makeText(MainActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//
//        helper = new DBHelper(MainActivity.this);
//
//
//        db = helper.getWritableDatabase();
//        try {
//            helper.createDataBase();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        helper.onCreate(db);
//
//        String sql = "select * from part;";
//        Cursor c = db.rawQuery(sql, null);
//        while(c.moveToNext()){
//            System.out.println("txt : "+c.getString(c.getColumnIndex("txt")));
//        }
        initLoadDB();
        Log.v("result",result);
        Toast.makeText(MainActivity.this, "Scanned: " + result, Toast.LENGTH_LONG).show();
    }
    private void initLoadDB() {

        DataAdapter mDbHelper = new DataAdapter(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();



        // db에 있는 값들을 model을 적용해서 넣는다.
        userList = mDbHelper.getTableData("8801230100049");
        result = userList.toString();
        // db 닫기
        mDbHelper.close();
    }

    public void scanBarcode(View view) {
        barcodeLauncher.launch(new ScanOptions());
    }

//    public void scanBarcodeInverted(View view){
//        ScanOptions options = new ScanOptions();
//        options.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.INVERTED_SCAN);
//        barcodeLauncher.launch(options);
//    }
//
//    public void scanMixedBarcodes(View view){
//        ScanOptions options = new ScanOptions();
//        options.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.MIXED_SCAN);
//        barcodeLauncher.launch(options);
//    }
//
//    public void scanBarcodeCustomLayout(View view) {
//        ScanOptions options = new ScanOptions();
//        options.setCaptureActivity(AnyOrientationCaptureActivity.class);
//        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
//        options.setPrompt("Scan something");
//        options.setOrientationLocked(false);
//        options.setBeepEnabled(false);
//        barcodeLauncher.launch(options);
//    }
//
//    public void scanPDF417(View view) {
//        ScanOptions options = new ScanOptions();
//        options.setDesiredBarcodeFormats(ScanOptions.PDF_417);
//        options.setPrompt("Scan something");
//        options.setOrientationLocked(false);
//        options.setBeepEnabled(false);
//        barcodeLauncher.launch(options);
//    }
//
//
//    public void scanBarcodeFrontCamera(View view) {
//        ScanOptions options = new ScanOptions();
//        options.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
//        barcodeLauncher.launch(options);
//    }
//
//    public void scanContinuous(View view) {
//        Intent intent = new Intent(this, ContinuousCaptureActivity.class);
//        startActivity(intent);
//    }
//
//    public void scanToolbar(View view) {
//        ScanOptions options = new ScanOptions().setCaptureActivity(ToolbarCaptureActivity.class);
//        barcodeLauncher.launch(options);
//    }
//
//    public void scanCustomScanner(View view) {
//        ScanOptions options = new ScanOptions().setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class);
//        barcodeLauncher.launch(options);
//    }
//
//    public void scanMarginScanner(View view) {
//        ScanOptions options = new ScanOptions();
//        options.setOrientationLocked(false);
//        options.setCaptureActivity(SmallCaptureActivity.class);
//        barcodeLauncher.launch(options);
//    }
//
//    public void scanWithTimeout(View view) {
//        ScanOptions options = new ScanOptions();
//        options.setTimeout(8000);
//        barcodeLauncher.launch(options);
//    }
//
//    public void tabs(View view) {
//        Intent intent = new Intent(this, TabbedScanning.class);
//        startActivity(intent);
//    }
//
//    public void about(View view) {
//        new LibsBuilder().start(this);
//    }

    /**
     * Sample of scanning from a Fragment
     */
    public static class ScanFragment extends Fragment {
        private final ActivityResultLauncher<ScanOptions> fragmentLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if(result.getContents() == null) {
                        Toast.makeText(getContext(), "Cancelled from fragment", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Scanned from fragment: " + result.getContents(), Toast.LENGTH_LONG).show();
                    }
                });

        public ScanFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_scan, container, false);
            Button scan = view.findViewById(R.id.scan_from_fragment);
            scan.setOnClickListener(v -> scanFromFragment());
            return view;
        }

        public void scanFromFragment() {
            fragmentLauncher.launch(new ScanOptions());
        }
    }
}
