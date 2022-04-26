package kz.reself.resod.api.model

import kz.reself.resod.api.data.Company

data class CompanyDTO(
    val number: Int,
    val numberOfElements: Int,
    val size: Int,
    val content: List<Company>
)
