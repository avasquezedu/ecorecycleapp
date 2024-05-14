package com.example.eco_recycle_app.ui.campaign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.eco_recycle_app.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CampaignDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class CampaignDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var lblId: TextView
    private lateinit var lblName: TextView
    private lateinit var lblDescription: TextView
    private lateinit var lblStartDate: TextView
    private lateinit var lblEndDate: TextView
    private lateinit var lblType: TextView
    private lateinit var lblStatus: TextView

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
        val view = inflater.inflate(R.layout.fragment_campaign_detail, container, false)
        initReferences(view)
        setData()
        val btnBackToList = view.findViewById<ImageButton>(R.id.btnBackToList)
        btnBackToList.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_detail, CampaignFragment())
                .addToBackStack(null)
                .commit()
        }
        return view
    }

    private fun initReferences(view: View) {
        lblId = view.findViewById(R.id.lblid)
        lblName = view.findViewById(R.id.lblName)
        lblDescription = view.findViewById(R.id.lblDescription)
        lblStartDate = view.findViewById(R.id.lblStartDate)
        lblEndDate = view.findViewById(R.id.lblEndDate)
        lblType = view.findViewById(R.id.lblType)
        lblStatus= view.findViewById(R.id.lblStatus)
    }

    private fun setData() {
        lblId.text = arguments?.getString("id").toString()
        lblName.text = arguments?.getString("name").toString()
        lblDescription.text = arguments?.getString("description").toString()
        lblStartDate.text = arguments?.getString("startDate").toString()
        lblEndDate.text = arguments?.getString("endDate").toString()
        lblType.text = arguments?.getString("type").toString()
        lblStatus.text = arguments?.getString("status").toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CampaignDetail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CampaignDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}