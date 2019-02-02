package `in`.nishachar.mypasscode.shared

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey var id: String,
    var userName : String,
    var passCode : Int,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (userName != other.userName) return false
        if (passCode != other.passCode) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image?.contentEquals(other.image!!)!!) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + passCode
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}
