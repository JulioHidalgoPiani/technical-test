package com.example.demo.service.impl;

import com.example.demo.model.DocType;
import com.example.demo.repository.DocTypeRepository;
import com.example.demo.service.DocTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocTypeServiceImpl implements DocTypeService {

    @Autowired
    private DocTypeRepository docTypeRepository;

    @Override
    public DocType save(DocType docType) {
        return docTypeRepository.save(docType);
    }

    @Override
    public DocType update(DocType docType) {
        return docTypeRepository.save(docType);
    }

    @Override
    public void removeById(Integer id) {
        docTypeRepository.deleteById(id);
    }

    @Override
    public List<DocType> getAll() {
        return docTypeRepository.findAll();
    }

    @Override
    public DocType findById(Integer id) {
        return docTypeRepository.findById(id).orElse(new DocType());
    }
}
