package com.mirays.services;

import com.mirays.entities.Stage;
import com.mirays.entities.StageName;
import com.mirays.repositories.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StageService {

    @Autowired
    private StageRepository stageRepository;

    public Stage save(StageName stageName, byte[] image) {
        return stageRepository.save(new Stage(stageName, image));
    }

    public Stage getInitialStage() {
        return save(StageName.APPROVED, new byte[0]);
    }
}
