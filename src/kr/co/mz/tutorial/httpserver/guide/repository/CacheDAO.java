package kr.co.mz.tutorial.httpserver.guide.repository;

import java.util.List;
import kr.co.mz.tutorial.httpserver.guide.model.CacheDTO;

public interface CacheDAO {

  List<CacheDTO> getCacheList();

  CacheDTO getCache();


}
