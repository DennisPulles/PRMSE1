package nl.avans.movieapp.service;

public class YoutubeKey {

    public YoutubeKey() {

    }

    private static final String YOUTUBE_API_KEY = "AIzaSyCF6G04m958QOHOW8BjLv4W-FmK_2TraWk";

    public static String getYoutubeApiKey() {
        return YOUTUBE_API_KEY;
    }

}
