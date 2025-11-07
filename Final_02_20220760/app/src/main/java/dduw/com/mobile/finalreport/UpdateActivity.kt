package dduw.com.mobile.finalreport

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dduw.com.mobile.finalreport.data.MusicDao
import dduw.com.mobile.finalreport.data.MusicDto
import dduw.com.mobile.finalreport.databinding.ActivityAddBinding
import dduw.com.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    lateinit var updateBinding: ActivityUpdateBinding
    lateinit var musicDao: MusicDao
    private var musicId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        musicDao = MusicDao(this)

        // 전달받은 데이터
        musicId = intent.getIntExtra("id", -1)
        val title = intent.getStringExtra("title") ?: ""
        val singer = intent.getStringExtra("singer") ?: ""
        val genre = intent.getStringExtra("genre") ?: ""
        val releaseDate = intent.getStringExtra("releaseDate") ?: ""
        val rate = intent.getFloatExtra("rate", 0.0f)
        val image = intent.getStringExtra("image") ?: ""

        // 기존 값 넣기
        updateBinding.edTitle.setText(title)
        updateBinding.edSinger.setText(singer)
        updateBinding.edGenre.setText(genre)
        updateBinding.edReleaseDate.setText(releaseDate)
        updateBinding.edRatingStar.rating = rate

        val resId = resources.getIdentifier(image, "mipmap", packageName)
        updateBinding.imageView.setImageResource(resId)
        updateBinding.imageView.tag = image

        updateBinding.btnOk.setOnClickListener {
            val updatedTitle = updateBinding.edTitle.text.toString()
            val updatedSinger = updateBinding.edSinger.text.toString()
            val updatedGenre = updateBinding.edGenre.text.toString()
            val updatedReleaseDate = updateBinding.edReleaseDate.text.toString()
            val updatedRate = updateBinding.edRatingStar.rating
            val updatedImage = updateBinding.imageView.tag?.toString()?.substringBefore('.') ?: ""

            musicDao.updateMusicItem(musicId, updatedTitle, updatedSinger, updatedGenre, updatedReleaseDate, updatedRate, updatedImage)
            setResult(RESULT_OK)
            finish()
        }

        //취소 버튼 누르기
        updateBinding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
