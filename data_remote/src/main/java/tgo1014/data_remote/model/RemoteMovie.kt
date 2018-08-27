package tgo1014.data_remote.model

import com.google.gson.annotations.SerializedName

data class RemoteMovie(
        @SerializedName("id") val id: Int?,
        @SerializedName("adult") val adult: Boolean?,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("budget") val budget: Int?,
        @SerializedName("remoteGenres") val remoteGenres: List<RemoteGenre>?,
        @SerializedName("homepage") val homepage: String?,
        @SerializedName("imdb_id") val imdbId: String?,
        @SerializedName("original_language") val originalLanguage: String?,
        @SerializedName("original_title") val originalTitle: String?,
        @SerializedName("overview") val overview: String?,
        @SerializedName("popularity") val popularity: Double?,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("production_companies") val productionCompanies: List<RemoteProductionCompany>?,
        @SerializedName("production_countries") val remoteProductionCountries: List<RemoteProductionCountry>?,
        @SerializedName("release_date") val releaseDate: String?,
        @SerializedName("revenue") val revenue: Int?,
        @SerializedName("runtime") val runtime: Int?,
        @SerializedName("spoken_languages") val remoteSpokenLanguages: List<RemoteSpokenLanguage>?,
        @SerializedName("status") val status: String?,
        @SerializedName("tagline") val tagline: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("video") val video: Boolean?,
        @SerializedName("vote_average") val voteAverage: Double?,
        @SerializedName("vote_count") val voteCount: Int?,
        var isFavorite: Boolean = false
) {

    data class RemoteProductionCompany(
            @SerializedName("id") val id: Int?,
            @SerializedName("logo_path") val logoPath: String?,
            @SerializedName("name") val name: String?,
            @SerializedName("origin_country") val originCountry: String?
    )


    data class RemoteProductionCountry(
            @SerializedName("iso_3166_1") val iso31661: String?,
            @SerializedName("name") val name: String?
    )


    data class RemoteGenre(
            @SerializedName("id") val id: Int?,
            @SerializedName("name") val name: String?
    )


    data class RemoteSpokenLanguage(
            @SerializedName("iso_639_1") val iso6391: String?,
            @SerializedName("name") val name: String?
    )
}