package cinemas.dtos;

public class TheaterDto {
    private Integer id;
    private String name;
    private String location;
    public TheaterDto() {
    }

    public TheaterDto(Integer id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public TheaterDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getLocation(){return location;}
}
