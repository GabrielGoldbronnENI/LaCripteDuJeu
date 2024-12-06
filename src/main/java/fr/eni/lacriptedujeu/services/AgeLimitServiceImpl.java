package fr.eni.lacriptedujeu.services;

import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.repositorys.AgeLimitRepository;
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

    public AgeLimit getById(int ageLimitID) {

        AgeLimit ageLimit = ageLimitRepository.getById(ageLimitID);
        if (ageLimit == null) {
            throw new NotFoundException("L'age limite avec l'ID " + ageLimitID + " est introuvable");
        }
        return ageLimit;
    }
}
