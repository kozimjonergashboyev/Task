package uz.kozimjon.taskapp.model

import java.io.Serializable

data class CharacterData(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val image: String?,
    val created: String?
) : Serializable
