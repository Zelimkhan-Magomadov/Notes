package zelimkhan.magomadov.notes.data.uisettings.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ui_settings")
data class UiSettings(
    @PrimaryKey
    val id: Long = 0L,
    val viewType: ViewType = ViewType.GRID
)