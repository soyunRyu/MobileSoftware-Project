package dduw.com.mobile.finalreport

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dduw.com.mobile.finalreport.data.MusicDto
import dduw.com.mobile.finalreport.databinding.ItemLayoutBinding

class MusicAdapter(var music : List<MusicDto>) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {
    val TAG = "MusicAdapter"

    override fun getItemCount(): Int = music.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MusicViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicAdapter.MusicViewHolder, position: Int) {
        val currentMusic = music[position]
        holder.itemBinding.apply {
            itemtitle.text = currentMusic.music
            itemSinger.text = currentMusic.singer
            ratingBar.rating = currentMusic.rate

            val imageName = currentMusic.image?.ifBlank { "add" } ?: "default_image"
            val resId = itemView.context.resources.getIdentifier(
                imageName,
                "mipmap",
                itemView.context.packageName
            )
            imageCover.setImageResource(resId)
        }
    }

    inner class MusicViewHolder(val itemBinding: ItemLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root){
        init {
            /*RecyclerView 항목 클릭 시 외부 click 이벤트 리스너 호출*/
            itemBinding.root.setOnClickListener{
                click?.onItemClick(it, adapterPosition)  // RecyclerView 항목 클릭 시 외부에서 지정한 리스너 호출
            }
            itemBinding.root.setOnLongClickListener {
                longClick?.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    /*사용자 정의 외부 click 이벤트 리스너 설정 */
    var click : OnItemClickListener? = null  // listener 를 사용하지 않을 때도 있으므로 null

    interface OnItemClickListener {
        fun onItemClick(view : View, position : Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.click = listener
    }

    var longClick : OnItemLongClickListener? = null

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int) : Boolean
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.longClick = listener
    }
}