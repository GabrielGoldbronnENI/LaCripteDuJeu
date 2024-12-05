package fr.eni.lacriptedujeu.services;

import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.repositorys.AgeLimitRepository;
import fr.eni.lacriptedujeu.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeLimitServiceImpl implements AgeLimitService {

    private final AgeLimitRepository ageLimitRepository;

    @Autowired
    public AgeLimitServiceImpl(AgeLimitRepository ageLimitRepository) {
        this.ageLimitRepository = ageLimitRepository;
    }


    public List<AgeLimit> getAll() {
        return ageLimitRepository.getAll();
    }
}
