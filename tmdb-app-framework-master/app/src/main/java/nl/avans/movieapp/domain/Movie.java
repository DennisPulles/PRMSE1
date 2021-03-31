package nl.avans.movieapp.domain;

import nl.avans.movieapp.controller.MovieController;

public class Movie {

    // De attributen moeten overeen komen met de waarden zoals die in de JSON gebruikt worden.
    // Je kunt die attribuutname ook aanpassen, maar dat vraagt finetuning.

    private int id;
    private String poster_path;
    private Boolean adult;
    private String title;
    private String release_date;
    private String original_language;
    private String overview;
    private int[] genre_ids;

    public Movie(int id, String poster_path, Boolean adult, String title, String overview, String release_date, String original_language, int[] genre_ids) {
        this.id = id;
        this.poster_path = MovieController.BASE_POSTER_PATH_URL + poster_path;
        this.adult = adult;
        this.title = title;
        this.release_date = release_date;
        this.original_language = original_language;
        this.overview = overview;
        this.genre_ids = genre_ids;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", poster_path='" + poster_path + '\'' +
                ", adult=" + adult +
                ", title='" + title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", original_language='" + original_language + '\'' +
                ", overview='" + overview + '\'' +
                ", genre_ids='" + genre_ids + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return MovieController.BASE_POSTER_PATH_URL + poster_path;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOverview() {
        return overview;
    }

    public int[] getGenreIds() {
        return genre_ids;
    }

}
