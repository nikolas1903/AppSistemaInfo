package com.example.sistemainfologin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>(){
    private var prsList: ArrayList<PersonModel> = ArrayList()
    private var onClickDeleteItem: ((PersonModel) -> Unit)? = null

    fun addItems(items:ArrayList<PersonModel>){
        this.prsList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PersonViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_prs,parent, false)
    )

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val prs = prsList[position]
        holder.bindView(prs)
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(prs) }
    }

    override fun getItemCount(): Int {
     return prsList.size
    }

    fun setOnClickDeleteItem(callback: (PersonModel) -> Unit){
        this.onClickDeleteItem = callback

    }


    class PersonViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var cpf = view.findViewById<TextView>(R.id.tvCpf)
        private var address = view.findViewById<TextView>(R.id.tvAddress)
        private var phone = view.findViewById<TextView>(R.id.tvPhone)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(prs:PersonModel){
            id.text = prs.id.toString()
            name.text = prs.name.toString()
            cpf.text = prs.cpf.toString()
            address.text = prs.address.toString()
            phone.text = prs.phone.toString()


        }
    }
}