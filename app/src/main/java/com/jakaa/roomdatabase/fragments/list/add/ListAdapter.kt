package com.jakaa.roomdatabase.fragments.list.add

import android.content.Context
import android.provider.DocumentsContract.Root
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jakaa.roomdatabase.R
import com.jakaa.roomdatabase.databinding.CustomRowBinding
import com.jakaa.roomdatabase.model.User

class ListAdapter(): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

   // private lateinit var binding: CustomRowBinding
    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val id_txt = itemView.findViewById<TextView>(R.id.id_txt)
        val firstName_txt = itemView.findViewById<TextView>(R.id.firstName_txt)
        val lastName_txt = itemView.findViewById<TextView>(R.id.lastName_txt)

        val rowLayout = itemView.rootView


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.id_txt.text = currentItem.id.toString()
        holder.firstName_txt.text = currentItem.firstName
        holder.lastName_txt.text = currentItem.lastName

        holder.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }
    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }
}