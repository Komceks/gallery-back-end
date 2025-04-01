package lt.restservice.bl.repository.custom;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CustomTagRepository {
    Set<String> findByImageId(Long id);
    Map<Long, Set<String>> findByImageIds(List<Long> imageIds);
}
