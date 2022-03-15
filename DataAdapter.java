package example.zxing;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter {
    protected static final String TAG = "DataAdapter";

    // TODO : TABLE 이름을 명시해야함
    protected static final String TABLE_NAME = "part";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;

    public DataAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public List getTableData(String barcode)
    {
        try
        {
            // Table 이름 -> antpool_bitcoin 불러오기
            String sql ="SELECT * FROM " + TABLE_NAME+ " WHERE barcode Like " +"'"+barcode+"';";

            // 모델 넣을 리스트 생성
            List userList = new ArrayList();

            // TODO : 모델 선언
            Part part = null;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                // 칼럼의 마지막까지
                while( mCur.moveToNext() ) {

                    // TODO : 커스텀 모델 생성
                    part = new Part();

                    // TODO : Record 기술
                    // id, name, account, privateKey, secretKey, Comment
                    part.setKind(mCur.getString(0));
                    part.setCode(mCur.getString(1));
                    part.setBarcode(mCur.getString(2));
                    part.setName(mCur.getString(3));
                    part.setStd(mCur.getString(4));
                    part.setQtybox(mCur.getString(5));
                    part.setBox(mCur.getString(6));
                    part.setQty(mCur.getString(7));



                    // 리스트에 넣기
                    userList.add(part);
                }

            }
            return userList;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}
