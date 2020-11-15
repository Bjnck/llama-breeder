package hrpg.server.common.dao.sequence;

public interface SequenceGenerator {
    long generateSequence(String seqName);
}
