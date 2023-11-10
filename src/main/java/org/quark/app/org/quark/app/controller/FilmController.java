package org.quark.app.org.quark.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.quark.app.model.Film;
import org.quark.app.repository.FilmRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GET
    @Path("films/{page}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilms(long page, short minLength) {
        return filmRepository.getFilms(page, minLength).map(s -> String.format("%s (%d min)", s.getTitle(), s.getLength()))
                .collect(Collectors.joining("\n"));
    }
}
