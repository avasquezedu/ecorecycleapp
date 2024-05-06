package com.example.eco_recycle_app.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.eco_recycle_app.entity.Campaign
import com.example.eco_recycle_app.util.SQLiteHelper

class CampaignRepository(context: Context) {
    private var sqLiteHelper: SQLiteHelper = SQLiteHelper(context)
    val TABLE_CAMPAIGNS: String = "campaigns"

    fun addCampaign(campaign: Campaign): String {
        var response = "";
        val db = sqLiteHelper.writableDatabase
        try {
            val values = ContentValues()
            values.put("name", campaign.name)
            values.put("description", campaign.description)
            values.put("start_date", campaign.startDate)
            values.put("end_date", campaign.endDate)
            values.put("type", campaign.type)
            values.put("status", campaign.status)
            val res = db.insert(TABLE_CAMPAIGNS, null, values)
            if (res.toInt() == -1) {
                response = "Error to insert Campaign"
            } else {
                response = "Campaign Registered successfully"
            }
        } catch (ex: Exception) {
            Log.d("===", ex.message.toString())
            response = "error ocurred"
        }
        return response;
    }

    fun updateCampaign(campaign: Campaign): String {
        var response = "";
        val db = sqLiteHelper.writableDatabase
        try {
            val values = ContentValues()
            values.put("name", campaign.name)
            values.put("description", campaign.description)
            values.put("start_date", campaign.startDate)
            values.put("end_date", campaign.endDate)
            values.put("type", campaign.type)
            values.put("status", campaign.status)
            val res = db.update(TABLE_CAMPAIGNS, values, "id=" + campaign.id, null)
            if (res == -1) {
                response = "Error to update Campaign"
            } else {
                response = "Campaign updated successfully"
            }
        } catch (ex: Exception) {
            Log.d("===", ex.message.toString())
            response = "error ocurred"
        }
        return response;
    }

    fun deleteCampaign(id: Int): String {
        var response = "";
        val db = sqLiteHelper.writableDatabase
        try {
            val res = db.delete(TABLE_CAMPAIGNS, "id=$id",null)
            if (res == -1) {
                response = "Error to delete Campaign"
            } else {
                response = "Campaign Deleted successfully"
            }
        } catch (ex: Exception) {
            Log.d("===", ex.message.toString())
            response = "error ocurred"
        }
        return response;
    }

    fun getCampaigns(): ArrayList<Campaign> {
        val campaigns : ArrayList<Campaign> = ArrayList()
        val query = "SELECT * FROM campaigns"
        val db = sqLiteHelper.readableDatabase
        val cursor: Cursor
        try {
            cursor = db.rawQuery(query, null)
            cursor.moveToFirst()
            do {
                val id:Int = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name:String = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val description:String = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val startDate:String = cursor.getString(cursor.getColumnIndexOrThrow("start_date"))
                val endDate:String = cursor.getString(cursor.getColumnIndexOrThrow("end_date"))
                val type:String = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                val status:String = cursor.getString(cursor.getColumnIndexOrThrow("status"))
                val campaign = Campaign()
                campaign.id = id
                campaign.name = name
                campaign.description = description
                campaign.startDate = startDate
                campaign.endDate = endDate
                campaign.type = type
                campaign.status = status
                campaigns.add(campaign)
            } while (cursor.moveToNext())
        } catch (ex: Exception) {
            Log.d("===", ex.message.toString())
        } finally {
            db.close()
        }
        return campaigns;
    }
}