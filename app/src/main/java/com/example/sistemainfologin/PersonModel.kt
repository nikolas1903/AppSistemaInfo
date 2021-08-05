package com.example.sistemainfologin

import java.util.*

data class PersonModel (
    val id: Int = getAutoId(),
    var name: String = "",
    var cpf: String = "",
    var address: String = "",
    var phone: String = "",
){
    companion object{
        fun getAutoId():Int{
            val random = Random()
            return random.nextInt(100)
        }
    }

}