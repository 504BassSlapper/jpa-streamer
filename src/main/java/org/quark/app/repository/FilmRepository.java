package org.quark.app.repository;


import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.quark.app.model.Actor;
import org.quark.app.model.Film$;
import org.quark.app.model.Film;

import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class FilmRepository {

    public static final int PAGE_SIZE = 12;

    @Inject
    JPAStreamer jpaStreamer;

    public Optional<Film> getFilm(short filmId) {
        return jpaStreamer.stream(Film.class)
                .filter(Film$.filmId.equal(filmId))
                .findFirst();
    }

    public Stream<Film> getFilms(long page, short minlength) {

        return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length))
                .filter(Film$.length.greaterOrEqual(minlength))
                .sorted(Film$.length)
                .skip(page * PAGE_SIZE)
                .limit(PAGE_SIZE);
    }

    public Stream<Film> getAllActorsFromFilm(String filmNameStartWith) {
        StreamConfiguration<Film> sc = StreamConfiguration.of(Film.class).joining(Film$.actors);
        return jpaStreamer.stream(sc)
                .filter(Film$.title.startsWith(filmNameStartWith))
                .sorted(Film$.length.reversed());
    }

    @Transactional
    public void updateFilmsRate(short minLength, Float rentalRate) {
        jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterOrEqual(minLength))
                .forEach(movie -> movie.setRentalRate(rentalRate));
    }



    public Stream<Film> getUpdatedFilms(short minLength){
        return jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterOrEqual(minLength))
                .sorted(Film$.length.reversed());
    }
}
