package com.example.showmethemoneyproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListViewAdapter(val List : MutableList<UsageDataModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return List.size
    }

    override fun getItem(position: Int): Any {
        return List[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.listview_item, parent, false)
        }


        val time = convertView?.findViewById<TextView>(R.id.whenUsed)
        val location = convertView?.findViewById<TextView>(R.id.whereUsed)
        val method = convertView?.findViewById<TextView>(R.id.howUsed)
        val usage = convertView?.findViewById<TextView>(R.id.usage)

        time!!.text = List[position].time
        location!!.text = List[position].location
        method!!.text = List[position].method
        usage!!.text = List[position].usage

        return convertView!!
    }


}