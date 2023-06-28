package kr.co.mz.singlethread.repository;

import java.util.List;
import kr.co.mz.tutorial.httpserver.guide.model.CacheDTO;

public interface CacheDAO {

  List<CacheDTO> getCacheList();

  CacheDTO getCache();

  int putCache();

  int deleteCache();

  int updateCache();

}
