package ua.estate.rialto.repository.flat;

import ua.estate.rialto.model.Flat;

public interface FlatRepository {
    // null if not found
    Flat get(int id);

    Flat save(Flat agent);
}
