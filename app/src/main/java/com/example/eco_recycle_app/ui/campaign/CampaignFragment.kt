package com.example.eco_recycle_app.ui.campaign

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eco_recycle_app.R
import com.example.eco_recycle_app.adapter.CampaignAdapter
import com.example.eco_recycle_app.entity.Campaign
import com.example.eco_recycle_app.repository.CampaignRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CampaignFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CampaignFragment : Fragment(), CampaignAdapter.MyAdapterListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var campaignRepository: CampaignRepository
    private lateinit var rvCampaigns: RecyclerView
    private var adapter: CampaignAdapter = CampaignAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_campaign, container, false)
        val btnNewCampaign = view.findViewById<FloatingActionButton>(R.id.btnNewCampaign)
        btnNewCampaign.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_campaign, FormCampaignFragment())
            transaction.addToBackStack(null)
            transaction.commit()
            btnNewCampaign.visibility = View.INVISIBLE
        }
        initReferences(view)
        loadData()
        return view
    }

    private fun initReferences(view: View) {
        campaignRepository = CampaignRepository(requireContext())
        rvCampaigns = view.findViewById(R.id.rvCampaigns)
        rvCampaigns.layoutManager = LinearLayoutManager(requireContext())
        rvCampaigns.adapter = adapter

        adapter.onClickDeleteItem {
            deletePerson(it.id)
        }
        adapter.onClickEditItem {
            val bundle = Bundle()
            bundle.putString("id", it.id.toString())
            bundle.putString("name", it.name)
            bundle.putString("description", it.description)
            bundle.putString("startDate", it.startDate)
            bundle.putString("endDate", it.endDate)
            bundle.putString("type", it.type)
            val formFragment = FormCampaignFragment()
            formFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_campaign, formFragment)
                .addToBackStack(null)
                .commit()
        }
    }
    private fun loadData() {
        val campaigns = campaignRepository.getCampaigns()
        adapter.addData(campaigns)
        adapter.contextMethod(requireContext())

    }

    fun deletePerson(id: Int) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage("Do you want to delete the campaign?")
        dialog.setCancelable(true)
        dialog.setPositiveButton("DELETE", DialogInterface.OnClickListener{ dialog, which ->
            var message = campaignRepository.deleteCampaign(id)
            loadData()
            showMessage(message)
            dialog.dismiss()
        })
        dialog.setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        dialog.create().show()
    }

    fun showMessage(message: String) {
        val dialog = android.app.AlertDialog.Builder(requireContext())
        dialog.setTitle("Information Message")
        dialog.setMessage(message)
        dialog.setPositiveButton("Acept") { dialogInterface: DialogInterface, i: Int ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_campaign, CampaignFragment())
                .addToBackStack(null)
                .commit()
        }
        dialog.create().show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CampaignFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(campaign: Campaign) =
            CampaignDetail().apply {
                arguments = Bundle().apply {
                    putString("id", campaign.id.toString())
                    putString("name", campaign.name)
                    putString("description", campaign.description)
                    putString("startDate", campaign.startDate)
                    putString("endDate", campaign.endDate)
                    putString("type", campaign.type)
                    putString("status", campaign.status)
                }
            }
    }

    override fun onItemClicked(campaign: Campaign) {
        val campaignDetail = newInstance(campaign)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_campaign, campaignDetail)
            .addToBackStack(null)
            .commit()
    }
}