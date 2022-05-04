package com.example.demo.service;

import com.example.demo.model.DocType;
import com.example.demo.model.User;

import java.util.List;

public interface DocTypeService {

    DocType save(DocType docType);

    DocType update(DocType docType);

    void removeById(Integer id);

    List<DocType> getAll();

    DocType findById(Integer id);
}
