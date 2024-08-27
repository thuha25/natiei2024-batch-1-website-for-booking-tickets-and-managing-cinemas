package cinemas.controllers;

import cinemas.dtos.TheaterDto;
import cinemas.services.TheatersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/theaters")
public class TheatersController {
    @Autowired
    private TheatersService theatersService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TheaterDto> getTheaterWithScreens(@PathVariable("id") int id) {
        var theaterDto = theatersService.getTheaterDtoById(id);
        if (theaterDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(theaterDto, HttpStatus.OK);
    }
}
