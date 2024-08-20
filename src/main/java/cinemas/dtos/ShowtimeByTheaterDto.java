package cinemas.dtos;

import cinemas.models.Movie;
import cinemas.models.Screen;

import java.time.LocalTime;

public class ShowtimeByTheaterDto {
    private int id;
    private Movie movie;
    private Screen screen;
    private String time;

    public ShowtimeByTheaterDto(int id, Movie movie, Screen screen, String time) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public String getTime() {
        return time;
    }
}
