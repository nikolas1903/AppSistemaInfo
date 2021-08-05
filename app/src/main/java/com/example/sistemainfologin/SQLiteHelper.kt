package com.example.sistemainfologin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){


companion object{
    private const val DATABASE_VERSION = 1
    private const val DATABASE_NAME = "sistemaInfo"
    private const val TABLE_NAME = "pessoas"
    private const val ID = "id"
    private const val NAME = "name"
    private const val CPF = "cpf"
    private const val ADDRESS = "address"
    private const val PHONE = "phone"
}



    override fun onCreate(db: SQLiteDatabase?) {
        val createTblPessoas = ("CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + CPF + " TEXT,"
                + ADDRESS + " TEXT,"
                + PHONE + " TEXT" + ")")

        db?.execSQL(createTblPessoas)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertPerson(prs: PersonModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, prs.id)
        contentValues.put(NAME, prs.name)
        contentValues.put(CPF, prs.cpf)
        contentValues.put(ADDRESS, prs.address)
        contentValues.put(PHONE, prs.phone)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }


    fun getAllPerson(): ArrayList<PersonModel>{
        val prsList: ArrayList<PersonModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var name:String
        var cpf:String
        var address:String
        var phone:String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                cpf = cursor.getString(cursor.getColumnIndex("cpf"))
                address = cursor.getString(cursor.getColumnIndex("address"))
                phone = cursor.getString(cursor.getColumnIndex("phone"))

                val prs = PersonModel(id = id, name = name, cpf = cpf, address = address, phone = phone)
                prsList.add(prs)
            }while(cursor.moveToNext())
        }
    return prsList
    }

    fun deletePersonById(id:Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put (ID, id)

        val success = db.delete(TABLE_NAME, "id=$id", null)
        db.close()
        return success
    }

}