package cn.service;

import cn.entity.ScoreEntity;

import java.util.List;

/**
 * @author Qiang
 * @since 06/01/2017
 */

public interface StudentScoreService {

    List<ScoreEntity> getStudentsScores(int studentId);

}
