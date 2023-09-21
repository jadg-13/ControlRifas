package site.soymegh.controlrifas.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import site.soymegh.controlrifas.R
import site.soymegh.controlrifas.data.adapter.AdapterRegister.*
import site.soymegh.controlrifas.data.entities.Register

class AdapterRegister (
    val listRegister: MutableList<Register>
): RecyclerView.Adapter<RegisterViewHolder>(), RegisterViewHolder.OnItemDeleteListener{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = RegisterViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))

        // Set the onItemDeleteListener
        viewHolder.onItemDeleteListener = this

        return viewHolder
    }


    override fun getItemCount():Int = listRegister.size

    override fun onBindViewHolder(holder: RegisterViewHolder, position: Int) {
        val item = listRegister[position]
        holder.render(item)
    }
    override fun onItemDelete(register: Register) {
        // Find the index of the deleted item
        val index = listRegister.indexOf(register)
        if (index != -1) {
            // Remove the item from the list
            listRegister.removeAt(index)
            // Notify the adapter that an item has been removed
            notifyItemRemoved(index)
        }
    }

}
