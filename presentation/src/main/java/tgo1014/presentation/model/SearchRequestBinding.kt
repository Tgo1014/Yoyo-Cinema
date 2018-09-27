package tgo1014.presentation.model

data class SearchRequestBinding(
        val page: Int?,
        val totalResults: Int?,
        val totalPages: Int?,
        val results: List<ResultBinding>?
) {
    data class ResultBinding(
            val voteCount: Int?,
            val id: Int?,
            val video: Boolean?,
            val voteAverage: Double?,
            val title: String?,
            val popularity: Double?,
            val posterPath: String?,
            val originalLanguage: String?,
            val originalTitle: String?,
            val genreIds: List<Int>?,
            val backdropPath: String?,
            val adult: Boolean?,
            val overview: String?,
            val releaseDate: String?,
            val favorite: Boolean = false
    )
}