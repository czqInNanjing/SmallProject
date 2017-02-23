package cn.service.impl;

import cn.dao.StudentScoreDAO;
import cn.entity.ScoreEntity;
import cn.service.StudentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Qiang
 * @since 06/01/2017
 */
@Service
public class StudentScoreServiceBean implements StudentScoreService {
    private final StudentScoreDAO dao;

    @Autowired
    public StudentScoreServiceBean(StudentScoreDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<ScoreEntity> getStudentsScores(int studentId) {
        return dao.getStudentsScores(studentId);
    }
}
