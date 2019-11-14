package me.s32xlevel.xsollaweather.business.model

import androidx.room.*

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "is_saved")
    val isSaved: Boolean = false
)

@Entity(tableName = "weather", foreignKeys = [
    ForeignKey(entity = CityEntity::class, parentColumns = ["id"], childColumns = ["city_id"], onDelete = ForeignKey.CASCADE)
])
data class WeatherEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "city_id")
    val cityId: Int,
    @ColumnInfo(name = "date_txt")
    val dateTxt: String,
    @ColumnInfo(name = "temp")
    val temp: Double,
    @ColumnInfo(name = "description")
    val description: String
)

class CityWithWeather() {
    @Embedded
    lateinit var city: CityEntity

    @Relation(parentColumn = "id", entityColumn = "city_id", entity = WeatherEntity::class)
    lateinit var weathers: List<WeatherEntity>
}