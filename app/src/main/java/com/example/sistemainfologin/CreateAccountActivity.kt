package com.example.sistemainfologin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccountActivity : AppCompatActivity() {


    //Elementos da Interface do Usuário
    private var etFullname: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnRegister: Button? = null
    private var mProgress: ProgressDialog? = null


    //Referências ao Banco de Dados
    private var mDataBaseReference: DatabaseReference? = null
    private var mDataBase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"

    //Variáveis Globais
    private var fullname: String? = null
    private var email: String? = null
    private var password: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialize()
    }

    private fun initialize () {
        etFullname = findViewById(R.id.et_full_name) as EditText
        etEmail = findViewById(R.id.et_email) as EditText
        etPassword = findViewById(R.id.et_password) as EditText
        btnRegister = findViewById(R.id.btn_register) as Button
        mProgress = ProgressDialog(this)

        mDataBase = FirebaseDatabase.getInstance()
        mDataBaseReference = mDataBase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnRegister!!.setOnClickListener { createNewAccount() }

    }

    private fun createNewAccount() {
        fullname = etFullname?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
        ) {

        } else {
            Toast.makeText(this, "Insira todos os dados.", Toast.LENGTH_SHORT).show()
        }

        mProgress!!.setMessage("Registrando usuário...")
        mProgress!!.show()

        mAuth!!
            .createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                mProgress!!.hide()

                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")

                    val userId = mAuth!!.currentUser!!.uid

        //Usuário verificou o e-mail?
                    verifyEmail()

                    val currentUserDb = mDataBaseReference!!.child(userId)
                    currentUserDb.child("fullname").setValue(fullname)

        //Atualizar as Informações no BD
                    updateUserInfoAndUI()

                } else {
                    Log.w(TAG, "CreateUserWithEmail:Failure", task.exception)
                    Toast.makeText(this@CreateAccountActivity,"A Autenticação falhou.", Toast.LENGTH_SHORT).show()
                }


            }
    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@CreateAccountActivity,
                        "E-Mail ded verificação enviado para: " + mUser.getEmail(),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this@CreateAccountActivity,
                        "Falha ao enviar o e-mail de verificação.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


}