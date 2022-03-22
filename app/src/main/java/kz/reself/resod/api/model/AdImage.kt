package kz.reself.resod.api.model

data class AdImage(
    var id :Long,
    var name :String,
    var storageUrl :String,
    var size :Long,
    var adId :Long,
    var fileExtension :String,
    var bucket :String,
)
