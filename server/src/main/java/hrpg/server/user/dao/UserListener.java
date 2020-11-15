package hrpg.server.user.dao;

import hrpg.server.common.dao.sequence.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class UserListener extends AbstractMongoEventListener<User> {

    private final SequenceGenerator sequenceGenerator;

    public UserListener(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        if (StringUtils.isBlank(event.getSource().getName())) {
            event.getSource().setName("User#" + sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
        }
    }
}
