package dduw.com.mobile.finalreport.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns

class MusicDao(context: Context){
    val helper = MusicDBHelper(context)

    @SuppressLint("Range")
    fun getAllMusic() : List<MusicDto> { //DB에서 가져오기
        val db = helper.readableDatabase
        val cursor = db.query(MusicDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val music  = arrayListOf<MusicDto>()
        with(cursor){
            while(moveToNext()){
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val musicTitle = getString(getColumnIndex(MusicDBHelper.CN_MUSIC))
                val singer = getString(getColumnIndex(MusicDBHelper.CN_SINGER))
                val genre = getString(getColumnIndex(MusicDBHelper.CN_GENRE))
                val image = getString(getColumnIndex(MusicDBHelper.CN_IMAGE))
                val releaseDate = getString(getColumnIndex(MusicDBHelper.CN_RELEASE))
                val rate = getFloat(getColumnIndex(MusicDBHelper.CN_RATE))

                val dto = MusicDto(id, musicTitle, singer, image, genre, releaseDate, rate)
                music.add(dto)
            }
        }
        cursor.close()
        helper.close()
        return music
    }

    fun addMusic(musicDto: MusicDto) : Long {
        val db = helper.writableDatabase

        val newMusic = ContentValues()
        newMusic.put(MusicDBHelper.CN_MUSIC,musicDto.music)
        newMusic.put(MusicDBHelper.CN_SINGER,musicDto.singer)
        newMusic.put(MusicDBHelper.CN_IMAGE ,musicDto.image)
        newMusic.put(MusicDBHelper.CN_GENRE,musicDto.genre)
        newMusic.put(MusicDBHelper.CN_RELEASE,musicDto.release_date)
        newMusic.put(MusicDBHelper.CN_RATE,musicDto.rate)

        val result = db.insert(MusicDBHelper.TABLE_NAME, null, newMusic)

        helper.close()
        return result   //id값 반환 -1반환 아니면 제대로 반환된 것임
    }

    fun updateMusicItem(id: Int, music: CharSequence, singer: CharSequence, genre: CharSequence, releaseDate: CharSequence, rating: Float,image: String) : Int{
        val db = helper.writableDatabase

        val updateValues = ContentValues().apply {
            put(MusicDBHelper.CN_MUSIC, music.toString())
            put(MusicDBHelper.CN_SINGER, singer.toString())
            put(MusicDBHelper.CN_GENRE, genre.toString())
            put(MusicDBHelper.CN_IMAGE , image)
            put(MusicDBHelper.CN_RELEASE, releaseDate.toString())
            put(MusicDBHelper.CN_RATE, rating)
        }

        val whereClause = BaseColumns._ID + "=?"
        val whereArgs = arrayOf(id.toString())

        val result = db.update(MusicDBHelper.TABLE_NAME, updateValues, whereClause, whereArgs)

        helper.close()
        return result
    }

    fun removeMusic(id: Int) : Int {
        val db = helper.writableDatabase
        //where _id = 1
        val whereCaluse = BaseColumns._ID + "=?"
        val whereArgs = arrayOf(id.toString())

        val result = db.delete(MusicDBHelper.TABLE_NAME, whereCaluse, whereArgs)

        helper.close()
        return result
    }
}
