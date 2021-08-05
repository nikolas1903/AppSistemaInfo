package com.example.sistemainfologin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //Variáveis Blogais
    private var email: String? = null
    private var password: String? = null

    //Elementos da Interface
    private var tvForgotPassword: TextView? = null
    private var etEmail: TextView? = null
    private var etPassword: TextView? = null
    private var btnLogin: TextView? = null
    private var btnRegister: TextView? = null
    private var mProgress: ProgressDialog? = null

    //Ref Ao BD
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColorTo(R.color.colorPrimary)
        }

        initialize()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Window.setStatusBarColorTo(color: Int) {
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.statusBarColor = ContextCompat.getColor(baseContext, color)
    }

    private fun initialize(){
        tvForgotPassword = findViewById(R.id.tv_forgot_password) as TextView
        etEmail = findViewById(R.id.et_email) as EditText
        etPassword = findViewById(R.id.et_password) as EditText
        btnLogin = findViewById(R.id.btn_login) as Button
        btnRegister = findViewById(R.id.btn_register) as Button
        mProgress = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        tvForgotPassword!!.setOnClickListener { startActivity(Intent(this@LoginActivity,
            ForgotPasswordActivity::class.java)) }

    btnRegister!!.setOnClickListener{ startActivity(Intent(this@LoginActivity,
        CreateAccountActivity::class.java)) }
        btnLogin!!.setOnClickListener { loginUser() }
    }


    private fun loginUser() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) ){
        mProgress!!.setMessage("Verificando o usuário...")
        mProgress!!.show()
        Log.d(TAG, "Login do Usuário")

        mAuth!!.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this){
            task ->

            mProgress!!.hide()

            if(task.isSuccessful){
                Log.d(TAG, "Logado com Sucesso.")
                updateUi()
            }else{
                Log.d(TAG, "Erro ao Logar", task.exception)
                Toast.makeText(this@LoginActivity, "A Autenticação Falhou.", Toast.LENGTH_SHORT).show()
            }
        }

        } else{
            Toast.makeText(this, "Preencha todos os dados.", Toast.LENGTH_SHORT).show()
    }
    }


    private fun updateUi(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


}