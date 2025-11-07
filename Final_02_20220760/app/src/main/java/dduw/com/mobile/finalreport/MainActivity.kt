//과제명 : 음악 리뷰 앱
//분반 : 02분반
//학번 : 20220760 성명 : 류소연
//제출일 : 2025년 6월 25일
package dduw.com.mobile.finalreport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dduw.com.mobile.finalreport.data.MusicDao
import dduw.com.mobile.finalreport.data.MusicDto
import dduw.com.mobile.finalreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var musicDao: MusicDao
    lateinit var adapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        musicDao = MusicDao(this)

        adapter = MusicAdapter(musicDao.getAllMusic())

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvMusic.layoutManager = layoutManager
        binding.rvMusic.adapter = adapter   // RecylcerView 에 adapter 설정

        // 클릭 시 수정
        adapter.setOnItemClickListener(object : MusicAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val music = adapter.music[position]

                val intent = Intent(this@MainActivity, UpdateActivity::class.java).apply {
                    putExtra("id", music.id)
                    putExtra("title", music.music)
                    putExtra("singer", music.singer)
                    putExtra("genre", music.genre)
                    putExtra("releaseDate", music.release_date)
                    putExtra("rate", music.rate)
                    putExtra("image", music.image)
                }

                startActivityForResult(intent, 100)
            }
        })

        // 롱클릭 시 삭제
        adapter.setOnItemLongClickListener(object : MusicAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int): Boolean {
                val id = adapter.music[position].id ?: 0
                var result = 1

                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("음악 삭제")
                    setMessage("해당 음악을 삭제하시겠습니까?")
                    setPositiveButton("Ok") { di, _ ->
                        result = musicDao.removeMusic(id)
                        if (result > 0) {
                            adapter.music = musicDao.getAllMusic()
                            adapter.notifyDataSetChanged()
                        }
                        di.cancel()
                    }
                    setNegativeButton("취소", null)
                    setCancelable(false)
                }
                builder.show()
                return true
            }
        })
    }

    //옵션 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    //메뉴 클릭 이벤트 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addMusic ->{ //노래 추가
                val intent = Intent (this, AddActivity::class.java)
                startActivityForResult(intent, 100)
                true
            }
            R.id.introProgrammer ->{
                val builder : AlertDialog.Builder = AlertDialog.Builder(this).apply {
                    setTitle("개발자 소개")
                    setIcon(R.mipmap.my_pic)
                    setItems(R.array.introP, null)
                    setPositiveButton("확인") {  di, _ -> di.cancel()  }
                    setCancelable(false)
                }
                builder.show()
                true
            }
            R.id.finishApp ->{ //종료 확인 다이얼로그 출력 후 종료 처리
                val builder : AlertDialog.Builder = AlertDialog.Builder(this).apply {
                    setTitle("앱 종료")
                    setMessage("앱을 종료하시겠습니까?")
                    setPositiveButton("종료") { _, _ -> finish() }
                    setNegativeButton("취소") { di, _ -> di.cancel() }
                    setCancelable(false)
                }
                builder.show()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(  //addActivity에서 결과값 받아서 화면에 나타냄
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            val updatedList = musicDao.getAllMusic()
            adapter.music = updatedList
            adapter.notifyDataSetChanged()
        }
    }
}