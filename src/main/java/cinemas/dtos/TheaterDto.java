package cinemas.dtos;

import java.util.List;

public class TheaterDto {
    private Integer id;
    private String name;
    private String location;
    private List<ScreenDto> screens;
    public TheaterDto() {
    }
    public TheaterDto(Integer id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public List<ScreenDto> getScreens() {
        return screens;
    }

    public void setScreens(List<ScreenDto> screens) {
        this.screens = screens;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
