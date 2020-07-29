package com.adhegde.itflex.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.adhegde.itflex.model.Projects;
import com.adhegde.itflex.services.SequenceGeneratorService;

@Component
public class AddProjectListener extends AbstractMongoEventListener<Projects> {


    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public AddProjectListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Projects> event) {
        event.getSource().setProjectId(sequenceGenerator.generateSequence(Projects.SEQUENCE_NAME));
    }

}
