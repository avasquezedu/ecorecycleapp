package com.example.eco_recycle_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eco_recycle_app.R
import com.example.eco_recycle_app.entity.Campaign
import com.example.eco_recycle_app.ui.campaign.CampaignFragment
import com.example.eco_recycle_app.ui.campaign.FormCampaignFragment


class CampaignAdapter(private val listener: MyAdapterListener): RecyclerView.Adapter<CampaignAdapter.MyViewHolder>() {

    private var campaigns: ArrayList<Campaign> = ArrayList()
    private lateinit var context: Context

    private var onClickDeleteItem: ((Campaign) -> Unit)? = null
    private var onClickEditItem: ((Campaign) -> Unit)? = null

    fun onClickDeleteItem(callback: (Campaign) -> Unit) {
        onClickDeleteItem = callback
    }

    fun onClickEditItem(callback: (Campaign) -> Unit) {
        onClickEditItem = callback
    }

    fun addData(items: ArrayList<Campaign>) {
        this.campaigns = items
    }

    fun contextMethod(context: Context) {
        this.context = context;
    }

    interface MyAdapterListener {
        fun onItemClicked()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.txtItemName)
        private var type = view.findViewById<TextView>(R.id.txtItemType)
        private var status = view.findViewById<TextView>(R.id.txtItemStatus)

        var btnEdit = view.findViewById<ImageButton>(R.id.btnItemEdit)
        var btnDelete = view.findViewById<ImageButton>(R.id.btnItemDelete)

        fun bindView(campaign: Campaign) {
            name.text = campaign.name
            type.text = campaign.type
            status.text = campaign.status
        }
        /*init {
            view.setOnClickListener {
                listener.onItemClicked()
            }}*/
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.fragment_campaign_item, parent, false)
    )

    override fun onBindViewHolder(holder: CampaignAdapter.MyViewHolder, position: Int) {
       val campaignItem = campaigns[position]
        holder.bindView(campaignItem)

        holder.btnEdit.setOnClickListener {
            //CampaignRepository(context).deleteCampaign(campaigns[position].id)
            onClickEditItem?.invoke(campaignItem)
        }

        //delete
        holder.btnDelete.setOnClickListener {
            //CampaignRepository(context).deleteCampaign(campaigns[position].id)
            onClickDeleteItem?.invoke(campaignItem)
        }
    }

    override fun getItemCount(): Int {
        return campaigns.size
    }

}