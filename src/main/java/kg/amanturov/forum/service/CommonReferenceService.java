package kg.amanturov.forum.service;





import kg.amanturov.forum.model.CommonReference;
import kg.amanturov.forum.model.CommonReferenceType;

import java.util.List;

public interface CommonReferenceService {

    List<CommonReference> findAll();

    List<CommonReference> findAllByCode(String code);
    CommonReference findByTitle(String search);


    List<CommonReference> findAllByType(String code);
    List<CommonReference> findAllById(Long id);

    CommonReference findById(Long id);

    List<CommonReferenceType> findAllTyeps();

    CommonReferenceType findTypeByCode(String code);
    List<CommonReference> findParentIdById(Long id);

    CommonReference findByTypeIdAndCode(Long typeId, String code);


    List<CommonReference> findByParentIdAndType(Long parentId, Long typeId);
}
