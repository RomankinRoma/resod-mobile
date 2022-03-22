package kz.reself.resod.api.model

data class OrganizationImage (
    var id :Long,
    var name :String,
    var storageUrl :String,
    var size :Long,
    var organizationId :Long,
    var fileExtension :String,
    var bucket :String,
    var header :Boolean,
)
