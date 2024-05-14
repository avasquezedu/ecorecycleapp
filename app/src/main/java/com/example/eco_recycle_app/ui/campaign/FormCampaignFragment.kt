package com.example.eco_recycle_app.ui.campaign

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.eco_recycle_app.R
import com.example.eco_recycle_app.entity.Campaign
import com.example.eco_recycle_app.repository.CampaignRepository

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FormCampaignFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FormCampaignFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var txtName: EditText
    private lateinit var txtDescription: EditText
    private lateinit var txtStartDate: EditText
    private lateinit var txtEndDate: EditText
    private lateinit var txtType: RadioButton
    private lateinit var btnRegister: Button
    private lateinit var btnBack: ImageButton
    private  lateinit var lblTitle: TextView
    private var edition = false;
    private var id: Int = 0
    private lateinit var campaignRepository: CampaignRepository

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
        val view = inflater.inflate(R.layout.fragment_form_campaign, container, false)
        val btnBackToCampaign = view.findViewById<ImageButton>(R.id.btnBackToCampaign)
        btnBackToCampaign.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_form_campaign, CampaignFragment())
                .addToBackStack(null)
                .commit()
            btnRegister.visibility = View.INVISIBLE
        }
        campaignRepository = CampaignRepository(requireContext())
        initReferences(view)
        setDataEditCampaign(view)
        return view
    }

    fun initReferences(view: View) {
        txtName = view.findViewById(R.id.txtName)
        txtDescription = view.findViewById(R.id.txtDescription)
        txtStartDate = view.findViewById(R.id.txtStartDate)
        txtEndDate = view.findViewById(R.id.txtEndDate)
        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val selectedRadioButtonId: Int = radioGroup.checkedRadioButtonId
        txtType = view.findViewById(selectedRadioButtonId)
        btnRegister = view.findViewById(R.id.btnRegisterCampaign)
        btnBack = view.findViewById(R.id.btnBackToCampaign)
        lblTitle = view.findViewById(R.id.lblTitle)

        btnRegister.setOnClickListener {
            val selectedRadioButtonId1: Int = radioGroup.checkedRadioButtonId
            txtType = view.findViewById(selectedRadioButtonId1)
            if(edition)
                editCampaign()
            if (!edition)
                addCampaign()
        }
    }

    fun setDataEditCampaign(view: View) {
        if (arguments != null) {
            edition = true
            id = arguments?.getString("id").toString().toInt()
            lblTitle.text = "Editar Datos de Campaña"
            btnRegister.text = "Guardar Cambios"
            txtName.setText(arguments?.getString("name"))
            txtDescription.setText(arguments?.getString("description"))
            txtStartDate.setText(arguments?.getString("startDate"))
            txtEndDate.setText(arguments?.getString("endDate"))
            if ("Marketing" == arguments?.getString("type")) {
                txtType = view.findViewById(R.id.rbdCampaignType1)
                view.findViewById<RadioGroup>(R.id.radioGroup).check(txtType.id)
            } else {
                txtType = view.findViewById(R.id.rbdCampaignType2)
                view.findViewById<RadioGroup>(R.id.radioGroup).check(txtType.id)
            }
        }
    }

    private fun prepareRequest(): Campaign {
        val campaign = Campaign()
        campaign.name = txtName.text.toString()
        campaign.description = txtDescription.text.toString()
        campaign.startDate = txtStartDate.text.toString()
        campaign.endDate = txtEndDate.text.toString()
        campaign.type = txtType.text.toString()
        campaign.status = "EN PROCESO"
        return campaign
    }

    private fun editCampaign() {
        var campaign = prepareRequest()
        campaign.id = id
        val campaignRepository = CampaignRepository(requireContext())
        val message = campaignRepository.updateCampaign(campaign)
        showMessage(message)
    }

    private fun addCampaign() {
        var campaign = prepareRequest()
        val message = campaignRepository.addCampaign(campaign)
        showMessage(message)
    }

    fun showMessage(message: String) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Information Message")
        dialog.setMessage(message)
        dialog.setPositiveButton("Acept") { dialogInterface: DialogInterface, i: Int ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_form_campaign, CampaignFragment())
                .addToBackStack(null)
                .commit()
            btnRegister.visibility = View.INVISIBLE
            btnBack.visibility = View.INVISIBLE
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
         * @return A new instance of fragment FormCampaignFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FormCampaignFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}