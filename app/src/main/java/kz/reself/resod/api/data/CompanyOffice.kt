package kz.reself.resod.api.data

data class CompanyOffice(
    val id: Long,
    val orgId: Long,
    val countryId: Long,
    val city: String,
    val address: String
)
