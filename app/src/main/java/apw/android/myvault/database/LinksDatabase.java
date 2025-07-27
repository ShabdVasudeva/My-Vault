package apw.android.myvault.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class LinksDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Links.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "LINKS";
    public static final String COLUMN_NAME = "URL";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_ID = "ID";

    public LinksDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DATE + " TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
