package apw.android.myvault.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import apw.android.myvault.CryptoUtil;
import apw.android.myvault.enums.LinkEntry;

import java.util.ArrayList;
import java.util.List;

public class LinksDAO {
    private final LinksDatabase db;

    public LinksDAO(Context context) {
        this.db = new LinksDatabase(context);
    }

    public void insertEncryptedLink(String url, String date){
        String encryptedLink = CryptoUtil.encrypt(url);
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LinksDatabase.COLUMN_NAME, encryptedLink);
        values.put(LinksDatabase.COLUMN_DATE, date);
        database.insert(LinksDatabase.TABLE_NAME, null, values);
        database.close();
    }

    public void removeEncryptedLink(int ID) {
        SQLiteDatabase databse = db.getWritableDatabase();
        databse.delete(LinksDatabase.TABLE_NAME, "ID = ?", new String[] {String.valueOf(ID)});
        databse.close();
    }

    public List<LinkEntry> getUrlList(){
        List<LinkEntry> list = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(LinksDatabase.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            String encrypted = cursor.getString(cursor.getColumnIndexOrThrow(LinksDatabase.COLUMN_NAME));
            String decrypted = CryptoUtil.decrypt(encrypted);
            String date = cursor.getString(cursor.getColumnIndexOrThrow(LinksDatabase.COLUMN_DATE));
            int ID = cursor.getInt(cursor.getColumnIndexOrThrow(LinksDatabase.COLUMN_ID));
            list.add(new LinkEntry(decrypted, date, ID));
        }
        cursor.close();
        database.close();
        return list;
    }
}
