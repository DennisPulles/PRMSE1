package nl.avans.movieapp.domain;

public class Trailer {

    private String key;
    private String name;
    private String site;

    public Trailer(String key, String name, String site) {
        this.key = key;
        this.name = name;
        this.site = site;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }
}
