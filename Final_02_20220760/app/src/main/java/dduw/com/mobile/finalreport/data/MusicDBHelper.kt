package dduw.com.mobile.finalreport.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class MusicDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 3) {
    val TAG = "MusicDBHelper"

    companion object{
        //노래명, 가수, 장르,발매일, 평가
        //Song title, composer, lyrics, singer, genre, release date, rate
        const val DB_NAME = "music_db"
        const val TABLE_NAME = "music_table"
        const val CN_MUSIC = "music"
        const val CN_SINGER = "singer"
        const val CN_IMAGE = "image"
        const val CN_GENRE = "genre"
        const val CN_RELEASE = "release_date"
        const val CN_RATE = "rate"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$CN_MUSIC TEXT,  $CN_IMAGE TEXT, $CN_SINGER TEXT,"+
                    "$CN_GENRE TEXT, $CN_RELEASE TEXT, $CN_RATE REAL )"
        Log.d(TAG, CREATE_TABLE)    // SQL 문장이 이상 없는지 Log에서 확인
        db?.execSQL(CREATE_TABLE)

        /*샘플 데이터*/
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '입춘','spring' ,'한로로', 'Korean Rock/Alt, 포크' , '2022-03-14' , 4.5)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, 'Far Away', 'far_away','라쿠나',  'Korean Rock/Alt' , '2022-09-14' , 5.0)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '엔진','engine' ,'김승주', 'Korean Rock/Alt' , '2023-01-27' , 4.5)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '빌려온 고양이(Do the Dance)', 'cat','ILLIT', 'POP' , '2025-06-16' , 4.0)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, 'WHAT TIME IS IT NOW?','what_time_is_it' ,'설', 'Korean Rock/Alt, 포크' , '2022-10-25' , 4.5)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}