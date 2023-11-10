package org.quark.app.org.quark.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.quark.app.model.Film;
import org.quark.app.org.quark.app.utils.Converter;
import org.quark.app.repository.FilmRepository;

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

    @GET
    @Path("actors/{filmNameStartWith}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getActors(@QueryParam("filmNameStartWith") @DefaultValue("") String filmNameStartWith) {

        return filmNameStartWith.isBlank() ? "No criteria to search film" : filmRepository.getAllActorsFromFilm(filmNameStartWith)
                .map(film -> String.format("%s (%d) %s"
                        , film.getTitle()
                        , film.getLength()
                        , film.getActors().stream()
                                .map(actor -> String.format("%s %s", actor.getFirstName(),
                                        actor.getLastName())).collect(Collectors.joining(" - "))
                )).collect(Collectors.joining("\n"));
    }

    @GET
    @Path("update/{minLength}/{rentalRate}")
    @Produces(MediaType.TEXT_PLAIN)
    public String updateRentalRate(short minLength, Float rentalRate) {
        filmRepository.updateFilmsRate(minLength, rentalRate);
        return filmRepository.getUpdatedFilms(minLength)
                .map(movie -> String.format("%s ( %d min ) %s ", movie.getTitle(),
                        movie.getLength(),
                        Converter.decimafloatToDecimal(movie.getRentalRate())))
                .collect(Collectors.joining("\n-"));
    }
}
