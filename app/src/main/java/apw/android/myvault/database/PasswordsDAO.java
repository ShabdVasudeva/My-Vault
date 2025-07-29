package apw.android.myvault.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import apw.android.myvault.CryptoUtil;
import apw.android.myvault.enums.PasswordEntry;

import java.util.ArrayList;
import java.util.List;

public class PasswordsDAO {
    private final PasswordsDatabase database;

    public PasswordsDAO(@NonNull Context context) {
        this.database = new PasswordsDatabase(context);
    }

    public void insertEncryptedPassword(String title, String username, String password) {
        SQLiteDatabase db = database.getWritableDatabase();
        String encryptedPassword = CryptoUtil.encrypt(password);
        ContentValues values = new ContentValues();
        values.put(PasswordsDatabase.COLUMN_TITLE, title);
        values.put(PasswordsDatabase.USERNAME, username);
        values.put(PasswordsDatabase.PASSWORD, encryptedPassword);
        db.insert(PasswordsDatabase.TABLE_NAME, null, values);
        db.close();
    }

    public void removeEncryptedPassword(int ID) {
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(PasswordsDatabase.TABLE_NAME, "ID = ?", new String[] {String.valueOf(ID)});
        db.close();
    }

    public List<PasswordEntry> getPasswords(){
        List<PasswordEntry> passwords = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(PasswordsDatabase.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String encryptedPassword = cursor.getString(cursor.getColumnIndexOrThrow(PasswordsDatabase.PASSWORD));
            String password = CryptoUtil.decrypt(encryptedPassword);
            String title = cursor.getString(cursor.getColumnIndexOrThrow(PasswordsDatabase.COLUMN_TITLE));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(PasswordsDatabase.USERNAME));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(PasswordsDatabase.COLUMN_ID));
            passwords.add(new PasswordEntry(username, password, title, id));
        }
        cursor.close();
        db.close();
        return passwords;
    }
}
