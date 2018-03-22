package ua.estate.rialto.repository.flat;

import org.springframework.stereotype.Repository;
import ua.estate.rialto.model.Flat;

@Repository
public class DataJpaFlatRepositoryImpl implements FlatRepository {
    private final CrudFlatRepository crudRepository;

    public DataJpaFlatRepositoryImpl(CrudFlatRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Flat get(int id) {
        return crudRepository.findOne(id);
    }

    @Override
    public Flat save(Flat agent) {
        if (!agent.isNew() && get(agent.getId()) == null) {
            return null;
        }
        return crudRepository.save(agent);
    }


}
