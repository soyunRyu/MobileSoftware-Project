package dduw.com.mobile.finalreport

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dduw.com.mobile.finalreport.data.MusicDao
import dduw.com.mobile.finalreport.data.MusicDto
import dduw.com.mobile.finalreport.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var addBinding: ActivityAddBinding
    lateinit var musicDao: MusicDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        musicDao = MusicDao(this)

        // $music - $singer / $image / $genre / $release_date / $rate"
        addBinding.btnOk.setOnClickListener {
            val music = addBinding.edTitle.text
            val singer = addBinding.edSinger.text
            val genre = addBinding.edGenre.text
            val releaseDate = addBinding.edReleaseDate.text
            val rate = addBinding.edRatingStar.rating

            musicDao.addMusic(MusicDto(null, music.toString(),
                singer.toString(), "add",genre.toString(), releaseDate.toString(), rate))

            setResult(RESULT_OK)
            finish()
        }

        //취소 버튼 누르기
        addBinding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }
}