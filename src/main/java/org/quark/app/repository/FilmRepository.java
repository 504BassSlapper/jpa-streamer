package org.quark.app.repository;


import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quark.app.model.EntityFilmManager;
import org.quark.app.model.Film;

import java.util.Optional;

@ApplicationScoped
public class FilmRepository {
    @Inject
    JPAStreamer jpaStreamer;

    public Optional<Film> getFilm(short filmId){
        return jpaStreamer.stream(Film.class)
                .filter(EntityFilmManager.filmId.equal(filmId))
                .findFirst();
    }
}
