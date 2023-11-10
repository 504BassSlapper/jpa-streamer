package org.quark.app.org.quark.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.quark.app.model.Film;
import org.quark.app.repository.FilmRepository;

import java.util.Optional;

@Path("/")
public class FilmController {

    @Inject
    FilmRepository filmRepository;

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "hello world";
    }

    @GET
    @Path("films/{filmId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilm(short filmId) {
        Optional<Film> film = filmRepository.getFilm(filmId);
        return film.isPresent() ? film.get().getTitle() : "no film found";
    }
}
