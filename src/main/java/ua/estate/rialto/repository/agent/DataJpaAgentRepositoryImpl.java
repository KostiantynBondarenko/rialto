package ua.estate.rialto.repository.agent;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ua.estate.rialto.model.Agent;

import java.util.List;

@Repository
public class DataJpaAgentRepositoryImpl implements AgentRepository {
    private static final Sort SORT_NAME_PHONE = new Sort("name", "phone");

    private final CrudAgentRepository crudRepository;

    public DataJpaAgentRepositoryImpl(CrudAgentRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Agent get(int id) {
        return crudRepository.findOne(id);
    }

    @Override
    public Agent save(Agent agent) {
        if (!agent.isNew() && get(agent.getId()) == null) {
            return null;
        }
        return crudRepository.save(agent);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public List<Agent> findByPhone(String phone){
        return crudRepository.findByPhone(phone);
    }

    @Override
    public List<Agent> findByPhoneOrAddPhone(String phone, String addPhone){
        return crudRepository.findByPhoneOrAddPhone(phone, addPhone);
    }

    @Override
    public List<Agent> getAll() {
        return crudRepository.findAll(SORT_NAME_PHONE);
    }
}
