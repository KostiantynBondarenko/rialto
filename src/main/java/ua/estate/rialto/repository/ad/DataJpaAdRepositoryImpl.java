package ua.estate.rialto.repository.ad;

import org.springframework.stereotype.Repository;
import ua.estate.rialto.model.Ad;

@Repository
public class DataJpaAdRepositoryImpl <T extends Ad> implements AdRepository {
    private final CrudAdRepository crudRepository;

    public DataJpaAdRepositoryImpl(CrudAdRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public T get(int id) {
        return (T) crudRepository.findOne(id);
    }

    @Override
    public T save(Ad ad) {
        if (!ad.isNew() && get(ad.getId()) == null) {
            return null;
        }
        return (T) crudRepository.save(ad);
    }
}
