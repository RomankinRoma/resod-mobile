package kz.reself.resod.api.model

data class Organization(
    var id :Long,
    var name :String,
    var description :String,
    var body :String,
    var email :String,
    var phone :String,
    var root :Boolean,
    var main :Boolean,
    var images:List<OrganizationImage>
)
