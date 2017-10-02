package cn.edu.nju.service;

import java.util.List;

/**
 * @author Qiang
 * @since 10/07/2017
 */
public interface FileSearchService {

    public void setFilePath(String filePath);

    public List<String> search(String keys);



}
