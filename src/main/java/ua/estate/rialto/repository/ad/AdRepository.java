package ua.estate.rialto.repository.ad;

import ua.estate.rialto.model.Ad;

public interface AdRepository<T extends Ad>
{
    // null if not found
    T get(int id);

    T save(T ad);
}
