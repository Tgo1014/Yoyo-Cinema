package tgo1014.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
        @PrimaryKey val id: Int? = null,
        val adult: Boolean? = null,
        val backdropPath: String? = null,
        val budget: Int? = null,
        val genres: List<Genre>? = arrayListOf(),
        val homepage: String? = null,
        val imdbId: String? = null,
        val originalLanguage: String? = null,
        val originalTitle: String? = null,
        val overview: String? = null,
        val popularity: Double? = null,
        val posterPath: String? = null,
        val productionCompanies: List<ProductionCompany>? = arrayListOf(),
        val productionCountries: List<ProductionCountry>? = arrayListOf(),
        val releaseDate: String? = null,
        val revenue: Int? = null,
        val runtime: Int? = null,
        val spokenLanguages: List<SpokenLanguage>? = arrayListOf(),
        val status: String? = null,
        val tagline: String? = null,
        val title: String? = null,
        val video: Boolean? = null,
        val voteAverage: Double? = null,
        val voteCount: Int? = null,
        val favorite: Boolean = false
) {

    data class ProductionCompany(
            val id: Int? = null,
            val logoPath: String? = null,
            val name: String? = null,
            val originCountry: String?
    )


    data class ProductionCountry(
            val iso31661: String? = null,
            val name: String? = null
    )


    data class Genre(
            val id: Int? = null,
            val name: String? = null
    )


    data class SpokenLanguage(
            val iso6391: String? = null,
            val name: String? = null
    )
}