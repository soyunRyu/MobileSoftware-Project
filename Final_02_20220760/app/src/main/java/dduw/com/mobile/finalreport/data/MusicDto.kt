package dduw.com.mobile.finalreport.data

import java.io.Serializable

class MusicDto(val id:Int?, var music:String, var singer:String, var image:String,
               var genre:String, var release_date:String, var rate: Float): Serializable{
    override fun toString() = "[${id?:0}] $music - $singer / $image /$genre / $release_date / $rate"
}
