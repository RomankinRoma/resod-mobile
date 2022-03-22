package kz.reself.resod.api.model
import lombok.Data
import lombok.Getter
import java.sql.Timestamp
@Data
open class BaseEntity {
    lateinit var createdAt : Timestamp
    lateinit var updatedAt : Timestamp
    lateinit var deletedAt : Timestamp
    lateinit var createdBy : String
    lateinit var updatedBy : String
    lateinit var deletedBy : String

}