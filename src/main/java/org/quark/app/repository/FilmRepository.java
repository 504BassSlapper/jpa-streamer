package org.quark.app.repository;


import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
                .filter(Film$.length.lessThan(minlength))
                .sorted(Film$.length)
                .skip(page * PAGE_SIZE)
                .limit(PAGE_SIZE);
    }

    public Stream<Film> getActors(String filmNameStartWith){
        StreamConfiguration<Film> sc = StreamConfiguration.of(Film.class).joining(Film$.actors);
        return  jpaStreamer.stream(sc)
                .filter(Film$.title.startsWith(filmNameStartWith))
                .sorted(Film$.length.reversed());
    }
}
