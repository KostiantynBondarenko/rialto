package ua.estate.rialto.repository.agent;

import ua.estate.rialto.model.Agent;

import java.util.List;

public interface AgentRepository {
    Agent save(Agent agent);

    // false if not found
    boolean delete(int id);

    // null if not found
    Agent get(int id);

    List<Agent> findByPhone(String phone);

    List<Agent> findByPhoneOrAddPhone(String phone, String addPhone);

    List<Agent> getAll();
}
