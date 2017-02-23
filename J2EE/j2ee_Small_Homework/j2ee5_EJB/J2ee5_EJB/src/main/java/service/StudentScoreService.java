package service;

import entity.Score;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author Qiang
 * @since 06/01/2017
 */
@Remote
public interface StudentScoreService {

    List<Score> getStudentsScores(int studentId);

}
