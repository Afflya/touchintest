package com.afflyas.touchintest.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.sql.Date


@Entity(tableName = MovieEntity.TABLE_NAME)
class MovieEntity() : Parcelable{

    @PrimaryKey
    @SerializedName(COLUMN_ID)
    @ColumnInfo(name = COLUMN_ID)
    var id: Int? = null

    @SerializedName(COLUMN_TITLE)
    @ColumnInfo(name = COLUMN_TITLE)
    var title: String? = null

    @SerializedName(COLUMN_VOTE_AVERAGE)
    @ColumnInfo(name = COLUMN_VOTE_AVERAGE)
    var voteAverage: Float? = null

    @SerializedName(COLUMN_POPULARITY)
    @ColumnInfo(name = COLUMN_POPULARITY)
    var popularity: Float? = null

    @SerializedName(COLUMN_POSTER_PATH)
    @ColumnInfo(name = COLUMN_POSTER_PATH)
    var posterPath: String? = null

    @SerializedName(COLUMN_BACKDROP_PATH)
    @ColumnInfo(name = COLUMN_BACKDROP_PATH)
    var backdropPath: String? = null

    @SerializedName(COLUMN_ORIGINAL_LANGUAGE)
    @ColumnInfo(name = COLUMN_ORIGINAL_LANGUAGE)
    var originalLanguage: String? = null

    @SerializedName(COLUMN_OVERVIEW)
    @ColumnInfo(name = COLUMN_OVERVIEW)
    var overview: String? = null

    @SerializedName(COLUMN_RELEASE_DATE)
    @ColumnInfo(name = COLUMN_RELEASE_DATE)
    @TypeConverters(DateConverter::class)
    var releaseDate: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        title = parcel.readString()
        voteAverage = parcel.readValue(Float::class.java.classLoader) as? Float
        popularity = parcel.readValue(Float::class.java.classLoader) as? Float
        posterPath = parcel.readString()
        backdropPath = parcel.readString()
        originalLanguage = parcel.readString()
        overview = parcel.readString()
        releaseDate = DateConverter.fromTimestamp(parcel.readLong())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeValue(voteAverage)
        parcel.writeValue(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeString(originalLanguage)
        parcel.writeString(overview)
        parcel.writeLong(DateConverter.dateToTimestamp(releaseDate)!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieEntity> {
        override fun createFromParcel(parcel: Parcel): MovieEntity {
            return MovieEntity(parcel)
        }

        override fun newArray(size: Int): Array<MovieEntity?> {
            return arrayOfNulls(size)
        }

        const val TABLE_NAME = "movies"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_VOTE_AVERAGE = "vote_average"
        const val COLUMN_POPULARITY = "popularity"
        const val COLUMN_POSTER_PATH = "poster_path"
        const val COLUMN_BACKDROP_PATH = "backdrop_path"
        const val COLUMN_ORIGINAL_LANGUAGE = "original_language"
        const val COLUMN_OVERVIEW = "overview"
        const val COLUMN_RELEASE_DATE = "release_date"
    }

}