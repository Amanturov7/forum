package kg.amanturov.forum.controller;


import kg.amanturov.forum.model.CommonReference;
import kg.amanturov.forum.model.CommonReferenceType;
import kg.amanturov.forum.service.CommonReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/common-reference")
public class CommonReferenceController {

    private final CommonReferenceService commonReferenceService;

    @Autowired
    public CommonReferenceController(CommonReferenceService commonReferenceService) {
        this.commonReferenceService = commonReferenceService;
    }

    @GetMapping("/all")
    public List<CommonReference> getAllCommonReferences() {
        return commonReferenceService.findAll();
    }

    @GetMapping("/by-type/{typeCode}")
    public List<CommonReference> getCommonReferencesByType(@PathVariable String typeCode) {
        return commonReferenceService.findAllByType(typeCode);
    }

    @GetMapping(value = "/parent/{id}")
    public List<CommonReference> findByParentId(@PathVariable Long id){
        return commonReferenceService.findParentIdById(id);
    }
    @GetMapping(value = "/{district}")
    public List<CommonReference> findById(@PathVariable String district) {
        return commonReferenceService.findAllByCode(district);
    }


    @GetMapping("/{id}")
    public CommonReference getCommonReferenceById(@PathVariable Long id) {
        return commonReferenceService.findById(id);
    }

    @GetMapping("/types")
    public List<CommonReferenceType> getAllReferenceTypes() {
        return commonReferenceService.findAllTyeps();
    }

}
