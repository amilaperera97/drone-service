package com.musalasoft.droneservice.util.converter;


import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityConverter {
    private final Mapper mapper;

    public EntityConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    public <F, T> T fill(F source, T destination) {
        if (source == null || destination == null) return null;
        mapper.map(source, destination);
        return destination;
    }

    public <F, T> List<T> convert(List<F> fromList, final Class<T> toClass) {
        return fromList.stream().map(from -> convert(from, toClass)).collect(Collectors.toList());
    }

    public <F, T> T convert(F from, final Class<T> toClass) {
        if (from == null) return null;
        return mapper.map(from, toClass);
    }
}