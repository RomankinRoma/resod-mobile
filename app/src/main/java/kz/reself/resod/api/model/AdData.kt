package kz.reself.resod.api.model

import lombok.Data
import java.math.BigDecimal

@Data
data class AdData (
    var id :Long,
    var name :String,
    var description :String,
    var orgId :Long,
    var adState: AdState,
    var serviceType: ServiceType,
    var buildingType: BuildingType,
//    var organization: Organization,
    var countryId :Long,
    var price :BigDecimal,
    var area :BigDecimal,
    var roomCount :Int,
    var bathType :String,
    var floorCount :Int,
    var statusType: StatusType,
    var constructionStageType: ConstructionStage,
    var characteristicType: CharacteristicType,
    var locationType: LocationType,
    var longitude: BigDecimal,
    var latitude: BigDecimal,
    var images:List<AdImage>,
    var equity:BigDecimal,
    var profitability:BigDecimal,
    var constructionYear:Int,
    var kitchenArea:BigDecimal,
    var livingArea:BigDecimal,
    var plotArea:BigDecimal,
    var floorNumber:Int,
    var fenceType:String,
    var rentPeriod:String,
    var employeeId: Long,
    var advertisementType: AdvertisementType,
    var features: Set<Feature>
    )
