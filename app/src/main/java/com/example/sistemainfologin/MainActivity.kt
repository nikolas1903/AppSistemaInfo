package com.example.sistemainfologin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edName: EditText
    private lateinit var edCpf: EditText
    private lateinit var edAddress: EditText
    private lateinit var edPhone: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter:PersonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()

        sqLiteHelper = SQLiteHelper(this)
        btnAdd.setOnClickListener{ addPerson() }

        btnView.setOnClickListener{ getPerson() }


        adapter?.setOnClickDeleteItem {
            deletePerson(it.id)
        }
    }

    private fun getPerson(){
        val prsList = sqLiteHelper.getAllPerson()
        Log.e("ppp", "${prsList.size}")

        adapter?.addItems(prsList)
    }

    private fun addPerson(){
        val name = edName.text.toString()
        val cpf = edCpf.text.toString()
        val address = edAddress.text.toString()
        val phone = edPhone.text.toString()
        val intent = Intent(this, SuccessActivity::class.java)

        if(name.isEmpty() || cpf.isEmpty()){
            Toast.makeText(this, "Preencha os campos necessários.", Toast.LENGTH_SHORT).show()
        }else if(cpf.length != 11){
            Toast.makeText(this, "O CPF deve conter 11 dígitos.", Toast.LENGTH_SHORT).show()
        } else{
            val prs = PersonModel(name = name, cpf = cpf, address = address, phone = phone)
            val status = sqLiteHelper.insertPerson(prs)

            if(status > -1) {
                Toast.makeText(this, "Pessoa adicionada com sucesso! CPF: $cpf", Toast.LENGTH_SHORT).show()
                clearEditText()
                startActivity(intent)
            }else{
                Toast.makeText(this, "Pessoa não adicionada... Verificar.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun deletePerson(id:Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Tem certeza que deseja excluir esse cadastro?")
        builder.setCancelable(true)

        builder.setPositiveButton("Tenho Certeza") { dialog, _ ->
            sqLiteHelper.deletePersonById(id)
            getPerson()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        var alert = builder.create()
        alert.show()
    }


    private fun clearEditText(){
        edName.setText("")
        edCpf.setText("")
        edAddress.setText("")
        edPhone.setText("")
        edName.requestFocus()

    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager (this)
        adapter =  PersonAdapter()
        recyclerView.adapter = adapter

    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edCpf = findViewById(R.id.edCpf)
        edAddress = findViewById(R.id.edAddress)
        edPhone = findViewById(R.id.edPhone)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        recyclerView = findViewById(R.id.recyclerView)
    }
}