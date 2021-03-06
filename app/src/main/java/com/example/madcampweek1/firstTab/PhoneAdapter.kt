package com.example.madcampweek1.firstTab

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.activity_phone_book_show.view.*
import kotlinx.android.synthetic.main.contact_item.view.*

data class Phone(val id:String?, val name:String?, val phone:String?)

class PhoneAdapter(private val list: List<Phone>) : RecyclerView.Adapter<PhoneAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item,
            parent, false)
        return Holder(view)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val phone = list[position]

        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "clicked: ${phone.name}, ${phone.phone}", Toast.LENGTH_SHORT).show()
            val intent = Intent(it.context, PhoneBookShowActivity::class.java)
            intent.putExtra("ProfileImage", R.drawable.ic_human)
//          여기서 잠깐 프로필사진에 컬러필터 뭐 씌울지 저장
            var num = 0
            var colorFilter = "";
            try {
                num = Character.getNumericValue(phone.phone.toString().last())
            } catch (nfe: NumberFormatException) {
                // not a valid int
            }
            colorFilter = when(num % 3) {
                0 -> "#db6f48"
                1 -> "#d8db48"
                else -> "#48d5cc"
            }
//
            intent.putExtra("ColorFilter", colorFilter)
            intent.putExtra("Name", phone.name)
            intent.putExtra("PhoneNumber", phone.phone)

            it.context.startActivity(intent)
        }

        holder.apply{
            setPhone(listener, phone)
            itemView.tag = phone
        }
    }
    override fun getItemCount() = list.size

    @SuppressLint("MissingPermission")
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mPhone: Phone? = null
        init {
            itemView.btnPhone.setOnClickListener {
                mPhone?.phone.let { phoneNumber ->
                    val uri = Uri.parse("tel:${phoneNumber.toString()}")
                    val intent = Intent(Intent.ACTION_CALL, uri)
                    itemView.context.startActivity(intent)
                }
            }
        }
        fun setPhone(listener: View.OnClickListener, phone: Phone) {
            this.mPhone = phone
            var num = 0
            try {
                num = Character.getNumericValue(phone.phone.toString().last())
            } catch (nfe: NumberFormatException) {
                // not a valid int
            }
            when(num % 3) {
                0 -> itemView.image_view.setColorFilter(Color.parseColor("#db6f48"))
                1 -> itemView.image_view.setColorFilter(Color.parseColor("#d8db48"))
                else -> itemView.image_view.setColorFilter(Color.parseColor("#48d5cc"))
            }
            itemView.textName.text = phone.name
            itemView.textPhone.text = phone.phone
            itemView.setOnClickListener(listener)
        }
    }
}
