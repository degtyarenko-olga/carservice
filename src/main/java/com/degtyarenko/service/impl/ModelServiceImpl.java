package com.degtyarenko.service.impl;

import com.degtyarenko.dto.ModelDto;
import com.degtyarenko.dto.ModelSaveDto;
import com.degtyarenko.entity.Model;
import com.degtyarenko.exeption.EntityIsUsedException;
import com.degtyarenko.exeption.EntityNotFoundException;
import com.degtyarenko.mappers.ModelMapper;
import com.degtyarenko.repository.ModelRepository;
import com.degtyarenko.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.degtyarenko.Constant.MODEL_ALREADY_EXIST;
import static com.degtyarenko.Constant.STRING;

/**
 * The type Model service.
 *
 * @author Degtyarenko Olga
 * @version 1.0
 * @since 2022-12-22
 */
@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    @Override
    public Model findById(Long id) {
        return modelRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(id));
    }

    @Override
    @Transactional
    public Model create(ModelSaveDto modelDto) {
        Model model = modelRepository.findByModelName(modelDto.getModelName());
        if (!Objects.isNull(model)) {
            throw new EntityIsUsedException(String.join(MODEL_ALREADY_EXIST, STRING, model.toString()));
        }
        Model newModel = modelMapper.toModel(modelDto);
        return modelRepository.save(newModel);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (modelRepository.findById(id).isPresent()) {
            modelRepository.deleteById(id);
        } else throw new EntityNotFoundException(id);
    }

    @Override
    @Transactional
    public Model update(ModelDto modelDto) {
        Model model = modelRepository.findByModelName(modelDto.getModelName());
        if (!Objects.isNull(model)) {
            throw new EntityIsUsedException(String.join(MODEL_ALREADY_EXIST, STRING, model.toString()));
        } else if (modelRepository.findById(modelDto.getId()).isPresent()) {
            Model newModel = modelMapper.toModel(modelDto);
            return modelRepository.save(newModel);
        } else throw new EntityNotFoundException(modelDto.getId());
    }

}
