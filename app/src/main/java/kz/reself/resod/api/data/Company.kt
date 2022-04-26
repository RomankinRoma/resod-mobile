package kz.reself.resod.api.data

import kz.reself.resod.R

data class Company (
    val body: String,
    val description: String,
    val email: String,
    val id: Long,
    val name: String,
    val imgUrl: Int = R.drawable.company1
)